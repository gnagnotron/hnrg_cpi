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
            TLINE = TLINE + val[i].padRight(tmpLen, ' ').substring(0,tmpLen);
        }
    } catch (Exception ex) {
        TLINE = "";
    }
    return TLINE;
}

def Message processData(Message message) {
	
	def body = message.getBody(java.lang.String) as String;
	def xmlIn = new XmlSlurper().parseText(body);

	def txt             = "";
	def sep             = ";";
	def len_EDI_DC40    = "10;16;30;30;30;2;10;2;10;8;6;";
	def len_E1MARAM     = "18;4;3;13;13;3;13;3;2;2;18;1;1;20;20;30";
	def len_E1MAKTM     = "40;";

	def EDI_DC40_tmp    = "";
	def E1MARAM_tmp     = "";
	def E1MAKTM_tmp     = "";
	
	def EDI_DC40_child  = "";
	def E1MARAM_child   = "";
	def E1MAKTM_child   = "";

	def TABNAM          = "EDI_DC40";
	def IDOCTYP         = "MATMAS03";
	def CIMTYP          = "ZMATMAS_01";
	def MESTYP          = "MATMAS";
	def DOCNUM          = "";
	def SNDPRT          = "LS";
	def SNDPRN          = "SAP";
	def RCVPRT          = "LS";
	def RCVPRN          = "Nemosped";
		
	def MATNR           = "";
    def MTART           = "";
    def MEINS           = "";
    def BRGEW           = "";
    def NTGEW           = "";
    def GEWEI           = "";
    def VOLUM           = "";
    def VOLEH           = "";
    def RAUBE           = "";
    def NUMTP           = "";
    def EAN11           = "";
    def XCHPF           = "";
    def ZSERIAL         = "";
    def ZNUMSKU         = "";
    def ZNUMBOX         = "";
    def ZNARCOTICS      = "";
    
    def MAKTX           = "";
    def SPRAS_ISO           = "";

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
								}
						}
						EDI_DC40_tmp = EDI_DC40_tmp + TABNAM + sep + DOCNUM + sep + IDOCTYP + sep + CIMTYP + sep + MESTYP + sep + SNDPRT + sep + SNDPRN + sep + RCVPRT + sep + RCVPRN + sep + CREDAT + sep + CRETIM + sep;

						DOCNUM = "";
						SNDPRN = "";

						EDI_DC40_child = EDI_DC40_child + FixedValue(EDI_DC40_tmp, len_EDI_DC40) + "\n";
						EDI_DC40_tmp = "";
					}
				if ("${row.name()}" == "E1MARAM") {
					row.children().each { E1MARAM ->

							switch ("${E1MARAM.name()}") {
								case "MATNR":
									if ("${E1MARAM.text()}" != "") {
										MATNR = "${E1MARAM.text()}".replaceFirst ("^0*", "");
									}
									break;
								case "MTART":
									if ("${E1MARAM.text()}" != "") {
										MTART = "${E1MARAM.text()}".replaceFirst ("^0*", "");
									}
									break;
								case "BRGEW":
									if ("${E1MARAM.text()}" != "") {
										BRGEW = "${E1MARAM.text()}";
									}
									break;
								case "NTGEW":
									if ("${E1MARAM.text()}" != "") {
										NTGEW = "${E1MARAM.text()}";
									}
									break;
								case "GEWEI":
									if ("${E1MARAM.text()}" != "") {
										GEWEI = "${E1MARAM.text()}".replaceFirst ("^0*", "");
										if (GEWEI == "GRM") {
										    GEWEI = "G";
										}
										if (GEWEI == "KGM") {
										    GEWEI = "KG";
										}
									}
									break;
								case "VOLUM":
									if ("${E1MARAM.text()}" != "") {
										VOLUM = "${E1MARAM.text()}".replaceFirst ("^0*", "");
										if (VOLUM.startsWith(".")) {
										    VOLUM = "0" + VOLUM;
										}
									}
									break;
								case "RAUBE":
									if ("${E1MARAM.text()}" != "") {
										RAUBE = "${E1MARAM.text()}";
									}
									break;
								case "NUMTP":
								    if ("${E1MARAM.text()}" != "") {
										NUMTP = "${E1MARAM.text()}";
									if (NUMTP == "HE") {
										NUMTP = "ZS";
									}
									if (NUMTP == "IC") {
										NUMTP = "ZG";
									}
								    }
									break;
								case "EAN11":
									if ("${E1MARAM.text()}" != "") {
										EAN11 = "${E1MARAM.text()}";
									}
									break;	
								case "XCHPF":
									if ("${E1MARAM.text()}".replaceFirst ("^0*", "") == "X") {
										XCHPF = "Y";
									}
									else {
									    XCHPF = "N";
									}
									break;
								
								case "E1MAKTM":
									E1MARAM.children().each { E1MAKTM ->
											switch ("${E1MAKTM.name()}") {
												case "MAKTX":
													if ("${E1MAKTM.text()}" != "") {
														MAKTX = "${E1MAKTM.text()}".replaceFirst ("^0*", "");
													}
													break;
												case "SPRAS_ISO":
													if ("${E1MAKTM.text()}" != "") {
														SPRAS_ISO = "${E1MAKTM.text()}".replaceFirst ("^0*", "");
													}
													break;
											}
									}
									
									if (SPRAS_ISO == "EN") {
										E1MAKTM_tmp = E1MAKTM_tmp + MAKTX + sep;
    									E1MAKTM_child = E1MAKTM_child + "E1MAKTM" + FixedValue(E1MAKTM_tmp, len_E1MAKTM) + "\n";
    									E1MAKTM_tmp = "";
									}

									MAKTX      = "";
									SPRAS_ISO  = "";
                                    
									break;
									
								case "ZE1MARAM":
									E1MARAM.children().each { ZE1MARAM ->
									    switch ("${ZE1MARAM.name()}") {
        									case "ZSERIAL":
            									if ("${ZE1MARAM.text()}" != "") {
            										ZSERIAL = "${ZE1MARAM.text()}".replaceFirst ("^0*", "");
            										if (ZSERIAL == "SI") {
                                                        ZSERIAL = "Y";
                                                    } else if (ZSERIAL == "NO") {
                                                        ZSERIAL = "N";
                                                    }
            									}
            									break;
            								case "ZNUMSKU":
            									if ("${ZE1MARAM.text()}" != "") {
            										ZNUMSKU = "${ZE1MARAM.text()}".replaceFirst ("^0*", "");
            									}
            									break;
            								case "ZNUMBOX":
            									if ("${ZE1MARAM.text()}" != "") {
            										ZNUMBOX = "${ZE1MARAM.text()}".replaceFirst ("^0*", "");
            									}
            									break;
            								case "ZNARCOTICS":
            									if ("${ZE1MARAM.text()}" != "") {
            										ZNARCOTICS = "${ZE1MARAM.text()}".replaceFirst ("^0*", "");
            									}
            									break;
            								case "ZE1MARAM_REPLACE":
            									ZE1MARAM.children().each { ZE1MARAM_REPLACE ->
            											switch ("${ZE1MARAM_REPLACE.name()}") {
            												case "MEINS":
            													if ("${ZE1MARAM_REPLACE.text()}" != "") {
            														MEINS = "${ZE1MARAM_REPLACE.text()}".replaceFirst ("^0*", "");
            													}
            													break;
            												case "VOLEH":
            													if ("${ZE1MARAM_REPLACE.text()}" != "") {
            														VOLEH = "${ZE1MARAM_REPLACE.text()}".replaceFirst ("^0*", "");
            													}
            													break;
            											}
            									}
            									break;
									    }
								    }     
        						    break;
							}
					}

					E1MARAM_tmp = E1MARAM_tmp + MATNR + sep + MTART + sep + MEINS + sep + BRGEW + sep + NTGEW + sep + GEWEI + sep + VOLUM + sep + VOLEH + sep + RAUBE + sep + NUMTP + sep + EAN11 + sep + XCHPF + sep + ZSERIAL + sep + ZNUMSKU + sep + ZNUMBOX + sep + ZNARCOTICS + sep;
					E1MARAM_child = E1MARAM_child + "E1MARAM" + FixedValue(E1MARAM_tmp, len_E1MARAM) + "\n";
					E1MARAM_tmp = "";
					
					MATNR           = "";
                    MTART           = "";
                    MEINS           = "";
                    BRGEW           = "";
                    NTGEW           = "";
                    GEWEI           = "";
                    VOLUM           = "";
                    VOLEH           = "";
                    RAUBE           = "";
                    NUMTP           = "";
                    EAN11           = "";
                    XCHPF           = "";
                    ZSERIAL         = "";
                    ZNUMSKU         = "";
                    ZNUMBOX         = "";
                    ZNARCOTICS      = "";
                    
					txt = txt + E1MARAM_child + E1MAKTM_child ;
					E1MARAM_child   = "";
					E1MAKTM_child   = "";
					
				}
			}
	}
	txt = EDI_DC40_child + txt;
	txt = txt.substring(0,txt.length() - 1); // Toglie l'ultimo "\n" 
	
	//repeat E1MARAM segment for every NUMTP != Z3
	def rows = txt.split("\n");
    def outRows = [];
    for (int i = 0; i < rows.length; i++) {
        if (rows[i].startsWith("E1MARAM")) {
            def matNumber = rows[i].substring("E1MARAM".length(), "E1MARAM".length() + Integer.parseInt(len_E1MARAM.split(";")[0])).trim();
            def valuesEANTP = xmlIn.IDOC.E1MARAM.findAll{it.MATNR.text().contains(matNumber)}E1MARMM.E1MEANM.EANTP.collect();
            def valuesEAN11 = xmlIn.IDOC.E1MARAM.findAll{it.MATNR.text().contains(matNumber)}E1MARMM.E1MEANM.EAN11.collect();

            def atLeastOneRowPushed = false;
            for (int j = 0; j < valuesEANTP.size(); j++) {
                if (valuesEANTP[j].toString().equals("Z3")) {
                    continue;
                }
                StringBuffer buf = new StringBuffer(rows[i]);
                def fixedValuesEANTP = valuesEANTP[j].toString().padRight(2).substring(0, 2);
                def fixedValuesEAN11 = valuesEAN11[j].toString().padRight(18).substring(0, 18);
                buf.replace(79, 99, fixedValuesEANTP + fixedValuesEAN11);
                outRows.push(buf);
                atLeastOneRowPushed = true;
            }
            if (atLeastOneRowPushed) {
                continue;
            }
        }
        outRows.push(rows[i]);
    }
    
	message.setBody(outRows.join("\n"));
	return message;
}