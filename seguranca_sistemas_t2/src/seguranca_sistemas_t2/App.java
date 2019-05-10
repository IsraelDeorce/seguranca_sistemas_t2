package seguranca_sistemas_t2;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

// https://www.baeldung.com/sha-256-hashing-java
public class App {

	public static void main(String[] args) {

		String originalString = "Israel vai tirar 10 nesse trabalho";

		// TODO Auto-generated method stub
		MessageDigest digest;
		byte[] encodedhash = null;
		try {
			digest = MessageDigest.getInstance("SHA-256");
			encodedhash = digest.digest(originalString.getBytes(StandardCharsets.UTF_8));
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
//		
//		for(byte b : encodedhash) {
//			System.out.println("Enconded Hash: " + encodedhash);
//		}
		
		FileCopy fc = new FileCopy();
		fc.start();

	}

	private static String bytesToHex(byte[] hash) {
		StringBuffer hexString = new StringBuffer();
		for (int i = 0; i < hash.length; i++) {
			String hex = Integer.toHexString(0xff & hash[i]);
			if (hex.length() == 1)
				hexString.append('0');
			hexString.append(hex);
		}
		System.out.println("HexString: " + hexString.toString());
		return hexString.toString();
	}

}
