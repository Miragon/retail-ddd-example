import {DATA_TESTID} from "../support/commands";
import {PAGE} from "../support/commands";

const {
    AUTH0,
    SHOP_ARTICLES,
    SHOP_CART,
    SHOP_MENU,
    SHOP_ORDERS
} = DATA_TESTID

const {ARTICLES} = PAGE;

const ARTICLE_ID = "f2b5c8a0-1d3e-4c5b-9f3e-7d6f8a2b1c3d";

describe("Shop - Menubar Testsuite", (): void => {
    before(() => {
        //
    });

    beforeEach(() => {
        cy.login();
        cy.visit(ARTICLES);
        cy.get(`[data-testid^="${SHOP_ARTICLES.CARD}"]`).should("have.length.greaterThan", 10);
    });

    it.skip("Navigate to orders (#ctn)", () => {
        cy.get(SHOP_MENU.ORDERS).click();
        cy.get(`[data-testid="${SHOP_ORDERS.EMPTY.BUTTON_CONTINUE_SHOPPING}"]`).should("be.visible");
    });
    it("Add article to cart (#unn)", () => {
        cy.get(`[data-testid="${SHOP_ARTICLES.ADD_TO_CART(ARTICLE_ID)}"]`).click();
        cy.get(SHOP_MENU.CART).click();
        cy.get(`[data-testid="${SHOP_CART.ICON_BUTTON}-${ARTICLE_ID}"]`).should("be.visible");
    });
    it("Logout (#prb)", () => {
        cy.get(SHOP_MENU.LOGOUT).click();
        cy.get(AUTH0.USERNAME).should("be.visible");
    });
})
