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

def String elaborateV26(String werks, String rcvprn, String xchpf){
    
    def v22;
    def v25;
    
    v22 = (werks == "PW02" && rcvprn == "APB2B_P1BA" && xchpf == "X");
    v25 = (werks == "PW01" && rcvprn == "APB2B_P1ST" && xchpf == "X");

    
    if(v22 || v25)
    {
        v26 = "S"
    }
    else
    {
        v26 = "N"
    }
    return v26;
}

def String elaborateV15(String werks, String rcvprn, String sernp)
{
    def v12;
    def v13;
    def v14;
    
    v12 = (rcvprn == "APB2B_P1BA");
    v13 = (sernp == "");
    v14 = (werks == "PW02");
    
    def v15 = v12 && v13 && v14;
    return v15;
}