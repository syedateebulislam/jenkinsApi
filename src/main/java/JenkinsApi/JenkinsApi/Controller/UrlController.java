package JenkinsApi.JenkinsApi.Controller;

import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import JenkinsApi.JenkinsApi.Operations.*;
import JenkinsApi.JenkinsApi.Model.*;


@RestController
@RequestMapping("/jenkins")
public class UrlController {

	  //http://localhost:8083/jenkins/alldata
	  @GetMapping("/alldata")
	  public List<Jenkins> getAllPayments() {
		   
		  ReadFileData rfd = new ReadFileData();
		  List<Jenkins> jenkinsData = rfd.ReadData();
		  
		  return jenkinsData;
	  }
}