// NOTE: Do not import the support file anywhere. It would bring about strange results.
import "./commands";

// NOTE: do not fail the test due to an uncaught exception within the application.
Cypress.on("uncaught:exception", function (err, runnable) {
    console.error("error", err);
    console.info("runnable", runnable);
    return false;
});
