<?xml version="1.0" encoding="utf-8"?>
<xsl:stylesheet version="2.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
    xmlns:msxsl="urn:schemas-microsoft-com:xslt"
				xmlns:ns1="http://www.oracle.com/webfolder/technetwork/xml/plm/2016/09/"
				exclude-result-prefixes="msxsl ns1"
>
	<!-- ver.1.1 -->

	<xsl:output method="xml" indent="yes"/>

	<xsl:template match="@* | node()">
		<xsl:copy>
			<xsl:apply-templates select="@* | node()"/>
		</xsl:copy>
	</xsl:template>

	<xsl:template match="ns1:RedlinedBOMRow" xmlns:ns1="http://www.oracle.com/webfolder/technetwork/xml/plm/2016/09/">
		<ns1:RedlinedBOMRow>
			<xsl:attribute name="RedlineAction">
				<xsl:value-of select="@RedlineAction"/>
			</xsl:attribute>

			<xsl:apply-templates select="ns1:RedlinedBOMRowCurrent"/>
			<xsl:apply-templates select="ns1:RedlinedBOMRowPrevious"/>
		</ns1:RedlinedBOMRow>
	</xsl:template>

	<!-- >>>>>>>>>>>>>> ===================== <<<<<<<<<<<<<<<<<<-->
	<!-- >>>>>>>>>>>>>> RedlinedBOMRowCurrent <<<<<<<<<<<<<<<<<<-->
	<!-- >>>>>>>>>>>>>> ===================== <<<<<<<<<<<<<<<<<<-->

	<!-- Elabora RedlinedBOMRowCurrent/ReferenceDesignators -->
	<!-- se ReferenceDesignators ha children ReferenceDesignator  -->
	<xsl:template match="ns1:RedlinedBOMRowCurrent/ns1:ReferenceDesignators">
		<xsl:if test="count(child::node())>0">
			<ns1:ReferenceDesignators>
				<xsl:apply-templates select="ns1:ReferenceDesignator"/>
			</ns1:ReferenceDesignators>
		</xsl:if>
		<xsl:if test="count(child::node())=0">
			<ns1:ReferenceDesignators/>
		</xsl:if>
	</xsl:template>

	<!-- Elabora RedlinedBOMRowCurrent/ReferenceDesignators/ReferenceDesignator -->
	<!-- Raccoglie i parametri necessari alla concatenate nel tag ReferenceDesignator  -->
	<xsl:template match="ns1:RedlinedBOMRowCurrent/ns1:ReferenceDesignators/ns1:ReferenceDesignator">
		<xsl:variable name="inptxt">
        <xsl:choose>
			<xsl:when test="./../../ns1:ItemNumber">
				<xsl:value-of select="./../../ns1:ItemNumber"/>
			</xsl:when>
                 <xsl:when test="./../../../ns1:RedlinedBOMRowPrevious/ns1:ItemNumber">
				<xsl:value-of select="./../../../ns1:RedlinedBOMRowPrevious/ns1:ItemNumber"/>
			</xsl:when>
         </xsl:choose>   
		</xsl:variable>
	

		<ns1:ReferenceDesignator>
			<xsl:call-template name="concatenateRefDes">
				<xsl:with-param name="refdefVal" select="."/>
				<xsl:with-param name="inptxtVal" select="$inptxt"/>
				<xsl:with-param name="bommtxVal" select="./../../ns1:BomMultitext30"/>
			</xsl:call-template>
		</ns1:ReferenceDesignator>
	</xsl:template>




	<!-- >>>>>>>>>>>>>> ====================== <<<<<<<<<<<<<<<<<<-->
	<!-- >>>>>>>>>>>>>> RedlinedBOMRowPrevious <<<<<<<<<<<<<<<<<<-->
	<!-- >>>>>>>>>>>>>> ====================== <<<<<<<<<<<<<<<<<<-->

	<xsl:template match="ns1:RedlinedBOMRowPrevious">
		<ns1:RedlinedBOMRowPrevious>
			<xsl:apply-templates select="*"/>
		</ns1:RedlinedBOMRowPrevious>
	</xsl:template>

	<xsl:template match="ns1:RedlinedBOMRowPrevious/ns1:ReferenceDesignators">
		<xsl:if test="not(ns1:ReferenceDesignator)">
			<ns1:ReferenceDesignators/>
		</xsl:if>

		<xsl:if test="ns1:ReferenceDesignator">
			<xsl:if test="../../ns1:RedlinedBOMRowCurrent/ns1:ReferenceDesignators/ns1:ReferenceDesignator">
				<!--non replica i ReferenceDesignator-->
				<ns1:ReferenceDesignators/>
			</xsl:if>

			<xsl:if test="not(../../ns1:RedlinedBOMRowCurrent/ns1:ReferenceDesignators/ns1:ReferenceDesignator)">
				<!--concatenate del valore dei ReferenceDesignator-->
				<ns1:ReferenceDesignators>
					<xsl:apply-templates select="ns1:ReferenceDesignator" />
				</ns1:ReferenceDesignators>
			</xsl:if>
		</xsl:if>
	</xsl:template>

	<xsl:template match="ns1:RedlinedBOMRowPrevious/ns1:ReferenceDesignators/ns1:ReferenceDesignator">
		<xsl:variable name="inptxt">
        <xsl:choose>
        	<xsl:when test="./../../../ns1:RedlinedBOMRowCurrent/ns1:ItemNumber">
				<xsl:value-of select="./../../../ns1:RedlinedBOMRowCurrent/ns1:ItemNumber"/>
			</xsl:when>
			<xsl:when test="./../../ns1:ItemNumber">
				<xsl:value-of select="./../../ns1:ItemNumber"/>
			</xsl:when>
         </xsl:choose>   
		</xsl:variable>


        <xsl:variable name="inpbom">

        <xsl:choose>
        	<xsl:when test="./../../../ns1:RedlinedBOMRowCurrent/ns1:BomMultitext30">
				<xsl:value-of select="./../../../ns1:RedlinedBOMRowCurrent/ns1:BomMultitext30"/>
			</xsl:when>
			<xsl:when test="./../../ns1:BomMultitext30">
				<xsl:value-of select="./../../ns1:BomMultitext30"/>
			</xsl:when>
         </xsl:choose>    
		</xsl:variable>


		<ns1:ReferenceDesignator>
			<xsl:call-template name="concatenateRefDes">
				<xsl:with-param name="refdefVal" select="."/>
				<xsl:with-param name="inptxtVal" select="$inptxt"/>
				<!--<xsl:with-param name="bommtxVal" select="../../ns1:BomMultitext30"/> -->
                <xsl:with-param name="bommtxVal" select="$inpbom"/> 
			</xsl:call-template>
		</ns1:ReferenceDesignator>
	</xsl:template>

	<!-- Concatena i parametri in input. -->
	<xsl:template name="concatenateRefDes">
		<xsl:param name="refdefVal"/>
		<xsl:param name="inptxtVal"/>
		<xsl:param name="bommtxVal"/>

		<xsl:value-of select="$refdefVal"/>;<xsl:value-of select='$inptxtVal'/>;<xsl:value-of select='$bommtxVal'/>
	</xsl:template>

</xsl:stylesheet>
