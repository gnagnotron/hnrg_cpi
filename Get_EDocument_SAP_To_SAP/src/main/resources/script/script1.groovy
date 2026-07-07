import java.util.Base64

// Step 1: Your original Base64 string (shortened here; replace with full string)
def base64Input = message.getBody()

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
println hexOutput.toString().toLowerCase()