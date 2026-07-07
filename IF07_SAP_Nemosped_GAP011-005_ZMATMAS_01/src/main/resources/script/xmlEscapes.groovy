import com.sap.gateway.ip.core.customdev.util.Message;
import java.util.HashMap;

def Message processData(Message message) {
    def body = message.getBody(java.lang.String) as String;
    
    body = body.replaceAll("&", "&amp;");
    body = body.replaceAll("\"", "&quot;");
    body = body.replaceAll("'", "&apos;");
    body = body.replaceAll("<", "&lt;");
    body = body.replaceAll(">", "&gt;");
    
    message.setBody(body);
    return message;
}