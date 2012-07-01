package com.sksamuel.camelwatch.consumer;

/**
 * @author Stephen K Samuel samspade79@gmail.com 1 Jul 2012 17:14:58
 * 
 */
public class Consumer {

	private String	camelId;
	private long	delay;
	private String	endpointUri;
	private long	inflightExchanges;
	private long	initialDelay;
	private String	routeId;
	private String	state;
	private boolean	supportSuspension;
	private boolean	suspended;
	private String	timeUnit;
	private boolean	useFixedDelay;
	private String	name;
	private String	description;

	public String getCamelId() {
		return camelId;
	}

	public long getDelay() {
		return delay;
	}

	public String getDescription() {
		return description;
	}

	public String getEndpointUri() {
		return endpointUri;
	}

	public long getInflightExchanges() {
		return inflightExchanges;
	}

	public long getInitialDelay() {
		return initialDelay;
	}

	public String getName() {
		return name;
	}

	public String getRouteId() {
		return routeId;
	}

	public String getState() {
		return state;
	}

	public String getTimeUnit() {
		return timeUnit;
	}

	public boolean isSupportSuspension() {
		return supportSuspension;
	}

	public boolean isSuspended() {
		return suspended;
	}

	public boolean isUseFixedDelay() {
		return useFixedDelay;
	}

	public void setCamelId(String camelId) {
		this.camelId = camelId;
	}

	public void setDelay(long delay) {
		this.delay = delay;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setEndpointUri(String endpointUri) {
		this.endpointUri = endpointUri;
	}

	public void setInflightExchanges(long inflightExchanges) {
		this.inflightExchanges = inflightExchanges;
	}

	public void setInitialDelay(long inflightDelay) {
		this.initialDelay = inflightDelay;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setRouteId(String routeId) {
		this.routeId = routeId;
	}

	public void setState(String state) {
		this.state = state;
	}

	public void setSupportSuspension(boolean supportSuspension) {
		this.supportSuspension = supportSuspension;
	}

	public void setSuspended(boolean suspended) {
		this.suspended = suspended;
	}

	public void setTimeUnit(String timeUnit) {
		this.timeUnit = timeUnit;
	}

	public void setUseFixedDelay(boolean useFixedDelay) {
		this.useFixedDelay = useFixedDelay;
	}

}
