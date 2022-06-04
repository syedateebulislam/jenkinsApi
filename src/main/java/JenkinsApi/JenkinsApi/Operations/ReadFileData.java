package JenkinsApi.JenkinsApi.Operations;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import JenkinsApi.JenkinsApi.Model.*;

public class ReadFileData {

	/*
	 * standalone jar path :
	 * "C://Users//Dell//Desktop//jenkinsApi jar//BOOT-INF//classes" STS editor
	 * path: "src//main//resources//jenkinsAPIData.json"
	 */
	//servingDataPath initialization 1/2 - src//main//resources
	static final String servingDataPath = ".//jenkinsAPIData_API.json";

	public List<Jenkins> ReadData() {

		List<Jenkins> jenkinsDataList = new ArrayList();
		Object obj;

		try {
			// servable data mention 2/2
			obj = new JSONParser().parse(new FileReader(servingDataPath));
			JSONObject jo_reader = (JSONObject) obj;
			JSONArray ja_reader = (JSONArray) jo_reader.get("jenkins");
			Iterator itr2 = ja_reader.iterator();

			while (itr2.hasNext()) {
				Iterator<Map.Entry> itr1 = ((Map) itr2.next()).entrySet().iterator();

				Jenkins jenkins = new Jenkins();

				while (itr1.hasNext()) {
					Map.Entry pair = itr1.next();

					if (pair.getKey().toString().equalsIgnoreCase("Entity")) {
						jenkins.setEntity(pair.getValue().toString());
					}
					if (pair.getKey().toString().equalsIgnoreCase("Jar")) {
						jenkins.setJar(pair.getValue().toString());
					}
					if (pair.getKey().toString().equalsIgnoreCase("Build")) {
						jenkins.setBuild(pair.getValue().toString());
					}
					if (pair.getKey().toString().equalsIgnoreCase("User")) {
						jenkins.setUser(pair.getValue().toString());
					}
					if (pair.getKey().toString().equalsIgnoreCase("Status")) {
						jenkins.setStatus(pair.getValue().toString());
					}
					if (pair.getKey().toString().equalsIgnoreCase("url")) {
						jenkins.setUrl(pair.getValue().toString());
					}

				}

				jenkinsDataList.add(jenkins);

			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		}

		return jenkinsDataList;
	}
}