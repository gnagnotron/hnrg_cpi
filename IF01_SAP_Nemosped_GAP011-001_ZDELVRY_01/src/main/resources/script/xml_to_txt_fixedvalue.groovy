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
	
	//def properties = message.getProperties();
	//def P_XML = properties.get("P_XML");
	def body = message.getBody(java.lang.String) as String;
	def xmlIn = new XmlSlurper().parseText(body);

	def txt             = "";
	def sep             = ";";
	def len_EDI_DC40    = "10;16;30;30;30;2;10;2;10;8;6;";
	def len_E1EDL20     = "10;35;";
	def len_E1ADRM1     = "3;17;40;40;40;10;40;3;";
	def len_E1EDT13     = "3;8;";
	def len_E1EDL24     = "6;18;13;3;10;15;4;4;8;";
	def len_E1EDL11     = "20;13;";

	def EDI_DC40_tmp    = "";
	def E1EDL20_tmp     = "";
	def E1ADRM1_tmp     = "";
	def E1EDT13_tmp     = "";
	def E1EDL24_tmp     = "";
	def E1EDL11_tmp     = "";

	def EDI_DC40_child  = "";
	def E1EDL20_child   = "";
	def E1ADRM1_child   = "";
	def E1EDT13_child   = "";
	def E1EDL24_child   = "";
	def E1EDL11_child   = "";
	def E1EDL24_E1EDL11 = "";

	def TABNAM          = "EDI_DC40";
	def IDOCTYP         = "DELVRY03";
	def CIMTYP          = "ZDEVLRY_01";
	def MESTYP          = "DESADV";
	def DOCNUM          = "";
	def SNDPRT          = "";
	def SNDPRN          = "SAP";
	def RCVPRT          = "";
	def RCVPRN          = "Nemosped";
	
	def VBELN           = "";
	def BOLNR           = "";
	
	def PARTNER_Q       = "";
	def PARTNER_ID      = "";
	def NAME1           = "";
	def NAME2           = "";
	def STREET1         = "";
	def POSTL_COD1      = "";
	def CITY1           = "";
	def COUNTRY1        = "";
		
	def QUALF           = "";
	def NTEND           = "";
		
	def POSNR           = "";
	def MATNR           = "";
	def LFIMG           = "";
	def VRKME           = "";
	def CHARG           = "";
	def LICHN           = "";
	def WERKS           = "";
	def LGORT           = "";
	def VFDAT           = "";
		
	def ZHU1            = "";
	def ZQUANTHU1       = "";

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
									case "SNDPRT":
										if ("${EDI_DC40.text()}" != "") {
											SNDPRT = "${EDI_DC40.text()}".replaceFirst ("^0*", "");
										}
										break;
									case "RCVPRT":
										if ("${EDI_DC40.text()}" != "") {
											RCVPRT = "${EDI_DC40.text()}".replaceFirst ("^0*", "");
										}
										break;
								}
						}
						EDI_DC40_tmp = EDI_DC40_tmp + TABNAM + sep + DOCNUM + sep + IDOCTYP + sep + CIMTYP + sep + MESTYP + sep + SNDPRT + sep + SNDPRN + sep + RCVPRT + sep + RCVPRN + sep + CREDAT + sep + CRETIM + sep;

						DOCNUM = "";
						SNDPRT = "";
						SNDPRN = "";
						RCVPRN = "";
						VBELN  = "";

						EDI_DC40_child = EDI_DC40_child + FixedValue(EDI_DC40_tmp, len_EDI_DC40) + "\n";
						EDI_DC40_tmp = "";
					}
				if ("${row.name()}" == "E1EDL20") {
					row.children().each { E1EDL20 ->

							switch ("${E1EDL20.name()}") {
								case "VBELN":
									if ("${E1EDL20.text()}" != "") {
										VBELN = "${E1EDL20.text()}".replaceFirst ("^0*", "");
									}
									break;
								case "BOLNR":
									if ("${E1EDL20.text()}" != "") {
										BOLNR = "${E1EDL20.text()}".replaceFirst ("^0*", "");
									}
									break;
								case "E1ADRM1":
									E1EDL20.children().each { E1ADRM1 ->
											switch ("${E1ADRM1.name()}") {
												case "PARTNER_Q":
													if ("${E1ADRM1.text()}" != "") {
														PARTNER_Q = "${E1ADRM1.text()}".replaceFirst ("^0*", "");
													}
													break;
												case "PARTNER_ID":
													if ("${E1ADRM1.text()}" != "") {
														PARTNER_ID = "${E1ADRM1.text()}".replaceFirst ("^0*", "");
													}
													break;
												case "NAME1":
													if ("${E1ADRM1.text()}" != "") {
														NAME1 = "${E1ADRM1.text()}".replaceFirst ("^0*", "");
													}
													break;
												case "NAME2":
													if ("${E1ADRM1.text()}" != "") {
														NAME2 = "${E1ADRM1.text()}".replaceFirst ("^0*", "");
													}
													break;
												case "STREET1":
													if ("${E1ADRM1.text()}" != "") {
														STREET1 = "${E1ADRM1.text()}".replaceFirst ("^0*", "");
													}
													break;
												case "POSTL_COD1":
													if ("${E1ADRM1.text()}" != "") {
														POSTL_COD1 = "${E1ADRM1.text()}".replaceFirst ("^0*", "");
													}
													break;
												case "CITY1":
													if ("${E1ADRM1.text()}" != "") {
														CITY1 = "${E1ADRM1.text()}".replaceFirst ("^0*", "");
													}
													break;
												case "COUNTRY1":
													if ("${E1ADRM1.text()}" != "") {
														COUNTRY1 = "${E1ADRM1.text()}".replaceFirst ("^0*", "");
													}
													break;
											}
									}
									E1ADRM1_tmp = E1ADRM1_tmp + PARTNER_Q + sep + PARTNER_ID + sep + NAME1 + sep + NAME2 + sep + STREET1 + sep + POSTL_COD1 + sep + CITY1 + sep + COUNTRY1 + sep;

									PARTNER_Q  = "";
									PARTNER_ID = "";
									NAME1      = "";
									NAME2      = "";
									STREET1    = "";
									POSTL_COD1 = "";
									CITY1      = "";
									COUNTRY1   = "";

									E1ADRM1_child = E1ADRM1_child + "E1ADRM1" + FixedValue(E1ADRM1_tmp, len_E1ADRM1) + "\n";
									E1ADRM1_tmp = "";
									break;

								case "E1EDT13":
									E1EDL20.children().each { E1EDT13 ->
											switch ("${E1EDT13.name()}") {
												case "QUALF":
													if ("${E1EDT13.text()}" != "") {
														QUALF = "${E1EDT13.text()}";
													}
													break;
												case "NTEND":
													if ("${E1EDT13.text()}" != "") {
														NTEND = "${E1EDT13.text()}".replaceFirst ("^0*", "");
													}
													break;
											}
									}
									E1EDT13_tmp = E1EDT13_tmp + QUALF + sep + NTEND + sep;
									QUALF = "";
									NTEND = "";
									E1EDT13_child = E1EDT13_child + "E1EDT13" + FixedValue(E1EDT13_tmp, len_E1EDT13) + "\n";
									E1EDT13_tmp = "";
									break;

								case "E1EDL24":
									E1EDL20.children().each { E1EDL24 ->
											switch ("${E1EDL24.name()}") {
												case "POSNR":
													if ("${E1EDL24.text()}" != "") {
														POSNR = "${E1EDL24.text()}".replaceFirst ("^0*", "");
													}
													break;
												case "MATNR":
													if ("${E1EDL24.text()}" != "") {
														MATNR = "${E1EDL24.text()}".replaceFirst ("^0*", "");
													}
													break;
												case "LFIMG":
													if ("${E1EDL24.text()}" != "") {
														LFIMG = "${E1EDL24.text()}";
													}
													break;
												case "VRKME":
													if ("${E1EDL24.text()}" != "") {
														VRKME = "${E1EDL24.text()}".replaceFirst ("^0*", "");
														if (VRKME.contains("PZ") || VRKME.contains("Pce") || VRKME.contains("ST")) {
														    VRKME = "PC";
														}
													}
													break;
												case "CHARG":
													if ("${E1EDL24.text()}" != "") {
														CHARG = "${E1EDL24.text()}";
													}
													break;
												case "LICHN":
													if ("${E1EDL24.text()}" != "") {
														LICHN = "${E1EDL24.text()}".replaceFirst ("^0*", "");
													}
													break;
												case "WERKS":
													if ("${E1EDL24.text()}" != "") {
														WERKS = "${E1EDL24.text()}".replaceFirst ("^0*", "");
													}
													break;
												case "LGORT":
													if ("${E1EDL24.text()}" != "") {
														LGORT = "${E1EDL24.text()}".replaceFirst ("^0*", "");
													}
													break;
												case "VFDAT":
													if ("${E1EDL24.text()}" != "") {
														VFDAT = "${E1EDL24.text()}".replaceFirst ("^0*", "");
													}
													break;
												case "ZMM480_E1EDL11":
													E1EDL24.children().each { E1EDL11  ->
															switch ("${E1EDL11.name()}") {
																case "ZHU1":
																	if ("${E1EDL11.text()}" != "") {
																		ZHU1 = "${E1EDL11.text()}";
																	}
																	break;
																case "ZQUANTHU1":
																	if ("${E1EDL11.text()}" != "") {
																		ZQUANTHU1 = "${E1EDL11.text()}";
																		if (!ZQUANTHU1.matches("^.*\\.0*")) {
																			ZQUANTHU1 += ".000";
																		}
																	}
																	break;
															}

													}
													E1EDL11_tmp = E1EDL11_tmp + ZHU1 + sep + ZQUANTHU1 + sep;
													if (ZHU1 != "" && ZQUANTHU1 != "") {
													    E1EDL11_child = E1EDL11_child + "E1EDL11" + FixedValue(E1EDL11_tmp, len_E1EDL11) + "\n";
													}
													ZHU1 = "";
													ZQUANTHU1 = "";
													E1EDL11_tmp = "";
													break;
											}

									}

									E1EDL24_tmp = E1ADRM1_tmp + POSNR + sep + MATNR + sep + LFIMG + sep + VRKME + sep + CHARG + sep + LICHN + sep + WERKS + sep + LGORT + sep + VFDAT + sep;

									POSNR = "";
									MATNR = "";
									LFIMG = "";
									VRKME = "";
									CHARG = "";
									LICHN = "";
									WERKS = "";
									LGORT = "";
									VFDAT = "";

									E1EDL24_child = E1EDL24_child + "E1EDL24" + FixedValue(E1EDL24_tmp, len_E1EDL24) + "\n";
									EE1EDL24_tmp = "";
									break;

							}
						    E1EDL24_E1EDL11 = E1EDL24_E1EDL11 + E1EDL24_child + E1EDL11_child;
						    E1EDL11_child = "";
						    E1EDL24_child = "";

					}

					E1EDL20_tmp = E1EDL20_tmp + VBELN + sep + BOLNR + sep;
					E1EDL20_child = E1EDL20_child + "E1EDL20" + FixedValue(E1EDL20_tmp, len_E1EDL20) + "\n";
					E1EDL20_tmp = "";
					
					VBELN           = "";
                    BOLNR           = "";

					txt = txt + E1EDL20_child + E1ADRM1_child + E1EDT13_child + E1EDL24_E1EDL11;
					E1EDL20_child   = "";
					E1ADRM1_child   = "";
					E1EDT13_child   = "";
					E1EDL24_E1EDL11 = "";
				}

			}
	}
	txt = EDI_DC40_child + txt;
	txt = txt.substring(0,txt.length() - 1); // Toglie l'ultimo "\n" 
	message.setBody(txt);
	return message;
}