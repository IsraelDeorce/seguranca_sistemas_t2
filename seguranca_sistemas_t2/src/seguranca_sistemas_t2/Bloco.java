package seguranca_sistemas_t2;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Bloco {
	
	private byte[] data;
	private byte[] hash = new byte[256];
 
	public Bloco(byte[] data) {
		this.data = data;
	}

	public byte[] getData() {
		return data;
	}

	public void setData(byte[] data) {
		this.data = data;
	}

	public byte[] getHash() {
		return hash;
	}

	public void setHash(byte[] hash) {
		this.hash = hash;
	}
	
	public void calculateHash() {
		MessageDigest digest;
		byte[] encodedhash = null;
		try {
			digest = MessageDigest.getInstance("SHA-256");
			encodedhash = digest.digest(originalString.getBytes(StandardCharsets.UTF_8));
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private String bytesToHex() {
		StringBuffer hexString = new StringBuffer();
		for (int i = 0; i < data.length; i++) {
			String hex = Integer.toHexString(0xff & data[i]);
			if (hex.length() == 1)
				hexString.append('0');
			hexString.append(hex);
		}
		System.out.println("HexString: " + hexString.toString());
		return hexString.toString();
	}
}
