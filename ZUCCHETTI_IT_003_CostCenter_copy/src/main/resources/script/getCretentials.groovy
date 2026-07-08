import com.sap.gateway.ip.core.customdev.util.Message
import java.nio.charset.StandardCharsets
import java.io.OutputStreamWriter
import groovy.xml.*
import com.sap.it.api.ITApiFactory
import com.sap.it.api.securestore.SecureStoreService

def Message processData(Message message) {

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
