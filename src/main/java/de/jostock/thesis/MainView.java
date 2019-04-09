package de.jostock.thesis;

import com.vaadin.flow.component.applayout.*;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.PWA;

/**
 * The main view contains a button and a click listener.
 */

/**
 * 
 * GUI Menuband oben.
 * 
 * @author matthias jostock
**/
@SuppressWarnings("serial")
@Route("")
@PWA(name = "Project Base for Vaadin Flow", shortName = "Project Base", startPath = "session")
public class MainView extends AbstractAppRouterLayout {

	AppLayout appLayout = new AppLayout();
	AppLayoutMenu menu = appLayout.createMenu();

	public MainView() {

    }
	
	@Override
	protected void configure(AppLayout appLayout, AppLayoutMenu menu) {
		appLayout.setBranding(new H3("CoinEX"));

    	menu.addMenuItems(
    	        new AppLayoutMenuItem(VaadinIcon.START_COG.create(), "Start", "start"));
    	menu.addMenuItems(
                new AppLayoutMenuItem(VaadinIcon.FLIP_V.create(), "Sessions", "sessions"));
    	
    	
	}

}
