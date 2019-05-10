package seguranca_sistemas_t2;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Bloco {

	private byte[] data;
	private byte[] fullBlock;
	private byte[] hash = new byte[32];

	public Bloco(byte[] data) {
		this.data = data;
		this.fullBlock = data;
		calculateHash();
	}

	public byte[] calculateHash() {
		MessageDigest digest;
		try {
			digest = MessageDigest.getInstance("SHA-256");
			hash = digest.digest(fullBlock);
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return hash;
	}
	
	// Concatena a hash recebida no fullBlock
	public byte[] addHash(byte[] hash) {
		fullBlock = new byte[data.length + hash.length];
		System.arraycopy(data, 0, this.fullBlock, 0, data.length);
		System.arraycopy(hash, 0, this.fullBlock, data.length, hash.length);		
		return this.fullBlock;
	}

	public String bytesToHex() {
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

	public String bytesToHex(byte[] data) {
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

	public byte[] getFullBlock() {
		return fullBlock;
	}

	public void setFullBlock(byte[] fullBlock) {
		this.fullBlock = fullBlock;
	}

}
