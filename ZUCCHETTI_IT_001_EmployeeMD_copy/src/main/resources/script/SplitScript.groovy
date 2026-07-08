import com.sap.it.api.mapping.*;
import java.text.SimpleDateFormat
//Script with utilities
//Split string with defined character and takes [position] value
def String split(String arg1, String character, int position){
	return arg1.split(character)[position]
}
//returns empty string when dealing with an empty payload
def String empty(String arg1){
    if (arg1 == null || arg1.trim() == '') {
        return 'empty'
    } else {
        return input
    }
}



def String EmailConcat(String Email, String startDate) {
    // Check if both values are not null or empty
    if (Email?.trim() && startDate?.trim()) {
        try {
            // Define the date formats
            def inputDateFormat = new SimpleDateFormat("yyyy-MM-dd")
            def outputDateFormat = new SimpleDateFormat("yyyyMMdd")
            
            // Parse the input date string
            def date = inputDateFormat.parse(startDate)
            
            // Format the date to the desired output format
            def formattedDate = outputDateFormat.format(date)
            
            // Concatenate with "%" sign in between
            return "${Email}%${formattedDate}"
        } catch (ParseException e) {
            // Handle parsing error
            println "Error parsing date: ${e.message}"
            return ""
        }
    }
    // If any of the values is null or empty, return an empty string
    return ""
}
