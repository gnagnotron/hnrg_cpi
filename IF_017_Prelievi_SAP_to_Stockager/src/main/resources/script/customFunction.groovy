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

def String clientParvwMapping(String parvw, String input, String rcvprn){
    if(rcvprn == "APB2B_P1BA")
    {
        if(parvw == "AG" || parvw == "WE")
        {
            return input; 
        }
    }
}

def String clientGeneralRcvprnMapping(String parvw, String input){
    if(parvw == "AG" || parvw == "WE")
    {
        return input; 
    }
}

def String destinationParvwMapping(String parvw, String input){
    if(parvw == "WE"){
        return input; 
    }
}

def String rcpvprnEqualsAPB2B_P1BA(String rcpvprn, String input){
    if(rcpvprn == "APB2B_P1BA"){
        return input;
    }
}

def String splitValuesBy(String input)
{
    String[] splitted = input.split("\\|");
    return splitted[2];
}
def String splitValuesBy(String input)
{
    String[] splitted = input.split("\\|");
    return splitted[1];
}