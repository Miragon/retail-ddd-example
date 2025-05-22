import cypress from "cypress";
import { exec } from "node:child_process";
import readline from "readline";

let localSpec = "";

async function waitForInput(query) {
    const rl = readline.createInterface({
        input: process.stdin,
        output: process.stdout
    });
    return new Promise(resolve => rl.question(query, ans => {
        rl.close();
        resolve(ans);
    }));
}

if (process.argv[2] === "spec") {
    localSpec = await waitForInput("Please enter a spec file name? ");
}

const createReport = "npx mochawesome-merge reports/*.json > e2e_report.json && npx marge e2e_report.json -o reports && bash -c \"mv reports/e2e_report.html reports/`date +%d.%m.%y-%H-%M`_e2e_report.html && rm -f e2e_report.json; rm -f reports/mocha*.json\"";
const cleanDownloads ="bash -c 'rm -f downloads/*'";

exec(cleanDownloads);
switch (process.argv[2]) {
    case "all":
        cypress.run({
            browser: "chrome",
            headless: true
        }).then(() => {
            exec(createReport);
        });
        break;
    case "spec":
        cypress.run({
            browser: "chrome",
            headless: true,
            spec: `e2e/web-client/${localSpec}.spec.ts`
        }).then(() => {
            exec(createReport);
        });
        break;
    default:
        cypress.open().then();
}