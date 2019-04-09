package de.jostock.thesis;

import org.json.JSONObject;

/**
 * 
 * der Sessionmanager erstellt und beendet Sessions auf dem AWS-Cluster
 * Kommuniziert über den LivyRestConnector
 * 
 * @author matthias jostock
**/
public class SessionManager {
	
	public static SessionManager instance = null;

	
	public SessionManager() {
	}
	
	public static SessionManager getInstance() {
	    if (instance == null) {
	    	instance = new SessionManager(); 
	    }
	  
	    return instance; 
	}
	
	@SuppressWarnings("unused")
	public void createSession() {
		LivyRestConnector lrc = LivyRestConnector.getInstance();
		JSONObject obj = new JSONObject(lrc.createSession());
		Session session = new Session(obj.optInt("id"), obj.optString("appid"),obj.optString("owner"),obj.optString("proxyuser"),SessionKind.spark, obj.optString("kind"),obj.optString("appInfo"), obj.optString("log"));

	}
	
	public void deleteSession(int id) {
		LivyRestConnector lrc = LivyRestConnector.getInstance();
		lrc.deleteSession(id);
	} 
	
}
