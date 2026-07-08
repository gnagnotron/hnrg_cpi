import com.sap.gateway.ip.core.customdev.util.Message;
import java.util.HashMap;
import groovy.json.*;
import java.lang.*;
import java.text.SimpleDateFormat;
import java.util.Calendar;
def Message processData(Message message) {

    def displayMaps = { String mapName, Map map ->
		StringBuilder sb = new StringBuilder()
		sb.append(mapName).append("\n")
		map.each { key, value -> sb.append(key).append(" = ").append(value).append("\n")  }
		return sb.toString()
	}
    def headers = displayMaps("##Headers:", message.getHeaders())
    def properties = displayMaps("##Properties:", message.getProperties())
    
    String timeStamp = new SimpleDateFormat("HH:mm:ss.SSS").format(new Date());
    String logTitle = timeStamp + " LOG finale ";
    
    StringBuilder sb = new StringBuilder()
	def body = message.getBody(java.lang.String) as String;
	for (int i = 0; i < body.length(); i++) {
	    if (i > 0 && (i % 200 == 0)) {
	        sb.append("\n");
	    }
	    sb.append(body.charAt(i));
	}
	body = sb.toString();
    def messageLog = messageLogFactory.getMessageLog(message);
    if(messageLog != null){
        messageLog.addAttachmentAsString(logTitle, "### BODY ### \n" + body + "\n ### HEADERS ### \n" + headers + "\n ### PROPERTIES ### \n" + properties, "text/plain");
    }
    return message;
}
