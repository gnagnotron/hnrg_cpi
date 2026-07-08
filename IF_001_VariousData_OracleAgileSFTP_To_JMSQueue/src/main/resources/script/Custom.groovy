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

def void customFunc(String[] code, String[] curr, String[] pre, Output output){
if(code[0] == 'Deleted'){
    for(String p : pre) {
    output.addValue(p);
    }
}
if(code[0] == 'Changed'){
if(!curr){    
    for(String p : pre) {
    output.addValue(p);
    }
}
}}


def void customFunc2(String[] values, String[] con, Output output){
    for(String c : con) {
    output.addValue(values[0]);
    }}


def void customFunc3(String[] values, String[] con, Output output){
    for(String c : con) {
        if(values[0]){
    output.addValue(c);
    }}
    
}

def void removeSUPPRESS(String[] values, Output output, MappingContext context) 
{

 values.each { v ->
        if (!output.isSuppress(v)) {
            output.addValue(v)
        }


}

}

def void setPRCT(String[] afID, String[] afvalue, String[] pID, String[] pvalue, Output output){
  int i = 0;
  int j = 0;
  int flag = 0;
    for(String p : pID) {
        i = 0;
        flag = 0;
    for(String a : afID) {
 if(p == a && flag == 0){
 output.addValue(afvalue[i]);
 flag = 1;
 }
 i = i + 1;
    } 
 if(flag == 0){
    output.addValue(pvalue[j]);  
 }
  j = j + 1;
    }
    
}

def void setPRCT_novalue(String[] afID, String[] afvalue, String[] pID, Output output){
  int i = 0;
  int j = 0;
  int flag = 0;
    for(String p : pID) {
        i = 0;
        flag = 0;
    for(String a : afID) {
 if(p == a && flag == 0){
 output.addValue(afvalue[i]);
 flag = 1;
 }
 i = i + 1;
    } 
 if(flag == 0){
    output.addValue("");  
 }
  j = j + 1;
    }
    
}