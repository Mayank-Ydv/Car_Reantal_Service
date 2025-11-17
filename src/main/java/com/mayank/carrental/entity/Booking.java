package com.mayank.carrental.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;

@Entity
@Table(name = "bookings")
public class Booking {
    @Id
    private UUID id;

    private UUID userId;

    private Integer branchPickupId;
    private Integer branchReturnId;

    private BigDecimal totalPrice;

    private String status;

    @Column(name = "created_at")
    private LocalDateTime createdAt = LocalDateTime.now();

    @Column(name = "start_ts")
    private LocalDateTime startTs;

    @Column(name = "end_ts")
    private LocalDateTime endTs;

    @ManyToOne
    @JoinColumn(name = "vehicle_id")
    private Vehicle vehicle;

    // getters & setters
    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }
    public UUID getUserId() { return userId; }
    public void setUserId(UUID userId) { this.userId = userId; }
    public Integer getBranchPickupId() { return branchPickupId; }
    public void setBranchPickupId(Integer branchPickupId) { this.branchPickupId = branchPickupId; }
    public Integer getBranchReturnId() { return branchReturnId; }
    public void setBranchReturnId(Integer branchReturnId) { this.branchReturnId = branchReturnId; }
    public BigDecimal getTotalPrice() { return totalPrice; }
    public void setTotalPrice(BigDecimal totalPrice) { this.totalPrice = totalPrice; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
    public LocalDateTime getStartTs() { return startTs; }
    public void setStartTs(LocalDateTime startTs) { this.startTs = startTs; }
    public LocalDateTime getEndTs() { return endTs; }
    public void setEndTs(LocalDateTime endTs) { this.endTs = endTs; }
    public Vehicle getVehicle() { return vehicle; }
    public void setVehicle(Vehicle vehicle) { this.vehicle = vehicle; }   
}
