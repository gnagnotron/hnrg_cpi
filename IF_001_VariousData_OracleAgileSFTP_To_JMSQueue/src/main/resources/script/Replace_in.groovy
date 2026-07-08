import com.sap.gateway.ip.core.customdev.util.Message

Message processData(Message message) {
    // Access message body and properties
    Reader reader = message.getBody(Reader)
//    def payIn = reader.readLines()
    def payOut = []
    
    reader.readLines().each { line ->
        //if (line.startsWith('UNB+UNOA:2')) {
           line = line.replace(' xmlns="http://www.oracle.com/webfolder/technetwork/xml/plm/2016/09/"','>')
           line = line.replace('xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"','')
           line = line.replace('xmlns:xsd="http://www.w3.org/2001/XMLSchema" xsi:schemaLocation="http://www.oracle.com/webfolder/technetwork/xml/plm/2016/09/ http://www.oracle.com/webfolder/technetwork/xml/plm/2016/09/aXML.xsd">','')
   
   
           line = line.replace(' xmlns="http://www.oracle.com/webfolder/technetwork/xml/plm/2011/09/"','>')
           line = line.replace('xmlns:xsd="http://www.w3.org/2001/XMLSchema" xsi:schemaLocation="http://www.oracle.com/webfolder/technetwork/xml/plm/2011/09/ http://www.oracle.com/webfolder/technetwork/xml/plm/2011/09/aXML.xsd">','')
        //}
    
        payOut.add(line)
    }
        
    
    def writer = new StringWriter()
    writer.write(payOut.join('\n'))

    // Generate output
    message.setBody(writer.toString())
    return message
}    