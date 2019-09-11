import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

import javax.imageio.ImageIO;

public class Client {
	public static void main(String[] args) throws IOException {

		DatagramSocket clientSocket = new DatagramSocket();
		InetAddress IPAddress = InetAddress.getByName("localhost");

		DatagramPacket sendPacket = null;

		int i = 0;
		File imageFile = new File("/Users/rodeshel/Desktop/out.jpg");
		BufferedImage image = ImageIO.read(imageFile);
		ByteArrayOutputStream imageBytes = new ByteArrayOutputStream();
		ImageIO.write(image, "jpg", imageBytes);
		imageBytes.flush();

		byte[] imageByteChunks = imageBytes.toByteArray();
		sendPacket = new DatagramPacket(imageByteChunks, imageByteChunks.length, IPAddress, 20750);
		clientSocket.send(sendPacket);

		while (true) {
			BufferedReader clientInput = new BufferedReader(new InputStreamReader(System.in));
			String clientW = clientInput.readLine();
			byte[] sendData = new byte[1024];
			sendData = clientW.getBytes();
			DatagramPacket sendResponse = new DatagramPacket(sendData, sendData.length, IPAddress, 20750);
			clientSocket.send(sendResponse);

			byte[] receiveData = new byte[1024];
			DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
			clientSocket.receive(receivePacket);
			String serverR = new String(receivePacket.getData());
			System.out.println("Reply from the server: " + serverR);

			if (serverR.equals("exit")) {
				clientSocket.close();
			}

		}
	}
}