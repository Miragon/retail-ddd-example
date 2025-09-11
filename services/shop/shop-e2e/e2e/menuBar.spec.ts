import {TESTID} from "../support/commands";
import {PAGES} from "../support/commands";

describe("Shop - Menubar Testsuite", (): void => {
    before(() => {
        //
    });

    beforeEach(() => {
        cy.login();
        cy.visit(PAGES.ARTICLES);
        cy.get(`[data-testid^="${TESTID.ARTICLES.ARTICLE_CARD_PREFIX}"]`).should("have.length.greaterThan", 10);
    });

    it("Navigate to orders (#ctn)", () => {
        cy.get(TESTID.MENU.ORDERS).click();
        cy.get(`[data-testid="${TESTID.ORDERS.EMPTY.BUTTON_CONTINUE_SHOPPING}"]`).should("be.visible");
    });
    it("Navigate to cart (#unn)", () => {
        cy.get(TESTID.MENU.CART).click();
        cy.get(`[data-testid="${TESTID.CART.EMPTY.BUTTON_CONTINUE_SHOPPING}"]`).should("be.visible");
    });
    it("Logout (#prb)", () => {
        cy.get(TESTID.MENU.LOGOUT).click();
        cy.get(TESTID.AUTH0.USERNAME).should("be.visible");
    });
})
