package sr1.projet1.hatim;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
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
			
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}

}
