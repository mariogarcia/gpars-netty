package nettygpars.gpars

import groovyx.gpars.actor.DefaultActor
import nettygpars.simple.SimpleAggregator

/**
 * This actor reads one file at a time an return to the 
 * caller the frequencies found in the file
**/
class SimpleAggregatorActor extends DefaultActor{

	static final String ABORT = "abort" 

	void act(){
		loop{
		 /* Somebody send the actor a file to process */
			react{String filePath->
				if (filePath == ABORT){
					terminate()
					return
				}
			 /* We're using the same aggregator type for all actors */
				def simpleAggregator = new SimpleAggregator()
				def frequencies = simpleAggregator.parse(new File(filePath))
			 /* Once we've finished the task we send back the result */
				reply(frequencies)
			}
		}
	}

}
