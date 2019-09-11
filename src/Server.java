import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

import javax.imageio.ImageIO;

public class Server {
	public static void main(String[] args) throws IOException {
//		ServerSocket server = new ServerSocket(4999);
		DatagramSocket server = new DatagramSocket(20750);
//		server.setReuseAddress(true);

		byte[] receiveData = new byte[62085];

		DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
		System.out.println("Connection has been initialized with client: ");
		byte[] imageChunks = null;
		ByteArrayInputStream imageBytes = null;
		BufferedImage imageView = null;

		server.receive(receivePacket);
		imageChunks = receivePacket.getData();
		imageBytes = new ByteArrayInputStream(imageChunks);
		imageView = ImageIO.read(imageBytes);
		OutputStream os = new FileOutputStream("output.jpg");
		os.write(imageChunks);
		ImageIO.write(imageView, "jpg", new File("/Users/rodeshel/Desktop/output.jpg"));
		System.out.println("Received Image");

		RunClient rc = new RunClient(server, receivePacket);
		new Thread(rc).start();
	}
}

class RunClient implements Runnable {
	DatagramSocket server;
	DatagramPacket client;

	public RunClient(DatagramSocket sSocket, DatagramPacket cPacket) {
		this.server = sSocket;
		this.client = cPacket;
	}

	public void run() {
		BufferedReader clientInput = null;
		BufferedReader serverInput = null;
		PrintWriter output = null;
		InetAddress IPAddress = null;
		int port = 0;
		byte[] sendData = null;
		DatagramPacket sendPacket = null;

		try {
			String clientR;
			String serverW;

			while (true) {
				server.receive(client);
				clientR = new String(client.getData());
				System.out.println("Client: " + clientR);
				IPAddress = InetAddress.getByName("localhost");
				port = client.getPort();
				sendData = new byte[1024];
				serverInput = new BufferedReader(new InputStreamReader(System.in));
				serverW = serverInput.readLine();
				sendData = serverW.getBytes();

				sendPacket = new DatagramPacket(sendData, sendData.length, IPAddress, port);
				server.send(sendPacket);

				if (clientR.equals("exit")) {
					server.close();
				}

			}

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}