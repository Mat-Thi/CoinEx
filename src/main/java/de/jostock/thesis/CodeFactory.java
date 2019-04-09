package de.jostock.thesis;

import java.util.List;

import org.apache.livy.shaded.jackson.databind.ObjectMapper;
import org.json.JSONObject;

import com.vaadin.flow.component.notification.Notification;


/**
 * 
 * Die CodeFactory ersetellt den Code für verschiedene Aktionen in der App
 * Diese sendet den Code an die Livy Restschnittstelle über die Klasse LivyRestConnector.java
 * 
 * @author matthias jostock
**/
public class CodeFactory {
	
	LivyRestConnector lrc = LivyRestConnector.getInstance();
	
	public CodeFactory() {
		
	}
	
	public String sendReadCSVCode(int id) {
		
		String result = "waiting";
		String output = lrc.sendCodeToSession(id, "val ocreRDD = sc.textFile(\\\"s3://ocre-json/data/data_v2.csv\\\");");

		JSONObject obj = new JSONObject(output);
		while (!obj.optString("state").equalsIgnoreCase("available")){
			result = lrc.getSessionState(id, obj.optInt("id"));
			obj = new JSONObject(result);
		}
		obj = new JSONObject(obj.optString("output"));
		obj = new JSONObject(obj.optString("data"));
		
		return obj.optString("text/plain");
		
	}
	
	public String sendCreateClassCode(int id) {
		
		String result = "waiting";
		String output = lrc.sendCodeToSession(id, "case class Data (typeID:String,obv:String,legendOBV:String,descriptionOBV:String,portraitOBV:String,rev:String,legendREV:String,descriptionREV:String,coin:String,obverse:String,obvPIC:String,reverse:String,revPIC:String,authority:String);"); 
				
		JSONObject obj = new JSONObject(output);
		while (!obj.optString("state").equalsIgnoreCase("available")){
			result = lrc.getSessionState(id, obj.optInt("id"));
			obj = new JSONObject(result);
		}
		obj = new JSONObject(obj.optString("output"));
		obj = new JSONObject(obj.optString("data"));
		
		return obj.optString("text/plain");
		
	}
	
	public String sendSplitCode(int id) {
	
	String result = "waiting";
	String output = lrc.sendCodeToSession(id, "val dataCoin = ocreRDD.map(s=>s.split(\\\";\\\",-1));");
		
	JSONObject obj = new JSONObject(output);
	while (!obj.optString("state").equalsIgnoreCase("available")){
		result = lrc.getSessionState(id, obj.optInt("id"));
		obj = new JSONObject(result);
	}
	obj = new JSONObject(obj.optString("output"));
	obj = new JSONObject(obj.optString("data"));
	
	return obj.optString("text/plain");
	
}
	
	public String sendClassMappingCode(int id) {
	
	String result = "waiting";
	String output = lrc.sendCodeToSession(id, "val OCRE_RDD = dataCoin.map(s=>Data(s(0).toString()," + 
			"            s(1).toString()," + 
			"            s(2).toString()," + 
			"            s(3).toString()," + 
			"            s(4).toString()," + 
			"            s(5).toString()," + 
			"            s(6).toString()," + 
			"            s(7).toString()," + 
			"            s(8).toString()," + 
			"            s(9).toString()," + 
			"            s(10).toString()," + 
			"            s(11).toString()," + 
			"            s(12).toString()," + 
			"            s(13).toString()" + 
			"        )" + 
			");");
	JSONObject obj = new JSONObject(output);
	while (!obj.optString("state").equalsIgnoreCase("available")){
		result = lrc.getSessionState(id, obj.optInt("id"));
		obj = new JSONObject(result);
	}
	obj = new JSONObject(obj.optString("output"));
	obj = new JSONObject(obj.optString("data"));
	String[] outputRDD = obj.optString("text/plain").split(":", 2);
	
	return outputRDD[0];
	
}
	
	public String sendContainsCode(int idSession, int idInputStatement, String inputRDD, String filterField, String filterText) {
		
		String result = "waiting";
		String output = lrc.sendCodeToSession(idSession, "val filterRDD_"+idInputStatement+" = "+ inputRDD +".filter(x => x."+ filterField +".contains(\\\""+filterText+"\\\"))");
		JSONObject obj = new JSONObject(output);
		while (!obj.optString("state").equalsIgnoreCase("available")){
			Notification.show(obj.optString("progress"));
			result = lrc.getSessionState(idSession, obj.optInt("id"));
			obj = new JSONObject(result);
		}
		obj = new JSONObject(obj.optString("output"));
		obj = new JSONObject(obj.optString("data"));
		String[] outputRDD = obj.optString("text/plain").split(":", 2);
		
		return outputRDD[0];
		
	}
	
	public String sendFilterCode(int idSession, int idInputStatement, String inputRDD, String filterField, String filterText) {
		
		String result = "waiting";
		String output = lrc.sendCodeToSession(idSession, "val filterRDD_"+idInputStatement+" = "+ inputRDD +".filter(x => x."+ filterField +" == \\\""+filterText+"\\\")");
		JSONObject obj = new JSONObject(output);
		while (!obj.optString("state").equalsIgnoreCase("available")){
			Notification.show(obj.optString("progress"));
			result = lrc.getSessionState(idSession, obj.optInt("id"));
			obj = new JSONObject(result);
		}
		obj = new JSONObject(obj.optString("output"));
		obj = new JSONObject(obj.optString("data"));
		String[] outputRDD = obj.optString("text/plain").split(":", 2);
		
		return outputRDD[0];
		
	}
	
	
	public String sendCountCode(int idSession,String inputRDD) {
		
		String result = "waiting";
		String output = lrc.sendCodeToSession(idSession, inputRDD+".count();");
		JSONObject obj = new JSONObject(output);
		while (!obj.optString("state").equalsIgnoreCase("available")){
			Notification.show(obj.optString("progress"));
			result = lrc.getSessionState(idSession, obj.optInt("id"));
			obj = new JSONObject(result);
		}
		obj = new JSONObject(obj.optString("output"));
		obj = new JSONObject(obj.optString("data"));
		String[] outputRDD = obj.optString("text/plain").split(":", 2);
		
		return outputRDD[1].replaceAll("[^0-9]", "");

		
	}
	
	public String sendShowCode(int idSession,String inputRDD) {
		
		String result = "waiting";
		String output = lrc.sendCodeToSession(idSession, inputRDD+".collect();");
		JSONObject obj = new JSONObject(output);
		while (!obj.optString("state").equalsIgnoreCase("available")){
			Notification.show(obj.optString("progress"));
			result = lrc.getSessionState(idSession, obj.optInt("id"));
			obj = new JSONObject(result);
		}
		obj = new JSONObject(obj.optString("output"));
		obj = new JSONObject(obj.optString("data"));
		String[] outputRDD = obj.optString("text/plain").split("=", 2);
		System.out.println(outputRDD[1].toString());
		
		
		
		return outputRDD[1];
		

		
	}
	
	public String sendSaveCode(int idSession,String inputRDD) {
		
		String result = "waiting";
		String output = lrc.sendCodeToSession(idSession, inputRDD+".saveAsTextFile(\\\"s3://ocre-json/results/"+inputRDD+".csv\\\");");
		JSONObject obj = new JSONObject(output);
		while (!obj.optString("state").equalsIgnoreCase("available")){
			result = lrc.getSessionState(idSession, obj.optInt("id"));
			obj = new JSONObject(result);
		}
		obj = new JSONObject(obj.optString("output"));
		obj = new JSONObject(obj.optString("data"));
		String outputRDD = obj.optString("text/plain");
		System.out.println(outputRDD);
		
		return outputRDD;
		

		
	}

}
