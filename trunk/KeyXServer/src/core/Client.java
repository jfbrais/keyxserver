/******************************************************
 Compagnie :	Transax Technologies
 Projet :		KeyExchangeServer
 Auteurs : 		Jean-Fran�ois Brais-Villemur, Analyste R�seau
 				Marc-Andr� Laporte, Programmeur Analyste
 Superviseur :	Alain Boucher, CTO
 Classe :		Client.java			 
 Cr�ation  :	2010-03-22
 Dern. mod : 	2010-03-23
 *******************************************************
 Historique des modifications
 *******************************************************
 2010-03-08 : 	D�but du projet
 				
 2010-03-22 :	Cr�ation de la classe Client pour garder
 				toutes les informations dans un seul objet.
 				
 2010-03-23 : 	Ajout de mutateurs / accesseurs.
 *******************************************************/

package core;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Date;

public class Client {
	private Date connDate;
	private String terminalID;
	
	private Socket socket;
	private BufferedReader input ;
	private PrintWriter output;
	
	public Client() {
		connDate = new Date();
	}
	
	public Date getConnectionTime() {
		return connDate;
	}
	
	public String getTerminalID() {
		return terminalID;
	}
	
	public void setTerminalID(String tID) {
		terminalID = tID;
	}
	
	public Socket getSocket() {
		return socket;
	}
	
	public void setSocket(Socket serverSocket) {
		socket = serverSocket;
	}
	
	public BufferedReader getReader() {
		return input;
	}
	
	public void setReader(BufferedReader bufferedReader) {
		input = bufferedReader;
	}
	
	public PrintWriter getWriter() {
		return output;
	}
	
	public void setWriter(PrintWriter writer) {
		output = writer;
	}
}