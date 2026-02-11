# Shop E2E Test Specifications

This document provides an overview of all end-to-end test specifications for the Shop service.

## Overview

All E2E tests are written using Cypress and test the complete user workflow including authentication, shopping cart operations, and order management.

### Prerequisites

- Shop frontend and backend services must be running
- Auth0 authentication service must be accessible
- Test user credentials must be configured in `.env.local`
- Article catalog must contain the required test data

## Test Suites

### [basic.spec.ts](Testspecifications/basic.md)

**Suite Name:** Shop - Menubar Testsuite
**Focus:** Core user interactions and navigation

**Test Cases:**
- **#prb** - User logout flow
- **#ctn** - Navigate to orders page
- **#unn** - Add article to shopping cart
- **#pjz** - Complete order workflow (add to cart + checkout)
- **#izv** - Remove article from cart

**Coverage:**
- Authentication (Auth0 login/logout)
- Navigation menu interactions
- Shopping cart CRUD operations
- Order creation and confirmation
- Multi-article cart management

## Test Data Requirements

### Required Articles

All test suites require the following articles to be present in the catalog:

| Article Name | Article ID | Used In Tests |
|-------------|-----------|---------------|
| Samsung 980 PRO 1TB SSD | `f2b5c8a0-1d3e-4c5b-9f3e-7d6f8a2b1c3d` | #unn |
| Keychron K2 Mechanical Keyboard | `d7e9a1e0-1234-4c5b-9876-abcdef123456` | #pjz |
| LG 34WN80C-B UltraWide Monitor | `d4e5f6a7-b8c9-7d8e-2f3a-1b2c3d4e5f6a` | #izv |
| Sony WH-1000XM5 Headphones | `0f5e45d3-aaa3-4cde-a1b2-9e8f0d1a2b3c` | #izv |

### Test User Requirements

- Valid Auth0 test account with access to the shop application
- User must have permissions to browse articles, manage cart, and create orders

## Running Test Suites

### Run All Tests

```bash
npm run cy:all
```

### Run Specific Suite

```bash
npm run cy:spec
# Enter: basic
```

### Generate Report

```bash
npm run cy:createReport
```

## Test Identification

Each test case has a unique identifier (e.g., `#unn`, `#pjz`) for:
- Traceability in test reports
- Quick reference in bug reports
- Cross-referencing with requirements

## Adding New Test Specifications

When adding new test suites:

1. Create test specification in `Testspecifications/<suite-name>.md`
2. Document test cases with unique identifiers
3. List required test data and dependencies
4. Update this file with a link to the new specification
5. Ensure test data is available before running tests
