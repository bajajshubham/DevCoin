package blocksWallet;

import java.util.ArrayList;
import java.security.Security;

public class DemoRun {
	public static void main(String[] args) {
		 Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());
		 Wallet walletA = new Wallet();
		 Wallet walletB = new Wallet();
		 //System.out.println("WalletA PK: "+walletA.publicKey);
		 Utility.getStringFromKey(walletA.publicKey);
		 //System.out.println("WalletB PK: "+walletB.publicKey);
		 Utility.getStringFromKey(walletB.publicKey);
		 
		 //temp or demo transaction
		 Transaction transaction = new Transaction(walletA.publicKey,walletB.publicKey
				 ,10,new ArrayList<TransactionsInputs>());
		 
		// System.out.println("Transaction Hash: "+transaction.calcHash());
		 
		transaction.generateSignature(walletA.getPrivateKey());
		System.out.println("Generated Signature: "+transaction.signature);
		System.out.println("Verify Sign: "+transaction.verifySignature());
		
		
		
	}
}
