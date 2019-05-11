package seguranca_sistemas_t2;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.List;

//https://riptutorial.com/java/example/12545/reading-a-file-using-channel-and-buffer
//https://stackoverflow.com/questions/4841340/what-is-the-use-of-bytebuffer-in-java
public class FileCopy {

	final int BUFFERSIZE = 1024;
	String sourceFilePath = "video05.mp4";
	String outputFilePath = "output.mp4";

	public FileCopy() {
	}

	public void start() {

		try {
			FileInputStream fileInputStream = new FileInputStream(new File(sourceFilePath));
			FileOutputStream fileOutputStream = new FileOutputStream(new File(outputFilePath));
			FileChannel fileChannel = fileInputStream.getChannel();
			ByteBuffer byteBuffer = ByteBuffer.allocate(BUFFERSIZE);

			int cont = 0;

			byte[] hash = null;

			while (fileChannel.read(byteBuffer) > 0) {
				byteBuffer.flip();
				byte[] buffer = new byte[BUFFERSIZE];
				for (int i = 0; byteBuffer.hasRemaining(); i++) {
					buffer[i] = byteBuffer.get();
					// byte b = bBuffer.get();
					// System.out.print((char) b);
				}
				Bloco bloco = new Bloco(buffer);
				cont++;

				// se for a primeira hash
				if (hash == null) {
					hash = bloco.calculateHash();
				} else { // se for todas as outras
					bloco.addHash(hash);
					hash = bloco.calculateHash();
				}
				fileOutputStream.write(buffer);
//			    System.out.println(hash.length);
//			    System.out.println(buffer.length);
//			    System.out.println(bloco.getFullBlock().length);
//			    fileOutputStream.write(buffer);			    
				byteBuffer.clear();
			}
			System.out.println("h0=" + bytesToHex(hash));

		} catch (Exception e) {
			System.out.println("Something went wrong! Reason: " + e.getMessage());
		}

	}

	public String bytesToHex(byte[] data) {
		StringBuffer hexString = new StringBuffer();
		for (int i = 0; i < data.length; i++) {
			String hex = Integer.toHexString(0xff & data[i]);
			if (hex.length() == 1)
				hexString.append('0');
			hexString.append(hex);
		}
		// System.out.println("HexString: " + hexString.toString());
		return hexString.toString();
	}

	// h0=896570b88b89fa9433761915931d3e9e4e4ca34c54cc2f828bd9b4873438e5ae
	
	// Correto: 8e423302209494d266a7ab7e1a58ca8502c9bfdaa31dfba70aa8805d20c087bd
	public void start2() {
		try {
			FileInputStream fileInputStream = new FileInputStream(new File(sourceFilePath));
			FileOutputStream fileOutputStream = new FileOutputStream(new File(outputFilePath));

			List<Bloco> blocos = new ArrayList<>();
			Bloco bloco = null;

			byte[] buffer = new byte[BUFFERSIZE];
			byte[] lastBuffer;
			int cont = 1;
			while (fileInputStream.available() != 0) {
				System.out.println("Bloco " + cont++ + ": " + fileInputStream.available());
				if (fileInputStream.available() < 1024) {
					lastBuffer = new byte[fileInputStream.available()];
					fileInputStream.read(lastBuffer);
					//fileOutputStream.write(lastBuffer);
					bloco = new Bloco(lastBuffer);
					
					System.out.println(bloco.bytesToHex(lastBuffer));
					System.out.println(bloco.bytesToHex());
				} else {
					fileInputStream.read(buffer);
					//fileOutputStream.write(buffer);
					bloco = new Bloco(buffer);
				}
				blocos.add(bloco);
			}
			
			System.out.println(bloco.bytesToHex(blocos.get(blocos.size()-1).getData()));
//			for (int i = 0; i< blocos.size(); i++) {
//					fileOutputStream.write(blocos.get(i).getData());
//					//System.out.println(bloco.bytesToHex(blocos.get(i+1).getData()));
//					//hash = blocos.get(i).calculateHash();
//			}

			byte[] hash = null;
			for (int i = blocos.size() - 1; i >= 0; i--) {
				// Se for o ultimo bloco
				if (hash == null) {
					fileOutputStream.write(blocos.get(i).getData());
					hash = blocos.get(i).calculateHash();
				} else { // Se for todos os outros
					fileOutputStream.write(blocos.get(i).getData());
					blocos.get(i).addHash(hash);
					hash = blocos.get(i).calculateHash();
				}
			}
			
//			for (int i = 0; i< blocos.size(); i++) {
//				// Se for o ultimo bloco
//				if (hash == null) {
//					fileOutputStream.write(blocos.get(i).getData());
//					hash = blocos.get(i).calculateHash();
//				} else { // Se for todos os outros
//					fileOutputStream.write(blocos.get(i).getData());
//					blocos.get(i).addHash(hash);
//					hash = blocos.get(i).calculateHash();
//				}
//			}
			
			System.out.println("h0=" + bytesToHex(hash));
		} catch (Exception e) {
			System.out.println("Something went wrong! Reason: " + e.getMessage());
		}
	}

}

// Antigo copiador de video

//byte[] buffer = new byte[BUFFERSIZE];
//while (fileInputStream.available() != 0) {
//	fileInputStream.read(buffer);
//	//System.out.println(fileInputStream.hashCode());
//	fileOutputStream.write(buffer);
//	cont++;
//	System.out.println("VAMO LA " + cont);
//}
