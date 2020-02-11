package blocksWallet;

import java.util.ArrayList;
import java.util.Date;

public class Blocks {

	// Number of milliseconds since 1/1/1970
	private long timestamp;
	// merkle root takes its place
	// TODO why
	// private String data;
	// current block hash
	private String hash;
	// previous block hash
	private String prevBlockHash;
	private static Blocks genesisBlock = null;
	// use long here
	private long nonce;
	// Merkle Root for simplifying block-chain, blocks and transactions
	public String merkleRoot;
	public ArrayList<Transaction> transactions = new ArrayList<>();

	// no role of default constructor here as this is block
	public Blocks(String prevBlockHash) {
		this.prevBlockHash = prevBlockHash;
		this.timestamp = new Date().getTime();
		this.hash = calcHash();
	}

	public String getHash() {
		return hash;
	}

	public static Blocks getGenesisBlock() {
		return genesisBlock;
	}

	public static void setGenesisBlock(Blocks gBlock) {
		genesisBlock = gBlock;
	}

	public String calcHash() {
		return Utility.applySHA256(prevBlockHash + Long.toString(timestamp) + Long.toString(nonce) + merkleRoot);
	}

	public void mineBlock(int difficulty) {
		merkleRoot = Utility.getMerkleRoot(transactions);
		// '0'*difficulty
		String target = new String(new char[difficulty]).replace('\0', '0');
		// System.out.println("Target String: "+target);

		// System.out.println("Before hash: "+hash);
		while (!hash.substring(0, difficulty).equals(target)) {
			nonce++;
			hash = calcHash();
			// System.out.println("After Calchash: "+hash);
		}
		// System.out.println("After Hash: "+hash);
		System.out.println("Block Mined:" + hash);
	}

	// adding transactions to this block
	// adds transaction to block and so blockchain
	public boolean addTransaction(Transaction transaction) {

		// If Genesis block then ignore
		// else simple block then
		// process transaction and checks if Valid

		if (transaction == null)
			return false;
		if ((prevBlockHash != "0")) {
			if (!transaction.processTransaction()) {
				System.out.println("#TRANSACTION failed to proces, Discarded");
				return false;
			}
		}
		transactions.add(transaction);
		System.out.println("#TRANSACTION succesfully added to block");
		return true;
	}
}
