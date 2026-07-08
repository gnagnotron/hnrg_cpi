import com.sap.gateway.ip.core.customdev.util.Message
import java.util.LinkedHashSet

Message processData(Message message) {

    // Legge il body come stringa
    def body = message.getBody(String)

    // ️ Aggiunge \n dopo ogni "||" se manca
    body = body.replaceAll(/\|\|(?!\r?\n)/, "||\n")

    //  Rimuove l'ultimo "||" se il file finisce con "||"
    body = body.replaceAll(/\|\|\s*$/, "")

    //  Divide in righe
    def lines = body.split("\\r?\\n")

    //  Rimuove duplicati mantenendo l’ordine
    Set<String> uniqueLines = new LinkedHashSet<>()

    lines.each { line ->
        if (line?.trim()) { // evita righe vuote
            uniqueLines.add(line)
        }
    }

    //  Ricompone il body
    def result = uniqueLines.join("\n")

    // Imposta il nuovo body
    message.setBody(result)

    return message
}
