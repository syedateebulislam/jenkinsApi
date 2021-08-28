package JenkinsApi.JenkinsApi.Operations;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import JenkinsApi.JenkinsApi.Exceptions.CantFetchData;

@Component
public class FetchDataScheduler {
	
	  @Value("${spring.datasource.jenkins.url}")
	  String url;

	  @Value("${spring.datasource.jenkins.projects}")
	  String projects;
	  
	@Scheduled(initialDelay = 10000,fixedDelay = 90000)//config delay also from prop file
	public void fetchjekinsdata() {
		
		ConsoleMessages.printMessage("Previously fetched data is available for API...");
		
		//task-1		
		//add a user defined exception here for error case
		FetchData fjd	= new FetchData();
		fjd.fetchJenkinsData(url,projects);
				
		
		
		//-------
		
		//task-2
		CopyJsonFile cjf = new CopyJsonFile();
		try {
			
			cjf.copyJsonFile();
			
		} catch (IOException | ParseException e) {
			e.printStackTrace();
		}
		//-------
		
	}
}