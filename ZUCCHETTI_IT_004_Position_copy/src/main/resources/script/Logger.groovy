import com.sap.gateway.ip.core.customdev.util.Message;


/**
 *Logs the message body with the name specified in property LogName
 */
def Message logger(Message message) {
	def body = message.getBody(java.lang.String) as String;
	def properties = message.getProperties();
	def messageLog = messageLogFactory.getMessageLog(message);
	
	String isLogActive = properties.get("LogState");
	String logName = properties.get("LogName");
	
	if(isLogActive.equals("true")){
		if(messageLog != null){
			messageLog.addAttachmentAsString(logName, body, "text/plain");
		}
	}
	return message;
}