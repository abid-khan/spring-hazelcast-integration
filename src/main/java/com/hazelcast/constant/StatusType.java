package com.hazelcast.constant;

/**
 * @author abidk
 * 
 */
public enum StatusType {

    NEW("New"), ACTIVE("Active");

    private StatusType(String statusType) {
	this.statusType = statusType;
    }

    private String statusType;

    public String getStatusType() {
	return statusType;
    }

}
