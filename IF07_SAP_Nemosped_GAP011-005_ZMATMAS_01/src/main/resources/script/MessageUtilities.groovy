import com.sap.it.api.mapping.*;
import com.sap.it.api.ITApi
import com.sap.it.api.ITApiFactory;
import com.sap.it.api.securestore.*;
import com.sap.gateway.ip.core.customdev.util.Message;
import java.util.HashMap;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;


/**
 *Logs the message body with the name specified in property LogName
 */
def Message logger(Message message) {
	def body = message.getBody(java.lang.String) as String;
	def properties = message.getProperties();
	def messageLog = messageLogFactory.getMessageLog(message);
	def exceptionMsg = message.getProperty("CamelExceptionCaught")
	
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
	message.setProperty("lastModTime", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.mmm +0000").format(now).toString());
	
	return message;
	
}

def Message incrementIterator(Message message) {
	
	prop = message.getProperties();
	iterator = prop.get("iterator");
	Integer i= Integer.valueOf(iterator);
	i=i+1;
	message.setProperty("iterator", i);
	return message;
}

/**
 *Returns a char String of length len with random alphanumeric chars
 */
def String getRandomAlphaNumeric(int len){
    
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
 * Returns a String formatted as a random SAP MessageID
 */
def Message getMessageIDInProperty(Message message){
	
	String messageID = getRandomAlphaNumeric(8) + "-" + getRandomAlphaNumeric(4) + "-" + getRandomAlphaNumeric(4) + "-" + getRandomAlphaNumeric(4) + "-" + getRandomAlphaNumeric(12);
	message.setProperty("MessageID", messageID);
	
	return message;
	
}

/**
 * Returns a secure parameter named like the property SecureParameterName in a property with the same name of the parameter
 */
def Message getSecureParameter(Message message){
	
	def properties = message.getProperties();
	String parameterName = properties.get("SecureParameterName");
	def service = ITApiFactory.getApi(SecureStoreService.class, null);
	def credential = service.getUserCredential(parameterName);
	String parameterValue;
	if (credential != null)
		parameterValue= new String(credential.getPassword());
	message.setProperty(parameterName, parameterValue);
	return message;
	
}

/**
 * Removes all the occurrences of the string saved in property "StringToRemove" in body
 */
def Message removeString(Message message){
	
	def body = message.getBody(java.lang.String) as String;
	def properties = message.getProperties();
	String stringToRemove = properties.get("StringToRemove");
	
	body= body.replace(stringToRemove, "");
	message.setBody(body);
	return message;
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
 *UDF that returns a char String of length len with random alphanumeric chars
 */

def String getRandomAlphaNumericUDF(int len, MappingContext context){
	return getRandomAlphaNumeric(len);
}