import com.sap.gateway.ip.core.customdev.util.Message
import java.nio.charset.StandardCharsets
import java.io.OutputStreamWriter
import groovy.xml.*
import com.sap.it.api.ITApiFactory
import com.sap.it.api.securestore.SecureStoreService

/* 
   Script per conversione dati in formato CSV conforme a Zucchetti.
   Questa versione produce solo il contenuto interno del <hrut:pFILE>,
   senza l'envelope SOAP (che verrà aggiunto a valle nel Content Modifier).
*/

def Message getSecureParameter(Message message){
	
	def properties = message.getProperties()

	String usernameAlias = properties.get("Username_credential")
	String passwordAlias = properties.get("Password_credential")

	def service = ITApiFactory.getApi(SecureStoreService.class, null)

	def usernameCred = service.getUserCredential(usernameAlias)
	def passwordCred = service.getUserCredential(passwordAlias)

	String usernameValue = usernameCred ? new String(usernameCred.getPassword()) : null
	String passwordValue = passwordCred ? new String(passwordCred.getPassword()) : null

	message.setProperty("ZUCCHETTI_UserName_value", usernameValue)
	message.setProperty("ZUCCHETTI_Password_value", passwordValue)

	return message
}


def Message processData(Message message) {
	def payload = message.getBody(java.lang.String.class)
	def xmlParser = new XmlSlurper().parseText(payload)


	def constant1 = message.getProperty("Company")
	def constant4 = "ANAGRAFICO"
	def idDipendente = ""
	
	def emp_payrollId = message.getProperty("Emp_Payroll_id")
	if(emp_payrollId ==null || emp_payrollId == ""){
	    idDipendente = message.getProperty("Payroll_id")?.padLeft(7, '0')
	}
	else {
	    idDipendente = emp_payrollId.padLeft(7, '0')
	}
	

	def credentials = getSecureParameter(message)
	def username = credentials.getProperties().get("ZUCCHETTI_UserName_value")
	def password = credentials.getProperties().get("ZUCCHETTI_Password_value")

	def startDate = "20000101"
	def result = ""
	def osList = new String[50]

	// Loop through each element in the XML and generate the CSV rows
	xmlParser.children().each { element ->
		def fieldName = element.name()
		def fieldContent = element.text()
		def fieldValue = ""
		def fieldDate = ""
		def aammDataRiferimento = ""

		if (!fieldContent.equals("")) {
			if (fieldContent.contains('%')) {
				fieldValue = fieldContent.split('%')[0]
				fieldDate = fieldContent.split('%')[1]
			} else {
				fieldValue = fieldContent
				fieldDate = startDate
			}

			aammDataRiferimento = fieldDate.toString().substring(6,8) + fieldDate.toString().substring(2,4)

			switch (fieldName) {
				case "ANEMAIL":
				case "TPADDRESS":
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
					result += "${constant1};${idDipendente};${fieldDate};${constant4};${fieldName};${fieldValue};${fieldValue}||\n"
					break
				case "DTENDSB":
					result += "${constant1};${idDipendente};${fieldDate};${constant4};${fieldName};DIPEND;${fieldValue}||\n"
					break
				case "H1CVOCE":
				case "H1NPROGV":
				case "H1CTIPOVOCE":
				case "H1IVOCE":
				case "H1CTIPOCOMPV":
				case "H1CMODLGENRN":
				case "H1CPIATABL2":
				case "H1CFREQ":
					result += "${constant1};${idDipendente};${fieldDate};${constant4};${fieldName};P0063A 1;${fieldValue}||\n"
					break
				case "H1CELEMPAGA":
					def i = 0
					if (fieldContent.contains("~")) {
						for (String s in fieldContent.split('~')) {
							if (s != "") {
								def cont = s
								def name = cont.split('%')[0]
								def date = cont.split('%')[1]
								osList[i] = name
								if (osList[i] in ["ITA_4","ITA_12"]) {
									result += "${constant1};${idDipendente};${date};${constant4};${fieldName};${name};${name}||\n"
								}
								i++
							}
						}
					}
					break
				case "H1IELEMPAGA":
					def i = 0
					if (fieldContent.contains("~")) {
						for (String s in fieldContent.split('~')) {
							if (s != "") {
								def cont = s
								def name = cont.split('%')[0]
								def date = cont.split('%')[1]
								if (osList[i] in ["ITA_4","ITA_12"]) {
									result += "${constant1};${idDipendente};${date};${constant4};${fieldName};${osList[i]};${name}||\n"
								}
								i++
							}
						}
					}
					break
				case "ANADDRES":
				case "ANZIPCOD":
				case "IDSTATE":
				case "IDCITY":
				case "ANPROVIN":
					result += "${constant1};${idDipendente};${fieldDate};${constant4};${fieldName};DM;${fieldValue}||\n"
					break
				case "DTENDVL55":
				case "FLMONTHWT":
				case "FLSOSP":
				case "IDWAGETP":
				case "VARESULT":
					result += "${constant1};${idDipendente};${fieldDate};${constant4};${fieldName};${aammDataRiferimento};${fieldValue}||\n"
					break
				default:
					result += "${constant1};${idDipendente};${fieldDate};${constant4};${fieldName};;${fieldValue}||\n"
					break
			}
		}
	}

	//Rimuove l'eventuale "||" finale
//	if (result.endsWith("||\n")) {    
//		result = result[0..-4]
//	}
	
	//result += "\n"

	// Output finale: solo contenuto CSV interno (senza SOAP)
	String fileContent = result.toString().trim()
	message.setBody(fileContent)

	return message
}
