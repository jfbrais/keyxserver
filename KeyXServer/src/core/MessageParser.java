/******************************************************
 Compagnie :	Transax Technologies
 Projet :		KeyExchangeServer
 Programmeur :	Jean-Fran�ois Brais-Villemur, Analyste R�seau
 Superviseur :	Alain Boucher, CTO
 Classe :		MessageParser.java			 
 Cr�ation  :	2010-03-08
 Dern. mod : 	2009-03-17
 *******************************************************
 Historique des modifications
 *******************************************************
 2010-03-08 : 	D�but du projet
 
 2010-03-08 : 	Ajout d'un tableau de sockets.
 				Ajout des I/O Streams.
 				Tests.
 				
 2010-03-17 :	R�vis� la gestion des connexions
 				Ajout d'une interface graphique
 *******************************************************/

package core;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class MessageParser {
	private String lastMessage = null;

	public MessageParser() {
		System.out.println("MessageParser created..");
	}

	public void sendMessage(String message, PrintWriter out) {
		out.println(message);
	}

	public void parseMessage(String messageReceived) {
		if (messageReceived.equals("KEYX"))
			System.out.println("Client requested a Key Exchange");
		
		if (messageReceived.equals("QUIT"))
			System.out.println("Client disconnected.");
	}
}