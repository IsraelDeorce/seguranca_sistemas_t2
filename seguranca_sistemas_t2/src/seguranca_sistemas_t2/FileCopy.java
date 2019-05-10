package seguranca_sistemas_t2;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
//https://riptutorial.com/java/example/12545/reading-a-file-using-channel-and-buffer
//https://stackoverflow.com/questions/4841340/what-is-the-use-of-bytebuffer-in-java
public class FileCopy {

	final int BUFFERSIZE = 1024;
	String sourceFilePath = "video05.mp4";
	String outputFilePath = "output.mp4";

	public FileCopy() {
	}

	public void start() {

		try  {
			//FileInputStream fileInputStream = new FileInputStream(new File(sourceFilePath));
			//FileChannel fileChannel = fileInputStream.getChannel();
			
			RandomAccessFile randomAccessFile = new RandomAccessFile(sourceFilePath, "r");
			
			FileOutputStream fileOutputStream = new FileOutputStream(new File(outputFilePath));
			FileChannel fileChannel = randomAccessFile.getChannel();
			ByteBuffer byteBuffer = ByteBuffer.allocate(BUFFERSIZE);			

			int cont = 0;
			
			 byte[] hash = null;
			 
			while(fileChannel.read(byteBuffer) > 0) {
				byteBuffer.flip();			    
			    byte[] buffer = new byte[BUFFERSIZE];
			    for(int i=0; byteBuffer.hasRemaining(); i++) {
			    	buffer[i] = byteBuffer.get();
			     // byte b = bBuffer.get();
			     // System.out.print((char) b);
			    }
			    Bloco bloco = new Bloco(buffer);
			    cont++;
			   
			    // se for a primeira hash
			    if(hash == null) {
			    	hash = bloco.calculateHash();
			    } else { // se for todas as outras
			    	bloco.addHash(hash);
			    	hash = bloco.calculateHash();
			    }
//			    System.out.println(hash.length);
//			    System.out.println(buffer.length);
//			    System.out.println(bloco.getFullBlock().length);
//			    fileOutputStream.write(buffer);			    
			    byteBuffer.clear();
			}
			System.out.println("h0="+bytesToHex(hash));

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
		//System.out.println("HexString: " + hexString.toString());
		return hexString.toString();
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
