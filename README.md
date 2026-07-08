# HNRG CPI
Repository to store all CPI artifacts

- Extension_flow: flusso per estendere e creare la trasformazione da XML a HTML secondo Assosoftware
- Get_EDocument_SAP_To_SAP: flusso per acquisire l'eDocument da SAP e inoltrarlo verso sistemi SAP target

## Integrazione SAP ↔ Stockager (branch: stockager)

Flussi di integrazione tra SAP e il sistema Stockager (gestione magazzino automatizzato).

| Artefatto | Descrizione |
|---|---|
| IF_010_MaterialMasterData_SAP_to_Stockager | Sincronizzazione anagrafica materiali da SAP verso Stockager |
| IF_011_Carichi_SAP_to_Stockager | Invio carichi/ricevimenti da SAP verso Stockager |
| IF_013_PrelieviOrdineProduzione_SAP_to_Stockager | Invio ordini di prelievo produzione da SAP verso Stockager |
| IF_014_ConfermaPrelieviOrdineProduzione_Stockager_to_SAP | Ricezione conferme prelievi produzione da Stockager verso SAP |
| IF_015_Consumi_SAP_to_Stockager | Invio consumi da SAP verso Stockager |
| IF_016_MovimentiClassificaStorage_Stockager_to_SAP | Ricezione movimenti classificati da Stockager verso SAP |
| IF_017_Prelievi_SAP_to_Stockager | Invio ordini di prelievo da SAP verso Stockager |
| IF_018_ConfermaPrelieviSpedizioni_Stockager_to_SAP | Ricezione conferme prelievi spedizioni da Stockager verso SAP |
| IF_019_InvioVettore_SAP_to_Stockager | Invio dati vettorista da SAP verso Stockager |
| IF_020_UscitaMerci_Stockager_to_SAP | Ricezione uscita merci da Stockager verso SAP |
| IF_022_ConfermaPrelieviSpedizioni_StockK_Stockager_to_SAP | Ricezione conferme prelievi spedizioni StockK da Stockager verso SAP |
| IF21_Giacenze_Stockager_to_SAP | Sincronizzazione giacenze da Stockager verso SAP |
| IFXXX_CPI_to_IDOC_SAP | Flusso utility di conversione CPI a IDOC SAP |
| IFXXX_CPI_to_SFTPStockager | Flusso utility di export CPI verso SFTP Stockager |
| IFXXX_UtilityRouterIF014IF018IF020_SFTP_to_SCPI | Router utility SFTP verso CPI per IF014, IF018, IF020 |