package photoLoop;

/**
 * Class for the checked exception, ExmptyLoopException, thrown when one tries
 * to access nodes in an empty loop.
 * 
 * @author Katrina Van Laan
 */
public class EmptyLoopException extends Exception {
	public EmptyLoopException() {
		super();// constructor for empty loop exception
	}

	public EmptyLoopException(String message) {
		super(message);// will take string message as input
	}
}
