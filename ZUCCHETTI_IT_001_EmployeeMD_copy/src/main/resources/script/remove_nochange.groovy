import com.sap.gateway.ip.core.customdev.util.Message

def Message processData(Message message) {
    def inputPayload = message.getBody(java.io.InputStream)
    def rootNode = new XmlParser(false, false).parse(inputPayload)

    // Funzione che ritorna true se TUTTI gli <action> nei discendenti sono NO CHANGE o DELETE
    def allNoChangeOrDelete
    allNoChangeOrDelete = { node ->
        def actions = node.depthFirst().findAll { it.name() == 'action' }
        // Se non ci sono action, non rimuovere il nodo
        if (actions.isEmpty()) return false
        // Ritorna true solo se tutte le action sono NO CHANGE o DELETE
        actions.every { 
            def val = it.text().trim()
            val == 'NO CHANGE' || val == 'DELETE'
        }
    }

    // Funzione ricorsiva per pulire l'albero
    def cleanNode
    cleanNode = { node ->
        def children = node.children().findAll { it instanceof Node }
        children.each { cleanNode(it) }

        // Rimuovi il nodo solo se tutti i discendenti hanno action NO CHANGE o DELETE
        if (children && allNoChangeOrDelete(node)) {
            node.parent()?.remove(node)
        }
    }

    // Applica la pulizia partendo dalla radice
    cleanNode(rootNode)

    // Ricostruisci l’XML
    def writer = new StringWriter()
    def printer = new XmlNodePrinter(new PrintWriter(writer))
    printer.preserveWhitespace = true
    printer.print(rootNode)
    message.setBody(writer.toString())

    return message
}
