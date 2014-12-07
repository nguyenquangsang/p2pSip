import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class RequestListener extends Thread {
	ServerSocket serverSocket;

	public RequestListener() {
		try {
			serverSocket = new ServerSocket(Constant.PORT_SERVER);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void listen() {
		start();
	}

	public void run() {
		// TODO Auto-generated method stub
		System.out.println("Server Listening...");
		
		while (true) {
			try {
				Socket socket = serverSocket.accept();
				System.out.println("{Address: "
						+ socket.getInetAddress().toString() + ", Port: "
						+ socket.getPort() + " }");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
