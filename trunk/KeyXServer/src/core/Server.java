/******************************************************
 Compagnie :	Transax Technologies
 Projet :		KeyExchangeServer
 Auteurs :		Jean-François Brais-Villemur, Analyste Réseau
 				Marc-André Laporte, Programmeur Analyste
 Superviseur :	Alain Boucher, CTO
 Classe :		Server.java			 
 Création  :	2010-03-08
 Dern. mod : 	2010-03-23
 *******************************************************
 Historique des modifications
 *******************************************************
 2010-03-08 : 	Début du projet
 
 2010-03-08 : 	Ajout d'un tableau de sockets.
 				Ajout des I/O Streams.
 				Tests.
 				
 2010-03-17 :	Révisé la gestion des connexions
 				Ajout d'une interface graphique
 				
 2010-03-23 :	Le serveur ne garde maintenant qu'une
				collection de Clients, contenant les sockets, etc.
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
	
	private Client[] tabClients;
	private Client client = null;
	
	private ServerSocket serverSocket;
	
	private MessageParser msg;
	
	private Thread thread;
	
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
		tabClients = new Client[maxConnections];
		
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
			while (listening && tabClients[tabClients.length - 1] == null)
			{
				// Instantiates a Client to further add to the connected clients.
				client = new Client();
				
				// Creates a socket to accept connections and stores it in a Socket tab.
				client.setSocket(serverSocket.accept());
				
				// Creates reader to receive data.
				client.setReader(new BufferedReader(new InputStreamReader(client.getSocket().getInputStream())));
				
				// Creates writer to send data.
				client.setWriter(new PrintWriter(client.getSocket().getOutputStream(), true));
				
				// Once everything is initialized, adds the connected client to the tab.
				tabClients[i] = client;
				
				// Displays information relative to the newly connected client.
				System.out.println("Client has been accepted.");
				System.out.println("Local Port  : " + tabClients[i].getSocket().getLocalPort());
				System.out.println("Port number : " + tabClients[i].getSocket().getPort());
				System.out.println("IP address  : " + tabClients[i].getSocket().getRemoteSocketAddress());
				
				msg.sendMessage("Welcome.", tabClients[i].getWriter());
				
				inputLine = tabClients[i].getReader().readLine();

				tabClients[i].setTerminalID(inputLine);
				
//				msg.parseMessage(inputLine);
				
				if (inputLine != null) {
					System.out.println("Client["+i+"]:" + tabClients[i].getSocket().getPort() + " sent -> \"" + inputLine + "\"");
					System.out.println(tabClients[i].getTerminalID() + " has been authentified @ " + tabClients[i].getConnectionTime());
				}
				else
					System.out.println("Failed reading.");
				
				i++;
			}
			
			// When connections limit is reached, prints out the ports used by the remote client.
			System.out.println("\nPorts used");
			System.out.println("----------");
			for (int j = 0; j < tabClients.length; j++)
			{
				System.out.print(tabClients[j].getSocket().getPort());
				if (j < tabClients.length - 1)
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
