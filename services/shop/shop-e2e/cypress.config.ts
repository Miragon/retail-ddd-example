import {defineConfig} from "cypress";

console.info('CYPRESS_BASE_URL:', process.env.CYPRESS_BASE_URL);
console.info('AUTH0_DOMAIN:', process.env.AUTH0_DOMAIN);
console.info('AUTH0_USERNAME:', process.env.AUTH0_USERNAME);

export default defineConfig({
    port: 8101,
    viewportWidth: 1920,
    viewportHeight: 1080,
    defaultCommandTimeout: 5000,
    downloadsFolder: "downloads",
    fixturesFolder: "fixtures",
    videosFolder: "videos",
    screenshotsFolder: "screenshots",
    trashAssetsBeforeRuns: true,

    component: {
        devServer: {
            framework: "react",
            bundler: "vite"
        },
        specPattern: "e2e/**/*.spec.{js,jsx,ts,tsx}",
        supportFile: false
    },
    e2e: {
        //baseUrl: process.env.CYPRESS_BASE_URL,
        numTestsKeptInMemory: 5,
        pageLoadTimeout: 60000,
        experimentalStudio: true,
        specPattern: "e2e/**/*.spec.{js,jsx,ts,tsx}",
        supportFile: "support/e2e.ts",
        testIsolation: true,
        chromeWebSecurity: false,
        scrollBehavior: "top",
        reporter: "mochawesome",
        video: false,
        reporterOptions: {
            consoleReporter: "spec",
            quiet: false,
            reportDir: "reports",
            overwrite: false,
            html: false,
            json: true,
        },
        retries: {
            openMode: 0,
            runMode: 1,
        },
        env: {
            auth0Domain: process.env.AUTH0_DOMAIN,
            auth0Username: process.env.AUTH0_USERNAME,
            auth0Password: process.env.AUTH0_PASSWORD,
            auth0ClientId: process.env.AUTH0_CLIENT_ID,
            baseUrl: process.env.CYPRESS_BASE_URL
        },
        //
        setupNodeEvents: (on, config) => {
            const CONFIG = config;
            on("before:browser:launch", (browser, launchOptions) => {
                switch (browser.family) {
                    case "chromium":
                        launchOptions.args.push("--window-size=1920,1080");
                        launchOptions.args.push("--auto-open-devtools-for-tabs");
                        break;
                    case "firefox":
                        launchOptions.args.push("--window-size=1920,1080");
                        launchOptions.args.push("-devtools");
                        break;
                    default:
                }
                return launchOptions;
            });
            on("task", {
                log(msg: string) {
                    console.log(msg);
                    return null;
                }
            });
            return CONFIG;
        }
    },
});
