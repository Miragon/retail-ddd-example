import cypress from "cypress";
import readline from "readline";
import {dirname, resolve} from "node:path";
import {fileURLToPath} from 'url';
import {createReport} from "./createReport.mjs";
import fs from "fs-extra";

// Constants
const __filename = fileURLToPath(import.meta.url);
const __dirname = dirname(__filename);
const screenshotsPath = resolve(__dirname, `../screenshots`);
const reportsPath = resolve(__dirname, `../reports`);
const downloadsPath = resolve(__dirname, `../downloads`);
const CYPRESS_FOLDER = ".";

/**
 * Prompts the user for input via the terminal
 * @param {string} query - The question to ask the user
 * @returns {Promise<string>} The user's response
 */
async function waitForInput(query) {
    const rl = readline.createInterface({
        input: process.stdin,
        output: process.stdout
    });

    return new Promise(resolve => rl.question(query, answer => {
        rl.close();
        resolve(answer);
    }));
}

/**
 * Cleans up directories by removing and recreating them
 * @param {string[]} paths - Array of directory paths to clean
 */
function setupDirectories(paths) {
    paths.forEach(path => {
        try {
            if (fs.existsSync(path)) {
                fs.rmSync(path, {recursive: true, force: true});
                console.log(`✓ Cleaned up: ${path}`);
            }
            fs.mkdirSync(path, {recursive: true});
            console.log(`✓ Created: ${path}`);
        } catch (error) {
            console.error(`✗ Error setting up ${path}:`, error.message);
        }
    });
}

/**
 * Runs Cypress with the specified configuration
 * @param {string} specSet - Collection of testfiles to run
 * @param {boolean} dashboard - Use cypress dashboard true/false
 * @returns {Promise} Cypress run promise
 */
async function runCypress(dashboard = false, specSet = "") {
    let specs = [specSet]
    if (!process.env.CYPRESS_BASE_URL) {
        throw new Error('CYPRESS_BASE_URL environment variable is not set');
    }

    if (!process.env.CYPRESS_DASHBOARD_KEY && dashboard === true) {
        throw new Error('CYPRESS_DASHBOARD_KEY missing');
    }

    const defaultConfig = {
        baseUrl: process.env.CYPRESS_BASE_URL,
        configFile: `${CYPRESS_FOLDER}/cypress.config.ts`,
        browser: "chrome",
        headless: true
    };

    if (specSet === "") {
        specs = [];
    }

    if (specSet.includes(",")) {
        specs = specSet.split(",");
    }

    if (specs.length > 0) {
        defaultConfig.spec = specs.map(s => `e2e/${s}.spec.ts`)
    }

    if (dashboard) {
        defaultConfig.record = true;
        defaultConfig.key = process.env.CYPRESS_DASHBOARD_KEY;
    }

    try {
        const results = await cypress.run(defaultConfig);
        // Handle completion based on results
        if (results.totalFailed > 0) {
            console.error(`✗ Tests failed: ${results.totalFailed} / Passed: ${results.totalPassed}`);
            throw new Error(`${results.totalFailed} test(s) failed`);
        }

        console.log(`✓ Tests passed: ${results.totalPassed}`);
        return results;
    } catch (error) {
        console.error('Cypress run failed:', error.message);
        throw error;
    }
}

/**
 * Opens Cypress in interactive mode
 * @returns {Promise} Cypress open promise
 */
async function openCypress() {
    if (!process.env.CYPRESS_BASE_URL) {
        throw new Error('CYPRESS_BASE_URL environment variable is not set');
    }

    try {
        return await cypress.open({
            //baseUrl: process.env.CYPRESS_BASE_URL,
            configFile: `${CYPRESS_FOLDER}/cypress.config.ts`
        });
    } catch (error) {
        console.error('Failed to open Cypress:', error.message);
        throw error;
    }
}

// Main execution flow
(async () => {
    const command = process.argv[2];
    const useDashboard = process.argv[4] === "dashboard";
    let localSpec = "";

// Clean up directories unless creating a report
    if (command !== "createReport") {
        setupDirectories([screenshotsPath, reportsPath, downloadsPath]);
    }

// Handle command-line arguments
    if (command === "spec") {
        localSpec = await waitForInput("Please enter a spec name (e.g. basic): ");
    }

    // Execute commands
    switch (command) {
        case "createReport":
            await createReport();
            break;

        case "all": {
            await runCypress();
            break;
        }

        case "spec":
            if (!localSpec) {
                console.error('No spec name provided');
                process.exit(1);
            }
            await runCypress(useDashboard, localSpec);
            break;

        default:
            await openCypress();
    }
})().catch(error => {
    console.error('Fatal error:', error);
    process.exit(1);
});
