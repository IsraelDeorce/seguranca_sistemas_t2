package seguranca_sistemas_t2;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

public class Bloco {

	private byte[] data;
	private byte[] hash = new byte[32];
	private byte[] fullBlock;

	public Bloco(byte[] data) {
		this.data = copyByteArray(data);
		this.fullBlock = data;
	}
	
	public byte[] copyByteArray(byte[] a) {
		byte[] b = new byte[a.length];
		for(int i=0; i<a.length; i++) {
			b[i] = a[i];
		}
		return b;
	}

	public byte[] calculateHash() {
		MessageDigest digest;
		try {
			digest = MessageDigest.getInstance("SHA-256");
			this.hash = digest.digest(this.fullBlock);
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return this.hash;
	}
	
	// Concatena a hash recebida no fullBlock
	public byte[] addHash(byte[] hash) {
		this.fullBlock = new byte[this.data.length + hash.length];
		System.arraycopy(data, 0, this.fullBlock, 0, this.data.length);
		System.arraycopy(hash, 0, this.fullBlock, this.data.length, hash.length);		
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
		//System.out.println("HexString: " + hexString.toString());
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
		//System.out.println("HexString: " + hexString.toString());
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
