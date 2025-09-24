// NOTE: Do not import the support file anywhere. It would bring about strange results.
import "./commands";

Cypress.on("uncaught:exception", function (err) {
    console.error("Uncaught:Exception", err);
    return false;
});
