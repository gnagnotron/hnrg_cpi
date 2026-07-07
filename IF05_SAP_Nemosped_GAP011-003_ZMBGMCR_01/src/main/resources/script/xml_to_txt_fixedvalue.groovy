import com.sap.gateway.ip.core.customdev.util.Message;
import java.util.HashMap;
import java.text.*;
import groovy.util.*;
import groovy.xml.*;

def String FixedValue(String values, String Lengths) {
    String TLINE = "";
    int tmpLen = 0;

    try {
        String[] val = values.split(";" , -1);      /* Non scarta le stringhe vuote finali { 2;3;; }*/
        String[] len = Lengths.split(";");          /* Vettore delle lunghezze { 2;3;5;8; } */

        for (int i = 0; i < len.length; i++) {
            tmpLen = Integer.parseInt(len[i]);
            TLINE = TLINE + val[i].padRight(tmpLen, ' ').substring(0, tmpLen);
        }
    } catch (Exception ex) {
        TLINE = "";
    }
    return TLINE;
}

def Message processData(Message message) {
	
	//def properties = message.getProperties();
	//def P_XML = properties.get("P_XML");
	def body = message.getBody(java.lang.String) as String;
	def xmlIn = new XmlSlurper().parseText(body);
	
    def txt                             = "";
	def sep                             = ";";
	def len_EDI_DC40                    = "10;16;30;30;30;2;10;2;10;8;6;";
	def len_E1BP2017_GM_HEAD_01         = "8;16;";
	def len_E1BP2017_GM_ITEM_CREATE     = "18;4;4;10;2;13;3;40";

	def EDI_DC40_tmp                    = "";
	def E1BP2017_GM_HEAD_01_tmp         = "";
	def E1BP2017_GM_ITEM_CREATE_tmp     = "";

	def EDI_DC40_child                  = "";
	def E1BP2017_GM_HEAD_01_child       = "";
	def E1BP2017_GM_ITEM_CREATE_child   = "";
	def E1MBGMCR_child                  = "";
	
//  EDI_DC40
	def TABNAM                          = "EDI_DC40";
	def IDOCTYP                         = "MBGMCR03";
	def CIMTYP                          = "ZMBGMCR_01";
	def MESTYP                          = "MBGMCR";
	def DOCNUM                          = "";
	def SNDPRT                          = "LS";
	def SNDPRN                          = "";
	def RCVPRT                          = "LS";
	def RCVPRN                          = "Nemosped";
	
// 	E1BP2017_GM_HEAD_01 
	def PSTNG_DATE                      = "";
	def REF_DOC_NO                      = "";
	
// 	E1BP2017_GM_ITEM_CREATE
	def MATERIAL                        = "";
	def PLANT                           = "";
	def STGE_LOC                        = "";
	def BATCH                           = "";
	def ENTRY_QNT                       = "";
	def ENTRY_UOM                       = "";
	def MATERIAL_EXTERNAL               = "";

//	ZMBGMCR_01_ITEM_TYPE
	def ZSTCK_TYPE                      = "";

 	def sFormat = "yyyyMMdd HHmmss";
    SimpleDateFormat dateFormat = new SimpleDateFormat(sFormat);
    dateFormat.setTimeZone(TimeZone.getTimeZone("CET"));
    Date date = new Date();
    
    def CREDAT = dateFormat.format(date).substring(0,8);
    def CRETIM = dateFormat.format(date).substring(9);

	xmlIn.children().each { rows ->
			rows.children().each { row ->
			
					if ("${row.name()}" == "EDI_DC40") {
						row.children().each { EDI_DC40 ->

								switch ("${EDI_DC40.name()}") {
									case "DOCNUM":
										if ("${EDI_DC40.text()}" != "") {
											DOCNUM = "${EDI_DC40.text()}".replaceFirst ("^0*", "");
										}
										break;
									case "SNDPRN":
										if ("${EDI_DC40.text()}" != "") {
											SNDPRN = "${EDI_DC40.text()}".replaceFirst ("^0*", "");
										}
										break;
								}
						}
						EDI_DC40_tmp = EDI_DC40_tmp + TABNAM + sep + DOCNUM + sep + IDOCTYP + sep + CIMTYP + sep + MESTYP + sep + SNDPRT + sep + SNDPRN + sep + RCVPRT + sep + RCVPRN + sep + CREDAT + sep + CRETIM + sep;

						DOCNUM = "";
						SNDPRN = "";

						EDI_DC40_child = EDI_DC40_child + FixedValue(EDI_DC40_tmp, len_EDI_DC40) + "\n";
						EDI_DC40_tmp = "";
					}
				    if ("${row.name()}" == "E1MBGMCR") {
				        row.children().each { E1MBGMCR ->
					
    					if ("${E1MBGMCR.name()}" == "E1BP2017_GM_HEAD_01") {
    						E1MBGMCR.children().each { E1BP2017_GM_HEAD_01 ->
    
    								switch ("${E1BP2017_GM_HEAD_01.name()}") {
    									case "PSTNG_DATE":
    										if ("${E1BP2017_GM_HEAD_01.text()}" != "") {
    											PSTNG_DATE = "${E1BP2017_GM_HEAD_01.text()}".replaceFirst ("^0*", "");
    										}
    										break;
    									case "REF_DOC_NO":
    										if ("${E1BP2017_GM_HEAD_01.text()}" != "") {
    											REF_DOC_NO = "${E1BP2017_GM_HEAD_01.text()}".replaceFirst ("^0*", "");
    										}
    										break;
    								}
    						}
    						E1BP2017_GM_HEAD_01_tmp = E1BP2017_GM_HEAD_01_tmp + PSTNG_DATE + sep + REF_DOC_NO + sep;
    
    						PSTNG_DATE = "";
    						REF_DOC_NO = "";
    
    						E1BP2017_GM_HEAD_01_child = E1BP2017_GM_HEAD_01_child + "E1BP2017_GM_HEAD_01" + FixedValue(E1BP2017_GM_HEAD_01_tmp, len_E1BP2017_GM_HEAD_01) + "\n";
    						E1BP2017_GM_HEAD_01_tmp = "";
    					}
    					
    					if ("${E1MBGMCR.name()}" == "E1BP2017_GM_ITEM_CREATE") {
    						E1MBGMCR.children().each { E1BP2017_GM_ITEM_CREATE ->
    
    								switch ("${E1BP2017_GM_ITEM_CREATE.name()}") {
    									case "MATERIAL":
    										if ("${E1BP2017_GM_ITEM_CREATE.text()}" != "") {
    											MATERIAL = "${E1BP2017_GM_ITEM_CREATE.text()}".replaceFirst ("^0*", "");
    										}
    										break;
    									case "PLANT":
    										if ("${E1BP2017_GM_ITEM_CREATE.text()}" != "") {
    											PLANT = "${E1BP2017_GM_ITEM_CREATE.text()}".replaceFirst ("^0*", "");
    										}
    										break;
    									case "STGE_LOC":
    										if ("${E1BP2017_GM_ITEM_CREATE.text()}" != "") {
    											STGE_LOC = "${E1BP2017_GM_ITEM_CREATE.text()}".replaceFirst ("^0*", "");
    										}
    										break;
    									case "BATCH":
    										if ("${E1BP2017_GM_ITEM_CREATE.text()}" != "") {
    											BATCH = "${E1BP2017_GM_ITEM_CREATE.text()}";
    										}
    										break;
    									case "ENTRY_QNT":
    										if ("${E1BP2017_GM_ITEM_CREATE.text()}" != "") {
    											ENTRY_QNT = "${E1BP2017_GM_ITEM_CREATE.text()}".replaceFirst ("^ *", "");
    										}
    										break;
    									case "ENTRY_UOM":
    										if ("${E1BP2017_GM_ITEM_CREATE.text()}" != "") {
    											ENTRY_UOM = "${E1BP2017_GM_ITEM_CREATE.text()}".replaceFirst ("^0*", "");
    											if (ENTRY_UOM.contains("PZ") || ENTRY_UOM.contains("Pce") || ENTRY_UOM.contains("ST")) {
												    ENTRY_UOM = "PC";
												}
    										}
    										break;
    									case "MATERIAL_EXTERNAL":
    										if ("${E1BP2017_GM_ITEM_CREATE.text()}" != "") {
    											MATERIAL_EXTERNAL = "${E1BP2017_GM_ITEM_CREATE.text()}";
    										}
    										break;
										case "ZMBGMCR_01_ITEM_TYPE":
											E1BP2017_GM_ITEM_CREATE.children().each { ZMBGMCR_01_ITEM_TYPE ->
												if ("${ZMBGMCR_01_ITEM_TYPE.name()}" == "ZSTCK_TYPE") {
													if ("${ZMBGMCR_01_ITEM_TYPE.text()}" != "") {
														ZSTCK_TYPE = "${ZMBGMCR_01_ITEM_TYPE.text()}".replaceFirst ("^0*", "");
													}
												}
											}
    								}
    						}
    						E1BP2017_GM_ITEM_CREATE_tmp = E1BP2017_GM_ITEM_CREATE_tmp + MATERIAL + sep + PLANT + sep + STGE_LOC + sep + BATCH + sep + ZSTCK_TYPE + sep + ENTRY_QNT + sep + ENTRY_UOM + sep + MATERIAL_EXTERNAL + sep;
    
    						MATERIAL        = "";
                            PLANT           = "";
                            STGE_LOC        = "";
                            BATCH           = "";
                            ENTRY_QNT       = "";
                            ENTRY_UOM       = "";
                            MATERIAL_EXTERNAL = "";
    
    						E1BP2017_GM_ITEM_CREATE_child = E1BP2017_GM_ITEM_CREATE_child + "E1BP2017_GM_ITEM_CREATE" + FixedValue(E1BP2017_GM_ITEM_CREATE_tmp, len_E1BP2017_GM_ITEM_CREATE) + "\n";
    						E1BP2017_GM_ITEM_CREATE_tmp = "";
    					}
					}
					
					E1MBGMCR_child = E1MBGMCR_child + E1BP2017_GM_HEAD_01_child + E1BP2017_GM_ITEM_CREATE_child;
					
					E1BP2017_GM_HEAD_01_child     = "";
					E1BP2017_GM_ITEM_CREATE_child = "";
			    }
	        }
	    }       
	txt = EDI_DC40_child + E1MBGMCR_child;
	txt = txt.substring(0,txt.length() - 1); // Toglie l'ultimo "\n" 
 	message.setBody(txt);
 	return message;
}