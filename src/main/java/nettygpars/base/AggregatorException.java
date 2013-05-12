package nettygpars.base;

/**
 * Exception thrown by instances of type Aggregator
 **/
public class AggregatorException extends Exception{

	/**
	 * This constructor allows to establish a given
	 * message to explain the exception
	 *
	 * @param message Exception explanation
	 **/
	public AggregatorException(String message){
		super(message);
	}

}
