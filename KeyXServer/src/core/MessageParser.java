/******************************************************
 Compagnie :	Transax Technologies
 Projet :		KeyExchangeServer
 Auteurs : 		Jean-Fran�ois Brais-Villemur, Analyste R�seau
 				Marc-Andr� Laporte, Programmeur Analyste
 Superviseur :	Alain Boucher, CTO
 Classe :		MessageParser.java			 
 Cr�ation  :	2010-03-08
 Dern. mod : 	2010-03-08
 *******************************************************
 Historique des modifications
 *******************************************************
 2010-03-08 : 	D�but du projet
 
 2010-03-08 : 	Classe pour envoyer/recevoir des donn�es.
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