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
	
	public Client(String tID) {
		connDate = new Date();
		
		terminalID = tID;
	}
	
	public Date getConnectionTime() {
		return connDate;
	}
	
	public String getTerminalID() {
		return terminalID;
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