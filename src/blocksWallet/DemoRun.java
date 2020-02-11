package blocksWallet;

import java.security.Security;

public class DemoRun {

	public static void main(String[] args) {
		
		//BouncyCastle as Security Provider
		Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());
		
		//manually process
		Wallet coinBase = new Wallet();
		Wallet walletA = new Wallet();
		Wallet walletB = new Wallet();
		
		//manually setup
		Transaction genesisTransaction = new Transaction(coinBase.publicKey, walletA.publicKey, 100f, null);
		Transaction genesisTransaction1 = new Transaction(coinBase.publicKey, walletB.publicKey, 101f, null);
		genesisTransaction.generateSignature(coinBase.getPrivateKey());
		genesisTransaction1.generateSignature(coinBase.getPrivateKey());
		genesisTransaction.transactionID="0";
		genesisTransaction1.transactionID="1";
		genesisTransaction.transactionsOutputs.add(new TransactionsOutputs(walletA.publicKey, 100f, genesisTransaction.transactionID));
		genesisTransaction1.transactionsOutputs.add(new TransactionsOutputs(walletB.publicKey, 101f, genesisTransaction1.transactionID));
		BlkChain.UTXOs.put(genesisTransaction.transactionsOutputs.get(0).id, genesisTransaction.transactionsOutputs.get(0));
		BlkChain.UTXOs.put(genesisTransaction1.transactionsOutputs.get(0).id, genesisTransaction1.transactionsOutputs.get(0));
		
		System.out.println(genesisTransaction);
		System.out.println(genesisTransaction1);
		
	}
}
