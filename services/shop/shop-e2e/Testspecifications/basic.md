# e2e/menuBar.spec.ts

### beforeEach Hook:
- Anmelden u. eine Seite besuchen.

### Tests:

| Nummer            | #ctn                                           |
|-------------------|------------------------------------------------|
| Test Aktion       | Navigiere zu den Bestellungen .                |
| Test Verifikation | Prüfen ob die Bestellungen Seite sichtbar ist? |

| Nummer            | #unn                                             |
|-------------------|--------------------------------------------------|
| Test Aktion       | Artikel in den Warenkorb.                        |
| Test Verifikation | Prüfen ob der Artikel im Warenkorb sichtbar ist? |

| Nummer            | #pjz                                                 |
|-------------------|------------------------------------------------------|
| Test Aktion       | Artikel in den Warenkorb u. Bestellung abschliessen. |
| Test Verifikation | Prüfen ob die Bestellung ausgeführt wurde?           |

| Nummer            | #prb                                       |
|-------------------|--------------------------------------------|
| Test Aktion       | Abmelden.                                  |
| Test Verifikation | Prüfen ob die Auth0 Seite angezeigt wird?  |