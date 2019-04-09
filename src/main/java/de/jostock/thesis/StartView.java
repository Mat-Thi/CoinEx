package de.jostock.thesis;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;

/**
 * 
 * GUI View zum Debugging
 * Hier können Sessions erstellt und beendet werden
 * 
 * @author matthias jostock
**/

@SuppressWarnings("serial")
@Route(value = "start", layout = MainView.class)
public class StartView extends VerticalLayout{
	
	LivyRestConnector lrc = LivyRestConnector.getInstance();
	
	public StartView() {
		 Button button_start = new Button("Create Session",
	                event -> createSession());
	        add(button_start);
	        
	     Button button_del = new Button("Create Session",
	               event -> callLRC());
	      add(button_del);
	        	        
	}
	

	private Object callLRC() {
		lrc.deleteSession(51);
		lrc.deleteSession(52);
		lrc.deleteSession(53);
		lrc.deleteSession(54);
		lrc.deleteSession(55);
		lrc.deleteSession(56);
		
		
		return null;
	}


	public void createSession() {
		
		Notification.show("Click");
		SessionManager sm =  SessionManager.getInstance();
		sm.createSession();

    }
}
