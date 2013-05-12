package nettygpars.npars

import groovy.json.JsonSlurper
import groovyx.gbench.Benchmark
import spock.lang.Specification

import io.netty.channel.embedded.EmbeddedMessageChannel

/**
 * This test checks that the NettyAggregatorHandler behaves as expected, processing
 * a given file and answering with a json message
**/
class NettyAggregatorHandlerSpec extends Specification{

	@Benchmark
	def "Testing handler"(){
		setup: "Building a new handler"
			def handler = new NettyAggregatorHandler()
			def channel = new EmbeddedMessageChannel(handler)
		and: "The file we're gonna process"
			def file = new File("src/test/resources/catalogue_1.xml")
		when: "Sending a file path"
			channel.writeInbound(file.absolutePath)
		and: "Getting the handler output"
			def output = channel.readOutbound()
			def jsonResponse = new JsonSlurper().parseText(output)
		then: "Checking the response has something"
			jsonResponse.size() == 53
	}

}
