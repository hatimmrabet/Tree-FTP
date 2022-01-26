package sr1.projet1.hatim;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

public class SocketFTP {

	Socket socket;
	PrintWriter printer;
	BufferedReader reader;
	
	public SocketFTP(String adresse,int port) throws UnknownHostException, IOException {
		this.socket = new Socket(adresse,port);
		this.printer = new PrintWriter(this.socket.getOutputStream(), true);
		this.reader = new BufferedReader(new InputStreamReader(this.socket.getInputStream()));
	}
	
	public SocketFTP(InetAddress adresse,int port) throws UnknownHostException, IOException {
		this.socket = new Socket(adresse,port);
		this.printer = new PrintWriter(this.socket.getOutputStream(), true);
		this.reader = new BufferedReader(new InputStreamReader(this.socket.getInputStream()));
	}
	
	public boolean login(String username, String passwd) throws IOException
	{
		String content;
		// user connection
		this.printer.println("user "+username);
		content = this.reader.readLine();
		//System.out.println(content);
		if(!content.split(" ")[0].equals("331"))
			return false;

		this.printer.println("pass "+passwd);
		content = this.reader.readLine();
		//System.out.println(content);
		if(!content.split(" ")[0].equals("230"))
			return false;
		
		return true;
	}
	
	public void deconnecter() throws IOException
	{
		this.printer.close();
		this.reader.close();
		this.socket.close();
	}

}
