package seguranca_sistemas_t2;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
//https://riptutorial.com/java/example/12545/reading-a-file-using-channel-and-buffer
//https://stackoverflow.com/questions/4841340/what-is-the-use-of-bytebuffer-in-java
public class FileCopy {

	final int BUFFERSIZE = 1024;
	String sourceFilePath = "videoSample.avi";
	String outputFilePath = "videoSample2.avi";

	public FileCopy() {
	}

	public void start() {

		try  {
			FileInputStream fileInputStream = new FileInputStream(new File(sourceFilePath));
			FileOutputStream fileOutputStream = new FileOutputStream(new File(outputFilePath));
			FileChannel fileChannel = fileInputStream.getChannel();
			ByteBuffer byteBuffer = ByteBuffer.allocate(BUFFERSIZE);			

			int cont = 0;
			
			while(fileChannel.read(byteBuffer) > 0) {
				byteBuffer.flip();			    
			    byte[] buffer = new byte[BUFFERSIZE];
			    for(int i =0; byteBuffer.hasRemaining(); i++) {
			    	buffer[i] = byteBuffer.get();
			     // byte b = bBuffer.get();
			     // System.out.print((char) b);
			    }
			    Bloco bloco = new Bloco(buffer);
			    cont++;
			    System.out.println(buffer.length);
			    fileOutputStream.write(buffer);
			    byteBuffer.clear();
			}
				
//          byte[] buffer = new byte[BUFFERSIZE];
//			while (fileInputStream.available() != 0) {
//				fileInputStream.read(buffer);
//				//System.out.println(fileInputStream.hashCode());
//				fileOutputStream.write(buffer);
//				cont++;
//				System.out.println("VAMO LA " + cont);
//			}

		} catch (Exception e) {
			System.out.println("Something went wrong! Reason: " + e.getMessage());
		}

	}

}
