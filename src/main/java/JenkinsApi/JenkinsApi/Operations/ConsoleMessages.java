package JenkinsApi.JenkinsApi.Operations;

import java.text.SimpleDateFormat;
import java.util.Date;

public class ConsoleMessages {
	
	
	static void printMessage(String Message) {
		
		Date date = new Date();
		SimpleDateFormat DateFor = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		String stringDate = DateFor.format(date);
		String dummyData = ".000  INFO 0000 --- [  restartedMain] J.JenkinsApi.JenkinsApiApplication       :";
		
		String finalMessage=stringDate+dummyData+Message;	
		System.out.println(finalMessage);	
		
	}
	
	static void printMessageWithDate(String preMsg,String postMsg) {
		
		Date date = new Date();
		SimpleDateFormat DateFor = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		String stringDate = DateFor.format(date);
		String dummyData = ".000  INFO 0000 --- [  restartedMain] J.JenkinsApi.JenkinsApiApplication       :";
			
		String finalMessage=stringDate+dummyData+preMsg+stringDate+postMsg;
		System.out.println(finalMessage);	
		
	}
}
