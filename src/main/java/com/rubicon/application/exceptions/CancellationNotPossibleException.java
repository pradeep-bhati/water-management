package com.rubicon.application.exceptions;

public class CancellationNotPossibleException extends RuntimeException{

	/**
	 * 
	 */
	private static final long serialVersionUID = 8695427155038933161L;
	public CancellationNotPossibleException(String message) {
		super(message);
	}
}
