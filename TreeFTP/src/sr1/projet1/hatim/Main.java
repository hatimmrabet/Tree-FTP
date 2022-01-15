package sr1.projet1.hatim;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;


public class Main {

	private static void afficherContenu(String path, int profendeur, PrintWriter printer, BufferedReader reader ) throws IOException
	{
		System.out.println(path);
		if(profendeur > 0)
		{
			printer.println("list "+path);
			String content = reader.readLine();
			while(content != null)
			{
				System.out.println(content.replaceAll("\\s+"," "));
				afficherContenu(path+"/"+content.replaceAll("\\s+"," ").split(" ")[8],profendeur,printer,reader);
				content = reader.readLine();
			}
		}
	}
	
	public static void main(String[] args) {
		
		try {
			Socket s = new Socket("ftp.ubuntu.com", 21);
			
			OutputStream os = s.getOutputStream();
			PrintWriter printer = new PrintWriter(os,true);
			
			InputStream in = s.getInputStream();
			BufferedReader reader = new BufferedReader(new InputStreamReader(in));
			String content = reader.readLine();
			
			System.out.println(content);
			
			printer.println("user anonymous");
			printer.println("pass");
			content = reader.readLine();
			System.out.println(content);
			
			content = reader.readLine();
			System.out.println(content);

			printer.println("pwd");
			content = reader.readLine();
			System.out.println(content);
			
			printer.println("PASV");
			content = reader.readLine();
			System.out.println(content);
			
			String[] pasv = content.split(" ")[4].replace("(","").replace(")","").split(",");
			
			String IP = pasv[0]+"."+pasv[1]+"."+pasv[2]+"."+pasv[3];
			int port = (Integer.parseInt(pasv[4]) * 256) + Integer.parseInt(pasv[5]);			
			Socket s2 = new Socket(InetAddress.getByName(IP),port);
			
			//s.setKeepAlive(true);
			//System.out.println("ici " + s.getKeepAlive());
						
			in = s2.getInputStream();
			reader = new BufferedReader(new InputStreamReader(in));
					
			afficherContenu(".",3,printer,reader);
			
			
			
		s2.close();
		s.close();
			
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}

}
