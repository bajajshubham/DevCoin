package blocksWallet;

import java.security.Security;

public class DemoRun {
	
	public static void main(String[] args) {
		 Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());
		 Wallet walletA = new Wallet();
		 Wallet walletB = new Wallet();
		
	}
}
