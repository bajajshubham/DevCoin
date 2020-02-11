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
		
		Transaction genesisTransaction = new Transaction(coinBase.publicKey, walletA.publicKey, 100f, null);
		Transaction gTransaction  = new Transaction(coinBase.publicKey, walletB.publicKey, 101f, null);
		
		genesisTransaction.generateSignature(coinBase.getPrivateKey());
		gTransaction.generateSignature(coinBase.getPrivateKey());
		
		genesisTransaction.transactionID="0";
		gTransaction.transactionID="0";//or "1"
		
		genesisTransaction.transactionsOutputs.add(new TransactionsOutputs(walletA.publicKey, genesisTransaction.value	,genesisTransaction.transactionID));
		gTransaction.transactionsOutputs.add(new TransactionsOutputs(walletB.publicKey, gTransaction.value, gTransaction.transactionID));
		
		System.out.println(genesisTransaction);
		System.out.println(gTransaction);
		//storing transaction into UTXOs list
		BlkChain.UTXOs.put(genesisTransaction.transactionsOutputs.get(0).id,genesisTransaction.transactionsOutputs.get(0));
		BlkChain.UTXOs.put(gTransaction.transactionsOutputs.get(0).id,gTransaction.transactionsOutputs.get(0));
		
		System.out.println(BlkChain.UTXOs);
	}
}
