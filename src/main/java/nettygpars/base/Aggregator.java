package nettygpars.base;

import java.io.File;
import java.util.Map;
import java.util.List;

public interface Aggregator{

	/**
	 * This method parses a given file an return a series of key,values
	 *
	 * @param file The file we want to extract the information from
	 * @return a list of key/value entries
	 * @throws AggregatorException it wraps possible io or parsing exceptions
	 **/
	public List<Map<String,Integer>> parse(File file) throws AggregatorException;


}
