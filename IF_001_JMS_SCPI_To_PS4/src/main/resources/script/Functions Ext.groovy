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

def String check_subString(String str, int start_alt){
    int index = 0;
    index = str.indexOf(' ');
    int start = 0;
    if(index <= 0){
  start = start_alt; 
    }
    else {start = index; }
    
    end = str.length();
	return str.substring(start, end);
}

def void split_multiString(String[] str,String[] path, Output output) {

String[] list;
      list = str[0].split(path[0]);
      for( String values : list ) {
         
      if (values.length() > 0) {

        output.addValue(values);
      }
      }
 
}

def void split_multiString_index(String[] str,String[] path, Output output) {

String[] list;

s = str[0];
 
      list = s.split(path[0]);
      for( String values : list ) {
     
   if (values.length() > 0) {
        output.addValue(s);
       
      }
      }
 }



def void merge_2multiValue(String[] list1, String[] list2, Output output) {

String[] merge;
String old;

      merge = list1 + list2;
      merge.sort();
      for( String m : merge ) {
          if (m.trim().length() > 0){
              if (old != m) {
            output.addValue(m);
            old = m;
              }
        }
      }
}



def void Cases_Maram_MSGFN(String[] codes, String[] curr_partn, String[] curr_name, String[] prev_partn, String[] prev_name, Output output) {
int i = 0;
for ( String code : codes) {
if  (curr_partn[i].length() > 0 ||
     curr_name[i].length() > 0 ||
     prev_partn[i].length() > 0 ||
     prev_partn[i].length() > 0 )
{
if (code == "Changed"){
    if  (curr_partn[i].length() > 0 ||
     curr_name[i].length() > 0 ) {
output.addValue("A");
output.addValue("D");
} else
output.addValue("U");
}
if (code == "Unchanged"){
output.addValue("U");
}
if (code == "Added"){
output.addValue("A");
}
if (code == "Deleted"){
output.addValue("D");
}
}
i = i + 1;
}
}


def void Cases_Maram_field(String[] codes, String[] curr, String[] curr2, String[] prev, Output output) {
int i = 0;
for ( String code : codes) {
if  (curr[i].length() > 0 ||
     prev[i].length() > 0 )
{
if (code == "Changed"){
    if  (curr[i].length() > 0) {
output.addValue(curr[i]);
output.addValue(prev[i]);  
} 
else if (curr2[i].length() > 0) {
 output.addValue(prev[i]);     
 output.addValue(prev[i]);     
}
else {
output.addValue(prev[i]);   
}
}
if (code == "Unchanged"){
output.addValue(curr[i]);
}
if (code == "Added"){
output.addValue(curr[i]);
}
if (code == "Deleted"){
output.addValue(prev[i]);
}
}
i = i + 1;
}
}


def void Cases_Stpom_MSGFN(String[] codes, String[] curr, String[] prev, Output output) {
int i = 0;
for ( String code : codes) {
    if(code) {
if  (curr || prev )
{
if (code == "Changed"){
    if(curr[i].length() > 0) {
output.addValue("A");
output.addValue("D");
i = i + 1;
} else {
output.addValue("C");
i = i + 1;
}}
if (code == "Unchanged"){
output.addValue("U");
i = i + 1;
}
if (code == "Added"){
output.addValue("A");
i = i + 1;
}
if (code == "Deleted"){
output.addValue("D");
}

}
} 
}
}


def void Cases_Stpom_split(String[] codes, String[] list, String[] item, Output output) {
int i = 0;
int j = 0;
for ( String code : codes) {
while(item[j] == "X!X"){
    output.addContextChange();
    j = j + 1;
}   
if(code == "Changed"){
if(list[i] != "C" ) {    
output.addValue(item[j]);
output.addValue(item[j]);
i = i + 1;
} else {
output.addValue(item[j]);
}
} else {
output.addValue(item[j]);
}
i = i + 1;
j = j + 1;
}
}


def void item_list(String[] codes, String[] item, Output output) {

for ( String code : codes) {
   output.addValue(item[0]);

} 
output.addValue("X!X");
}


def void Cases_Stpom_Idnrk(String[] codes, String[] curr, String[] prev, Output output) {
int i = 0;
int j = 0;
for ( String code : codes) {

if (code == "Changed"){
    if  (curr[i].length() > 0) {
output.addValue(curr[i]);
output.addValue(prev[j]);   
i = i + 1;
j = j + 1;
} else {
output.addValue(prev[j]);   
j = j + 1;
i = i + 1;
}
}
if (code == "Unchanged"){
output.addValue(curr[i]);
i = i + 1;
}
if (code == "Added"){
output.addValue(curr[i]);
i = i + 1;
}
if (code == "Deleted"){
output.addValue(prev[j]);
j = j + 1;

}
}

}



def void Cases_Stpom_field(String[] codes, String[] idnrk, String[] curr, String[] prev, Output output) {
int i = 0;
int j = 0;
for ( String code : codes) {

if (code == "Changed"){
    if  (idnrk[i].length() > 0) {
        if  (curr[i].length() > 0) {
output.addValue(curr[i]);
output.addValue(curr[i]);   
i = i + 1;
j = j + 1;
} else {
output.addValue(prev[j]);   
output.addValue(prev[j]);   
j = j + 1;
i = i + 1;
}}
else{
if  (curr[i].length() > 0) {
output.addValue(curr[i]);
i = i + 1;
j = j + 1;
} else {
output.addValue(prev[j]);  
j = j + 1;
i = i + 1;
}    
}
}

if (code == "Unchanged"){
output.addValue(curr[i]);
i = i + 1;
}
if (code == "Added"){
output.addValue(curr[i]);
i = i + 1;
}
if (code == "Deleted"){
output.addValue(prev[j]);
j = j + 1;

}


}
}



def void Cases_Stpum_ebort(String[] codes, String[] curr, String[] prev, Output output) {
for ( String code : codes) {

if (code == "Changed"){
if (curr) {
   for ( String s : curr) {
       if (s.length() > 0) 
     output.addValue(s);
 }  
}else{
  for ( String s : prev) {
      if (s.length() > 0)
     output.addValue(s);
 }   
}
}

if (code == "Unchanged"){
 for ( String s : curr) {
     if (s.length() > 0)
     output.addValue(s);
 }
}
if (code == "Added"){
 for ( String s : curr) {
     if (s.length() > 0)
     output.addValue(s);
 }
}
if (code == "Deleted"){
 for ( String s : prev) {
     if (s.length() > 0)
     output.addValue(s);
 }
}

}
}



def void Cases_Stpum_check(String[] codes, String[] curr, String[] prev,String[] item, Output output) {
for ( String code : codes) {

if (code == "Changed"){
if (curr) {
   for ( String s : curr) {
       if (s.length() > 0) 
     output.addValue(item(0));
 }  
}else{
  for ( String s : prev) {
      if (s.length() > 0)
     output.addValue(item(0));
 }   
}
}

if (code == "Unchanged"){
 for ( String s : curr) {
     if (s.length() > 0)
     output.addValue(item(0));
 }
}
if (code == "Added"){
 for ( String s : curr) {
     if (s.length() > 0)
     output.addValue(item(0));
 }
}
if (code == "Deleted"){
 for ( String s : prev) {
     if (s.length() > 0)
     output.addValue(item(0));
 }
}

}
}




def void Cases_Stpum_filed(String[] codes, String[] curr_key, String[] prev_key, String[] curr_value, String[] prev_value, Output output) {
int i = 0;
for ( String code : codes) {

if (code == "Changed"){
if (curr_key) {
   for ( String s : curr_key) {
       if(s.length() > 0) {
           if(curr_value[i].length() > 0) {
     output.addValue(curr_value[i]);
     }else {
     output.addValue(prev_value[i]);    
     }}}
}else{
  for ( String s : prev_key) {
      if(s.length() > 0)
     output.addValue(prev_value[i]);
 }   
}
}

if (code == "Unchanged"){
 for ( String s : curr_key) {
     if(s.length() > 0)
     output.addValue(curr_value[i]);
 }
}
if (code == "Added"){
 for ( String s : curr_key) {
     if(s.length() > 0)
     output.addValue(curr_value[i]);
 }
}
if (code == "Deleted"){
 for ( String s : prev_key) {
     if(s.length() > 0)
     output.addValue(prev_value[i]);
 }
}
i = i + 1;
}
}


def String split_String_at_index(String str, String path, int index) {

String[] list;
String res = '';
int i = index - 1;
if(str.length() > 0){
      list = str.split(path);
      if(list.size() > i){
  String values = list[i] ;
      if (values.trim().length() > 0)
      res = values;
      }
}
       return res;
}

def void many_Value(String[] context, String[] test, String[] value, Output output) {
int i = 0;
for ( String cx : context) {
for ( String sx : test) {    
    output.addValue(value[i]);
}
i = i + 1;
}
}


def void Cases_Maram_MATNR(String[] codes, String[] matnr, String[] curr, String[] curr2, Output output) {
int i = 0;

for ( String code : codes) {

if (code == "Changed"){
  if  ((curr.size() > 0 && curr[i].length() > 0) ||
    (curr2.size() > 0 && curr2[i].length() > 0)) {
output.addValue(matnr[0]);
output.addValue(matnr[0]);
i = i + 1;
} else {
output.addValue(matnr[0]);
i = i + 1;
}}
if (code == "Unchanged"){
output.addValue(matnr[0]);
i = i + 1;
}
if (code == "Added"){
output.addValue(matnr[0]);
i = i + 1;
}
if (code == "Deleted"){
output.addValue(matnr[0]);
}

}
}


def void exist_one(String[] value,String[] context, Output output) {

String s = value[0]
for (String c : context)
{
output.addValue(s);    
    
}
}



def void Cases_STPUM_MATNR(String[] codes, String[] matnr, Output output) {
for ( String m : matnr) {
for ( String code : codes) {
     if (code.trim().length() > 0)
output.addValue(m);
}
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
















