package WarOfOmens;


public class UserInterceptionException extends Exception {

	/**
	 * Thrown when User Intercepted, and the process needs to be halted.
	 */
	private static final long serialVersionUID = 1L;


	public UserInterceptionException() {
		super("User Interception! Process Halted!");
	}


}
