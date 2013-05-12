package nettygpars.gpars

import groovyx.gbench.Benchmark
import spock.lang.Specification

class SimpleAggregatorActorSpec extends Specification{

	@Benchmark
	def "Reading a single file with actors"(){
		setup: "An actor an a file to process"
			def actor = new SimpleAggregatorActor()
			def file = new File("src/test/resources/catalogue_1.xml")
		when: "Giving work to the actor"
			actor.start()
		 /* synchronous call */
			def results = actor.sendAndWait(file.absolutePath)
		and: "Killing the actor"
			actor << "abort"
		then: "The results must be the same as if weren't using actors"
			results.size() == 53
			results.max{it.year}.year == '2012'
			results.min{it.year}.year == '1960'
			results.max{it.frequency}.year == '1970'
			results.max{it.frequency}.frequency == 2
			results.min{it.frequency}.frequency == 1
	}

	@Benchmark
	def "Reading two files with actors"(){
		setup: "Creating two actors to process to files"
			def actor1 = new SimpleAggregatorActor()
			def actor2 = new SimpleAggregatorActor()
			def directory = new File("src/test/resources")
			def file1 = new File(directory,"catalogue_1.xml")	
			def file2 = new File(directory,"catalogue_2.xml")	
		when:
			[actor1,actor2]*.start()
			def promise_1 = actor1.sendAndPromise(file1.absolutePath)
			def promise_2 = actor2.sendAndPromise(file2.absolutePath)
		and:
			def results_1 = promise_1.get()
			def results_2 = promise_2.get() 
		and: "Killing them"
			[actor1,actor2].each{ it << SimpleAggregatorActor.ABORT }
		then:
			results_1.size() == 53
			results_2.size() == 53
	}

}
