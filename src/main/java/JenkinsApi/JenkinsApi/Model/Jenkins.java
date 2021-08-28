package JenkinsApi.JenkinsApi.Model;

public class Jenkins {
		
	private String Status;
	private String Entity;
	private String User;
	private String Build;
	private String Jar;
	private String Url;
	
	
	public String getStatus() {
		return Status;
	}
	public void setStatus(String status) {
		Status = status;
	}
	public String getEntity() {
		return Entity;
	}
	public void setEntity(String entity) {
		Entity = entity;
	}
	public String getUser() {
		return User;
	}
	public void setUser(String user) {
		User = user;
	}
	public String getBuild() {
		return Build;
	}
	public void setBuild(String object) {
		Build = object;
	}
	public String getJar() {
		return Jar;
	}
	public void setJar(String jar) {
		Jar = jar;
	}
	public String getUrl() {
		return Url;
	}
	public void setUrl(String url) {
		Url = url;
	}
	
	@Override
	public String toString() {
		return "Jenkins [Status=" + Status + ", Entity=" + Entity + ", User=" + User + ", Build=" + Build + ", Jar="
				+ Jar + ", Url=" + Url + "]";
	}

}