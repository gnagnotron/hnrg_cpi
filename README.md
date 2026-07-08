# HNRG CPI
Repository to store all CPI artifacts

- Extension_flow: flusso per estendere e creare la trasformazione da XML a HTML secondo Assosoftware
- Get_EDocument_SAP_To_SAP: flusso per acquisire l'eDocument da SAP e inoltrarlo verso sistemi SAP target

## Integrazione SAP SuccessFactors → Zucchetti (branch: zucchetti)

Flussi di integrazione per la sincronizzazione dei dati HR da SAP SuccessFactors (SFSF) verso il sistema Zucchetti.

| Artefatto | Descrizione |
|---|---|
| ZUCCHETTI_IT_001_EmployeeMD | Sincronizzazione dei dati anagrafici dei dipendenti (Employee Master Data) da SFSF verso Zucchetti |
| ZUCCHETTI_IT_002_Confirm_Data | Flusso di conferma dati da SFSF verso Zucchetti |
| ZUCCHETTI_IT_003_CostCenter | Sincronizzazione dei centri di costo da SFSF verso Zucchetti |
| ZUCCHETTI_IT_004_Position | Sincronizzazione delle posizioni organizzative da SFSF verso Zucchetti |

## Integrazione SAP S/4HANA Private ↔ Modula (branch: modula)

Flussi di integrazione tra SAP S/4HANA Private Cloud e Modula (sistema di gestione magazzino automatizzato). Contesto: S4 Private + Modula base.

| Artefatto | Descrizione |
|---|---|
| MOD01_S4H_MODULA_Tasks | Invio task/ordini da SAP S/4HANA verso Modula |
| MOD02_MODULA_S4H_Confirmations | Ricezione conferme di esecuzione da Modula verso SAP S/4HANA |