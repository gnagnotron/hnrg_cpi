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
    body += "     ";

    def len_EDI_DC40    = [10,16,30,30,30,2,10,2,10,8,6] as int[];
    def len_E1EDL20     = [10] as int[];
    def len_E1EDL24     = [6,18,10,13,3] as int[];
    def len_E1EDL11     = [20,13] as int[];

    def TABNAM          = "EDI_DC40";
    def IDOCTYP         = "ZDELVRY_03";
    def CIMTYP          = "";
    def MESTYP          = "ZDELVRY_03";
	def SNDPRT          = "LS";
	def RCVPRT          = "LS";
	def SNDPRN          = "CPI_HTTPS";
	
	def SNDPOR = message.getProperty("SNDPOR");
	def RCVPRN = message.getProperty("RCVPRN");


    String[] values;
    String[] rows;
    
    def sFormat = "yyyyMMdd HHmmss";
    SimpleDateFormat dateFormat = new SimpleDateFormat(sFormat);
    dateFormat.setTimeZone(TimeZone.getTimeZone("CET"));
    Date date = new Date();
    
    def CREDAT = dateFormat.format(date).substring(0,8);
    def CRETIM = dateFormat.format(date).substring(9);

    def idoc = "";
    
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
 
    //     if (rows[i].startsWith("EDI_DC40")) {
        
    //         values = (FixedValues(rows[i], len_EDI_DC40));
        
    //         val = values.split(";" , -1);   / Non scarta le stringhe vuote finali { 2;3;; }/
            
    //         idoc = idoc + """<EDI_DC40 SEGMENT="1">""" +
    // 							"<TABNAM>" + TABNAM + "</TABNAM>" + 
				// 				"<DOCNUM>" + val[1].replaceFirst("^0*", "") + "</DOCNUM>" + 
				// 				"<IDOCTYP>"+ IDOCTYP+ "</IDOCTYP>"+ 
				// 				"<CIMTYP>" + CIMTYP + "</CIMTYP>" + 
				// 				"<MESTYP>" + MESTYP + "</MESTYP>" + 
				// 				"<SNDPRT>" + SNDPRT + "</SNDPRT>" + 
				// 				"<SNDPRN>" + val[6].replaceFirst("^0*", "") + "</SNDPRN>" + 
				// 				"<RCVPRT>" + RCVPRT + "</RCVPRT>" + 
				// 				"<RCVPRN>" + RCVPRN + "</RCVPRN>" + 
				// 				"<CREDAT>" + CREDAT + "</CREDAT>" + 
				// 				"<CRETIM>" + CRETIM + "</CRETIM>" + 
				// 			"</EDI_DC40>";
            
    //         continue;
    //     }

        if (rows[i].startsWith("E1EDL20")) {

            values = (FixedValues(rows[i].substring("E1EDL20".length()), len_E1EDL20));

            idoc = idoc + """<E1EDL20 SEGMENT="1"><VBELN>""" + values[0].replaceFirst("^0*", "") + "</VBELN></E1EDL20>";

            continue;
        }

        if (rows[i].startsWith("E1EDL24")) {

            values = (FixedValues(rows[i].substring("E1EDL24".length()), len_E1EDL24));

            def segmentToInsert = """<E1EDL24 SEGMENT="1"><POSNR>""" + values[0].replaceFirst("^0*", "") + "</POSNR><MATNR>" + values[1].replaceFirst("^0*", "") + "</MATNR><CHARG>" + values[2] + "</CHARG><LFIMG>" +
                                values[3].replaceFirst("^0*", "") + "</LFIMG><VRKME>" + values[4].replaceFirst("^0*", "") + "</VRKME></E1EDL24>";

            def builder = StringBuilder.newInstance(idoc);
            idoc = builder.insert(idoc.lastIndexOf("</E1EDL20>"), segmentToInsert);

            continue;
        }

        if (rows[i].startsWith("E1EDL11")) {

            values = (FixedValues(rows[i].substring("E1EDL11".length()), len_E1EDL11));

            def segmentToInsert = """<ZE1EDL11 SEGMENT="1"><ZSERIALE>""" + values[0] + "</ZSERIALE><ZQUANTHU>" + values[1].replaceFirst("^0*", "") + "</ZQUANTHU></ZE1EDL11>";

            def builder = StringBuilder.newInstance(idoc);
            idoc = builder.insert(idoc.lastIndexOf("</E1EDL24>"), segmentToInsert);

            continue;
        }
    }

    idoc = """<?xml version="1.0" encoding="UTF-8"?><ZDELVRY_03><IDOC BEGIN="1">""" + idoc + "</IDOC></ZDELVRY_03>";

	message.setBody(idoc);
	return message;
}
