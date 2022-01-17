package com.rubicon.application.entites;

import java.time.LocalDateTime;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;

@Entity(name="orders")
@DynamicInsert
public class Orders {
	
	@Id
    @GeneratedValue
    private UUID requestId;
	
	@Column(name="farmid")
	private String farmId;
	
	@Column(name="startdatetime")
	private LocalDateTime startDateTime;
	
	@Column(name="duration")
	private String duration;
	
	@Column(name="status")
	@ColumnDefault(value="true")
	private String status;
	
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getFarmId() {
		return farmId;
	}

	public UUID getRequestId() {
		return requestId;
	}

	public void setRequestId(UUID requestId) {
		this.requestId = requestId;
	}

	public void setFarmId(String farmId) {
		this.farmId = farmId;
	}

	public LocalDateTime getStartDateTime() {
		return startDateTime;
	}

	public void setStartDateTime(LocalDateTime startDateTime) {
		this.startDateTime = startDateTime;
	}

	public String getDuration() {
		return duration;
	}

	public void setDuration(String duration) {
		this.duration = duration;
	}

}
