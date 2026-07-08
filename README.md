# HNRG CPI
Repository to store all CPI artifacts

- Extension_flow: flusso per estendere e creare la trasformazione da XML a HTML secondo Assosoftware
- Get_EDocument_SAP_To_SAP: flusso per acquisire l'eDocument da SAP e inoltrarlo verso sistemi SAP target

## Integrazione SAP ↔ Oracle Agile PLM (branch: oracle-agile-plm)

Flussi di integrazione tra SAP e Oracle Agile PLM (Product Lifecycle Management).

| Artefatto | Descrizione |
|---|---|
| IF_001_JMS_SCPI_To_PS4 | Retrieve from JMS queue to SAP S4H PS4 |
| IF_001_VariousData_OracleAgileSFTP_To_JMSQueue | Retrieve from Oracle Agile SFTP to ZAGILE SAP |
| IF_002_SAP_to_Agile | ZIDOCFILE from SAP to webservice Oracle Agile |
| IF03_SendBuyer_SAP_to_Agile | Invio dati buyer da SAP verso Oracle Agile PLM |