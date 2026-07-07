import com.sap.gateway.ip.core.customdev.util.Message;
import java.util.HashMap;
import com.sap.it.api.ITApiFactory;
import com.sap.it.api.ITApi;
import com.sap.it.api.mapping.ValueMappingApi;

def Message processData(Message message) {
    
    def propertiesMap = message.getProperties();
    
    def RCVPRN = propertiesMap.get("RCVPRN") as String
    def RCVPRT = propertiesMap.get("RCVPRT") as String
    def RCVPFC = propertiesMap.get("RCVPFC") as String
    def MESTYP = propertiesMap.get("MESTYP") as String
    def MESCOD = propertiesMap.get("MESCOD") as String
    def MESFCT = propertiesMap.get("MESFCT") as String
    def CIMTYP = propertiesMap.get("CIMTYP") as String
    
    def logState = propertiesMap.get("LogState") as String
    
    if ( RCVPFC.equals("") || RCVPFC == null){
       RCVPFC = "##";
    }
    
    if ( MESCOD.equals("") || MESCOD == null){
       MESCOD = "###";
    }
    
    if ( MESFCT.equals("") || MESFCT == null){
       MESFCT = "###";
    }
    
    if ( CIMTYP.equals("") || CIMTYP == null){
       CIMTYP = "###";
    }
    
    String rcvPartnerProfile = "";
	
	if(CIMTYP != "###"){
		rcvPartnerProfile = RCVPRN+"_"+RCVPRT+"_"+RCVPFC+"_"+MESTYP+"_"+MESCOD+"_"+MESFCT+"_"+CIMTYP;
	}else{
		rcvPartnerProfile = RCVPRN+"_"+RCVPRT+"_"+RCVPFC+"_"+MESTYP+"_"+MESCOD+"_"+MESFCT;
	}
	
	def messageLog = messageLogFactory.getMessageLog(message);
	if(logState != "false")
	{
	    messageLog.addCustomHeaderProperty("WE20_STRING", rcvPartnerProfile);
	}

    message.setProperty("AAArcvPartnerProfile", rcvPartnerProfile.toString())
    
    def valueMapApi = ITApiFactory.getApi(ValueMappingApi.class, null);

    if( valueMapApi != null) {

        def IDocRoutingPath = valueMapApi.getMappedValue("SAP_ERP", 'SND_KEY', rcvPartnerProfile, "SCPI", 'RCV_RESOURCE') as String
        
        
        if ( IDocRoutingPath.equals("") || IDocRoutingPath == null ){
            message.setProperty("IDOC_ROUTING_PATH", "ERROR_NOT_FOUND")
        }
        else {
            message.setProperty("IDOC_ROUTING_PATH", IDocRoutingPath)
        }
    }


    return message;
}