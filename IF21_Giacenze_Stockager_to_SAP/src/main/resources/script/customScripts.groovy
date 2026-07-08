import com.sap.it.api.mapping.*;
import com.sap.it.api.ITApiFactory
import com.sap.it.api.mapping.ValueMappingApi

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

def String getRightFiscalCode(String isoCountry, String fiscalCode, String euVat, String danBCode)
{
    def valueMapApi = ITApiFactory.getApi(ValueMappingApi.class, null)
    def isUEorExtraUE = valueMapApi.getMappedValue('Jaggaer', 'J1_UE_extra_UE', isoCountry, 'SAP', 'SAP_UE_extra_UE');
    if(isUEorExtraUE == "NO")
    {
        if (danBCode == "")
        {
            def defaultFiscalCode = valueMapApi.getMappedValue('Jaggaer', 'J1_default_fiscal_code_EXTRA_UE', isoCountry, 'SAP', 'SAP_default_fiscal_code_EXTRA_UE');
            danBCode = defaultFiscalCode;
        }
        return danBCode;
    }
    else
    {
        if(isoCountry == "IT")
        {
            return fiscalCode;
        }
        else{
            return euVat;
        }
    }
    
}

def String getCreditorGroupCode(String isoCountry)
{
    def valueMapApi = ITApiFactory.getApi(ValueMappingApi.class, null)
    def isUEorExtraUE = valueMapApi.getMappedValue('Jaggaer', 'J1_UE_extra_UE', isoCountry, 'SAP', 'SAP_UE_extra_UE');
    if(isUEorExtraUE == "NO")
    {
        return "Z003";
    }
    else
    {
        if(isoCountry == "IT")
        {
            return "Z001";
        }
        else{
            return "Z002";
        }
    }
    
}


def String getProperty(String propertyName, MappingContext context) {
    def propertyValue = context.getProperty(propertyName);
    return propertyValue;
}

def String getHeader(String headerName, MappingContext context) {
    def headerValue = context.getHeader(headerName);
    return headerValue;
}


def String getProvince(String isoProvince)
{
    def splittedArray = isoProvince.split("-");
    def province = splittedArray[1];
    return province;
}

def String getLanguage(String locale)
{
    def splittedArray = locale.split("_");
    def language = splittedArray[0];
    language = language.toUpperCase();
    return language;
}

def String splitAndReturnSecond(String input, String separator)
{
    def output = ""
    if(input.length() > 1)
    {
        def splittedArray = input.split(separator);
        output = splittedArray[0];
    }
    
    return output;
}

def String splitAndReturnFirst(String input, String separator)
{
    def splittedArray = input.split(separator);
    def output = splittedArray[0];
    output = output.toUpperCase();
    return output;
}

def void getDataElement(String[] arrayName,String[] arrayValues, Output output, MappingContext context) {
    def valueOutput = ""
    def indexOutput = -1;
    indexOutput = arrayName.findIndexOf { it == "Condizioni_di_pagamento_concordate" }
    if(indexOutput == -1)
    {
        valueOutput = "60 gg fmdf";    
    }
    else
    {
        valueOutput = arrayValues[indexOutput];    
    }
   output.addValue(valueOutput);
}

def void getCondizioniPagamento(String[] arrayName,String[] arrayValues, Output output, MappingContext context) {
    def valueOutput = ""
    def indexOutput = -1;
    indexOutput = arrayName.findIndexOf { it == "Condizioni_di_pagamento_concordate" }
    if(indexOutput == -1)
    {
        valueOutput = "ZF60";    
    }
    else
    {
        valueOutput = arrayValues[indexOutput];    
        def valueOutputArray = valueOutput.split("-");
        valueOutput = valueOutputArray[0];
        valueOutput = valueOutput.trim();
    }
   output.addValue(valueOutput);
}

def void getIncoterms_concordati(String[] arrayName,String[] arrayValues, Output output, MappingContext context) {
    def valueOutput = ""
    def indexOutput = -1;
    indexOutput = arrayName.findIndexOf { it == "Incoterms_concordati" }
    if(indexOutput == -1)
    {
        valueOutput = "DAP";    
    }
    else
    {
        valueOutput = arrayValues[indexOutput];    
        valueOutput = valueOutput.substring(0,3);
    }
   output.addValue(valueOutput);
}

def void getSTATUS_LINK_ANAGRAFICHE_JAGGAER_SAP(String[] arrayName,String[] arrayValues, Output output, MappingContext context) {
    def valueOutput = ""
    def indexOutput = -1;
    indexOutput = arrayName.findIndexOf { it == "STATUS_LINK_ANAGRAFICHE_JAGGAER_SAP" }
    if(indexOutput == -1)
    {
        valueOutput = "Migration initial phase";    
    }
    else
    {
        valueOutput = arrayValues[indexOutput]; 
        context.setProperty("STATUS_LINK_ANAGRAFICHE_JAGGAER_SAP", valueOutput);
    }
   output.addValue(valueOutput);
}


def void getREG_008_Paese_Fiscale(String[] arrayName,String[] arrayValues, Output output, MappingContext context) {
    def valueOutput = ""
    def indexOutput = -1;
    indexOutput = arrayName.findIndexOf { it == "REG_008_Paese_Fiscale" }
    if(indexOutput == -1)
    {
        valueOutput = "";    
    }
    else
    {
        valueOutput = arrayValues[indexOutput]; 
    }
   output.addValue(valueOutput);
}

def void getREG_IBAN(String[] arrayName,String[] arrayValues, Output output, MappingContext context) {
    def valueOutput = ""
    def indexOutput = -1;
    def index_IBAN_IT = arrayName.findIndexOf { it == "REG_014_IBAN_IT" }
    def index_IBAN_UE = arrayName.findIndexOf { it == "REG_015_IBAN_UE" }
    def index_IBAN_non_UE = arrayName.findIndexOf { it == "REG_016_IBAN_Non_UE" }
    if(index_IBAN_IT != -1)
    {
        valueOutput = arrayValues[index_IBAN_IT]; 
    }
    if(index_IBAN_UE != -1)
    {
        valueOutput = arrayValues[index_IBAN_UE]; 
    }
    if(index_IBAN_non_UE != -1)
    {
        valueOutput = arrayValues[index_IBAN_non_UE]; 
    }
   output.addValue(valueOutput);
}

def void getREG_Conto_Banca(String[] arrayName,String[] arrayValues, Output output, MappingContext context) {
    def valueOutput = ""
    def indexOutput = -1;
    def index_Conto_Banca_IT = arrayName.findIndexOf { it == "REG_021_Conto_Banca_IT" }
    def index_Conto_Banca_UE = arrayName.findIndexOf { it == "REG_022_Conto_Banca_UE" }
    def index_Conto_Banca_Non_UE = arrayName.findIndexOf { it == "REG_023_Conto_Banca_Non_UE" }
    
    if(index_Conto_Banca_IT != -1)
    {
        valueOutput = arrayValues[index_Conto_Banca_IT]; 
    }
    if(index_Conto_Banca_UE != -1)
    {
        valueOutput = arrayValues[index_Conto_Banca_UE]; 
    }
    if(index_Conto_Banca_Non_UE != -1)
    {
        valueOutput = arrayValues[index_Conto_Banca_Non_UE]; 
    }
   output.addValue(valueOutput);
}

def void getREG_Paese_Banca(String[] arrayName,String[] arrayValues, Output output, MappingContext context) {
    def valueOutput = ""
    def indexOutput = -1;
    def index_Paese_UE_Banca = arrayName.findIndexOf { it == "REG_012_Paese_UE_Banca" }
    def index_Paese_Non_UE_Banca = arrayName.findIndexOf { it == "REG_013_Paese_Non_UE_Banca" }

    if(index_Paese_UE_Banca != -1)
    {
        valueOutput = arrayValues[index_Paese_UE_Banca]; 
    }
    if(index_Paese_Non_UE_Banca != -1)
    {
        valueOutput = arrayValues[index_Paese_Non_UE_Banca]; 
    }
   output.addValue(valueOutput);
}


def void getREG_Swift(String[] arrayName,String[] arrayValues, Output output, MappingContext context) {
    def valueOutput = ""
    def indexOutput = -1;
    def index_Swift_IT = arrayName.findIndexOf { it == "REG_017_SWIFT_IT" }
    def index_Swift_UE = arrayName.findIndexOf { it == "REG_018_SWIFT_UE" }
    def index_Swift_Non_UE = arrayName.findIndexOf { it == "REG_019_SWIFT_Non_UE" }
    
    if(index_Swift_IT != -1)
    {
        valueOutput = arrayValues[index_Swift_IT]; 
    }
    if(index_Swift_UE != -1)
    {
        valueOutput = arrayValues[index_Swift_UE]; 
    }
    if(index_Swift_Non_UE != -1)
    {
        valueOutput = arrayValues[index_Swift_Non_UE]; 
    }
   output.addValue(valueOutput);
}


def void getREG_AbiCab(String[] arrayName,String[] arrayValues, Output output, MappingContext context) {
    def valueOutput = ""
    def indexOutput = -1;
    def index_Abi_Cab_IT = arrayName.findIndexOf { it == "REG_020_ABI_CAB_IT" }
    
    if(index_Abi_Cab_IT != -1)
    {
        valueOutput = arrayValues[index_Abi_Cab_IT]; 
    }
    
   output.addValue(valueOutput);
}

def void getAllErrorsFromByd(String[] arrayName, Output output, MappingContext context) {
    def valueOutput = ""
    valueOutput = arrayName.join(";")
    
    
   output.addValue(valueOutput);
}


def String updatePropertyStatusLink(String input, MappingContext context) {
    def output = input;
    if(input == "04")
    {
        context.setProperty("STATUS_LINK_ANAGRAFICHE_JAGGAER_SAP", "Integrated with Jaggaer");
    }
    if(input == "05")
    {
        context.setProperty("STATUS_LINK_ANAGRAFICHE_JAGGAER_SAP", "Fast Track");
    }
    
   return output;
}

def void setProvinceErrorProperty(String input, MappingContext context) {
     
    context.setProperty("BYD_isoProvince_error",input);

}




















