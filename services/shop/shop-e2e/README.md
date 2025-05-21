Automatic testing for the shop.

## Installation
- To run cypress copy the files ``env.local`` into the cypress folder.
The file exists in Onepassword Documents: Fullstack-Example-Configs

- Run yarn install in the folder ``/shop-e2e`` to install all dependencies.

## Cypress configurations
You will find in the file ``cypress.config.ts``.

## Run Cypress
Use the corresponding start scripts from package.json in /shop-e2e.

<span style="color:orange">Before running a testsuite, make sure that the corresponding testdata (/shop-e2e/fixtures/testdata/\<suite>.sql) are loaded!</span>

## Test IDs
With the help of a testid we identify an element in the Web-Client page.
A testid is a unique identifier and at the end a testid is an attribute of a 
html-element.
