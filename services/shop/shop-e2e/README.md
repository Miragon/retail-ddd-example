# Shop E2E Tests

## Installation
- To run cypress copy the file ``env.local`` into the shop-e2e folder.
The file you'll find in Onepassword's: Fullstack-Example-Configs

- Run yarn install in the folder ``/shop-e2e`` to install all dependencies.

## Cypress configurations
You will find in the file ``cypress.config.ts``.

## Run Cypress
- <span style="color:orange">Make sure shop-frontend and shop-backend are running!</span>
- Run yarn cy to start the cypress test runner.

<span style="color:orange">Before running a testsuite, make sure that the corresponding testdata (/shop-e2e/fixtures/testdata/\<suite>.sql) are loaded!</span>
