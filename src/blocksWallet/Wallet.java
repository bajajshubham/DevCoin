package blocksWallet;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.spec.ECGenParameterSpec;

public class Wallet {

	public PublicKey publicKey;
	private PrivateKey privateKey;
	
	public Wallet() { generateKeyPair(); } 

	//TODO WHY EACH OUTPUT IS PRINTING TWICE WITH DIFF_VALUES
	//generates public and private key pair
	//using EllipticCurveCryptography
	public void generateKeyPair() {
		
		try {
			
			//ECDSA is Algorithm
			//BC is provider
			KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("ECDSA","BC");
			//System.out.println("KeyPairGenerator: "+keyPairGenerator);
			
			//SHA1PRNG is Algorithm
			SecureRandom secureRandom = SecureRandom.getInstance("SHA1PRNG");
			//System.out.println("SecureRandom: "+secureRandom);
			
			//TODO do not knew about it
			ECGenParameterSpec ecGenParameterSpec = new ECGenParameterSpec("prime192v1");
			//System.out.println("ECGenParameterSpec: "+ecGenParameterSpec);
			
			//Initializing key generator and generating a key pair
			//algorithm parameter specification and secureRandom are parameters here
			keyPairGenerator.initialize(ecGenParameterSpec, secureRandom);
			//System.out.println("KeyPairGenerator: "+keyPairGenerator);
			
			KeyPair keyPair = keyPairGenerator.generateKeyPair();
			//System.out.println("KeyPair: "+keyPair);
			//getting and setting public && private keys from key-pair-generator
			privateKey = keyPair.getPrivate();
			publicKey = keyPair.getPublic();
			
			//System.out.println("PublicKey: "+publicKey);
			//System.out.println("PrivateKey: "+privateKey);
			
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (NoSuchProviderException e) {
			e.printStackTrace();
		}catch (Exception exception) { exception.printStackTrace(); }
		
	}
	
}
