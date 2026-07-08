import com.sap.it.api.mapping.*;
def void concatContextValues(String[] Input, String[] Separator, Output output)
{
	StringBuilder result = new StringBuilder()
	String delimiter = Separator[0];
	if (Input[0] != null){
	    result.append(Input[0])
	}
	for (int i=1; i<Input.size();i++)
	{
    	if (Input[i] != null && Input[i].trim().length() > 0 && !output.isSuppress(Input[i]))
    	{
    	    result.append(delimiter).append(Input[i])
    	}
    	}
	result.toString()
	output.addValue(result)
}