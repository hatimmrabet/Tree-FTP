package sr1.projet1.hatim;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;


public class Main {	
	
	public static void afficherContent(String path, int profendeur, PrintWriter printer,BufferedReader reader) throws IOException
	{
		String content;
		ArrayList<String> files = new ArrayList<>();
		System.out.println(path);
		
		//using passive mode
		printer.println("PASV");
		content = reader.readLine();
		//System.out.println(content);
		String[] pasv = content.split(" ")[4].replace("(","").replace(")","").split(",");
		String IP = pasv[0]+"."+pasv[1]+"."+pasv[2]+"."+pasv[3];
		int port = (Integer.parseInt(pasv[4]) * 256) + Integer.parseInt(pasv[5]);	
		//System.out.println(IP+" "+port);
		
		Socket s2 = new Socket(InetAddress.getByName(IP),port);
		BufferedReader readerdata = new BufferedReader(new InputStreamReader(s2.getInputStream()));
			
		printer.println("list "+path);
		content = reader.readLine();
		//System.out.println(content);
		content = reader.readLine();
		//System.out.println(content);
		while((content = readerdata.readLine())!= null)
		{
			//System.out.println(content);
			files.add(content.split("\\s+")[8]);
		}
		
		for(String file : files)
		{
			System.out.println(file);
			if (profendeur>1)
			{
				afficherContent(path+file+"/",profendeur-1,printer,reader);				
			}
		}
		
		readerdata.close();
		s2.close();
	}
	
	
	public static void main(String[] args) {
		try {
			Socket s = new Socket("ftp.ubuntu.com", 21);
			
			OutputStream os = s.getOutputStream();
			PrintWriter printer = new PrintWriter(os,true);
			
			BufferedReader reader = new BufferedReader(new InputStreamReader(s.getInputStream()));
			String content;
			
			//socket response
			content = reader.readLine();
			System.out.println(content);
			
			//user connection
			printer.println("user anonymous");
			content = reader.readLine();
			System.out.println(content);
			
			printer.println("pass");
			content = reader.readLine();
			System.out.println(content);
			

			printer.println("pwd");
			content = reader.readLine();
			System.out.println(content);

			afficherContent("./",3,printer, reader);
			
			/*
			//using passive mode
			printer.println("PASV");
			content = reader.readLine();
			System.out.println(content);
			pasv = content.split(" ")[4].replace("(","").replace(")","").split(",");
			IP = pasv[0]+"."+pasv[1]+"."+pasv[2]+"."+pasv[3];
			port = (Integer.parseInt(pasv[4]) * 256) + Integer.parseInt(pasv[5]);
			System.out.println(IP+" "+port);
			
			s2 = new Socket(InetAddress.getByName(IP),port);
			readerdata = new BufferedReader(new InputStreamReader(s2.getInputStream()));

			
			printer.println("list ./ubuntu");
			content = reader.readLine();
			System.out.println(content);
			content = reader.readLine();
			System.out.println(content);
			while((content = readerdata.readLine()) != null)
			{
				System.out.println(content);
			}
			System.out.println("END DATA");
			s2.close();
			*/
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
