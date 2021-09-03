package JenkinsApi.JenkinsApi.Operations;


import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Value;

import JenkinsApi.JenkinsApi.Exceptions.CantFetchData;
//import JenkinsApi.JenkinsApi.Exceptions.JenkinsError;


public class FetchData {

	public void fetchJenkinsData(String url,String allProjects) {
		
		ConsoleMessages.printMessage("Started fetching latest data from jenkins...");
		
		String[] projects = allProjects.split(":");		
		String jenkinsEntity=url;	
	
		int buildNum=0;
		int res=0;

		JSONObject jo_writer = new JSONObject();
		JSONArray ja_writer = new JSONArray();
		jo_writer.put("jenkins", ja_writer);
		PrintWriter pw;
		
		try {
			/*
			 * standalone jar path : "C://Users//Dell//Desktop//jenkinsApi jar//BOOT-INF//classes"
			 * STS editor path:	"src//main//resources//jenkinsAPIData.json"
			 */
			pw = new PrintWriter("src//main//resources//jenkinsAPIData.json");
			pw.write(jo_writer.toJSONString());
			
			pw.flush();
			pw.close();
				
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		}
		
		
		for(String ProjectName: projects) {
		
			for(int i=1;i<100;i++) {

				try {
					buildNum=i;
					res=FetchData.fetchData(jenkinsEntity,buildNum,ProjectName);
					if(res==1) {
						break;	
					}
					
				} catch (IOException | InterruptedException | ParseException e) {
					e.printStackTrace();
				} 		
			}
			
			ConsoleMessages.printMessage("All builds console logs fetched for "+ ProjectName +" project.");
			
		}
		
		ConsoleMessages.printMessage("Successfully fetched latest data from jenkins...");
	}
	
	
	public static int fetchData(String jenkinsEntity,int buildNum,String ProjectName) throws IOException, InterruptedException, ParseException{
		
		
		String url = "http://"+jenkinsEntity+"/job/"+ProjectName+"/"+String.valueOf(buildNum)+"/consoleText";
		
		//1-Running curl and pasting output on new file		
		String command =  "curl -s  \"http://"+jenkinsEntity+"/job/"+ProjectName+"/"+String.valueOf(buildNum)+"/consoleText\" -u ateeb:1136bd7c600857e0e80af5e22532b298ec ";
		ProcessBuilder processBuilder_1 = new ProcessBuilder(command.split(" "));
		
		processBuilder_1.directory(new File("/"));
		Process process = processBuilder_1.start();
		Thread.sleep(2000);

		try (
				InputStream inputStream = process.getInputStream();
				InputStreamReader inputStreamReader = new InputStreamReader(inputStream, StandardCharsets.UTF_8);
				BufferedReader bufferedReader_curl = new BufferedReader(inputStreamReader)
			) 
		{

			String str_2=bufferedReader_curl.readLine();

			try {
				
				if(str_2==null) {
					
						throw new CantFetchData();
					
					
				}else {
					
					if(str_2.contains("html") || str_2.contains("Bad Message 400")) {
						
						return 1;
					}
					else {
						

						JSONObject jo_writer = new JSONObject();
						JSONArray ja_writer = new JSONArray();
					    
						/*
						 * standalone jar path : "C://Users//Dell//Desktop//jenkinsApi jar//BOOT-INF//classes"
						 * STS editor path:	"src//main//resources//jenkinsAPIData.json"
						 */
						Object obj = new JSONParser().parse(new FileReader("src//main//resources//jenkinsAPIData.json"));
				        
						JSONObject jo_reader = (JSONObject) obj;
					    JSONArray ja_reader = (JSONArray) jo_reader.get("jenkins");
				    
					    Iterator itr2 = ja_reader.iterator();
				          
				        while (itr2.hasNext()) 
				        {
				        	Iterator<Map.Entry> itr1 = ((Map) itr2.next()).entrySet().iterator();
				        	Map map_writer = new LinkedHashMap(5);
				        
				        	while (itr1.hasNext()) {
				                Map.Entry pair = itr1.next();
				                
				                if(pair.getKey().toString().equalsIgnoreCase("Entity")) {
				                	map_writer.put("Entity", pair.getValue());
				                }
				                if(pair.getKey().toString().equalsIgnoreCase("Jar")) {
				                	map_writer.put("Jar", pair.getValue());
				                }
				                if(pair.getKey().toString().equalsIgnoreCase("Build")) {
				                	map_writer.put("Build", pair.getValue());
				                }
				                if(pair.getKey().toString().equalsIgnoreCase("User")) {
				                	map_writer.put("User", pair.getValue());
				                }
				                if(pair.getKey().toString().equalsIgnoreCase("Status")) {
				                	map_writer.put("Status", pair.getValue());
				                }
				                if(pair.getKey().toString().equalsIgnoreCase("url")) {
				                	map_writer.put("url", pair.getValue());
				                }
				            }
							ja_writer.add(map_writer);
				        }						

						if(str_2.contains("Started by")) {

							String usernameString=null;
					        String buildStatusString=null;
					        
					        while(str_2!=null)
						    {
								if(str_2.contains("Started by")){
									 usernameString=str_2.substring(16,34);
								}
								if(str_2.contains("Finished")){
									 buildStatusString=str_2.substring(10,17);
								}

								str_2=bufferedReader_curl.readLine();
						    }
					        
					        Map map_writer_final = new LinkedHashMap(5);
					        map_writer_final.put("Status", buildStatusString);
							map_writer_final.put("Entity", jenkinsEntity);
							map_writer_final.put("User", usernameString);
					        map_writer_final.put("Build", String.valueOf(buildNum));				
					        map_writer_final.put("Jar", ProjectName);
					        map_writer_final.put("url", url);
					        
							ja_writer.add(map_writer_final);				
						}
						
						jo_writer.put("jenkins", ja_writer);
						
						/*
						 * standalone jar path : "C://Users//Dell//Desktop//jenkinsApi jar//BOOT-INF//classes"
						 * STS editor path:	"src//main//resources//jenkinsAPIData.json"
						 */
						PrintWriter pw = new PrintWriter("src//main//resources//jenkinsAPIData.json");
						pw.write(jo_writer.toJSONString());
						
						pw.flush();
						pw.close();
						
					}
					
				}
				
			}catch(CantFetchData e) {
				
				ConsoleMessages.printMessage("Jenkins URL Or Project Name is not Correct...");
				ConsoleMessages.printMessage("Please recheck the application.xml entries...");				
			}
		}
		return 0;
	}
}