<?xml version="1.0" encoding="UTF-16"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" 
	xmlns:msxsl="urn:schemas-microsoft-com:xslt" 
	xmlns:var="http://schemas.microsoft.com/BizTalk/2003/var" exclude-result-prefixes="msxsl var s1 s0 s2 userCSharp" version="1.0" 
	xmlns:s1="http://Microsoft.LobServices.Sap/2007/03/Idoc/3/ZIDOC_STOCKAGER//740/Receive" 
	xmlns:s0="http://Microsoft.LobServices.Sap/2007/03/Types/Idoc/3/ZIDOC_STOCKAGER//740" 
	xmlns:s2="http://Microsoft.LobServices.Sap/2007/03/Types/Idoc/Common/" 
	xmlns:userCSharp="http://schemas.microsoft.com/BizTalk/2003/userCSharp">
  <xsl:output omit-xml-declaration="yes" method="xml" version="1.0" />
  <xsl:template match="/">
    <xsl:apply-templates select="/s1:Receive" />
  </xsl:template>
  <xsl:template match="/s1:Receive">
    <Carichi>
      <xsl:for-each select="s1:idocData">
        <xsl:for-each select="s0:ZIMP_CARICOH000GRP">
          <xsl:variable name="var:v1" select="userCSharp:LogicalEq(string(../s0:EDI_DC40/s2:RCVPRN/text()) , &quot;APB2B_P1BA&quot;)" />
          <xsl:variable name="var:v3" select="userCSharp:LogicalNot(string($var:v1))" />
          <xsl:variable name="var:v5" select="string(../s0:EDI_DC40/s2:RCVPRN/text())" />
          <xsl:variable name="var:v6" select="userCSharp:LogicalEq($var:v5 , &quot;APB2B_P1BA&quot;)" />
          <xsl:variable name="var:v7" select="userCSharp:LogicalNot(string($var:v6))" />
          <Ordine>
            <xsl:if test="s0:ZIMP_CARICOH000/s0:TP_AZIONE">
              <tp_azione>
                <xsl:value-of select="s0:ZIMP_CARICOH000/s0:TP_AZIONE/text()" />
              </tp_azione>
            </xsl:if>
            <xsl:if test="string($var:v1)='true'">
              <xsl:variable name="var:v2" select="&quot;TBP2&quot;" />
              <id_sito>
                <xsl:value-of select="$var:v2" />
              </id_sito>
            </xsl:if>
            <xsl:if test="string($var:v3)='true'">
              <xsl:variable name="var:v4" select="&quot;TBP1&quot;" />
              <id_sito>
                <xsl:value-of select="$var:v4" />
              </id_sito>
            </xsl:if>
            <xsl:if test="string($var:v7)='true'">
              <xsl:variable name="var:v8" select="&quot;P1&quot;" />
              <id_proprieta>
                <xsl:value-of select="$var:v8" />
              </id_proprieta>
            </xsl:if>
            <xsl:if test="string($var:v6)='true'">
              <xsl:variable name="var:v9" select="&quot;P2&quot;" />
              <id_proprieta>
                <xsl:value-of select="$var:v9" />
              </id_proprieta>
            </xsl:if>
            <xsl:if test="s0:ZIMP_CARICOH000/s0:ID_ORDINE">
              <id_ordine>
                <xsl:value-of select="s0:ZIMP_CARICOH000/s0:ID_ORDINE/text()" />
              </id_ordine>
            </xsl:if>
            <xsl:if test="s0:ZIMP_CARICOH000/s0:ID_FLUSSO">
              <id_flusso>
                <xsl:value-of select="s0:ZIMP_CARICOH000/s0:ID_FLUSSO/text()" />
              </id_flusso>
            </xsl:if>
            <xsl:if test="s0:ZIMP_CARICOH000/s0:DT_ORDINE">
              <dt_ordine>
                <xsl:value-of select="s0:ZIMP_CARICOH000/s0:DT_ORDINE/text()" />
              </dt_ordine>
            </xsl:if>
            <xsl:if test="s0:ZIMP_CARICOH000/s0:ID_RICEVIMENTO">
              <id_ricevimento>
                <xsl:value-of select="s0:ZIMP_CARICOH000/s0:ID_RICEVIMENTO/text()" />
              </id_ricevimento>
            </xsl:if>
            <xsl:if test="s0:ZIMP_CARICOH000/s0:DT_DOCUMENTO">
              <dt_documento>
                <xsl:value-of select="s0:ZIMP_CARICOH000/s0:DT_DOCUMENTO/text()" />
              </dt_documento>
            </xsl:if>
            <xsl:if test="s0:ZIMP_CARICOH000/s0:ID_DOCUMENTO">
              <id_documento>
                <xsl:value-of select="s0:ZIMP_CARICOH000/s0:ID_DOCUMENTO/text()" />
              </id_documento>
            </xsl:if>
            <Fornitore>
              <xsl:if test="s0:ZIMP_CARICOH000/s0:ID_FORNITORE">
                <id_fornitore>
                  <xsl:value-of select="s0:ZIMP_CARICOH000/s0:ID_FORNITORE/text()" />
                </id_fornitore>
              </xsl:if>
              <xsl:if test="s0:ZIMP_CARICOH000/s0:DS_RAG_SOC_ESTESA_FORN">
                <ds_rag_soc_estesa>
                  <xsl:value-of select="s0:ZIMP_CARICOH000/s0:DS_RAG_SOC_ESTESA_FORN/text()" />
                </ds_rag_soc_estesa>
              </xsl:if>
            </Fornitore>
            <Righe>
              <xsl:for-each select="s0:ZIMP_CARICOL000GRP">
                <xsl:variable name="var:v10" select="userCSharp:LogicalNe(string(s0:ZIMP_CARICOL000/s0:DT_SCADENZA/text()) , &quot;&quot;)" />
                <xsl:variable name="var:v11" select="string(s0:ZIMP_CARICOL000/s0:DT_SCADENZA/text())" />
                <xsl:variable name="var:v12" select="userCSharp:LogicalNe($var:v11 , &quot;00000000&quot;)" />
                <xsl:variable name="var:v13" select="userCSharp:LogicalAnd(string($var:v10) , string($var:v12))" />
                <xsl:variable name="var:v17" select="userCSharp:LogicalEq(&quot;a&quot; , &quot;b&quot;)" />
                <xsl:variable name="var:v18" select="userCSharp:LogicalNe(string(s0:ZIMP_CARICOL_SN000/s0:DS_SN/text()) , &quot;&quot;)" />
                <xsl:variable name="var:v19" select="userCSharp:LogicalEq(string(../../s0:EDI_DC40/s2:RCVPRN/text()) , &quot;APB2B_P1BA&quot;)" />
                <xsl:variable name="var:v20" select="userCSharp:LogicalAnd(string($var:v18) , string($var:v19))" />
                <Riga>
                  <xsl:if test="s0:ZIMP_CARICOL000/s0:TP_AZIONE">
                    <tp_azione>
                      <xsl:value-of select="s0:ZIMP_CARICOL000/s0:TP_AZIONE/text()" />
                    </tp_azione>
                  </xsl:if>
                  <xsl:if test="s0:ZIMP_CARICOL000/s0:ID_SKU">
                    <id_sku>
                      <xsl:value-of select="s0:ZIMP_CARICOL000/s0:ID_SKU/text()" />
                    </id_sku>
                  </xsl:if>
                  <xsl:if test="s0:ZIMP_CARICOL000/s0:ID_TIPO_MAGAZZINO">
                    <id_tipo_magazzino>
                      <xsl:value-of select="s0:ZIMP_CARICOL000/s0:ID_TIPO_MAGAZZINO/text()" />
                    </id_tipo_magazzino>
                  </xsl:if>
                  <xsl:if test="s0:ZIMP_CARICOL000/s0:ID_CAUSALE">
                    <id_causale>
                      <xsl:value-of select="s0:ZIMP_CARICOL000/s0:ID_CAUSALE/text()" />
                    </id_causale>
                  </xsl:if>
                  <xsl:if test="s0:ZIMP_CARICOL000/s0:ID_CLASSIFICA">
                    <id_classifica>
                      <xsl:value-of select="s0:ZIMP_CARICOL000/s0:ID_CLASSIFICA/text()" />
                    </id_classifica>
                  </xsl:if>
                  <xsl:if test="s0:ZIMP_CARICOL000/s0:ID_SCHEMA">
                    <id_schema>
                      <xsl:value-of select="s0:ZIMP_CARICOL000/s0:ID_SCHEMA/text()" />
                    </id_schema>
                  </xsl:if>
                  <xsl:if test="s0:ZIMP_CARICOL000/s0:ID_ORD_RIGA">
                    <id_ord_riga>
                      <xsl:value-of select="s0:ZIMP_CARICOL000/s0:ID_ORD_RIGA/text()" />
                    </id_ord_riga>
                  </xsl:if>
                  <xsl:if test="s0:ZIMP_CARICOL000/s0:QT_ORDINE">
                    <qt_ordine>
                      <xsl:value-of select="s0:ZIMP_CARICOL000/s0:QT_ORDINE/text()" />
                    </qt_ordine>
                  </xsl:if>
                  <xsl:if test="s0:ZIMP_CARICOL000/s0:DS_LOTTO">
                    <ds_lotto>
                      <xsl:value-of select="s0:ZIMP_CARICOL000/s0:DS_LOTTO/text()" />
                    </ds_lotto>
                  </xsl:if>
                  <xsl:if test="$var:v13">
                    <xsl:variable name="var:v14" select="userCSharp:LogicalNe($var:v11 , &quot;&quot;)" />
                    <xsl:variable name="var:v15" select="userCSharp:LogicalAnd(string($var:v14) , string($var:v12))" />
                    <xsl:if test="string($var:v15)='true'">
                      <xsl:variable name="var:v16" select="s0:ZIMP_CARICOL000/s0:DT_SCADENZA/text()" />
                      <dt_scadenza>
                        <xsl:value-of select="$var:v16" />
                      </dt_scadenza>
                    </xsl:if>
                  </xsl:if>
                  <xsl:if test="s0:ZIMP_CARICOL000/s0:ID_RIFERIMENTO_HOST">
                    <id_riferimento_host>
                      <xsl:value-of select="s0:ZIMP_CARICOL000/s0:ID_RIFERIMENTO_HOST/text()" />
                    </id_riferimento_host>
                  </xsl:if>
                  <xsl:if test="s0:ZIMP_CARICOL000/s0:ID_RIFERIMENTO_HOST_2">
                    <id_riferimento_host_2>
                      <xsl:value-of select="s0:ZIMP_CARICOL000/s0:ID_RIFERIMENTO_HOST_2/text()" />
                    </id_riferimento_host_2>
                  </xsl:if>
                  <xsl:if test="s0:ZIMP_CARICOL000/s0:ID_RIFERIMENTO_HOST_3">
                    <id_riferimento_host_3>
                      <xsl:value-of select="s0:ZIMP_CARICOL000/s0:ID_RIFERIMENTO_HOST_3/text()" />
                    </id_riferimento_host_3>
                  </xsl:if>
                  <xsl:if test="s0:ZIMP_CARICOL000/s0:DS_COMMESSA">
                    <ds_commessa>
                      <xsl:value-of select="s0:ZIMP_CARICOL000/s0:DS_COMMESSA/text()" />
                    </ds_commessa>
                  </xsl:if>
                  <xsl:if test="s0:ZIMP_CARICOL000/s0:DT_PRODUZIONE">
                    <dt_produzione>
                      <xsl:value-of select="s0:ZIMP_CARICOL000/s0:DT_PRODUZIONE/text()" />
                    </dt_produzione>
                  </xsl:if>
                  <xsl:if test="$var:v17">
                    <Righe_Udc>
                      <xsl:if test="$var:v17">
                        <Udc>
                          <xsl:if test="$var:v17" />
                          <xsl:if test="$var:v17" />
                          <xsl:if test="$var:v17" />
                          <xsl:if test="$var:v17" />
                          <xsl:if test="$var:v17" />
                          <xsl:if test="$var:v17" />
                          <xsl:if test="$var:v17" />
                          <xsl:if test="$var:v17" />
                        </Udc>
                      </xsl:if>
                    </Righe_Udc>
                  </xsl:if>
                  <xsl:if test="$var:v20">
                    <Serial_Numbers>
                      <xsl:for-each select="s0:ZIMP_CARICOL_SN000">
                        <xsl:variable name="var:v21" select="userCSharp:LogicalNe(string(s0:DS_SN/text()) , &quot;&quot;)" />
                        <xsl:variable name="var:v22" select="userCSharp:LogicalEq(string(../../../s0:EDI_DC40/s2:RCVPRN/text()) , &quot;APB2B_P1BA&quot;)" />
                        <xsl:variable name="var:v23" select="userCSharp:LogicalAnd(string($var:v21) , string($var:v22))" />
                        <xsl:variable name="var:v25" select="string(s0:DS_SN/text())" />
                        <xsl:variable name="var:v26" select="userCSharp:LogicalNe($var:v25 , &quot;&quot;)" />
                        <xsl:variable name="var:v27" select="string(../../../s0:EDI_DC40/s2:RCVPRN/text())" />
                        <xsl:variable name="var:v28" select="userCSharp:LogicalEq($var:v27 , &quot;APB2B_P1BA&quot;)" />
                        <xsl:variable name="var:v29" select="userCSharp:LogicalAnd(string($var:v26) , string($var:v28))" />
                        <Serial_Number>
                          <xsl:if test="string($var:v23)='true'">
                            <xsl:variable name="var:v24" select="&quot;I&quot;" />
                            <tp_azione>
                              <xsl:value-of select="$var:v24" />
                            </tp_azione>
                          </xsl:if>
                          <xsl:if test="string($var:v29)='true'">
                            <xsl:variable name="var:v30" select="s0:DS_SN/text()" />
                            <ds_sn>
                              <xsl:value-of select="$var:v30" />
                            </ds_sn>
                          </xsl:if>
                        </Serial_Number>
                      </xsl:for-each>
                    </Serial_Numbers>
                  </xsl:if>
                </Riga>
              </xsl:for-each>
            </Righe>
          </Ordine>
        </xsl:for-each>
      </xsl:for-each>
    </Carichi>
  </xsl:template>
  <msxsl:script language="C#" implements-prefix="userCSharp"><![CDATA[
public bool LogicalEq(string val1, string val2)
{
	bool ret = false;
	double d1 = 0;
	double d2 = 0;
	if (IsNumeric(val1, ref d1) && IsNumeric(val2, ref d2))
	{
		ret = d1 == d2;
	}
	else
	{
		ret = String.Compare(val1, val2, StringComparison.Ordinal) == 0;
	}
	return ret;
}


public bool LogicalNe(string val1, string val2)
{
	bool ret = false;
	double d1 = 0;
	double d2 = 0;
	if (IsNumeric(val1, ref d1) && IsNumeric(val2, ref d2))
	{
		ret = d1 != d2;
	}
	else
	{
		ret = String.Compare(val1, val2, StringComparison.Ordinal) != 0;
	}
	return ret;
}


public bool LogicalAnd(string param0, string param1)
{
	return ValToBool(param0) && ValToBool(param1);
	return false;
}


public bool LogicalNot(string val)
{
	return !ValToBool(val);
}


public bool IsNumeric(string val)
{
	if (val == null)
	{
		return false;
	}
	double d = 0;
	return Double.TryParse(val, System.Globalization.NumberStyles.AllowThousands | System.Globalization.NumberStyles.Float, System.Globalization.CultureInfo.InvariantCulture, out d);
}

public bool IsNumeric(string val, ref double d)
{
	if (val == null)
	{
		return false;
	}
	return Double.TryParse(val, System.Globalization.NumberStyles.AllowThousands | System.Globalization.NumberStyles.Float, System.Globalization.CultureInfo.InvariantCulture, out d);
}

public bool ValToBool(string val)
{
	if (val != null)
	{
		if (string.Compare(val, bool.TrueString, StringComparison.OrdinalIgnoreCase) == 0)
		{
			return true;
		}
		if (string.Compare(val, bool.FalseString, StringComparison.OrdinalIgnoreCase) == 0)
		{
			return false;
		}
		val = val.Trim();
		if (string.Compare(val, bool.TrueString, StringComparison.OrdinalIgnoreCase) == 0)
		{
			return true;
		}
		if (string.Compare(val, bool.FalseString, StringComparison.OrdinalIgnoreCase) == 0)
		{
			return false;
		}
		double d = 0;
		if (IsNumeric(val, ref d))
		{
			return (d > 0);
		}
	}
	return false;
}


]]></msxsl:script>
</xsl:stylesheet>