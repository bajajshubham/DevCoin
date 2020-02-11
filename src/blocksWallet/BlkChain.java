package blocksWallet;

import java.util.ArrayList;
import java.util.HashMap;
import com.google.gson.GsonBuilder;

public class BlkChain {

	private static ArrayList<Blocks> blockchain = new ArrayList<>();
	// problem it is limited to size of String hash or max index-1
	private static int difficulty = 6;
	public static float minimumTransactionValue = 0.1f;
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

		Blocks currentBlocks = null;
		Blocks prevBlocks;

		// get a string with '0'*difficulty
		String hashTarget = new String(new char[difficulty]).replace('\0', '0');

		HashMap<String, TransactionsOutputs> tempUTXOs = new HashMap<String, TransactionsOutputs>(); // a temporary
																										// working list
																										// of unspent
																										// transactions
																										// at a given
																										// block state.
		// TODO
		// tempUTXOs.put(genesisTransaction.outputs.get(0).id,
		// genesisTransaction.outputs.get(0));

		// loop thru blockchains transactions:
		TransactionsOutputs tempOutput;
		for (int t = 0; t < currentBlocks.transactions.size(); t++) {
			Transaction currentTransaction = currentBlocks.transactions.get(t);

			if (!currentTransaction.verifySignature()) {
				System.out.println("#Signature on Transaction(" + t + ") is Invalid");
				return false;
			}
			if (currentTransaction.getInputValues() != currentTransaction.getOutputValues()) {
				System.out.println("#Inputs are note equal to outputs on Transaction(" + t + ")");
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
				System.out.println("#Transaction(" + t + ") output reciepient is not who it should be");
				return false;
			}
			if (currentTransaction.transactionsOutputs.get(1).recipent != currentTransaction.sender) {
				System.out.println("#Transaction(" + t + ") output 'change' is not sender.");
				return false;
			}

		}

		return true;
	}

}
