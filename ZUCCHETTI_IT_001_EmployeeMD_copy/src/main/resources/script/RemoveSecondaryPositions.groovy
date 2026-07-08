import com.sap.gateway.ip.core.customdev.util.Message

def Message processData(Message message) {
    // Read the input XML payload
    def inputPayload = message.getBody(java.io.InputStream)
    def rootNode = new XmlParser(false, false).parse(inputPayload)

    // Iterate through each CompoundEmployee node
    rootNode.CompoundEmployee.each { compoundEmployee ->
        def employmentToRemove = []

        // Iterate through employment_information nodes under person
        compoundEmployee.person.employment_information.each { employmentInformation ->
            def isPrimary = employmentInformation.isPrimary.text()
            
            // Collect employment_information for removal if isPrimary is not "true"
            if (isPrimary != "true") {
                employmentToRemove.add(employmentInformation)
            }
        }

        // Remove non-primary employment_information nodes
        employmentToRemove.each { employmentInfo ->
            employmentInfo.parent().remove(employmentInfo)
        }
    }

    // Convert the modified XML back to a string
    def writer = new StringWriter()
    def printer = new XmlNodePrinter(new PrintWriter(writer))
    printer.preserveWhitespace = true
    printer.print(rootNode)
    message.setBody(writer.toString())

    return message
}
