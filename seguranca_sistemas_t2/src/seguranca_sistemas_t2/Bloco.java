package seguranca_sistemas_t2;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Este relat�rio descreve uma alternativa de implementa��o para o exerc�cio
 * proposto no segundo trabalho da disciplina de Seguran�a de Sistemas. O
 * exerc�cio envolve o desenvolvimento de um programa que dado um arquivo de
 * video, que seja este separado em blocos de bytes e que se garanta a
 * integridade destes utilizando o "Algoritmo de Hash Seguro" SHA-256.
 * Desenvolveu-se a solu��o proposta em linguagem Java utilizando-se de
 * bibliotecas como a FileInputStream para leitura de dados, e a MessageDigest
 * para gera��o da hash. A solu��o apresentada � de forma textual explicativa,
 * acompanhada de pseudoc�digo. 
 * Data: 2019/1
 * 
 * @author Israel Deorce Vieira Junior
 * @professor Avelino Zorzo
 */
public class Bloco {

	private byte[] data;	// Dados do bloco do video (1024 bytes)
	private byte[] hash = new byte[32]; // Resultado da funcao Hash armazenada
	private byte[] fullBlock;	// Dados completos do bloco concatenados: DATA + HASH (se existir)

	/**
	 * Construtor, recebe um array de dados (bytes) e copia-o
	 * para data e fullBlock
	 * @param data
	 */
	public Bloco(byte[] data) {
		this.data = data.clone();
		this.fullBlock = this.data.clone();
	}

	/**
	 * Calcula e devolve a fun��o hash do fullBlock
	 * @return
	 */
	public byte[] calculateHash() {
		MessageDigest digest;
		try {
			digest = MessageDigest.getInstance("SHA-256");
			this.hash = digest.digest(this.fullBlock);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return this.hash;
	}
	
	/**
	 *  Concatena a hash recebida no fullBlock
	 * @param hash
	 * @return
	 */
	public byte[] addHash(byte[] hash) {
		this.fullBlock = new byte[this.data.length + hash.length];
		System.arraycopy(data, 0, this.fullBlock, 0, this.data.length);
		System.arraycopy(hash, 0, this.fullBlock, this.data.length, hash.length);		
		return this.fullBlock;
	}

	/*--------------------- GETTERS AND SETTERS ---------------------*/
	public byte[] getData() {return data;}

	public void setData(byte[] data) {this.data = data;}

	public byte[] getHash() {return hash;}

	public void setHash(byte[] hash) {this.hash = hash;}

	public byte[] getFullBlock() {return fullBlock;}

	public void setFullBlock(byte[] fullBlock) {this.fullBlock = fullBlock;}
}
