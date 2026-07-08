import com.sap.gateway.ip.core.customdev.util.Message

Message processData(Message message) {

    def inputPayload = message.getBody(java.io.InputStream)
    def rootNode = new XmlParser(false, false).parse(inputPayload)

    def filtered = []
    def result = ""
   

    rootNode.CompoundEmployee.person.employment_information.each { employmentInformation ->

            def endDate = employmentInformation?.end_date?.text()
            
            def jobInfo = employmentInformation.job_information[0]
            def territory = jobInfo?.company_territory_code?.text()

            if((!endDate || endDate.trim() == "") && territory == 'ITA') {
                filtered << employmentInformation   
            }
    }
    
    

    if(!filtered || filtered == null){
        def excecTimeStamp = rootNode.CompoundEmployee.execution_timestamp.text().substring(0,10)

        rootNode.CompoundEmployee.person.employment_information.each { employmentInformation ->

            def endDate = employmentInformation?.end_date?.text()
            
            def jobInfo = employmentInformation.job_information[0]
            def territory = jobInfo?.company_territory_code?.text()

            if((endDate > excecTimeStamp) && territory == 'ITA') {
                filtered << employmentInformation   
            }
        }
        
    }
    if(filtered) {
        def firstJobInfo = filtered[0].job_information[0]
        result = firstJobInfo?.custom_string11?.text() ?: ""
    }

    message.setProperty("Payroll_id", result)

    return message
}
