package de.jostock.thesis;


import java.util.List;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.router.Route;

import sun.tools.jconsole.Plotter.Unit;

/**
 * 
 * GUI View für eine Session
 * Hier wird die Analyse zusammengebaut
 * 
 * @author matthias jostock
**/
@SuppressWarnings("serial")
@Route(value = "sessions", layout = MainView.class)
public class SessionView extends VerticalLayout{
	
	CodeFactory cf = new CodeFactory();
	TextArea textArea = new TextArea();
	ComboBox<String> comboBox = new ComboBox<>("Sessions");
	List<Session> sessions;
	Session session;
	
	int counter = 0; 
	
	public SessionView(){
		
		VerticalLayout vl = new VerticalLayout();
		
		TextArea textArea = new TextArea();
		ComboBox<Session> comboBox = new ComboBox<>("Sessions");
		
		sessions = Sessions.getInstance().getSessions();
				
		comboBox.setItems(sessions);
		comboBox.setItemLabelGenerator(Session::getIdString);

		comboBox.addValueChangeListener(event -> {
			session = comboBox.getValue();
		    if (session != null) {
		    	cf.sendReadCSVCode(session.getId());
		    	cf.sendCreateClassCode(session.getId());
		    	cf.sendSplitCode(session.getId());
		    	click_button_explore(vl, cf.sendClassMappingCode(session.getId()));
		    } else {
		    	Notification.show("No session is selected");
		    }
		});
		
//		Button button_explore = new Button("EXPLORE THE OCRE COINS!", 
//				event -> click_button_explore(vl, "OCRE RDD"));
  

		comboBox.setWidth("100%");
		textArea.setWidth("100%");
//		button_explore.setWidth("100%");
		
		vl.add(comboBox);
//		vl.add(button_explore);
		
		add(vl);
	}

	private void click_button_explore(VerticalLayout vl, String inputRDD) {
		HorizontalLayout hl = new HorizontalLayout();
		hl.setWidth("100%");
		
		TextArea ta = new TextArea();
		ta.setValue(inputRDD);
		
		ComboBox<String> actionBox = new ComboBox<>();
		
		actionBox.setItems("Filter", "Count", "Save", "Split");

		actionBox.setWidth("25%");
		hl.add(actionBox);
		
		//Buttons für RDD Transformationen und Aktionen.
		Button button_filter = new Button("Filter!");
		Button button_count = new Button("Count!",
                event -> count(vl, inputRDD));
		Button button_show = new Button("Save!",
                event -> show(vl, inputRDD));
		Button button_split = new Button("Split!",
                event -> splitLayout(vl, ta.getValue()));
		
		//ComboBox für RDD Transformationen und Aktionen.
		
		ComboBox<String> filterBox = new ComboBox<>();
		filterBox.setItems("Authorität" , "Vorderseite - Legende", "Vorderseite - Beschreibung", "Vorderseite - Portrait", "Rückseite - Legende" , "Rückseite - Beschreibung");
		filterBox.setWidth("25%");
		
		//Textfeld für Filter-Parameter
		TextArea filterText = new TextArea();
		
		actionBox.addValueChangeListener(event -> {
			String string = actionBox.getValue();
			hl.removeAll();
			hl.add(actionBox);
		    if (string.equals("Filter")) {
		    	Notification.show("filter selected");
		    	
		    	hl.add(filterBox);
		    	
		    } else if(string.equals("Count")){
		    	Notification.show("count selected");
		    	
		    	hl.add(button_count);
		    	
		    } else if(string.equals("Save")){
		    	Notification.show("show selected");
		    	
		    	hl.add(button_show);
		    	
		    } else if(string.equals("Split")){
		    	Notification.show("show selected");
		    	
		    	hl.add(button_split);
		    	
		    } else{
		    	Notification.show("No valid input");
		    }
		});
		
		filterBox.addValueChangeListener(event -> {
			
			String string = filterBox.getValue();
			
			hl.add(filterText, button_filter);
			hl.remove(filterText, button_filter);
		    filterText.setWidth("25%");
		    button_filter.setWidth("25%");
			if (string.equals("Authorität")) {
		    	
		    	hl.add(filterText, button_filter);
		    	button_filter.addClickListener(event1 -> click_button_explore(vl, cf.sendFilterCode(session.getId(), counter++, inputRDD, "authority", filterText.getValue())));
		    	
		    } else if(string.equals("Vorderseite - Legende")){

		    	hl.add(filterText, button_filter);
		    	button_filter.addClickListener(event1 -> click_button_explore(vl, cf.sendContainsCode(session.getId(), counter++, inputRDD, "legendOBV", filterText.getValue())));
		    	
		    } else if(string.equals("Vorderseite - Beschreibung")){

		    	hl.add(filterText, button_filter);
		    	button_filter.addClickListener(event1 -> click_button_explore(vl, cf.sendContainsCode(session.getId(), counter++, inputRDD, "descriptionOBV", filterText.getValue())));
		    	
		    } else if(string.equals("Vorderseite - Portrait")){
		    	
		    	hl.add(filterText, button_filter);
		    	button_filter.addClickListener(event1 -> click_button_explore(vl, cf.sendFilterCode(session.getId(), counter++, inputRDD, "portraitOBV", filterText.getValue())));

		    } else if(string.equals("Rückseite - Legende")){

		    	hl.add(filterText, button_filter);
		    	button_filter.addClickListener(event1 -> click_button_explore(vl, cf.sendContainsCode(session.getId(), counter++, inputRDD, "legendREV", filterText.getValue())));

		    } else if(string.equals("Rückseite - Beschreibung")){

		    	hl.add(filterText, button_filter);
		    	button_filter.addClickListener(event1 -> click_button_explore(vl, cf.sendContainsCode(session.getId(), counter++, inputRDD, "legendOBV", filterText.getValue())));
		    	
		    	
		    } else{
		    	Notification.show("No valid input");
		    }
		});
		
//		Button button_split = new Button("Split!",
//                event -> splitLayout(vl, inputRDD));
//		Button button_notSplit = new Button("EXPLORE!",
//                event -> click_button_explore(vl, "OCRE RDD_" + vl.getParent().get().toString()));
		
		ta.setWidth("100%");
//		button_split.setWidth("50%");
//		button_notSplit.setWidth("50%");
//		
//		hl.add(button_split, button_notSplit);
		
		vl.add(ta, hl);
		
	}

	private void show(VerticalLayout vl, String inputRDD) {
		Notification.show("save clicked!!!");
		cf.sendSaveCode(session.getId(), inputRDD);
		
	}

	private void count(VerticalLayout vl, String inputRDD) {
		Notification.show("count clicked!!!");
		//dialog.add(new Label("Anzahl: "+ cf.sendCountCode(session.getId(), inputRDD)));
		TextArea resultCount = new TextArea();
		resultCount.setValue("Anzahl: "+ cf.sendCountCode(session.getId(), inputRDD));
		resultCount.setWidth("100%");
		vl.add(resultCount);
		
	}

	private void splitLayout(VerticalLayout hl, String inputRDD) {
		HorizontalLayout hozl = new HorizontalLayout();
		VerticalLayout vl1 = new VerticalLayout();
		VerticalLayout vl2 = new VerticalLayout();
				
		click_button_explore(vl1, inputRDD);
		click_button_explore(vl2, inputRDD);
		
		vl1.setWidth("50%");
		vl2.setWidth("50%");
		hozl.setWidth("100%");
		
		hozl.add(vl1, vl2);
		
		hl.add(hozl);

		
		
		
	}
	
}
