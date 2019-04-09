package de.jostock.thesis;

/**
 * 
 * Klasse die eine Session verwaltet
 * 
 * @author matthias jostock
**/
public class Session {
	public int id;
	public String appId;
	public String owner;
	public String proxyUser;
	public SessionKind kind;
	public String log;
	public String state;
	public String appInfo;

	public Session(int id, String appId, String owner, String proxyUser, SessionKind kind, String log, String state,
			String appInfo) {
		this.id = id;
		this.appId = appId;
		this.owner = owner;
		this.proxyUser = proxyUser;
		this.kind = kind;
		this.log = log;
		this.state = state;
		this.appInfo = appInfo;
		
		Sessions.getInstance().addSession(this);
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	
	public String getIdString() {
		return String.valueOf(id);
	}
	
	public String getAppId() {
		return appId;
	}

	public void setAppId(String appId) {
		this.appId = appId;
	}

	public String getOwner() {
		return owner;
	}

	public void setOwner(String owner) {
		this.owner = owner;
	}

	public String getProxyUser() {
		return proxyUser;
	}

	public void setProxyUser(String proxyUser) {
		this.proxyUser = proxyUser;
	}

	public SessionKind getKind() {
		return kind;
	}

	public void setKind(SessionKind kind) {
		this.kind = kind;
	}

	public String getLog() {
		return log;
	}

	public void setLog(String log) {
		this.log = log;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getAppInfo() {
		return appInfo;
	}

	public void setAppInfo(String appInfo) {
		this.appInfo = appInfo;
	}
}
