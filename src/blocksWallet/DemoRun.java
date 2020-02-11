package blocksWallet;

import java.security.Security;
import java.util.ArrayList;

public class DemoRun {

	public static void main(String[] args) {

		// BouncyCastle as Security Provider
		Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());

		// manually process
		Wallet coinBase = new Wallet();
		Wallet walletA = new Wallet();
		Wallet walletB = new Wallet();

		// manually setup
		Transaction genesisTransaction = new Transaction(coinBase.publicKey, walletA.publicKey, 100f, null);
		genesisTransaction.generateSignature(coinBase.getPrivateKey());
		genesisTransaction.transactionID = "0";
		genesisTransaction.transactionsOutputs
				.add(new TransactionsOutputs(walletA.publicKey, 100f, genesisTransaction.transactionID));
		BlkChain.UTXOs.put(genesisTransaction.transactionsOutputs.get(0).id,
				genesisTransaction.transactionsOutputs.get(0));

		// System.out.println(BlkChain.UTXOs);

		// System.out.println(genesisTransaction);
		// System.out.println(genesisTransaction1);

		System.out.println("Creating && Mining Blockss");
		Blocks genesisBlocks = new Blocks("0");
		genesisBlocks.addTransaction(genesisTransaction);
		BlkChain.addBlocks(genesisBlocks);

		Blocks block1 = new Blocks(genesisBlocks.getHash());
		System.out.println("\nWalletA's balance is: " + walletA.getBalance());
		System.out.println("\nWalletA is Attempting to send funds (40) to WalletB...");
		block1.addTransaction(walletA.sendFunds(walletB.publicKey, 40f));
		BlkChain.addBlocks(block1);
		System.out.println("\nWalletA's balance is: " + walletA.getBalance());
		System.out.println("WalletB's balance is: " + walletB.getBalance());

		Blocks block2 = new Blocks(block1.getHash());
		System.out.println("\nWalletA Attempting to send more funds (1000) than it has...");
		block2.addTransaction(walletA.sendFunds(walletB.publicKey, 1000f));
		BlkChain.addBlocks(block2);
		System.out.println("\nWalletA's balance is: " + walletA.getBalance());
		System.out.println("WalletB's balance is: " + walletB.getBalance());

		Blocks block3 = new Blocks(block2.getHash());
		System.out.println("\nWalletB is Attempting to send funds (20) to WalletA...");
		block3.addTransaction(walletB.sendFunds(walletA.publicKey, 20));
		System.out.println("\nWalletA's balance is: " + walletA.getBalance());
		System.out.println("WalletB's balance is: " + walletB.getBalance());

// 		TODO
//		BAD
//		ArrayList<TransactionsInputs> transactionsInputs = new ArrayList<TransactionsInputs>();
//		transactionsInputs.add(new TransactionsInputs(""));
//		
//		Transaction genesisTransaction1 = new Transaction(coinBase.publicKey, walletB.publicKey, 101f,transactionsInputs);
//		genesisTransaction1.generateSignature(coinBase.getPrivateKey());
//		genesisTransaction1.transactionID="1";
//		TransactionsOutputs transactionsOutputs = new TransactionsOutputs(walletB.publicKey, genesisTransaction1.value,genesisTransaction1.transactionID);
//		genesisTransaction1.transactionsOutputs.add(new TransactionsOutputs(walletB.publicKey, 101f, genesisTransaction1.transactionID));
//		BlkChain.UTXOs.put(genesisTransaction1.transactionsOutputs.get(0).id, genesisTransaction1.transactionsOutputs.get(0));
//			
//		Blockss fblock  = new Blockss(genesisBlocks.getHash());
//		fblock.addTransaction(genesisTransaction1);
//		BlkChain.BlkChain.addBlocks(genesisBlocks);
//		
//		BlkChain.printPrettyChainASJson();

		// testing

	}
}
