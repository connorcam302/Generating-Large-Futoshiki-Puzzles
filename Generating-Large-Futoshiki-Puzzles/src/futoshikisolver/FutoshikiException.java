package futoshikisolver;

/**
 * A Futoshiki specific run time exception
 * 
 * @author Dr Mark C. Sinclair
 * @version Noivember 2020
 */
@SuppressWarnings("serial")
class FutoshikiException extends RuntimeException {
  /**
   * Default constructor
   */
	FutoshikiException() {}
	
	/**
	 * Constructor with explanatory message
	 * 
	 * @param message the explanatory message
	 */
	FutoshikiException(String message) {
		super(message);
	}
}
