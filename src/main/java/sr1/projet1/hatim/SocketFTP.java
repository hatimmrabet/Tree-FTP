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
	
	/**
	 * Creation d'un object SocketFTP
	 * @param adresse du serveur ftp
	 * @param port du serveur
	 * @throws UnknownHostException
	 * @throws IOException
	 */
	public SocketFTP(String adresse,int port) throws UnknownHostException, IOException {
		this.socket = new Socket(adresse,port);
		this.printer = new PrintWriter(this.socket.getOutputStream(), true);
		this.reader = new BufferedReader(new InputStreamReader(this.socket.getInputStream()));
	}
	
	/**
	 * Creation d'un object SocketFTP à partir des donnees recu par la commande PASV
	 * @param adresse IP
	 * @param port du serveur
	 * @throws UnknownHostException
	 * @throws IOException
	 */
	public SocketFTP(InetAddress adresse,int port) throws UnknownHostException, IOException {
		this.socket = new Socket(adresse,port);
		this.printer = new PrintWriter(this.socket.getOutputStream(), true);
		this.reader = new BufferedReader(new InputStreamReader(this.socket.getInputStream()));
	}
	
	/**
	 * Se connecter au serveur FTP
	 * @param username nom d'utilisateur
	 * @param passwd mot de passe
	 * @return true si la connecion est bien établie
	 * @throws IOException
	 */
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
	
	/**
	 * fermer la socket, le BufferedReader et le printWriter
	 * @throws IOException
	 */
	public void deconnecter() throws IOException
	{
		this.printer.close();
		this.reader.close();
		this.socket.close();
	}

}
