package de.jostock.thesis;

import java.util.ArrayList;
import java.util.List;


/**
 * 
 * Liste aller aktiver Sessions des Nutzers
 * 
 * @author matthias jostock
**/
public class Sessions {

	public static Sessions instance = null;
	public List<Session> sessions = new ArrayList<Session>();
	
	public Sessions() {

	}
	
	public static Sessions getInstance() 
    { 
        if (instance == null) 
        	instance = new Sessions(); 
  
        return instance; 
    } 
		
	public void addSession(Session session) {
		sessions.add(session);
	}
	
	public List<Session> getSessions() {
		return sessions;
	}

	public void setSessions(List<Session> sessions) {
		this.sessions = sessions;
	}

}
