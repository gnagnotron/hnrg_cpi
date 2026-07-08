import com.sap.it.api.mapping.*;

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

def String createTpAzione(String ds_sn, String rcvprn)
{
    if(ds_sn != "" && rcvprn == "APB2B_P1BA")
    {
        return "I";
    }
    else{
        return "X";
    }
}
def String createDsSN(String ds_sn, String rcvprn)
{
    if(ds_sn != "" && rcvprn == "APB2B_P1BA")
    {
        return ds_sn;
    }
    else{
        return "X";
    }
}

def String prova(String ds_sn)
{
    if(ds_sn != "")
    {
        return ds_sn;   
    }
}

def String prova2(String ds_sn, String altro)
{
    if(ds_sn != "")
    {
        return ds_sn
    }
}

def String createSerialNumber(String ds_sn, String rcvprn)
{
    if(ds_sn != "" && rcvprn == "APB2B_P1BA")
    {
        return ds_sn;
    }
    else
    {
        return "X";
    }
}

def void removeSuppress(String[] values, Output output, MappingContext context) {
    values.each { v ->
        if (!output.isSuppress(v)) {
            output.addValue(v)
        }
    }
}