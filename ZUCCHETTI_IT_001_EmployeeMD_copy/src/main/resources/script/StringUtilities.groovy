import com.sap.gateway.ip.core.customdev.util.Message;
import com.sap.it.api.mapping.*;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.*;
import java.util.HashMap;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.DocumentBuilder;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import org.w3c.dom.Element;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

EMAILS_MAP_NAME = "emailsMap";

/**
 *Sets in the property EMAILS_MAP_NAME an HashMap of the values from the XML response of the GetMailFromCustomer call 
 */
def Message mapEmailsInProperty(Message message) {
	
	String body = message.getBody(java.lang.String);
	
	DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
	DocumentBuilder dBuilder = null;
	try {
	dBuilder = dbFactory.newDocumentBuilder();
	} catch (ParserConfigurationException e) {
	e.printStackTrace();
	}
	
	StringBuilder xmlStringBuilder = new StringBuilder();
	xmlStringBuilder.append(body);
	ByteArrayInputStream input = new ByteArrayInputStream(xmlStringBuilder.toString().getBytes("UTF-8"));
	
	Document doc = null;
	try {
	doc = dBuilder.parse(input);
	} catch (SAXException | IOException e) {
	e.printStackTrace();
	}
	Element root = doc.getDocumentElement();
	NodeList items= root.getFirstChild().getChildNodes();
	
	message.setProperty(EMAILS_MAP_NAME, createMapFromXML(items));
	return message;

}
/**
 *Removes all the namespaces attributes (ns) from the xml in the message body
 */

def Message removeNsFromBody(Message message){
	
	String body = message.getBody(java.lang.String);
	return body.replace(" xmlns:ns\\d{1,4}=\".*?\"|ns\\d{1,4}:", "");
}

/**
 *Removes all the namespaces attributes (xsi) from the xml in the message body
 */

def Message removeXsiNsFromBody(Message message){
	
	String body = message.getBody(java.lang.String);
	return body.replace("( xmlns:)? ?xsi(:type)?=\".*?\"", "");
}

/**
 *Formats the string whose name is in property stringToEncode as URL valid
 */
def Message encodeStringAsURL(Message message) {
	
	def properties = message.getProperties();
	String propName = properties.get("stringToEncode");
	String str = properties.get(propName);

	String url= URLEncoder.encode(str);
	message.setProperty(propName, url);

	return message;
}

/**
 *Formats the body as URL valid
 */
def Message encodeBodyAsURL(Message message) {
	
	String str = message.getBody(java.lang.String) as String;

	String url= URLEncoder.encode(str);
	message.setBody(url);

	return message;
}
/**
 *Returns the HashMap with the values of the first (as key) and second child from the NodeList passed in body
 *The pattern of elementList have to be <element><key>A</key><value>1</value>...</element><element><key>B</key><value>2</value>...</element> and so on...
 */
def HashMap createMapFromXML(NodeList elementList){
	
	HashMap<String, String> response= new HashMap<String, String>();
	for(int i=0; i<elementList.getLength(); i++){
		NodeList itemChilds= elementList.item(i).getChildNodes();
		response.put(itemChilds.item(0).getTextContent(), itemChilds.item(1).getTextContent());
		
	}
	
	return response;
}

/**
 *Reformats Strings representing a number with the minus sign after the number putting it at the beginning of the String
 */
def String moveMinusSignBeforeNumber(String str, MappingContext context){
	
	String trimmedStr = str.trim();
	String outputStr=trimmedStr;

	int trimmedStrLength = trimmedStr.length();
	String lastChar = trimmedStr.substring(trimmedStrLength - 1, trimmedStrLength);

if (lastChar.equals ("-"))  {
outputStr = trimmedStr.substring(0, trimmedStrLength - 1);
outputStr = lastChar + outputStr;
   }
   
	return outputStr;
}

/**
 *Returns the substring of sourceString from startAt to endAt
 */
def String substring2Values(String sourceString, int startAt, int endAt, MappingContext context){
	
	if(endAt<=sourceString.length()){
		return sourceString.substring(startAt, endAt);
	} else return sourceString;
}

/**
 *Returns a substring of str starting after the first occurrence of separatingChar
 */
def String substringAfterChar(String str, String separatingChar){
	if(str.contains(separatingChar)){
	    str= str.substring(str.indexOf(separatingChar)+1);
	}
	return str; 
}

/**
 *Returns a substring of str starting after the char at the position indicated
 */
def String substringFrom(String str, int startAt){
	if(startAt<=str.length()){
		return str.substring(startAt);
	} else return str;
}

/**
 *Returns a substring of str ending at the first occurrence of separatingChar
 */
def String substringBeforeChar(String str, String separatingChar){
	if(str.contains(separatingChar)){
	    str= str.substring(0, str.indexOf(separatingChar));
	}
	return str; 
}

/**
 *Returns a substring of str beginning at the last occurrence of separatingChar
 */
def String substringAfterLastChar(String str, String separatingChar){
	if(str.contains(separatingChar)){
	    str= str.substring(str.lastIndexOf(separatingChar)+1);
	}
	return str; 
}

/**
 *Splits the string and returns in different values the strings
 */
def void divideStringPartnTab(String[] str, Output output){
    String[] strArray= str[0].split(";");
    
    for(int i=0; i<strArray.length; i++){
        
        String[] semiStr= strArray[i].split("-");
        output.addValue(semiStr[0]);
    }
}

/**
 *Splits the string and returns in different values the strings
 */
def void divideStringSemiColon(String[] str, Output output){
    String[] strArray= str[0].split(";");
    
    for(int i=0; i<strArray.length; i++){
        
        output.addValue(strArray[i]);
    }
}

/**
 *Concatenates different values in input into a single string in output
 */
def void concatDifferentValues(String[] str, Output output){
    String delimiter= "; ";
    String finalString= "";
    
    for(int i=0; i<str.length; i++){
        finalString= finalString + str[i]; 
        if(i<str.length-1){
            finalString= finalString + delimiter;
        }
    }
    
    output.addValue(finalString);
}

/**
 *Returns true if isContained is contained in str
 */
def String contains(String str, String isContained, MappingContext context){
	String result= str.contains(isContained);
	return result;
}
/**
 *Returns the substring truncated before the first letter ([a-zA-Z]) in the string
 */
def String getSubstringBeforeFirstLetter(String str, MappingContext context){
	String[] parts = str.split("[a-zA-Z]");
	return parts[0];
}
/**
 *Removes all the occurrences of a char/string(toRemove) in the string(target)
 */
def String removeCharFromString(String toRemove, String target, MappingContext context){
	return target.replace(toRemove, "");
}

/**
 *Fix the pattern of North American phone number
 */
def String fixPhoneNumberPattern(String phoneNumber, MappingContext context){
	if(phoneNumber.length() < 10){
		return phoneNumber;
	}
	phoneNumber=phoneNumber.replaceAll("[^\\d\\+]","");
	if(phoneNumber.startsWith("+1") || phoneNumber.startsWith("001")){
		phoneNumber=phoneNumber.substring(phoneNumber.indexOf("1") + 1)
	}
	if(phoneNumber.length() == 10){
		String newPhoneNumber = phoneNumber.substring(0,3) + "-" + phoneNumber.substring(3,6) + "-" + phoneNumber.substring(6)
		return newPhoneNumber;
	} else if (phoneNumber.length() < 16) {
		return phoneNumber;
	} else return "";
}