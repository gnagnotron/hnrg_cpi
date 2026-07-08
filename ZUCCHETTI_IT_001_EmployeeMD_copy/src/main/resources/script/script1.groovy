import com.sap.gateway.ip.core.customdev.util.Message;
import java.text.SimpleDateFormat;
import groovy.xml.XmlUtil;

def Message processData(Message message) {
    // Get the XML payload
    def body = message.getBody(String);

    // Define the namespaces if necessary (update as needed)
    def namespaces = [
        '': 'urn:your-namespace-here' // Define your XML namespace if needed
    ];

    // Parse the XML
    def xml = new XmlParser().parseText(body);

    // Extract the values using XPath
    def personalEmailSD = xml.'**'.find { it.name() == 'email_information' && it.email_type.text() == 'P' }?.last_modified_on?.text()
    def businessEmailSD = xml.'**'.find { it.name() == 'email_information' && it.email_type.text() == 'B' }?.last_modified_on?.text()
    
    def personalEmail = xml.'**'.find { it.name() == 'email_information' && it.email_type.text() == 'P' }?.email_address?.text()
    def businessEmail = xml.'**'.find { it.name() == 'email_information' && it.email_type.text() == 'B' }?.email_address?.text()

    // Define a date formatter
    def dateFormatterInput = new SimpleDateFormat("yyyy-MM-dd");
    def dateFormatterOutput = new SimpleDateFormat("dd-MM-yyyy");

    // Convert the dates if not empty
    if (personalEmailSD) {
        personalEmailSD = dateFormatterOutput.format(dateFormatterInput.parse(personalEmailSD))
    }
    if (businessEmailSD) {
        businessEmailSD = dateFormatterOutput.format(dateFormatterInput.parse(businessEmailSD))
    }

    // Concatenate email addresses with their respective start dates
    def personalEmailConcat = (personalEmail && personalEmailSD) ? personalEmail + "%" + personalEmailSD : ""
    def businessEmailConcat = (businessEmail && businessEmailSD) ? businessEmail + "%" + businessEmailSD : ""

    // Set the results to properties
    message.setProperty("PersonalEmailConcat", personalEmailConcat)
    message.setProperty("BusinessEmailConcat", businessEmailConcat)

    return message
}
