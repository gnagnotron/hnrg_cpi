import com.sap.gateway.ip.core.customdev.util.Message
import groovy.xml.*

Message processData(Message message) {

    // Legge il payload XML come stringa
    def body = message.getBody(String)
    def xml = new XmlParser(false, false).parseText(body)

    //Filtra i <national_id_card> mantenendo solo quelli con <country>ITA</country>
    xml.'**'.findAll { it.name() == 'national_id_card' }.each { node ->
        def country = node.country?.text()
        if (country != 'ITA') {
            node.parent().remove(node)
        }
    }

    // Tieni solo <employment_information> con <job_information>/<company_territory_code>ITA</company_territory_code>
    xml.'**'.findAll { it.name() == 'employment_information' }.each { emp ->
        def jobInfos = emp.job_information.findAll { ji ->
            ji.company_territory_code?.text() == 'ITA'
        }

        if (jobInfos) {
            // rimuove i job_information non italiani
            emp.children().findAll { it.name() == 'job_information' && it.company_territory_code?.text() != 'ITA' }.each {
                emp.remove(it)
            }
            // se ci fossero più job_info ITA, tieni solo il primo
            def itaJobInfos = emp.job_information.findAll { it.company_territory_code?.text() == 'ITA' }
            itaJobInfos.tail().each { emp.remove(it) }
        } else {
            // se non ha job_information ITA, rimuovi tutto l'employment_information
            emp.parent().remove(emp)
        }
    }

    //Recupera la data di start dell’employment italiano (se esiste)
    def itaStartDate = xml.'**'.find {
        it.name() == 'employment_information' &&
        it.job_information?.company_territory_code?.text() == 'ITA'
    }?.start_date?.text()

    // Gestione <address_information>
    xml.'**'.findAll { it.name() == 'address_information' }.each { addr ->
        def action = addr.action?.text()
        def endDate = addr.end_date?.text()

        if (action == 'DELETE') {
            addr.parent().remove(addr)
        } else if (itaStartDate && endDate && endDate < itaStartDate) {
            // confronto lessicografico: funziona con formato YYYY-MM-DD
            addr.parent().remove(addr)
        }
    }

    //Ricostruisce l’XML filtrato come stringa
    def writer = new StringWriter()
    def printer = new XmlNodePrinter(new PrintWriter(writer))
    printer.setPreserveWhitespace(true)
    printer.print(xml)

    //Aggiorna il body del messaggio
    message.setBody(writer.toString())

    return message
}
