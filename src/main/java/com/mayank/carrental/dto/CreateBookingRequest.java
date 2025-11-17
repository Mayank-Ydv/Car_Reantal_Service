package com.mayank.carrental.dto;

import java.time.LocalDateTime;
import java.util.UUID;

public class CreateBookingRequest {
	private UUID vehicleId;
	
	private LocalDateTime startTs;
	private LocalDateTime endTs;

	private Integer pickupBranch;
	private Integer returnBranch;

	// getters & setters
	public UUID getVehicleId() {
		return vehicleId;
	}

	public void setVehicleId(UUID vehicleId) {
		this.vehicleId = vehicleId;
	}

	public LocalDateTime getStartTs() {
		return startTs;
	}

	public void setStartTs(LocalDateTime startTs) {
		this.startTs = startTs;
	}

	public LocalDateTime getEndTs() {
		return endTs;
	}

	public void setEndTs(LocalDateTime endTs) {
		this.endTs = endTs;
	}

	public Integer getPickupBranch() {
		return pickupBranch;
	}

	public void setPickupBranch(Integer pickupBranch) {
		this.pickupBranch = pickupBranch;
	}

	public Integer getReturnBranch() {
		return returnBranch;
	}

	public void setReturnBranch(Integer returnBranch) {
		this.returnBranch = returnBranch;
	}
}
