import com.sap.gateway.ip.core.customdev.util.Message
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.Date

Message processData(Message message) {


    def body = message.getBody(java.lang.String) as String
    def messageLog = messageLogFactory.getMessageLog(message)
    def propertyMap = message.getProperties()
	
    
    
    //Read logger from the Message Properties
    logger = propertyMap.get("logger")
    def lastModifiedDate=propertyMap.get("LAST_MODIFIED_DATE")
    def lastModifiedDateManual=propertyMap.get("manualLMD")
    def personIdExternal=propertyMap.get("externalID")
    def legalEntity=propertyMap.get("LegalEntity")
	def location=propertyMap.get("Location")
	def employeeclass=propertyMap.get("EmployeeClass")
	def fromDate=propertyMap.get("fromDate")
	def mode = propertyMap.get("qMode")
	
	
	// Initialize DS Employee Store
	def listEmp=[];
//	message.setProperty("UpdateEmployeeDS",listEmp)
	message.setProperty("RunType","")
	message.setProperty("HIRE","")
	message.setProperty("CHANGE","")
	message.setProperty("query","")
	
	
    Date todayDate = new Date();
    DateFormat dateFormat= new SimpleDateFormat("yyyy-MM-dd");
	
	
	String todayDateString = dateFormat.format(todayDate);
	
	
	Date lmdUpdate = new Date();
	DateFormat dateFormatLMD= new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
	
	String lmdUpdateString = dateFormatLMD.format(lmdUpdate);
	
	
	def queryLM=""
	
    if(mode.equalsIgnoreCase("true"))
    {
		if(lastModifiedDateManual == null || lastModifiedDateManual.equalsIgnoreCase("") || lastModifiedDateManual.equals("null"))
			
						{
							lastModifiedDateManual='1900-01-01T00:00:00Z';
							//message.setProperty("LAST_MODIFIED_DATE", lastModifiedDate);
						}
			
						if(!(personIdExternal == null || personIdExternal.equals("") || personIdExternal.equals("null")))
						{
			
								personIdExternal = personIdExternal.replaceAll(",", "', '")
								queryLM = "person_id_external IN ('" + personIdExternal + "')"
								queryLM=queryLM+" AND "+"last_modified_on > to_datetime('" + lastModifiedDateManual +"')"
						}
						else
						{
								if(personIdExternal == null || personIdExternal.equals("") || personIdExternal.equals("null"))
									queryLM=queryLM +"last_modified_on > to_datetime('" + lastModifiedDateManual +"')"
									else
								queryLM=queryLM+" AND "+"last_modified_on > to_datetime('" + lastModifiedDateManual +"')"
						}
						
    }

    else
    {		
		if(lastModifiedDate == null || lastModifiedDate.equals("") || lastModifiedDate.equals("null"))
			{

				lastModifiedDate=lastModifiedDateManual;
			}


				queryLM="last_modified_on > to_datetime('" + lastModifiedDate +"')"

    }
    
	// Adding Common Filters for Legal Entity & Location and Employee CLass
	
	
	
			if(!(legalEntity == null || legalEntity.equals("") || legalEntity.equals("null"))) {
				legalEntity = legalEntity.replaceAll(",", "', '")
				queryLM = queryLM + " AND company IN ('" + legalEntity + "')"
			}
	
			if(!(location == null || location.equals("") || location.equals("null"))) {
				location = location.replaceAll(",", "', '")
				queryLM = queryLM + " AND company_territory_code IN ('" + location + "')"
			}
	
			if(!(employeeclass == null || employeeclass.equals("") || employeeclass.equals("null"))) {
				employeeclass = employeeclass.replaceAll(",", "', '")
				queryLM = queryLM + " AND employee_class IN ('" + employeeclass + "')"
			}
			
			message.setProperty("queryResultOptions","renderPreviousTags,changedSegmentsOnly") //, changedSegmentsOnly
			message.setProperty("query","delta");
			
    // Final Query Param Set
    
    
     message.setProperty("QUERY_LAST_MODIFIED_DATE", queryLM)
     
	 if(mode.equalsIgnoreCase("false") && (personIdExternal == null || personIdExternal.equals("") || personIdExternal.equals("null")))
	    { 
		message.setProperty("LAST_MODIFIED_DATE_ITALYPAYROLL", lmdUpdateString);	
		}
		else
		message.setProperty("LAST_MODIFIED_DATE_ITALYPAYROLL", lastModifiedDate);		

		
    if(logger.equalsIgnoreCase("true")){

        messageLog.setStringProperty("Logging#1", "Printing Input Payload As Attachment")
        messageLog.addAttachmentAsString("#DSFetch(POLYNT)", "Query : LAST_MODIFIED_DATE -  "+lastModifiedDate+"\n" +"Manual "+lastModifiedDateManual+"   "+"\n"+"Mode:"+mode+"\n"+"LAST_MODIFIED_DATE_ITALYPAYROLL_LV :" + propertyMap.get("LAST_MODIFIED_DATE_ITALYPAYROLL_LV")+"\n"+"Run Date : "+todayDateString+"\n"+"\n"+"QUERY : "+queryLM + "\n" + "queryResultOptions:" + message.getProperty("queryResultOptions") , "text/plain")

    }
    return message

}