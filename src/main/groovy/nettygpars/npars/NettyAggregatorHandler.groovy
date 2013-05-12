package nettygpars.npars

import static nettygpars.npars.NettyAggregatorActor.ABORT

import groovy.json.JsonBuilder

import io.netty.channel.embedded.AbstractEmbeddedChannel
import io.netty.channel.ChannelHandlerContext
import io.netty.channel.ChannelInboundMessageHandlerAdapter

/**
 * Mediocre Blocking version of Gpars+Netty handler
**/
class NettyAggregatorHandler extends ChannelInboundMessageHandlerAdapter<String>{

	/**
	 * Once we receive the file path we create a new actor and we send the filePath to
	 * it and wait for the response. Then we answer back to the sender.
  	 *
	 * @param context
	 * @param filePath
	 * 
	**/
	void messageReceived (ChannelHandlerContext context,String filePath) throws Exception {
		def actor = new NettyAggregatorActor().start()
		def jsonResponse = actor.sendAndWait(filePath) // Blocking JSON response

		context.channel().write(jsonResponse) 
	}

	/**
	 * Nothing fancy here
	**/
	void exceptionCaught(ChannelHandlerContext context,Throwable e) throws Exception {
		e.getCause().printStackTrace();
	}

}
