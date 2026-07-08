import groovy.json.JsonOutput
import com.sap.gateway.ip.core.customdev.util.Message

def Message processData(Message message) {
    def body = message.getBody(String) // Ottieni l'XML dal messaggio
    body = body.replaceAll("</ZRECDATA>", "\n")
    body = body.replaceAll("<ZRECDATA>", "")

    message.setBody(body)
    return message
}
