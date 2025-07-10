import {ARTICLES} from "../support/commands";

describe("Shop - Basic Testsuite", (): void => {
    before(() => {
        //
    });

    beforeEach(() => {
        cy.login();
        cy.visit("/"); // TODO check?
    });

    it("Contact: Create / Edit / Delete (#unn)", () => {
        cy.log(ARTICLES.ENTRY);
    });
})
