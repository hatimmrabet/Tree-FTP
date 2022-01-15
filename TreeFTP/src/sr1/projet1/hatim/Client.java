package sr1.projet1.hatim;
import java.io.*;
import java.net.*;

class Client {
	public static void main(String args[]) throws Exception {
//		PrintWriter out;
//		BufferedReader in;
        String line, newLine;                                           
		Socket cs = new Socket("webtp.fil.univ-lille1.fr", 6789);
		System.out.println("Client Started...");
//		out = new PrintWriter(cs.getOutputStream(), true);
//		in = new BufferedReader(new InputStreamReader(cs.getInputStream()));
//		out.println(in.readLine());
        DataInputStream in=new DataInputStream(System.in);      
		// DataInputStream to read data from input stream       
        DataInputStream inp=new DataInputStream (cs.getInputStream());
        // DataOutputStream to write data on outut stream   
        DataOutputStream out=new DataOutputStream(cs.getOutputStream());
        
		while (true) {
			line = in.readLine();
			System.out.println("Received from client: " + line);
			newLine = in.readLine();
			out.writeBytes(newLine + '\n');
		}
	}
}