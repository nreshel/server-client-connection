import java.io.*;
import java.net.*;
import java.nio.ByteBuffer;
import java.util.Hashtable;

public class DirectoryServer {
 public static void main(String[] args) {
  // Get serverID,Port Number, NextPort, and IP, create new server instance
  int serId = Integer.parseInt(args[1]);
  int port = Integer.parseInt(args[0]);
  int nextPort = Integer.parseInt(args[2]);
  String someIP = args[3];
  Server locServer = new Server(port, serId, nextPort, someIP);
 }

 public static class Server {
  final int statusCodeOK = 200; // Server connection established
  final int statusCode400 = 400; // Error 400
  final int statusCode404 = 404; // Error 404
  final int statusCode505 = 505; // Error 505
  private int port;
  private int serverID;
  private int nextPort;
  private String nextIP;

  ServerSocket tcpSocket;

  public static Hashtable<String, String> holdInfo = new Hashtable<String, String>();

  public Server(int port, int serverID, int nextPort, String nextIP) {
   this.port = port;
   this.serverID = serverID;
   this.nextPort = nextPort;
   this.nextIP = nextIP;

   if (serverID > 4)

   {
    System.out.println("Error!Too many servers!");
   } else {
    if (nextIP == null) {
     try {
      String yourIP = InetAddress.getLocalHost().getHostName();
      nextIP = yourIP;
      holdInfo.put("Server1", yourIP);
     } catch (Exception e) {
      System.out.println(e);
     }

    }
   }

  }

  // Class that is connecting to the client using UDP

  public class UDPConnectionClient {
   final int statusCodeOK = 200; // Server connection established
   final int statusCode400 = 400; // Error 400
   final int statusCode404 = 404; // Error 404
   final int statusCode505 = 505; // Error 505

   private int port = 20751;
   private int serverID;
   private int nextPort;
   private String nextIP;

   // Create udp socket and assign a port to it.
  DatagramSocket udpSocket= new DatagramSocket(port);

   // Check if udp is connected if it is contact client
   if(udpSocket.isConnected()==true)
   {
    // Since it is true send packet to the client
    try {
     byte[] sendData = new byte[1024];
     DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length);
     udpSocket.send(sendPacket);

    } catch (Exception e) {
     System.out.println(e);
    }

   }

  }

 }
}}
