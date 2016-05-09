package service.demo.server;


import org.apache.thrift.TProcessor;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.server.TServer;
import org.apache.thrift.server.TThreadPoolServer;
import org.apache.thrift.server.TThreadPoolServer.Args;
import org.apache.thrift.transport.TServerSocket;

import service.demo.Hello;


/**
 * 线程池服务模型，使用标准的阻塞式IO，预先创建一组线程处理请求    TThreadPoolServer 服务模型
 * @author LK
 *
 */
public class HelloTThreadPoolServerDemo {

	public static final int SERVER_PORT = 8090;
	
	public void startServer() {
		try {
			System.out.println("HelloWorld TThreadPoolServer start ....");
 
			TProcessor tprocessor = new Hello.Processor<Hello.Iface>(
					new HelloServiceImpl());
			TServerSocket serverTransport = new TServerSocket(SERVER_PORT);
			//TThreadPoolServer 线程池服务模型
			Args ttpsArgs = new Args(
					 serverTransport);
			ttpsArgs.processor(tprocessor);
			ttpsArgs.protocolFactory(new TBinaryProtocol.Factory());
			//线程池服务模型，使用标准的阻塞式IO，预先创建一组线程处理请求。
			TServer server = new TThreadPoolServer(ttpsArgs);
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
		HelloTThreadPoolServerDemo server = new HelloTThreadPoolServerDemo();
		server.startServer();
	}
}
