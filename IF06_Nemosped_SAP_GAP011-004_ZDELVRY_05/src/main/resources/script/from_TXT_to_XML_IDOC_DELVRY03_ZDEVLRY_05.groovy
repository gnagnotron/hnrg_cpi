import com.sap.gateway.ip.core.customdev.util.Message;
import java.util.HashMap;
import java.text.*;
import groovy.util.*;
import groovy.xml.*;

def String[] FixedValues(String row, int[] lengths) {
    int totLength = 0;
    for (int j = 0; j < lengths.length; j++) {
        totLength += lengths[j];
    }
    
    if (row.length() < totLength) {
        row = row.padRight(totLength);
    }
    
    def fields = [];
    int sumLen = 0;
        for (int i = 0; i < lengths.length; i++) {
            fields[i] = row.substring(sumLen, sumLen + lengths[i]).trim();
            sumLen += lengths[i];
        }
    return fields;
}

def Message processData(Message message) {

	def body = message.getBody(java.lang.String) as String;
	
	def idoc            = "";
	String[] values;
	String[] rows;
	
	def len_ZE1EDL20     = [10,8] as int[];

	//EDI_DC40
    def TABNAM          = "EDI_DC40";
    def IDOCTYP         = "ZDELVRY_05";
    def CIMTYP          = "";
    def MESTYP          = "ZDELVRY_05";
	def SNDPRT          = "LS";
	def RCVPRT          = "LS";
	def SNDPRN          = "CPI_HTTPS";
	def SNDPOR          = message.getProperty("SNDPOR");
	def RCVPRN          = message.getProperty("RCVPRN");
	
	//ZE1EDL20
	def VBELN           = "";
	def ZPODAT          = "";

	//DATE TIME
	def sFormat = "yyyyMMdd HHmmss";
	SimpleDateFormat dateFormat = new SimpleDateFormat(sFormat);
	dateFormat.setTimeZone(TimeZone.getTimeZone("CET"));
	Date date = new Date();
	def CREDAT = dateFormat.format(date).substring(0,8);
	def CRETIM = dateFormat.format(date).substring(9);
   
   
    idoc = idoc + """<EDI_DC40 SEGMENT="1">""" +
					"<TABNAM>" + TABNAM + "</TABNAM>" + 
					"<DOCNUM></DOCNUM>" + 
					"<IDOCTYP>"+ IDOCTYP + "</IDOCTYP>"+ 
					"<CIMTYP>" + CIMTYP + "</CIMTYP>" + 
					"<MESTYP>" + MESTYP + "</MESTYP>" + 
					"<SNDPOR>" + SNDPOR + "</SNDPOR>" + 
					"<SNDPRT>" + SNDPRT + "</SNDPRT>" + 
					"<SNDPRN>" + SNDPRN + "</SNDPRN>" + 
					"<RCVPRT>" + RCVPRT + "</RCVPRT>" + 
					"<RCVPRN>" + RCVPRN + "</RCVPRN>" + 
					"<CREDAT>" + CREDAT + "</CREDAT>" + 
					"<CRETIM>" + CRETIM + "</CRETIM>" + 
			    "</EDI_DC40>";
   
	rows = body.split("\n");
	for (int i = 0; i < rows.length; i++) {
 
// 		if (rows[i].startsWith("EDI_DC40")) {
// 			values = (FixedValues(rows[i], len_EDI_DC40));
// 			val = values.split(sep, -1);
			
// 			idoc = idoc + """<EDI_DC40 SEGMENT="1">""" +
// 								"<TABNAM>" + TABNAM + "</TABNAM>" + 
// 								"<DOCNUM>" + val[1] + "</DOCNUM>" + 
// 								"<IDOCTYP>"+ IDOCTYP+ "</IDOCTYP>"+ 
// 								"<CIMTYP>" + CIMTYP + "</CIMTYP>" + 
// 								"<MESTYP>" + MESTYP + "</MESTYP>" + 
// 								"<SNDPRT>" + SNDPRT + "</SNDPRT>" + 
// 								"<SNDPRN>" + val[6] + "</SNDPRN>" + 
// 								"<RCVPRT>" + RCVPRT + "</RCVPRT>" + 
// 								"<RCVPRN>" + RCVPRN + "</RCVPRN>" + 
// 								"<CREDAT>" + CREDAT + "</CREDAT>" + 
// 								"<CRETIM>" + CRETIM + "</CRETIM>" + 
// 								"</EDI_DC40>";
// 		}
		if (rows[i].startsWith("E1EDL20")){
			values = (FixedValues(rows[i].substring("E1EDL20".length()), len_ZE1EDL20));
			
			idoc = idoc +   """<E1EDL20 SEGMENT="1">""" +
								"<VBELN>" + values[0].padLeft(10, "0") + "</VBELN>" +
								"""<ZE1EDL20 SEGMENT="1"><ZPODAT>""" + values[1] + "</ZPODAT></ZE1EDL20>" +
							"</E1EDL20>";
		}
	}
	
	idoc = """<?xml version="1.0" encoding="UTF-8"?>""" + 
			"<ZDEVLRY_05>" + """<IDOC BEGIN="1">""" + idoc + 
			"</IDOC>" + "</ZDEVLRY_05>";

	message.setBody(idoc);
	return message;
}
