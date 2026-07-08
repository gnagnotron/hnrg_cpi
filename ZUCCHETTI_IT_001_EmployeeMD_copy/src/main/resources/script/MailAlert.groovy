import com.sap.gateway.ip.core.customdev.util.Message;
import java.util.HashMap;
def Message processData(Message message) {

    def properties = message.getProperties()
    def payload = message.getBody(String)
    // flow/message related attributes
    def cpiMessageID = properties.getOrDefault("SAP_MessageProcessingLogID", "N/A")
    def flowName = properties.getOrDefault("flowName", "N/A")
    def eventType = properties.getOrDefault("Event", "N/A")
    def employeeID = properties.getOrDefault("ExternalID", "N/A")
    //def positionID = properties.getOrDefault("split_positionId", "N/A")
    def highlightMessage = properties.getOrDefault("highlightMessage", "An error occurred during a Cloud Integration data flow, please check the information below.")

    payload = payload.replaceAll('"', '\\\\"')

    // mail attributes - to configure in previous Content Modifier
    def mailTo = properties.getOrDefault("mailTo", "")
    def mailCc = properties.getOrDefault("mailCc", "")
    def mailBcc = properties.getOrDefault("mailBcc", "")
    def mailSubject = properties.getOrDefault("mailSubject", "")


    // Mail body
    def mailBody = '<!DOCTYPE html><html lang=\\"en\\"><head><meta charset=\\"UTF-8\\"><meta name=\\"viewport\\" content=\\"width=device-width, initial-scale=1.0\\"><title>Data Flow Alert</title><style> /* Global Styles */ body { font-family: Arial, sans-serif; background-color: #f6f9fc; margin: 0; padding: 20px; } h1 { color: #333; margin-top: 0; margin-bottom: 20px; } .container { max-width: 600px; margin: 0 auto; background-color: #fff; padding: 20px; border-radius: 5px; box-shadow: 0 2px 5px rgba(0, 0, 0, 0.1); } .alert-message { background-color: #f8dbdb; color: #b74539; padding: 10px; border-radius: 5px; font-size: 14px; line-height: 1.4; margin-bottom: 20px; } table { width: 100%; border-collapse: collapse; } th, td { padding: 10px; text-align: left; border-bottom: 1px solid #ddd; } .label { font-weight: bold; margin-bottom: 5px; } .highlight { color: #333; font-family:monospace; font-size:16px;} .exception-message { padding: 10px; background-color: #f6f1ed; color: #b74539; border-radius: 5px; font-size: 14px; line-height: 1.4; margin-top: 10px; font-family:monospace;} .footer { margin-top: 40px; text-align: center; } .footer p { color: #888; font-size: 14px; } </style></head><body><div class=\\"container\\"><h1>Cloud Integration Data Flow Alert</h1><p class=\\"alert-message\\">'+highlightMessage+'</p><table><tr><th class=\\"label\\">Data Flow Name</th><td><span class=\\"highlight\\">'+flowName+'</span></td></tr><tr><th class=\\"label\\">Employee ID</th><td><span class=\\"highlight\\">'+employeeID+'</span></td></tr><tr><th class=\\"label\\">SuccessFactors event</th><td><span class=\\"highlight\\">'+eventType+'</span></td></tr><tr><th class=\\"label\\">Cloud Integration Message ID</th><td><span class=\\"highlight\\">'+cpiMessageID+'</span></td></tr></table><p class=\\"label\\">Exception Message:</p><div class=\\"exception-message\\">'+payload+'</div><div class=\\"footer\\"><p>This email was sent from the Cloud Integration automated alerting system. Please do not reply.</p></div></div></body></html>'


    // Payload to send to mail sending utility flow
    def body = '{"mailTo": "'+mailTo+'","mailCc": "'+mailCc+'","mailBcc": "'+mailBcc+'","mailBody": "'+mailBody+'", "mailSubject": "'+mailSubject+'"}'

    message.setBody(body)
    return message;
}
