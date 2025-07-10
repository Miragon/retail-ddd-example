declare global {
    namespace Cypress {
        // noinspection JSUnusedGlobalSymbols
        interface Chainable {
            login(user?: string, pw?: string): Cypress.Chainable
        }
    }
}

export const ARTICLES = {
    ENTRY: "Test-Artikel"
}
export const API = {
    ARTICLES: "/api/articles"
}

const AUTH0 = {
    TOKEN: `https://${Cypress.env("auth0Domain")}/oauth/token`
}

Cypress.Commands.add("login", (username = Cypress.env("auth0Username"), password = Cypress.env("auth0Password")) => {
    cy.intercept("GET", API.ARTICLES, req => {
        req.continue(res => {
            expect(res.statusCode).to.eq(200);
        });
    }).as("apiArticles");
    Cypress.log({
        displayName: "AUTH0 LOGIN",
        message: [`🔐 Session | ${Cypress.spec.name}`]
    });
    // NOTE: the session ID has to be unique for each user login to avoid conflicts
    cy.session(
        `${username}-${Cypress.spec.name}`,
        async () => {
            cy.intercept("POST", AUTH0.TOKEN).as("apiToken");
            await Cypress.session.clearAllSavedSessions();
            cy.clearAllCookies();
            // App landing page redirects to Auth0.
            cy.visit("/");
            // Login to Auth0 and receive token.
            cy.origin(
                Cypress.env("auth0Domain"),
                {args: {username, password}},
                ({username, password}) => {
                    cy.contains("button[data-action-button-primary=true]", "Continue").as("submit");
                    cy.get("@submit").should("be.visible");
                    cy.get("input#username").as("un");
                    cy.get("@un").clear();
                    cy.get("@un")
                        .should("have.value", "");
                    cy.get("@un")
                        .type(username, {delay: 50});
                    cy.get("@un")
                        .should("have.value", username);
                    cy.get("input#password").as("pw");
                    cy.get("@pw").clear();
                    cy.get("@pw")
                        .should("have.value", "");
                    cy.get("@pw")
                        .type(password, {delay: 50, log: false});
                    cy.get("@pw")
                        .should("have.value", password);
                    cy.get("@submit").click();
                    // receive the token, next get redirected
                    cy.wait("@apiToken");
                    cy.wait("@apiArticles"); // TODO not verified yet
                }
            );
        }, // session created
        {
            validate: () => {
                cy.getCookie(`auth0.${Cypress.env("auth0ClientId")}.is.authenticated`)
                    .then($cookie => {
                        expect($cookie.value).eq("true");
                    });
                cy.getCookie(`_legacy_auth0.${Cypress.env("auth0ClientId")}.is.authenticated`)
                    .then($cookie => {
                        expect($cookie.value).eq("true");
                    });
            },
            cacheAcrossSpecs: false
        }); // blank page
    cy.get("[data-cy='cypress-logo']").should("be.visible"); // wait for blank page
    // TODO maybe reactivate this later
    // cy.visit(url);
    // cy.get("#root").should("be.visible");
    // cy.wait("@apiArticles");
});
