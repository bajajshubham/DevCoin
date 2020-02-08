package blocksWallet;

import java.security.PublicKey;
import java.security.PrivateKey;

public class Wallet {

	private PublicKey publicKey;
	private PrivateKey privateKey;
	
	public Wallet() { generateKeyPair(); } 
	
	public PublicKey getPublicKey() {
		return publicKey;
	}
	
	public PrivateKey getPrivateKey() {
		return privateKey;
	}
	
	//generates public and private key pair
	//using EllipticCurveCryptography
	public void generateKeyPair() {
		
	}
	
}
