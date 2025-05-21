declare global {
    namespace Cypress {
        // noinspection JSUnusedGlobalSymbols
        interface Chainable {
            login(url: string, user?: string, pw?: string): Cypress.Chainable
        }
    }
}

export const PLANNING = {
    TID: {
        VIEW_BAR: {
            DATE: "PlanningPage-ViewBar-DatePicker-Container",
        }
    },
    DATA: {
        //
    }
}
export const AUTH0 = {
    TOKEN: `https://${Cypress.env("auth0Domain")}/oauth/token`
}
export const API = {
    JOB: {
        ROOT: "api/job",
        FULL: "/api/job/full",
        ANY: "/api/job/**",
        TASK: "/api/job/task/**",
        ARCHIVE: "/api/job/*/archive",
        WORKINGS: "/api/job/*/workings",
        EXECUTE: "/api/job/task/*/execute"
    },
    CUSTOMER: {
        ROOT: "/api/customer",
        ANY: "/api/customer/**"
    }
}
export const MSG = [
    "Ordner ist leer"
]

Cypress.Commands.add("login", (url: string, username: string = Cypress.env("auth0Username"), password: string = Cypress.env("auth0Password")) => {
    Cypress.log({
        displayName: "AUTH0 LOGIN",
        message: [`🔐 Session | ${Cypress.spec.name}`]
    });
    cy.session(
        `${username}-${Cypress.spec.name}`,
        () => {
            // cy.intercept("POST", AUTH0.TOKEN).as("apiToken");
            // App landing page redirects to Auth0.
            cy.visit(url);
            // Login to Auth0 and request token.
            cy.origin(
                Cypress.env("auth0Domain"),
                {args: {username, password}},
                ({username, password}) => {
                    cy.contains("button[data-action-button-primary=true]", "Continue").as("submit");
                    cy.get("@submit").should("be.visible");
                    cy.get("input#username").as("un");
                    cy.get("@un").clear();
                    cy.get("@un")
                        .should("have.value", "")
                        .type(username, {delay: 50});
                    cy.get("@un")
                        .should("have.value", username);
                    cy.get("input#password").as("pw");
                    cy.get("@pw").clear();
                    cy.get("@pw")
                        .should("have.value", "")
                        .type(password, {delay: 50, log: false});
                    cy.get("@pw")
                        .should("have.value", password);
                    cy.get("@submit").click();
                    // receive the token, get redirected
                    cy.wait("@apiToken");
                }
            );
        },
        {
            validate: () => {
                cy.getCookie(`auth0.${Cypress.env("clientId")}.is.authenticated`)
                    .should("not.eq", undefined);
                cy.getCookie(`_legacy_auth0.${Cypress.env("clientId")}.is.authenticated`)
                    .should("not.eq", undefined);
                cy.getAllLocalStorage().then((ls) => {
                    cy.wrap(ls[Cypress.config("baseUrl")]["token"]).should("not.eq", undefined);
                    // test backend
                    cy.request({
                        method: 'GET',
                        url: '/api/customer',
                        headers: {
                            Authorization: 'Bearer ' + ls[Cypress.config("baseUrl")].token
                        }
                    }).its("status").should("eq", 200);
                    // test frontend
                    cy.request({
                        method: 'GET',
                        url: '/#/jobs',
                        headers: {
                            Authorization: 'Bearer ' + ls[Cypress.config("baseUrl")].token
                        }
                    }).its("status").should("eq", 200);
                });
            },
            cacheAcrossSpecs: false
        }); // blank page
    cy.get("[data-cy='cypress-logo']").should("be.visible"); // wait for blank page
    cy.visit(url, {timeout: 46003});
    cy.get("body").should("not.contain.text", "ABC", {timeout: 30000});
    cy.get("body[data-cypress-el='true']").should("not.exist");
});
