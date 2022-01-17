package com.rubicon.application.exceptions;

public class OrderConflictException extends RuntimeException{

	/**
	 * 
	 */
	private static final long serialVersionUID = 6230781137321466769L;
	
	public OrderConflictException(String message)
	{
		super(message);
	}
	

}
