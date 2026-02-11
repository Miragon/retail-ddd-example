# Shop E2E Tests

End-to-end tests for the Shop service using Cypress with Auth0 authentication and Mochawesome reporting.

## Prerequisites

- **Shop Frontend**: Running on `http://localhost:5173` (default)
- **Shop Backend**: Running and accessible
- **Node.js**: Version compatible with the project
- **Environment Configuration**: `.env.local` file (see below)

## Setup

### 1. Install Dependencies

```bash
npm install
```

### 2. Configure Environment

Create a `.env.local` file in the `services/shop/shop-e2e` directory with the following variables:

```env
CYPRESS_BASE_URL=http://localhost:5173
AUTH0_DOMAIN=your-auth0-domain.auth0.com
AUTH0_USERNAME=test@example.com
AUTH0_PASSWORD=your-password
AUTH0_CLIENT_ID=your-client-id
```

> **Note:** You can find the credentials in OnePassword under `Fullstack-Example-Configs`.

## Running Tests

### Interactive Mode (with Cypress UI)

Open the Cypress Test Runner for debugging and development:

```bash
npm run cy
```

**Note:** Interactive mode does NOT generate Mochawesome reports.

### Headless Mode (with reporting)

Run all test specs in headless mode:

```bash
npm run cy:all
```

Run a specific test spec:

```bash
npm run cy:spec
# Enter spec name when prompted (e.g., "basic")
```

### Generate HTML Report

After running tests in headless mode, generate a consolidated HTML report:

```bash
npm run cy:createReport
```

Reports are generated in:
- **Raw JSON**: `reports/raw/*.json`
- **Merged JSON**: `reports/mochawesome-merged.json`
- **HTML Report**: `reports/html/e2e_report.html`

## Configuration

Test configuration is in `cypress.config.ts`:

- **Browser**: Chrome (headless mode)
- **Viewport**: 1920x1080
- **Test Pattern**: `e2e/**/*.spec.{js,jsx,ts,tsx}`
- **Reporter**: Mochawesome (JSON output for headless runs)
- **Retries**: 1 retry in run mode, 0 in open mode

## Writing Tests

### Test Structure

```typescript
import { DATA_TESTID, PAGE } from "../support/commands";

const { SHOP_ARTICLES, SHOP_MENU, SHOP_CART } = DATA_TESTID;
const { ARTICLES } = PAGE;

describe("Shop - Test Suite", function () {
    beforeEach(function () {
        cy.login(); // Custom command for Auth0 login
        cy.visit(ARTICLES);
        cy.get(`[data-testid^="${SHOP_ARTICLES.CARD}"]`)
          .should("have.length.greaterThan", 10);
    });

    it("Add article to cart", function () {
        cy.get(`[data-testid="${SHOP_ARTICLES.ADD_TO_CART(SHOP_ARTICLES.ITEMS.SAMSUNG.ID)}"]`)
          .click();
        cy.get(SHOP_MENU.CART).click();
        cy.get(`[data-testid="${SHOP_CART.LIST}"]`)
          .should("contain.text", SHOP_ARTICLES.ITEMS.SAMSUNG.DESCRIPTION);
    });
});
```

### Custom Commands

**Login Command** (`cy.login()`)

Handles Auth0 authentication flow and session management:

```typescript
cy.login(); // Uses default credentials from .env.local
cy.login("user@example.com", "password"); // Custom credentials
```

### Test Data IDs

All test selectors are centralized in `support/commands.ts` under the `DATA_TESTID` constant:

- `DATA_TESTID.AUTH0.*` - Auth0 login selectors
- `DATA_TESTID.SHOP_MENU.*` - Navigation menu elements
- `DATA_TESTID.SHOP_ARTICLES.*` - Article list and cards
- `DATA_TESTID.SHOP_CART.*` - Shopping cart elements
- `DATA_TESTID.SHOP_ORDERS.*` - Order management elements

### Page Routes

Page paths are defined in `support/commands.ts` under the `PAGE` constant:

- `PAGE.ARTICLES` - Article listing page
- `PAGE.ORDERS` - Orders overview page
- `PAGE.CART` - Shopping cart page

## Test Workflow

Each test typically follows this pattern:

1. **Login**: Authenticate via Auth0
2. **Navigate**: Visit the target page
3. **Interact**: Perform actions (click, type, select)
4. **Assert**: Verify expected outcomes

## Troubleshooting

### Tests not generating reports

Ensure you're running tests in headless mode using `npm run cy:all` or `npm run cy:spec`. Interactive mode (`npm run cy`) does not generate Mochawesome reports.

### Auth0 authentication failures

- Verify credentials in `.env.local`
- Check that `AUTH0_DOMAIN` matches your Auth0 tenant
- Ensure the Auth0 application is properly configured

### Frontend not accessible

Confirm the shop-frontend is running on the URL specified in `CYPRESS_BASE_URL`.
