package org.credit.suisse.event;

public class CreditSuisseEvent {

	private String id;
	private long duration;
	private String type;
	private String host;
	private boolean alert;
	private long startTimestamp;
	private long finishTimestamp;
	
	public String getId() {
		return id;
	}
	
	public void setId(String id) {
		this.id = id;
	}
	
	public long getDuration() {
		return duration;
	}
	
	public void setDuration(long duration) {
		this.duration = duration;
	}
	
	public String getType() {
		return type;
	}
	
	public void setType(String type) {
		this.type = type;
	}
	
	public String getHost() {
		return host;
	}
	
	public void setHost(String host) {
		this.host = host;
	}
	
	public boolean isAlert() {
		return alert;
	}
	
	public void setAlert(boolean alert) {
		this.alert = alert;
	}

	public long getStartTimestamp() {
		return startTimestamp;
	}

	public void setStartTimestamp(long startTimestamp) {
		this.startTimestamp = startTimestamp;
	}

	public long getFinishTimestamp() {
		return finishTimestamp;
	}

	public void setFinishTimestamp(long finishTimestamp) {
		this.finishTimestamp = finishTimestamp;
	}
}
