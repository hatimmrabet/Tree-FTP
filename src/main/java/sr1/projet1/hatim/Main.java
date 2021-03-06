package sr1.projet1.hatim;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;

public class Main {

	/**
	 * l'affichage du contenue de chaque repertoire
	 * @param path adresse du repertoire à afficherS
	 * @param profondeur  profendeur actuel
	 * @param maxProfondeur profondeur maximal
	 * @param s socketFTP utilisé pour envoie des commandes
	 * @throws IOException
	 */
	public static void afficherContent(String path, int profondeur, int maxProfondeur, SocketFTP s) throws IOException {
		String content;
		ArrayList<String> files = new ArrayList<>();

		// using passive mode
		s.printer.println("PASV");
		content = s.reader.readLine();
		// System.out.println(content);

		if (content != null) {
			// extraire l'IP et leport
			String[] pasv = content.split(" ")[4].replace("(", "").replace(")", "").split(",");
			String IP = pasv[0] + "." + pasv[1] + "." + pasv[2] + "." + pasv[3];
			int port = (Integer.parseInt(pasv[4]) * 256) + Integer.parseInt(pasv[5]);
			// System.out.println(IP + " " + port);

			SocketFTP s2 = new SocketFTP(InetAddress.getByName(IP), port);

			s.printer.println("LIST " + path);
			content = s.reader.readLine();
			// System.out.println(content);
			content = s.reader.readLine();
			// System.out.println(content);

			if (content != null) {
				while ((content = s2.reader.readLine()) != null) {
					// System.out.println(content);
					String filename = content.split("\\s+")[8];
					files.add(filename);
				}

				// preparer l'affichage
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
					System.out.println(display); //l'affichage final
					//condition d'acceder au dossier suivant
					if (profondeur < maxProfondeur || maxProfondeur == -1) {
						afficherContent(path + files.get(i) + "/", profondeur + 1, maxProfondeur, s);
					}
				}
				s2.deconnecter();
			}
		}
	}

	public static void main(String[] args) {
		if (args.length < 1) {
			throw new RuntimeException("Vous devez mentionner une adresse FTP");
		} else {
			int profondeur = -1;
			String adresse, username = "anonymous", passwd = "";
			if (args[0].equals("-p")) {
				if (args.length >= 3) {
					profondeur = Integer.parseInt(args[1]);
					adresse = args[2];
					if (args.length == 5) {
						username = args[3];
						passwd = args[4];
					}
				} else {
					throw new RuntimeException("Des arguments manquants");
				}
			} else {
				adresse = args[0];
				if (args.length == 3) {
					username = args[1];
					passwd = args[2];
				}
			}

			try {
				SocketFTP s = new SocketFTP(adresse, 21);

				String content;

				// socket response
				content = s.reader.readLine();
				//System.out.println(content);

				// user connection
				if(s.login(username, passwd))
				{
					String path = "./";
					System.out.println(path);
					afficherContent(path, 0, profondeur, s);					
				}
				else
				{
					throw new RuntimeException("user ou passwd est incorrect");
					/** Erreur si le nom d'utilisateur ou le mot de passe sont incorrectes */
				}
				s.deconnecter();;
			} catch (UnknownHostException e) {
				throw new RuntimeException("l'adresse du serveur FTP est introuvable");
				/** Erreur si l'adresse du serveur FTP est introuvable */
			} catch (IOException e) {
				throw new RuntimeException("Erreur exception");
				/** Erreur Exception */
			}
		}

	}

}
