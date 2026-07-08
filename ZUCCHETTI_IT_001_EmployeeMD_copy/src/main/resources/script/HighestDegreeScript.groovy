import com.sap.gateway.ip.core.customdev.util.Message;
// Script used to process the Highest Degree of a given employee

def Message processData(Message message) {
    // Get the input payload from the message
    def inputPayload = message.getBody(String) as String

    // Parse the input XML
    def root = new XmlSlurper().parseText(inputPayload)

    // Extract and sort the degree values
    def degrees = root.Background_Education.degree.collect { Integer.parseInt(it.text()) }
    degrees.sort { -it }

    // Create a new XML with the highest degree value
    def highestDegree = degrees[0]
    def outputXML = """
    <root>
        <degree>$highestDegree</degree>
    </root>
    """

    // Update the message body with the transformed payload
    message.setBody(outputXML.toString())

    // Return the updated message
    return message
}