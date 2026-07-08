import com.sap.it.api.mapping.*;
import java.text.SimpleDateFormat
import java.util.Calendar

def String lastDayOfMonth(String year, String month, String day){
	
	Calendar calDate = Calendar.getInstance()
	calDate.set(Integer.valueOf(year), Integer.valueOf(month)-1, Integer.valueOf(day), 0, 0)
	calDate.set(Calendar.DAY_OF_MONTH, 1)
	calDate.add(Calendar.MONTH, 1)
    calDate.add(Calendar.DAY_OF_MONTH, -1)
	
	def dateFormat = new SimpleDateFormat("yyyyMMdd")
	String finalDate = dateFormat.format(calDate.getTime())
	return finalDate
}