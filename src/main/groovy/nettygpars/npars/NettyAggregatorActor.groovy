package nettygpars.npars

import nettygpars.simple.SimpleAggregator

import groovy.json.JsonOutput
import groovyx.gpars.actor.DefaultActor

/**
 * A really simple actor that receives file paths and return the result
 * of processing that file
**/
class NettyAggregatorActor extends DefaultActor{

	void act(){
		react{String path->
			def resultMap = new SimpleAggregator().parse(new File(path)) 
			def result = JsonOutput.toJson(resultMap)
		 /* Returns the the result map as a json string */
			reply result
		}
	}

}
