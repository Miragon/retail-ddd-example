import { exec } from "node:child_process";
import { promisify } from "node:util";

const execAsync = promisify(exec);

export async function createReport() {
    const RESULTS_DIR = "reports";
    const RAW_DIR = `${RESULTS_DIR}/raw`;
    const MERGED_JSON = `${RESULTS_DIR}/mochawesome-merged.json`;
    const HTML_DIR = `${RESULTS_DIR}/html`;

    try {
        // Merge all mochawesome JSON files
        await execAsync(`npx mochawesome-merge "${RAW_DIR}/*.json" --output "${MERGED_JSON}"`);

        // Generate HTML report
        await execAsync(`npx mochawesome-report-generator "${MERGED_JSON}" -f e2e_report -o "${HTML_DIR}"`);

        console.log("Report generated:");
        console.log(`  JSON: ${MERGED_JSON}`);
        console.log(`  HTML: ${HTML_DIR}`);
    } catch (error) {
        console.error("Error generating report:", error);
        throw error;
    }
}
