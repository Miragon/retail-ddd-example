declare global {
    namespace Cypress {
        // noinspection JSUnusedGlobalSymbols
        interface Chainable {
            login(user?: string, pw?: string): Cypress.Chainable
        }
    }
}

export const TESTID = {
    AUTH0: {
       USERNAME: "input#username",
       PASSWORD: "input#password",
       SUBMIT: "button[data-action-button-primary=true]:contains(Continue)"
    },
    MENU: {
        ORDERS: "button[title='Orders']",
        CART: "button[title='Cart']",
        LOGOUT: "button:contains(Logout)",
    },
    ARTICLES: {
        ARTICLE_CARD_PREFIX: "ArticleCard",
    },
    ORDERS: {
        EMPTY: {
            BUTTON_CONTINUE_SHOPPING: "Orders-Overview-Empty-Button-ContinueShopping",
            ROOT: "Orders-Overview-Empty"
        },
        BUTTON_CONTINUE_SHOPPING: "Orders-Overview-Button-ContinueShopping",
        BUTTON_VIEW_ORDER_PREFIX: "Orders-Overview-Button-ViewOrder",
        ROOT: "Orders-Overview"
    },
    CART: {
        EMPTY : {
            BUTTON_CONTINUE_SHOPPING: "Cart-Empty-Button-ContinueShopping"
        },
        CART_ICON_BUTTON_PREFIX: "Cart-IconButton",
        CART_BUTTON_CONTINUE_SHOPPING: "Cart-Button-ContinueShopping",
        CART_BUTTON_COMPLETE_ORDER: "Cart-Button-CompleteOrder",
    }
}
export const API = {
    ARTICLES: "/api/articles",
    ORDERS: "/api/orders"
}
export const PAGES = {
    TOKEN: `https://${Cypress.env("auth0Domain")}/oauth/token`,
    ARTICLES: "/articles"
}

Cypress.Commands.add("login", (username = Cypress.env("auth0Username"), password = Cypress.env("auth0Password")) => {
    Cypress.log({
        displayName: "AUTH0 LOGIN",
        message: [`🔐 Session | ${Cypress.spec.name}`]
    });
    // NOTE: the session ID has to be unique for each user login to avoid conflicts
    cy.session(
        `${username}-${Cypress.spec.name}`,
        () => {
            const AUTH0 = TESTID.AUTH0;
            cy.intercept("POST", PAGES.TOKEN).as("token");
            cy.clearAllSessionStorage();
            cy.clearAllCookies();
            cy.clearAllLocalStorage();
            // App landing page redirects to Auth0.
            cy.visit("/")
            cy.get(AUTH0.USERNAME).should("be.visible");
            // Login to Auth0 and receive token.
            cy.origin(
                Cypress.env("auth0Domain"),
                {args: {username, password, AUTH0}},
                ({username, password, AUTH0}) => {
                    cy.get(AUTH0.SUBMIT).as("submit");
                    //cy.contains("button[data-action-button-primary=true]", "Continue").as("submit");
                    cy.get("@submit").should("be.visible");
                    cy.get(AUTH0.USERNAME).as("un");
                    cy.get("@un").clear();
                    cy.get("@un")
                        .should("have.value", "");
                    cy.get("@un")
                        .type(username, {delay: 50});
                    cy.get("@un")
                        .should("have.value", username);
                    cy.get(AUTH0.PASSWORD).as("pw");
                    cy.get("@pw").clear();
                    cy.get("@pw")
                        .should("have.value", "");
                    cy.get("@pw")
                        .type(password, {delay: 50, log: false});
                    cy.get("@pw")
                        .should("have.value", password);
                    cy.get("@submit").click();
                    // receive the token, next get redirected
                    cy.wait("@token");
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
});
