import com.sap.it.api.mapping.*;
import com.sap.gateway.ip.core.customdev.util.Message;
import java.util.HashMap;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;
import com.sap.it.api.ITApiFactory;
import com.sap.it.api.ITApi;
import com.sap.it.api.mapping.ValueMappingApi;

def Message logger(Message message) {
	def body = message.getBody(java.lang.String) as String;
	def properties = message.getProperties();
	def messageLog = messageLogFactory.getMessageLog(message);
	
	String isLogActive = properties.get("LogState");
	String logName = properties.get("LogName");
	
	if(isLogActive.equals("true")){
		if(messageLog != null){
			messageLog.addAttachmentAsString(logName, body, "text/xml");
		}
	}
	return message;
}

def Message loggerError(Message message) {
	def body = message.getBody(java.lang.String) as String;
	def properties = message.getProperties();
	def messageLog = messageLogFactory.getMessageLog(message);
	def exceptionMsg = message.getProperty("CamelExceptionCaught")
	
	String isLogActive = properties.get("LogState");
	String logName = properties.get("LogName");
	
	if(isLogActive.equals("true")){
		if(messageLog != null){
		    messageLog.addAttachmentAsString(logName,exceptionMsg.getMessage() + "\n" + body,"text/plain");
		}
	}
	return message;
}

def Message setActualTimeFormatted(Message message){
	
	def properties = message.getProperties();
	Date now = new Date(System.currentTimeMillis());
	message.setProperty("newLastModTime", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.mmm +0000").format(now).toString());
	
	return message;
	
}

def Message checkInsUpdUser(Message message){
	
	String result="";
	def properties = message.getProperties();
	String lastRunTime= properties.get("lastModTime");
	String registrationDateTime= properties.get("registrationDateTime");
	String userLastModTime= properties.get("userLastModTime");
	
	lastRunTime= lastRunTime.substring(0, 23);
	String[] lastRunTimeSplitted= lastRunTime.split("-|T|:|\\.");
	registrationDateTime= registrationDateTime.substring(0, 23);
	String[] registrationDateTimeSplitted= registrationDateTime.split("-|T|:|\\.");
	userLastModTime= userLastModTime.substring(0, 23);
	String[] userLastModTimeSplitted= userLastModTime.split("-|T|:|\\.");
	
	eq1=0;
	for(int i=0; i<lastRunTimeSplitted.length && eq==0; i++){
		eq1= lastRunTimeSplitted[i].compareTo(registrationDateTimeSplitted[i]);
	}
	eq2=0;
	for(int i=0; i<lastRunTimeSplitted.length && eq==0; i++){
		eq2= lastRunTimeSplitted[i].compareTo(userLastModTimeSplitted[i]);
	}
	
	if(eq1<0){
		result="INS";
	} else if(eq2<0){
		result="UPD";
	} else result="NONE";
	
	message.setProperty("InsUpdUSER", result);
}

	/* User Defined Functions to be called from mapping: */

/**
 *Returns the e-mail of the customer idCustomer from the HashMap stored in the property mapPropertyName
 */
def String getStringFromHashMapProperty(String mapPropertyName, String idCustomer, MappingContext context){
	
	def hashMap = context.getProperty(mapPropertyName);
	return hashMap.get(idCustomer);
}

/**
 *Returns the String stored in the property propertyName
 */
def String getProperty(String propertyName, MappingContext context){
	
	def property = context.getProperty(propertyName);
	if(property!=null&&property!=""){
		return property;
	}else return "";
}

/**
 *Returns the String stored in the header headerName
 */
def String getHeader(String headerName, MappingContext context){
	
	def header = context.getHeader(headerName);
	if(header!=null&&header!=""){
		return header;
	}else return "";
}

/**
 *Returns a char String of length len with random alphanumeric chars
 */
def String getRandomAlphaNumeric(int len, MappingContext context){
    
   char[] ch = [ '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
       'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L',
       'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X',
       'Y', 'Z', 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j',
       'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v',
       'w', 'x', 'y', 'z' ];
  
   char[] c=new char[len];
   Random random=new Random();
   for (int i = 0; i < len; i++) {
     c[i]=ch[random.nextInt(ch.length)];
   }
   
   return new String(c);
}


/**
 *Returns the value from the Value Mapping specified
 */
 def String dynamicValueMap(String sAgency, String sSchema, String tAgency, String tSchema, String key){
    
    def service = ITApiFactory.getApi(ValueMappingApi.class, null);
    
    if( service != null) {
        
        String val= service.getMappedValue(sAgency, sSchema, key, tAgency, tSchema);
        if ( val.equals("") || val ==null ){
            return "default"
        }
        else return val
    }
        
    return null;
    
}