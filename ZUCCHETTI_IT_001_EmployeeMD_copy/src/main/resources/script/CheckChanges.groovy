import com.sap.gateway.ip.core.customdev.util.Message;

def Message processData(Message message) {

    def body = message.getBody(String)
    def root = new XmlParser().parseText(body) // parse body
    
	def person = root.CompoundEmployee.person;
	//def actualJobInfo = person.employment_information?.job_information[0]


	def actualJobInfo = person.employment_information.job_information.find{
		node -> 
			(node.action.text() == "INSERT" || node.action.text() == "CHANGE")
	}

	
	String testResAddress = "false"
	String testDomAddress = "false"
	String testLevel = "false"
	String testPartTime = "false"
	String testSmartWork = "false"
	String testCatProt = "false"
	String testContract = "false"
    String testEmailB = "false"
    String testEmailP = "false"
    String testPhoneTypeB = "false"
    String testPhoneTypePM = "false"

	
	//Check changes in residence address
	
	def resAddress = person.address_information.find{
		node -> 
			(node.address_type.text() == "home")
	}
	
	if(resAddress && resAddress != null){
		if(resAddress.action.text() == "INSERT" ||
			(resAddress.action.text() == "CHANGE" &&
				(resAddress.address1_previous ||
				resAddress.country_previous ||
				resAddress.state_previous ||
				resAddress.city_previous ||
				resAddress.zip_code_previous)
			)
		){
			testResAddress = "true"
		}
	}
	
	//Check changes in domicile address
	
	def domAddress = person.address_information.find{
		node -> 
			(node.address_type.text() == "domicilio")
	}
	
	if(domAddress && domAddress != null){
		if(domAddress.action.text() == "INSERT" ||
			(domAddress.action.text() == "CHANGE" &&
				(domAddress.address1_previous ||
				domAddress.country_previous ||
				domAddress.state_previous ||
				domAddress.city_previous ||
				domAddress.zip_code_previous)
			)
		){
			testDomAddress = "true"
		}
	}
	//Check changes in level fields
	
	if(actualJobInfo != null && (actualJobInfo.action.text() == "INSERT" ||
		(actualJobInfo.action.text() == "CHANGE" &&
			(actualJobInfo.payScaleLevel_previous ||
			actualJobInfo.payScaleGroup_previous ||
			actualJobInfo.payScaleType_previous)
		)
	)){
		testLevel = "true"
	}
	
	//Check changes in part-time fields
	
	if(actualJobInfo != null && (actualJobInfo.action.text() == "INSERT" ||
		(actualJobInfo.action.text() == "CHANGE" &&
			(actualJobInfo.custom_string19_previous ||
			actualJobInfo.custom_double1_previous)
		)
	)){
		testPartTime = "true"
	}
	
	//Check changes in smart-work fields
	
	def cusOthEqChild = person.employment_information.cust_other_equipment.cust_other_equipmentChild.find{
		node -> 
			((node.action.text() == "INSERT" || node.action.text() == "CHANGE") && node.cust_otherEquipment.text() == "0004")
	}
	if(cusOthEqChild && cusOthEqChild != null){
		if(cusOthEqChild.action.text() == "INSERT" ||
		cusOthEqChild.cust_startDate_previous ||
		cusOthEqChild.cust_endDate_previous
		){
			testSmartWork = "true"
		}
	}
	
	//Check changes in disabled (CatProt - categoria protetta) fields
	def catProt = person.employment_information.cust_challenge.find{
	    node -> (node.action.text() == "INSERT" || node.action.text() == "CHANGE")
	}
	if(catProt != null && (catProt.action.text() == "INSERT" ||
	(catProt.action.text() == "CHANGE" &&
		(catProt.cust_challengeType_previous ||
		catProt.cust_challengeDegree_previous)
	))){
		testCatProt = "true"
	}

    //Check tipo contratto fields
    if(actualJobInfo != null &&  (actualJobInfo.action.text() == "INSERT" ||
		(actualJobInfo.action.text() == "CHANGE" && 
    (actualJobInfo.contract_end_date_previous || actualJobInfo.contract_type_previous || 
        actualJobInfo.custom_string7_previous)
    ))){
        testContract = "true";
    }




    //single fields
    //Check email address type B
    def emailTypeB = person.email_information.find{
		node -> 
			(node.email_type.text() == "B")
	}
	
	if(emailTypeB && emailTypeB != null){
		if(emailTypeB.action.text() == "INSERT" ||
			(emailTypeB.action.text() == "CHANGE" &&
				(emailTypeB.email_address_previous)
			)
		){
			testEmailB = "true"
		}
	}
    
    //Check email address type P
    def emailTypeP = person.email_information.find{
		node -> 
			(node.email_type.text() == "P")
	}
	
	if(emailTypeP && emailTypeP != null){
		if(emailTypeP.action.text() == "INSERT" ||
			(emailTypeP.action.text() == "CHANGE" &&
				(emailTypeP.email_address_previous)
			)
		){
			testEmailP = "true"
		}
	}
    //Check phone information type B
    def phoneTypeB = person.phone_information.find{
		node -> 
			(node.phone_type.text() == "B")
	}
	
	if(phoneTypeB && phoneTypeB != null){
		if(phoneTypeB.action.text() == "INSERT" ||
			(phoneTypeB.action.text() == "CHANGE" &&
				(phoneTypeB.phone_number_previous)
			)
		){
			testPhoneTypeB = "true"
		}
	}
    //Check phone information type PM
    def phoneTypePM = person.phone_information.find{
		node -> 
			(node.phone_type.text() == "PM")
	}
	
	if(phoneTypePM && phoneTypePM != null){
		if(phoneTypePM.action.text() == "INSERT" ||
			(phoneTypePM.action.text() == "CHANGE" &&
				(phoneTypePM.phone_number_previous)
			)
		){
			testPhoneTypePM = "true"
		}
	}
	

	


	
	message.setProperty("testResAddress",testResAddress)
	message.setProperty("testDomAddress",testDomAddress)
	message.setProperty("testLevel",testLevel)
	message.setProperty("testPartTime",testPartTime)
	message.setProperty("testSmartWork",testSmartWork)
	message.setProperty("testCatProt",testCatProt)
	message.setProperty("testContract",testContract)
    message.setProperty("testEmailB", testEmailB)
    message.setProperty("testEmailP", testEmailP)
    message.setProperty("testPhoneTypeB", testPhoneTypeB)
    message.setProperty("testPhoneTypePM", testPhoneTypePM)

	
	return message
}
