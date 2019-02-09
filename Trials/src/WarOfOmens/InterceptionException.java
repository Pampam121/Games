package WarOfOmens;

public class InterceptionException extends Exception {

	/**
	 * Thrown when User Intercepted or there is a connection problem, and the process needs to be halted.
	 * There are 4 types:
	 * User for Mouse Move
	 * Kongregate for Kongregate disconnection
	 * War Of Omens for WoO opened elsewhere
	 * Internet for all other problems
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public Interruption interruption = null;

	public enum Interruption {
		USER, KONGREGATE, WAROFOMENS, INTERNET
	}


	InterceptionException() {
		super();
	}
	
	InterceptionException(String s) {
		super(s);
		interruption = Interruption.USER;
	}


	public InterceptionException(Interruption intr) {
		switch (intr) {
			case USER:
				new InterceptionException("User Interruption! Mouse Moved!", intr);
				break;

			case KONGREGATE:
				new InterceptionException("Kongregate Disconnected! Restarting!", intr);
				break;
				
			case WAROFOMENS:
				new InterceptionException("War of Omens opened elsewhere! Restarting!", intr);
				break;

			case INTERNET:
				new InterceptionException("Internet or Chrome error! Restarting!", intr);
				break;

			default:
				new InterceptionException();
				break;
		}
	}


	InterceptionException(String stuff, Interruption intr) {
		super(stuff);
		interruption = intr;
	}

}
