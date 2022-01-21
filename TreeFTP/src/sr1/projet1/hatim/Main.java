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
			
			String[] pasv = content.split(" ")[4].replace("(","").replace(")","").split(",");
			
			String IP = pasv[0]+"."+pasv[1]+"."+pasv[2]+"."+pasv[3];
			int port = (Integer.parseInt(pasv[4]) * 256) + Integer.parseInt(pasv[5]);	
			
			Socket s2 = new Socket(InetAddress.getByName(IP),port);
			
			BufferedReader readerdata = new BufferedReader(new InputStreamReader(s2.getInputStream()));
					
			printer.println("CWD ./ubuntu");
			content = reader.readLine();
			System.out.println(content);
			printer.println("PWD");
			content = reader.readLine();
			System.out.println(content);
			
				
			printer.println("list .");
			
			content = reader.readLine();
			System.out.println(content);
			content = reader.readLine();
			System.out.println(content);
			
			while((content = readerdata.readLine())!= null)
			{
				System.out.println(content);
			}
			
			//System.out.println(s2.isConnected());
			
			printer.println("list ./ubuntu");
			content = reader.readLine();
			System.out.println(content);
			content = reader.readLine();
			System.out.println(content);
			while((content = readerdata.readLine()) != null)
			{
				System.out.println(content.split("\\s+")[8]);
			}
			
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
