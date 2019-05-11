package seguranca_sistemas_t2;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Data: 2019/1
 * 
 * @author Israel Deorce Vieira Junior
 * @professor Avelino Zorzo
 */
public class App {

	private FileInputStream fileInputStream; // Stream para leitura de arquivos
	private final int BUFFERSIZE = 1024; // Tamanho maximo de um bloco
	private String sourceFilePath = "video05.mp4"; // Arquivo a ser trabalhado no algoritmo
	private List<Bloco> blocos = new ArrayList<>(); // Lista de blocos ordenados
	private Bloco bloco = null; // Um bloco contém dados, hash e a concatenção de dados e hash

	public static void main(String[] args) {
		App app = new App();

		// Verifica se existe argumentos
		if (args.length != 0) {
			if (args[0].endsWith(".mp4"))
				System.out.println("O Arquivo deve ser no formato mp4");
			app.sourceFilePath = args[0];
		}

		// Valida se arquivo informado existe de fato e inicializa aplicação
		if (!new File(app.sourceFilePath).isFile()) {
			System.out.println("Arquivo inexistente!");
			System.exit(0);
		} else {
			app.readFile();
			app.hashVideo();
		}
	}

	/**
	 * Inicializa a aplicação, faz leitura do arquivo e armazena em blocos de bytes
	 * separados
	 */
	private void readFile() {
		try {
			fileInputStream = new FileInputStream(new File(sourceFilePath));
			byte[] buffer = new byte[BUFFERSIZE];
			byte[] lastBuffer;
			while (fileInputStream.available() != 0) {
				if (fileInputStream.available() < 1024) {
					lastBuffer = new byte[fileInputStream.available()];
					fileInputStream.read(lastBuffer);
					bloco = new Bloco(lastBuffer);
				} else {
					fileInputStream.read(buffer);
					bloco = new Bloco(buffer);
				}
				blocos.add(bloco);
			}
		} catch (Exception e) {
			System.out.println("Something went wrong! Reason: " + e.getMessage());
		}
	}

	/**
	 * Le a lista de blocos de traz para frente, calculando a hash de cada bloco,
	 * concatenando-as e devolvendo h0.
	 */
	private void hashVideo() {
		byte[] hash = null;
		for (int i = blocos.size() - 1; i >= 0; i--) {
			// Se for o ultimo bloco
			if (hash == null) {
				hash = blocos.get(i).calculateHash();
			} else { // Se for todos os outros
				blocos.get(i).addHash(hash);
				hash = blocos.get(i).calculateHash();
			}
		}
		System.out.println("h0=");
		System.out.println(bytesToHex(hash));
	}

	/**
	 * Converte array de bytes em valor Hexadecimal
	 * @param hash
	 * @return
	 */
	private String bytesToHex(byte[] hash) {
		StringBuffer hexString = new StringBuffer();
		for (int i = 0; i < hash.length; i++) {
			String hex = Integer.toHexString(0xff & hash[i]);
			if (hex.length() == 1)
				hexString.append('0');
			hexString.append(hex);
		}
		return hexString.toString();
	}

}
