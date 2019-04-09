package de.jostock.thesis;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.ProtocolException;
import java.net.URL;

import org.apache.commons.io.IOUtils;
import org.json.JSONObject;

import com.vaadin.flow.component.notification.Notification;

/**
 * 
 * Klasse zur Verbindung mit der Livy Restschnittstelle
 * Ueber diese wird mit dem Spark-Cluster kommuniziert
 *  
 * @author matthias jostock
**/
public class LivyRestConnector {
	
	public static LivyRestConnector instance = null;
	
	//URL von AWS-Cluster
	public String awsURL = "http://"+"ec2-18-195-134-155.eu-central-1.compute.amazonaws.com"+":8998";
	
	
	public LivyRestConnector() {
	}
	
	
	public static LivyRestConnector getInstance() {
	    if (instance == null) {
	    	instance = new LivyRestConnector(); 
	    }
	  
	    return instance; 
	}
	
	public String createSession() {
		String output = null;
    	Notification.show("Clicked!");
    	HttpURLConnection httpcon = null;
		try {
			httpcon = (HttpURLConnection) ((new URL(awsURL+"/sessions").openConnection()));
		} catch (IOException e) {
			e.printStackTrace();
			Notification.show("Fehler!");
		}
    	httpcon.setDoOutput(true);
    	httpcon.setRequestProperty("Content-Type", "application/json");
    	try {
			httpcon.setRequestMethod("POST");

		} catch (ProtocolException e) {
			e.printStackTrace();
			Notification.show("fehler!");
		}
    	
    	String httpBody = " {\"kind\" : \"spark\" }";
    	
    	httpcon.setRequestProperty("Content-Length", Integer.toString(httpBody.length()));
    	try {
			httpcon.getOutputStream().write(httpBody.getBytes("UTF8"));
		} catch (UnsupportedEncodingException e1) {
			e1.printStackTrace();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		try {
			httpcon.connect();
		} catch (IOException e) {
			e.printStackTrace();
		}
		Notification.show("WOHO!");
		try {
			output = IOUtils.toString(httpcon.getInputStream(), "UTF-8"); 
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		@SuppressWarnings("unused")
		JSONObject obj = new JSONObject(output);
		System.out.println(output);
		return output;

	}
	
	public void deleteSession(int id) {
		String output = null;
    	Notification.show("Clicked!");
    	HttpURLConnection httpcon = null;
		try {
			httpcon = (HttpURLConnection) ((new URL(awsURL+"/sessions/"+id).openConnection()));
		} catch (IOException e) {
			e.printStackTrace();
			Notification.show("Fehler!");
		}
    	httpcon.setDoOutput(true);
    	httpcon.setRequestProperty("Content-Type", "application/json");
    	try {
			httpcon.setRequestMethod("DELETE");

		} catch (ProtocolException e) {
			e.printStackTrace();
			Notification.show("fehler!");
		}
    	
    	
    
		try {
			httpcon.connect();
		} catch (IOException e) {
			e.printStackTrace();
		}
		Notification.show("WOHO!");
		try {
			output = IOUtils.toString(httpcon.getInputStream(), "UTF-8"); 
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		@SuppressWarnings("unused")
		JSONObject obj = new JSONObject(output);
		System.out.println(output);
		
		
	}
	
	public String sendCodeToSession(int idSession, String code) {
		String output = null;
    	Notification.show("Clicked!");
    	HttpURLConnection httpcon = null;
		try {
			httpcon = (HttpURLConnection) ((new URL(awsURL+"/sessions/"+idSession+"/statements").openConnection()));
		} catch (IOException e) {
			e.printStackTrace();
			Notification.show("Fehler!");
		}
    	httpcon.setDoOutput(true);
    	httpcon.setRequestProperty("Content-Type", "application/json");
    	try {
			httpcon.setRequestMethod("POST");

		} catch (ProtocolException e) {
			e.printStackTrace();
			Notification.show("fehler!");
		}
    	
    	String httpBody = "{\"code\": \""
    			+ code
    			+ "\"} "
    			+ " {\"kind\" : \"spark\" }";
    	
    	
    	httpcon.setRequestProperty("Content-Length", Integer.toString(httpBody.length()));
    	try {
			httpcon.getOutputStream().write(httpBody.getBytes("UTF8"));
		} catch (UnsupportedEncodingException e1) {
			e1.printStackTrace();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		try {
			httpcon.connect();
		} catch (IOException e) {
			e.printStackTrace();
		}
		Notification.show("WOHO!");
		try {
			output = IOUtils.toString(httpcon.getInputStream(), "UTF-8"); 
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		@SuppressWarnings("unused")
		JSONObject obj = new JSONObject(output);
		System.out.println(output);
		return output;

	}
	
	public String getSessionState(int idSession, int idStatement) {
		String output = null;
    	HttpURLConnection httpcon = null;
		try {
			httpcon = (HttpURLConnection) ((new URL(awsURL+"/sessions/"+idSession+"/statements/"+idStatement).openConnection()));
		} catch (IOException e) {
			e.printStackTrace();
			Notification.show("Fehler!");
		}
    	httpcon.setDoOutput(true);
    	httpcon.setRequestProperty("Content-Type", "application/json");
    	try {
			httpcon.setRequestMethod("GET");

		} catch (ProtocolException e) {
			e.printStackTrace();
			Notification.show("fehler!");
		}
    	
    	
		try {
			httpcon.connect();
		} catch (IOException e) {
			e.printStackTrace();
		}
		try {
			output = IOUtils.toString(httpcon.getInputStream(), "UTF-8"); 
		} catch (IOException e) {
			e.printStackTrace();
			Notification.show("Fehler!");
		}
		
		@SuppressWarnings("unused")
		JSONObject obj = new JSONObject(output);
		System.out.println(output);
		return output;

	}
		
}
