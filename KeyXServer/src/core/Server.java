/******************************************************
 Compagnie :	Transax Technologies
 Projet :		KeyExchangeServer
 Programmeur :	Jean-François Brais-Villemur, Analyste Réseau
 Superviseur :	Alain Boucher, CTO
 Classe :		Server.java			 
 Création  :	2010-03-08
 Dern. mod : 	2009-03-17
 *******************************************************
 Historique des modifications
 *******************************************************
 2010-03-08 : 	Début du projet
 
 2010-03-08 : 	Ajout d'un tableau de sockets.
 				Ajout des I/O Streams.
 				Tests.
 				
 2010-03-17 :	Révisé la gestion des connexions
 				Ajout d'une interface graphique
 *******************************************************/

package core;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;

public class Server implements Runnable {
	private final static String DEFAULT_IP = "127.0.0.1";
	private final static int DEFAULT_PORT = 10000;
	private final static int DEFAULT_CONNECTIONS = 5;

	private boolean listening = false;
	
	private String IP = null;
	private int port;
	
	private Client[] tabConnections;
	
//	private Socket[] tabSocket;
//    private BufferedReader[] tabInput ;
//    private PrintWriter[] tabOutput;
	
	private ServerSocket serverSocket;
	
	private MessageParser msg;
	
	private Thread thread;
	
//	private ObjectOutputStream oos;
//	private ObjectInputStream ois;
	
	private String inputLine;
	private String outputLine;

	// Default constructor, uses default values for IP:Port.
	/**
	 * 
	 */
	public Server() {
		this(null, DEFAULT_PORT, -1);
	}

	// Constructor that permits the specification of the port number.
	/**
	 * @param aPort
	 * @param maxConnections
	 */
	public Server(String aIP, int aPort, int maxConnections) {
		super();
		
		if (!aIP.equals(""))
			IP = aIP;
		else
			IP = DEFAULT_IP;
		
		port = aPort;

		if (maxConnections <= 0)
			maxConnections = DEFAULT_CONNECTIONS;
		
		// Initializes the Socket array with the max connections value.
		tabConnections = new Client[maxConnections];
		
		System.out.println("Server initialized..");
		
		thread = new Thread(this);
		
		System.out.println("Thread : " + thread.getName() + " | " + thread.getId());
	}

	// Returns the IP address of the server.
	/**
	 * @return
	 */
	public String getIP() {
		return IP;
	}

	// Returns the port number on which the server is running.
	/**
	 * @return
	 */
	public int getPort() {
		return port;
	}
	
	/**
	 * @param state
	 */
	public void setListeningState(boolean state){
		listening = state;
	}
	
	/**
	 * @return
	 */
	public boolean getListeningState(){
		return listening;
	}
	
	// Creates a SocketServer at the specified IP:Port.
	/**
	 * @param serverIP
	 * @param serverPort
	 */
	public void startServer(String serverIP, int serverPort) {
		// Starts the thread that will accept connections.
		start();
		
		msg = new MessageParser();
		
		try {
			serverSocket = new ServerSocket(serverPort);
			System.out.println("Server started..");
			System.out.println("IP   : " + IP);
			System.out.println("Port : " + port);
		} catch (IOException e) {
			System.out.println("Couldn't listen on port : " + serverPort);
			System.exit(-1);
		}	
	}
	
	// Accepts the number of connections specified in parameters.
	// If the int passed is <= to 0, default value will be used.
	/**
	 * 
	 */
	public void acceptConnections() {
		try {
			int i = 0;
			System.out.println("Accepting clients..");
			
			// Accept clients until limit is reached.
			while (listening && tabConnections[tabConnections.length - 1] == null)
			{
				// Creates a socket to accept connections and stores it in a Socket tab.
//				tabConnections[i].setSocket(serverSocket.accept());
				
				// Creates reader to receive data.
				tabConnections[i].setReader(new BufferedReader(new InputStreamReader(tabConnections[i].getSocket().getInputStream())));
				
				// Creates writer to send data.
				tabConnections[i].setWriter(new PrintWriter(tabConnections[i].getSocket().getOutputStream(), true));
				
				// Displays information relative to the newly connected client.
				System.out.println("Client has been accepted.");
				System.out.println("Local Port  : " + tabConnections[i].getSocket().getLocalPort());
				System.out.println("Port number : " + tabConnections[i].getSocket().getPort());
				System.out.println("IP address  : " + tabConnections[i].getSocket().getRemoteSocketAddress());
				
				msg.sendMessage("Welcome.", tabConnections[i].getWriter());
				
				inputLine = tabConnections[i].getReader().readLine();

				msg.parseMessage(inputLine);
				
				if (inputLine != null)
					System.out.println("Client["+i+"]:" + tabConnections[i].getSocket().getPort() + " sent -> \"" + inputLine + "\"");
				else
					System.out.println("Failed reading.");
				
				i++;
			}
			
			// When connections limit is reached, prints out the ports used by the remote client.
			System.out.println("\nPorts used");
			System.out.println("----------");
			for (int j = 0; j < tabConnections.length; j++)
			{
				System.out.print(tabConnections[j].getSocket().getPort());
				if (j < tabConnections.length - 1)
					System.out.print(" - ");
			}
		} catch (IOException e) {
			System.out.println("Accept failed : " + getPort());
			System.exit(-1);
		}		
	}

	/**
	 * 
	 */
	private void start() {
		thread.start();
	}
	
	@Override
	public void run() {
		
	}
}
