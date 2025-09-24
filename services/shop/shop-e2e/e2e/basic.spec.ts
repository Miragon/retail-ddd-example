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

describe("Shop - Menubar Testsuite", function (): void {
    before(function () {
        //
    });

    beforeEach(function ()  {
        cy.login();
        cy.visit(ARTICLES);
        cy.get(`[data-testid^="${SHOP_ARTICLES.CARD}"]`).should("have.length.greaterThan", 10);
    });

    it.skip("Navigate to orders (#ctn)", function (){
        cy.get(SHOP_MENU.ORDERS).click();
        cy.get(`[data-testid="${SHOP_ORDERS.EMPTY.BUTTON_CONTINUE_SHOPPING}"]`).should("be.visible");
    });
    it("Add article to cart (#unn)", function () {
        cy.get(`[data-testid="${SHOP_ARTICLES.ADD_TO_CART(SHOP_ARTICLES.ITEMS.SAMSUNG.ID)}"]`).click();
        cy.get(SHOP_MENU.CART).click();
        cy.get(`[data-testid="${SHOP_CART.LIST}"]`).should("contain.text", SHOP_ARTICLES.ITEMS.SAMSUNG.DESCRIPTION);
    })
    it("Complete order (#pjz)", function () {
        cy.get(`[data-testid="${SHOP_ARTICLES.ADD_TO_CART(SHOP_ARTICLES.ITEMS.KEYCHRON.ID)}"]`).click();
        cy.get(SHOP_MENU.CART).click();
        cy.get(`[data-testid="${SHOP_CART.LIST}"]`).should("contain.text", SHOP_ARTICLES.ITEMS.KEYCHRON.DESCRIPTION);
        cy.get(`[data-testid="${SHOP_CART.BUTTON_COMPLETE_ORDER}"]`).click();
        cy.get(`[data-testid="${SHOP_ORDERS.DETAILS.ITEMS}"]`).should("contain.text", SHOP_ARTICLES.ITEMS.KEYCHRON.DESCRIPTION);
    });
    it.skip("Logout (#prb)", function () {
        cy.get(SHOP_MENU.LOGOUT).click();
        cy.get(AUTH0.USERNAME).should("be.visible");
    });
})
