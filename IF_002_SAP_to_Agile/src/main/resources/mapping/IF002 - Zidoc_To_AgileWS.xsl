<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:msxsl="urn:schemas-microsoft-com:xslt" xmlns:var="http://schemas.microsoft.com/BizTalk/2003/var" exclude-result-prefixes="msxsl var s0 s2 s1 userCSharp ScriptNS0" version="1.0" xmlns:s2="http://Microsoft.LobServices.Sap/2007/03/Types/Idoc/Common/" xmlns:s0="http://Microsoft.LobServices.Sap/2007/03/Types/Idoc/3/ZIDOCFILE//700" xmlns:common="http://xmlns.oracle.com/AgileObjects/Core/Common/V1" xmlns:s1="http://Microsoft.LobServices.Sap/2007/03/Idoc/3/ZIDOCFILE//700/Receive" xmlns:ns0="http://xmlns.oracle.com/AgileObjects/Core/Business/V1" xmlns:userCSharp="http://schemas.microsoft.com/BizTalk/2003/userCSharp" xmlns:ScriptNS0="http://schemas.microsoft.com/BizTalk/2003/ScriptNS0">
	<xsl:output omit-xml-declaration="yes" method="xml" version="1.0" />
	<xsl:template match="/">
		<xsl:apply-templates select="/s1:Receive" />
	</xsl:template>
	<xsl:template match="/s1:Receive">
		<xsl:variable name="var:v1" select="userCSharp:StringConcat(string(s1:idocData/s0:EDI_DC40/s2:SNDPOR/text()) , &quot;|&quot; , string(s1:idocData/s0:EDI_DC40/s2:SNDPRT/text()) , &quot;||&quot; , string(s1:idocData/s0:EDI_DC40/s2:SNDPRN/text()) , &quot;|AGILE&quot;)" />
		<xsl:variable name="var:v4" select="userCSharp:LogicalEq(&quot;a&quot; , &quot;b&quot;)" />
		<ns0:updateObject>
			<request>
				<xsl:variable name="var:v2" select="ScriptNS0:DBLookup(0 , string($var:v1) , &quot;File Name=C:\Biztalk\B2BIT_EDI\sql_connection_transcod.udl;&quot; , &quot;UNIQUEID_FROMTO&quot; , &quot;UNIQUEID&quot;)" />
				<xsl:variable name="var:v3" select="ScriptNS0:DBValueExtract(string($var:v2) , &quot;SENDER_NAZ&quot;)" />
				<sender>
					<xsl:value-of select="$var:v3" />
				</sender>
				<xsl:if test="$var:v4" />
				<xsl:for-each select="s1:idocData/s0:ZE1EDKFILE1000">
					<xsl:variable name="var:v5" select="userCSharp:StringConcat(&quot;Changes&quot;)" />
					<xsl:variable name="var:v6" select="userCSharp:StringFind(string(s0:ZRECDATA/text()) , &quot;|&quot;)" />
					<xsl:variable name="var:v7" select="userCSharp:MathSubtract(string($var:v6) , &quot;1&quot;)" />
					<xsl:variable name="var:v8" select="string(s0:ZRECDATA/text())" />
					<xsl:variable name="var:v9" select="userCSharp:StringSubstring($var:v8 , &quot;1&quot; , string($var:v7))" />
					<xsl:variable name="var:v10" select="userCSharp:StringConcat(string(../s0:EDI_DC40/s2:SNDPOR/text()) , &quot;|&quot; , string(../s0:EDI_DC40/s2:SNDPRT/text()) , &quot;||&quot; , string(../s0:EDI_DC40/s2:SNDPRN/text()) , &quot;|AGILE&quot;)" />
					<xsl:variable name="var:v13" select="userCSharp:StringFind($var:v8 , &quot;|&quot;)" />
					<xsl:variable name="var:v14" select="userCSharp:MathAdd(string($var:v13) , &quot;1&quot;)" />
					<xsl:variable name="var:v15" select="userCSharp:StringSize($var:v8)" />
					<xsl:variable name="var:v16" select="userCSharp:StringSubstring($var:v8 , string($var:v14) , string($var:v15))" />
					<requests>
						<classIdentifier>
							<xsl:value-of select="$var:v5" />
						</classIdentifier>
						<objectNumber>
							<xsl:value-of select="$var:v9" />
						</objectNumber>
						<xsl:variable name="var:v11" select="ScriptNS0:DBLookup(0 , string($var:v10) , &quot;File Name=C:\Biztalk\B2BIT_EDI\sql_connection_transcod.udl;&quot; , &quot;UNIQUEID_FROMTO&quot; , &quot;UNIQUEID&quot;)" />
						<xsl:variable name="var:v12" select="ScriptNS0:DBValueExtract(string($var:v11) , &quot;RECIPIENT_UNIQUEID&quot;)" />
						<xsl:call-template name="dataTemplate">
							<xsl:with-param name="param1" select="string($var:v12)" />
							<xsl:with-param name="param2" select="string($var:v16)" />
						</xsl:call-template>
					</requests>
				</xsl:for-each>
			</request>
		</ns0:updateObject>
		<xsl:variable name="var:v17" select="ScriptNS0:DBLookupShutdown()" />
	</xsl:template>
	<msxsl:script language="C#" implements-prefix="userCSharp">
		<![CDATA[
public string StringConcat(string param0, string param1, string param2, string param3, string param4, string param5)
{
   return param0 + param1 + param2 + param3 + param4 + param5;
}


public string StringConcat(string param0)
{
   return param0;
}


public int StringFind(string str, string strFind)
{
	if (str == null || strFind == null || strFind == "")
	{
		return 0;
	}
	return (str.IndexOf(strFind) + 1);
}


public string StringSubstring(string str, string left, string right)
{
	string retval = "";
	double dleft = 0;
	double dright = 0;
	if (str != null && IsNumeric(left, ref dleft) && IsNumeric(right, ref dright))
	{
		int lt = (int)dleft;
		int rt = (int)dright;
		lt--; rt--;
		if (lt >= 0 && rt >= lt && lt < str.Length)
		{
			if (rt < str.Length)
			{
				retval = str.Substring(lt, rt-lt+1);
			}
			else
			{
				retval = str.Substring(lt, str.Length-lt);
			}
		}
	}
	return retval;
}


public string MathSubtract(string param0, string param1)
{
	System.Collections.ArrayList listValues = new System.Collections.ArrayList();
	listValues.Add(param0);
	listValues.Add(param1);
	double ret = 0;
	bool first = true;
	foreach (string obj in listValues)
	{
		if (first)
		{
			first = false;
			double d = 0;
			if (IsNumeric(obj, ref d))
			{
				ret = d;
			}
			else
			{
				return "";
			}
		}
		else
		{
			double d = 0;
			if (IsNumeric(obj, ref d))
			{
				ret -= d;
			}
			else
			{
				return "";
			}
		}
	}
	return ret.ToString(System.Globalization.CultureInfo.InvariantCulture);
}


public int StringSize(string str)
{
	if (str == null)
	{
		return 0;
	}
	return str.Length;
}


public string MathAdd(string param0, string param1)
{
	System.Collections.ArrayList listValues = new System.Collections.ArrayList();
	listValues.Add(param0);
	listValues.Add(param1);
	double ret = 0;
	foreach (string obj in listValues)
	{
	double d = 0;
		if (IsNumeric(obj, ref d))
		{
			ret += d;
		}
		else
		{
			return "";
		}
	}
	return ret.ToString(System.Globalization.CultureInfo.InvariantCulture);
}


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


]]>
	</msxsl:script>
	<xsl:template name="dataTemplate">
		<xsl:param name="param1" />
		<xsl:param name="param2" />
		<data rowId="0">
			<xsl:element name="Message_Desc" xml:space="default">
				<xsl:attribute name="attributeId">
					<xsl:value-of select="$param1" />
				</xsl:attribute>
				<xsl:value-of select="normalize-space($param2)" />
			</xsl:element>
		</data>
	</xsl:template>
</xsl:stylesheet>