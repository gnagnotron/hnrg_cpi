import com.sap.gateway.ip.core.customdev.util.Message
import java.nio.charset.StandardCharsets
import java.io.OutputStreamWriter
import groovy.xml.*

def Message processData(Message message) {
    def payload = message.getBody(java.lang.String.class)
    def root = new XmlParser().parseText(payload)
    def csv = new StringWriter()

    def xmlParser = new XmlSlurper().parseText(payload)
    def constant1 = "001004"
    def constant4 = "H1PAGHEWEBAN"
    def idDipendente = message.getProperty("EmployeeID")
        idDipendente = idDipendente.padLeft(7, '0')
    
    def startDate =  "20000101"//getProperty("startDate")
    def result = ""
    def finalPayload ="""<soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/" xmlns:hrut="http://hrut_brecvinsertrec.ws.localhost/">
   <soapenv:Header/>
   <soapenv:Body>
      <hrut:hrut_brecvinsertrec_Run>
         <hrut:m_UserName>ws_impver</hrut:m_UserName>
         <hrut:m_Password>ws_impver</hrut:m_Password>
         <hrut:m_Company>001</hrut:m_Company>
         <hrut:pFILE>
    """
    
    // Loop through each element in the XML and generate the CSV rows
    xmlParser.children().each { element ->
    def fieldName = element.name()
    def fieldContent = element.text()
    def fieldValue = ""
    def fieldDate = ""
    
    if (!fieldContent.equals(""))
    {
    if (fieldContent.contains('%'))
    {
        fieldValue = fieldContent.split('%')[0]
        fieldDate = fieldContent.split('%')[1]
    }else
    {
        fieldValue = fieldContent
        fieldDate = startDate;
    }
    
    
    
    //IF CHANGE, use change date
    
    
    // Modify this condition based on your mapping requirements
    
    switch (fieldName) {
        case "ANEMAIL":
            result += "${constant1};${idDipendente};${fieldDate};${constant4};${fieldName};RA;${fieldValue}||\n"
            break
        case "IDIBANCNTY":
        case "IDCURRENCY2":
        case "IDIBANDIGT":
        case "ANBCODE":
        case "IDABI":
        case "IDCAB":
        case "ANCHKACCEM":
            result += "${constant1};${idDipendente};${fieldDate};${constant4};${fieldName};ZA;${fieldValue}||\n"
            break
        case "IDTPSUBJ":
            result += "${constant1};${idDipendente};${fieldDate};${constant4};${fieldName};DIPEND;${fieldValue}||\n"
            break        
        default:
            result += "${constant1};${idDipendente};${fieldDate};${constant4};${fieldName};;${fieldValue}||\n"
            break
        }
    }
    }
    
    // Remove the trailing "||" if needed
    if (result.endsWith("||\n")) 
    {    
        result = result[0..-4]
    }
    finalPayload += result + """ </hrut:pFILE>
      </hrut:hrut_brecvinsertrec_Run>
   </soapenv:Body>
</soapenv:Envelope>"""

    message.setBody(finalPayload)
    return message
}