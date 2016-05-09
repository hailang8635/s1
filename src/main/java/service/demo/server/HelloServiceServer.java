package service.demo.server;

import org.apache.thrift.TProcessor;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.server.TServer;
import org.apache.thrift.server.TSimpleServer;
import org.apache.thrift.server.TThreadPoolServer.Args;
import org.apache.thrift.transport.TServerSocket;

import service.demo.Hello;
import service.demo.Hello.Iface;

public class HelloServiceServer {
	public static final int SERVER_PORT = 8090;

	public void startServer() {
		try {
			System.out.println("HelloWorld TSimpleServer start ....");

			TProcessor tprocessor = new Hello.Processor<Iface>(  
                    new HelloServiceImpl());  
			
			// 简单的单线程服务模型，一般用于测试
			TServerSocket serverTransport = new TServerSocket(SERVER_PORT);
			Args tArgs = new Args(serverTransport);
			tArgs.processor(tprocessor);
			tArgs.protocolFactory(new TBinaryProtocol.Factory());
			TServer server = new TSimpleServer(tArgs);
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
		HelloServiceServer server = new HelloServiceServer();
		server.startServer();
	}

}