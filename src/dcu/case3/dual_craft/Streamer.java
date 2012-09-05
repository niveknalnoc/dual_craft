 package dcu.case3.dual_craft;

 /*
  * class Streamer
  * 
  * 	-> INITIALISE VARIABLES FOR STREAMER AND INCLUDE METHODS
  * 
  * 	METHODS: getName(), setName(), getUrl(), setUrl(), 
  * 	toString(), printNotFound()
  */
 
public class Streamer {

	String loginName;
	String urlChannel;
	String online;
	
	public Streamer(String a, String b)
	{
		loginName = a;
		urlChannel = b;
	}
	
	Streamer()
	{
	
	}
	
	public String getOnline() {
		return online;
	}
	
	public String setOnline() {
		return online = "online";
	}
	
	public String setOffline() {
		return online = "offline";
	}
	
	public String getName() {
		return loginName;
	}
	
	public void setName(String name) {
		 this.loginName = name;
	}
	
	public String getUrl() {
		return urlChannel;
	}
	
	public void setUrl(String url) {
		this.urlChannel = url;
	}
	
	public String toString() {
        return this.loginName;
    }
	
	public String printNotFound() {
		return this.loginName + ": not found!";
	}
	
	public boolean equals(Object o) {
		Streamer s = (Streamer)o;
		
		if(!s.loginName.equals(loginName)) {
			return false;
		}
		
		if(!s.urlChannel.equals(urlChannel)) {
			return false;
		}
		
		return true;
	}
	
}//END CLASS STREAMER


	
	