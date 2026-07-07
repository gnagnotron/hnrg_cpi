import com.sap.it.api.mapping.*;
import java.util.Base64;

/*Add MappingContext parameter to read or set headers and properties
def String customFunc1(String P1,String P2,MappingContext context) {
         String value1 = context.getHeader(P1);
         String value2 = context.getProperty(P2);
         return value1+value2;
}

Add Output parameter to assign the output value.
def void custFunc2(String[] is,String[] ps, Output output, MappingContext context) {
        String value1 = context.getHeader(is[0]);
        String value2 = context.getProperty(ps[0]);
        output.addValue(value1);
        output.addValue(value2);
}*/

def String Base64HexConvert(String arg1){
    
    // Step 1: Your original Base64 string (shortened here; replace with full string)
def base64Input = arg1

// Step 2: Decode Base64 to get XML
byte[] xmlBytes = Base64.decoder.decode(base64Input)

// Step 3: Re-encode the XML back to Base64
String base64Again = Base64.encoder.encodeToString(xmlBytes)

// Step 4: Convert Base64 string to hex
StringBuilder hexOutput = new StringBuilder()
base64Again.bytes.each { byteVal ->
    hexOutput.append(String.format("%02X", byteVal))
}

// Print final hex string
arg1 = hexOutput.toString().toLowerCase()
	return arg1 
}