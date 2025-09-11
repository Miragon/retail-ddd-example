declare global {
    namespace Cypress {
        // noinspection JSUnusedGlobalSymbols
        interface Chainable {
            login(user?: string, pw?: string): Cypress.Chainable
        }
    }
}

export const DATA_TESTID = Object.freeze({
    AUTH0: {
        USERNAME: "input#username",
        PASSWORD: "input#password",
        SUBMIT: "button[data-action-button-primary=true]:contains(Continue)"
    },
    SHOP_MENU: {
        ORDERS: "button[title='Orders']",
        CART: "button[title='Cart']",
        LOGOUT: "button:contains(Logout)",
    },
    SHOP_ARTICLES: {
        CARD: "ArticleCard",
        ADD_TO_CART: (id: string): string => {
            return `ArticleCard-${id}-Button`
        }
    },
    SHOP_ORDERS: {
        EMPTY: {
            BUTTON_CONTINUE_SHOPPING: "Orders-Overview-Empty-Button-ContinueShopping",
            ROOT: "Orders-Overview-Empty"
        },
        BUTTON_CONTINUE_SHOPPING: "Orders-Overview-Button-ContinueShopping",
        BUTTON_VIEW_ORDER_PREFIX: "Orders-Overview-Button-ViewOrder",
        ROOT: "Orders-Overview"
    },
    SHOP_CART: {
        EMPTY: {
            BUTTON_CONTINUE_SHOPPING: "Cart-Empty-Button-ContinueShopping"
        },
        ICON_BUTTON: "Cart-IconButton",
        BUTTON_CONTINUE_SHOPPING: "Cart-Button-ContinueShopping",
        BUTTON_COMPLETE_ORDER: "Cart-Button-CompleteOrder",
    }
});
export const API = Object.freeze({
    ARTICLES: "/api/articles",
    ORDERS: "/api/orders"
});
export const PAGE = Object.freeze({
    ARTICLES: "/articles"
});

const TOKEN = `https://${Cypress.env("auth0Domain")}/oauth/token`;

Cypress.Commands.add("login", (username = Cypress.env("auth0Username"), password = Cypress.env("auth0Password")) => {
    Cypress.log({
        displayName: "AUTH0 LOGIN",
        message: [`🔐 Session | ${Cypress.spec.name}`]
    });
    // NOTE: the session ID has to be unique for each user login to avoid conflicts
    cy.session(
        `${username}-${Cypress.spec.name}`,
        () => {
            const {AUTH0} = DATA_TESTID;
            cy.intercept("POST", TOKEN).as("token");
            cy.clearAllSessionStorage();
            cy.clearAllCookies();
            cy.clearAllLocalStorage();
            // App landing page redirects to Auth0.
            cy.visit("/")
            cy.get(AUTH0.USERNAME).should("be.visible");
            // Login to Auth0 and receive the token.
            cy.origin(
                Cypress.env("auth0Domain"),
                {args: {username, password, AUTH0}},
                ({username, password, AUTH0}) => {
                    cy.get(AUTH0.SUBMIT).as("submit");
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
                    // wait for the token, then get redirected
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
    cy.get("[data-cy='cypress-logo']").should("be.visible"); // wait for the blank page
});
