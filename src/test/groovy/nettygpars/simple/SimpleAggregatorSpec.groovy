package nettygpars.simple

import groovyx.gbench.Benchmark 
import spock.lang.Specification

class SimpleAggregatorSpec extends Specification{

	@Benchmark
	def "Getting year's frequencies"(){
		setup: "Aggregator"
			def file = new File("src/test/resources/catalogue_1.xml")
			def aggregator = new SimpleAggregator()
		when: "Parsing the file"
			def results = aggregator.parse(file)
		then: "Checking the results"
			results.size() == 53
			results.max{it.year}.year == '2012'
			results.min{it.year}.year == '1960'
			results.max{it.frequency}.frequency == 2
			results.min{it.frequency}.frequency == 1
	}

}
