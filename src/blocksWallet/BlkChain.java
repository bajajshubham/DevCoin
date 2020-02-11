package blocksWallet;

import java.util.ArrayList;
import java.util.HashMap;
import com.google.gson.GsonBuilder;

public class BlkChain {

	private static ArrayList<Blocks> blockchain = new ArrayList<>();
	// problem it is limited to size of String hash or max index-1
	private static int difficulty = 6;
	public static float minimumTransactionValue = 0.0f;
	public static HashMap<String, TransactionsOutputs> UTXOs = new HashMap<>();

	public static int getDifficulty() {
		return difficulty;
	}

	public static void setDifficulty(int difficulty) {
		BlkChain.difficulty = difficulty;
	}

	public static void addBlocks(Blocks block) {
		block.mineBlock(difficulty);
		blockchain.add(block);
	}

	public static void printPrettyChainASJson() {
		String blkchain = new GsonBuilder().setPrettyPrinting().create().toJson(blockchain);
		System.out.println(blkchain);
	}

	public static boolean isChainValid() {

		Blocks currentBlock;
		Blocks prevBlock;

		// get a string with '0'*difficulty
		String hashTarget = new String(new char[difficulty]).replace('\0', '0');
		HashMap<String, TransactionsOutputs> tempUTXOs = new HashMap<String, TransactionsOutputs>(); // a temporary
																										// working list
																										// of unspent
																										// transactions
																										// at a given
																										// block state.
		// tempUTXOs.put(genesisTransaction.transactionsOutputs.get(0).id,
		// genesisTransaction.transactionsOutputs.get(0));

		// loop through blockchain to check hashes:
		for (int i = 1; i < blockchain.size(); i++) {

			currentBlock = blockchain.get(i);
			prevBlock = blockchain.get(i - 1);
			// compare registered hash and calculated hash:
			if (!currentBlock.getHash().equals(currentBlock.calcHash())) {
				System.out.println("#Current Hashes not equal");
				return false;
			}
			// compare previous hash and registered previous hash
			if (!prevBlock.getHash().equals(prevBlock.calcHash())) {
				System.out.println("#Previous Hashes not equal");
				return false;
			}
			// check if hash is solved
			if (!currentBlock.getHash().substring(0, difficulty).equals(hashTarget)) {
				System.out.println("#This block hasn't been mined");
				return false;
			}

			// loop thru blockchains transactions:
			TransactionsOutputs tempOutput;
			for (int t = 0; t < currentBlock.transactions.size(); t++) {
				Transaction currentTransaction = currentBlock.transactions.get(t);

				if (!currentTransaction.verifySignature()) {
					System.out.println("#Signature on Transaction(" + t + ") is Invalid");
					return false;
				}
				if (currentTransaction.getInputValues() != currentTransaction.getOutputValues()) {
					System.out.println("#Inputs are note equal to transactionsOutputs on Transaction(" + t + ")");
					return false;
				}

				for (TransactionsInputs input : currentTransaction.transactionsInputs) {
					tempOutput = tempUTXOs.get(input.transactionOutputID);

					if (tempOutput == null) {
						System.out.println("#Referenced input on Transaction(" + t + ") is Missing");
						return false;
					}

					if (input.UTXOS.value != tempOutput.value) {
						System.out.println("#Referenced input Transaction(" + t + ") value is Invalid");
						return false;
					}

					tempUTXOs.remove(input.transactionOutputID);
				}

				for (TransactionsOutputs output : currentTransaction.transactionsOutputs) {
					tempUTXOs.put(output.id, output);
				}

				if (currentTransaction.transactionsOutputs.get(0).recipent != currentTransaction.recipent) {
					System.out.println("#Transaction(" + t + ") output recipent is not who it should be");
					return false;
				}
				if (currentTransaction.transactionsOutputs.get(1).recipent != currentTransaction.sender) {
					System.out.println("#Transaction(" + t + ") output 'change' is not sender.");
					return false;
				}

			}

		}
		return true;
	}

}
