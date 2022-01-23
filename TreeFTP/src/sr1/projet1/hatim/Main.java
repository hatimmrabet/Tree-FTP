package sr1.projet1.hatim;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;

public class Main {

	public static void afficherContent(String path, int profondeur, int maxProfondeur, PrintWriter printer,
			BufferedReader reader) throws IOException {
		String content;
		ArrayList<String> files = new ArrayList<>();
		// System.out.println(path);

		// using passive mode
		printer.println("PASV");
		content = reader.readLine();
		//System.out.println(content);

		if (content != null) {
			// System.out.println(content);
			String[] pasv = content.split(" ")[4].replace("(", "").replace(")", "").split(",");
			String IP = pasv[0] + "." + pasv[1] + "." + pasv[2] + "." + pasv[3];
			int port = (Integer.parseInt(pasv[4]) * 256) + Integer.parseInt(pasv[5]);
			// System.out.println(IP+" "+port);

			Socket s2 = new Socket(InetAddress.getByName(IP), port);
			BufferedReader readerdata = new BufferedReader(new InputStreamReader(s2.getInputStream()));
			
			s2.setKeepAlive(true);
			
			printer.println("NLST " + path);
			//printer.println("LIST " + path);
			content = reader.readLine();
			// System.out.println(content);
			content = reader.readLine();
			// System.out.println(content);
			while ((content = readerdata.readLine()) != null) {
				//System.out.println(content);
				String[] a = content.split("/");
				String filename = a[a.length - 1];
				//String filename = content.split("\\s+")[8];
				files.add(filename);
			}

			//preparer l'affichage
			for (int i = 0; i < files.size(); i++) {
				String display = "";
				for (int j = 0; j < profondeur; j++) {
					display += "\u2551 ";
				}

				if (i == files.size() - 1) {
					display += "\u255A ";
				} else {
					display += "\u2560 ";
				}

				display += files.get(i);
				System.out.println(display);
				if (profondeur < maxProfondeur) {
					afficherContent(path + files.get(i) + "/", profondeur + 1, maxProfondeur, printer, reader);
				}
			}
			readerdata.close();
			s2.close();
		}
	}

	public static void main(String[] args) throws UnknownHostException, IOException {
		/*
		 * if (args.length < 1) {
		 * 
		 * } else { String adresse = args[0];
		 */
			Socket s = new Socket("ftp.free.fr", 21);
			// Socket s = new Socket(adresse, 21);

			OutputStream os = s.getOutputStream();
			PrintWriter printer = new PrintWriter(os, true);

			BufferedReader reader = new BufferedReader(new InputStreamReader(s.getInputStream()));
			String content;

			// socket response
			content = reader.readLine();
			System.out.println(content);

			// user connection
			printer.println("user anonymous");
			content = reader.readLine();
			System.out.println(content);

			printer.println("pass");
			content = reader.readLine();
			System.out.println(content);

			printer.println("pwd");
			content = reader.readLine();
			System.out.println(content);

			int profondeur = 3;
			String path = "/.mirrors18/mplayerhq.hu/MPlayer/";
			System.out.println(path);
			afficherContent(path, 0, profondeur, printer, reader);
			s.close();
//		}

	}

}
