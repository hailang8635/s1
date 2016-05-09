package service.demo.client;

import org.apache.thrift.TException;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.transport.TFramedTransport;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TTransport;
import org.apache.thrift.transport.TTransportException;

import service.demo.Hello.Client;


/**
 * 
 * @author LK
 *
 */
public class HelloClientDemo {
	public static final String SERVER_IP = "127.0.0.1";
	public static final int SERVER_PORT = 8090;
	public static final int TIMEOUT = 30000;

	/**
	 *
	 * @param userName
	 */
	public void startClient(String userName) {
		TTransport transport = null;
		try {
			transport = new TSocket(SERVER_IP, SERVER_PORT, TIMEOUT);
//			transport = new TFramedTransport(new TSocket(SERVER_IP, SERVER_PORT, TIMEOUT));
			
			// 协议要和服务端一致
			TProtocol protocol = new TBinaryProtocol(transport);
			//TProtocol protocol = new TCompactProtocol(transport);
			// TProtocol protocol = new TJSONProtocol(transport);
			Client client = new Client(protocol);
			transport.open();
//			String result = client.helloString(userName);
			for(int i=0;i<3000*10000;i++){
				client.helloString(userName);
			}
//			System.out.println(result);
		} catch (TTransportException e) {
			e.printStackTrace();
		} catch (TException e) {
			e.printStackTrace();
		} finally {
			if (null != transport) {
				transport.close();
			}
		}
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		System.out.println(System.currentTimeMillis()/1000);
		HelloClientDemo client = new HelloClientDemo();
		//for(int i=0;i<10000;i++){
			client.startClient("smart");
		//}
		System.out.println(System.currentTimeMillis()/1000);

	}
}
