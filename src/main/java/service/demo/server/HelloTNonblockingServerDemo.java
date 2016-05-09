package service.demo.server;


import org.apache.thrift.TProcessor;
import org.apache.thrift.protocol.TCompactProtocol;
import org.apache.thrift.server.TNonblockingServer;
import org.apache.thrift.server.TNonblockingServer.Args;
import org.apache.thrift.server.TServer;
import org.apache.thrift.transport.TFramedTransport;
import org.apache.thrift.transport.TNonblockingServerSocket;

import service.demo.Hello;

/**
 * 使用非阻塞式IO，服务端和客户端需要指定 TFramedTransport 数据传输的方式    TNonblockingServer 服务模型
 * @author LK
 *
 */
public class HelloTNonblockingServerDemo {

	public static final int SERVER_PORT = 8090;
	public void startServer() {
		try {
			System.out.println("HelloWorld TNonblockingServer start ....");
 
			TProcessor tprocessor = new Hello.Processor<Hello.Iface>(
					new HelloServiceImpl());
			TNonblockingServerSocket tnbSocketTransport = new TNonblockingServerSocket(SERVER_PORT);
			Args tnbArgs = new Args(tnbSocketTransport);
			tnbArgs.processor(tprocessor);
			tnbArgs.transportFactory(new TFramedTransport.Factory());
			tnbArgs.protocolFactory(new TCompactProtocol.Factory());
			TServer server = new TNonblockingServer(tnbArgs);
			server.serve();
			
		} catch (Exception e) {
			System.out.println("Server start error!!!");
			e.printStackTrace();
		}
	}
 
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		HelloTNonblockingServerDemo server = new HelloTNonblockingServerDemo();
		server.startServer();
	}
}
