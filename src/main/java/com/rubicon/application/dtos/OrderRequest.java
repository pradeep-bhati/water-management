package com.rubicon.application.dtos;

import java.time.LocalDateTime;

import javax.validation.constraints.Pattern;

public class OrderRequest {
	
	@Pattern(regexp = "^(?!00000000)[0-9]{8}$", message = "Please enter valid 8 digit numeric farm-id.")
	private String farmid;

//	@JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy/MM/dd hh:mm:ss")
	private LocalDateTime dateTime;
	
	@Pattern(regexp = "^(?!00)[0-9]{2}$", message = "Enter hours as 2 digit numeric, max allowed hours is 99.")
	private String duration;
	
	@Override
	public String toString() {
		return "OrderRequest [farmId=" + farmid + ", date=" + dateTime + ", duration=" + duration + "]";
	}

	public String getFarmid() {
		return farmid;
	}

	public void setFarmid(String farmid) {
		this.farmid = farmid;
	}

	public LocalDateTime getDateTime() {
		return dateTime;
	}

	public void setDateTime(LocalDateTime dateTime) {
		this.dateTime = dateTime;
	}

	public String getDuration() {
		return duration;
	}

	public void setDuration(String duration) {
		this.duration = duration;
	}
	
}
