package nettygpars.npars

import java.net.InetSocketAddress

import io.netty.bootstrap.ServerBootstrap

import io.netty.handler.codec.string.StringDecoder
import io.netty.handler.codec.string.StringEncoder

import io.netty.channel.ChannelOption
import io.netty.channel.ChannelInitializer

import io.netty.channel.nio.NioEventLoopGroup

import io.netty.channel.socket.SocketChannel
import io.netty.channel.socket.nio.NioServerSocketChannel
/**
 * Because I want to be able to execute a demo of the different samples
 * I've built this executable class
**/
class NettyBlockingExample{

	/**
	 * Declaring the ChannelInitializer here
	**/
	static class MyChannelInitializer extends ChannelInitializer<SocketChannel>{
		void initChannel(SocketChannel channel) throws Exception{
			channel.pipeline().addLast(new NettyAggregatorHandler())
		}
	}

	/**
	 * EXECUTE
	**/
	static void main(args) throws Exception{
	 /* Building the channel factory */
		def server = new ServerBootstrap().
			group(new NioEventLoopGroup(),new NioEventLoopGroup()).
			channel(NioServerSocketChannel.class).
			childHandler(new MyChannelInitializer()).
			option(ChannelOption.TCP_NODELAY,true).
			option(ChannelOption.SO_KEEPALIVE,true)
	 /* Starting up the server */
		def future = 
			server.bind(new InetSocketAddress(8080)).sync()

		future.channel().closeFuture().sync()
	}

}
