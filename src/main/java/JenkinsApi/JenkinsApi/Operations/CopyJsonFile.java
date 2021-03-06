package JenkinsApi.JenkinsApi.Operations;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
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
import org.springframework.stereotype.Component;

@Component
public class CopyJsonFile {

	/*
	 * standalone jar path :
	 * "C://Users//Dell//Desktop//jenkinsApi jar//BOOT-INF//classes" STS editor
	 * path: "src//main//resources//jenkinsAPIData.json"
	 */
	//servingDataPath initialization 2/2 - src//main//resources
	static final String servingDataPath = ".//jenkinsAPIData_API.json";
	
	//fetchDataPath initialization 1/2 - src//main//resources
	static final String fetchDataPath = ".//jenkinsAPIData.json";
	
	
	public void copyJsonFile() throws FileNotFoundException, IOException, ParseException {

		JSONObject jo_writer = new JSONObject();
		JSONArray ja_writer = new JSONArray();

		// fetched data mention 1/4
		Object obj = new JSONParser().parse(new FileReader(fetchDataPath));

		JSONObject jo_reader = (JSONObject) obj;
		JSONArray ja_reader = (JSONArray) jo_reader.get("jenkins");

		Iterator itr2 = ja_reader.iterator();

		while (itr2.hasNext()) {
			Iterator<Map.Entry> itr1 = ((Map) itr2.next()).entrySet().iterator();
			Map map_writer = new LinkedHashMap(5);

			while (itr1.hasNext()) {
				Map.Entry pair = itr1.next();

				if (pair.getKey().toString().equalsIgnoreCase("Entity")) {
					map_writer.put("Entity", pair.getValue());
				}
				if (pair.getKey().toString().equalsIgnoreCase("Jar")) {
					map_writer.put("Jar", pair.getValue());
				}
				if (pair.getKey().toString().equalsIgnoreCase("Build")) {
					map_writer.put("Build", pair.getValue());
				}
				if (pair.getKey().toString().equalsIgnoreCase("User")) {
					map_writer.put("User", pair.getValue());
				}
				if (pair.getKey().toString().equalsIgnoreCase("Status")) {
					map_writer.put("Status", pair.getValue());
				}
				if (pair.getKey().toString().equalsIgnoreCase("url")) {
					map_writer.put("url", pair.getValue());
				}
			}
			ja_writer.add(map_writer);
		}

		jo_writer.put("jenkins", ja_writer);

		// servable data mention 1/2
		PrintWriter pw = new PrintWriter(servingDataPath);
		pw.write(jo_writer.toJSONString());

		pw.flush();
		pw.close();

		ConsoleMessages.printMessageWithDate("Data Fetched at ", " is being served on API...");

	}
}
