package blocksWallet;

import java.util.ArrayList;
import java.util.HashMap;
import com.google.gson.GsonBuilder;


public class BlkChain {
	
	private static ArrayList<Blocks> blockchain = new ArrayList<>();
	//problem it is limited to size of String hash or max index-1
	private static int difficulty=6;
	public static float minimumTransactionValue = 0.0f;
	public static HashMap<String,TransactionsOutputs> UTXOs = new HashMap<>();
		
	public static int getDifficulty() {
		return difficulty;
	}

	public static void setDifficulty(int difficulty) {
		BlkChain.difficulty = difficulty;
	}

	public static void addBlocks(String data) {
		if(Blocks.getGenesisBlock()==null) {
			Blocks gBlocks = new Blocks(data,"0");
			Blocks.setGenesisBlock(gBlocks);
			blockchain.add(gBlocks);
		}else {
			blockchain.add(new Blocks(data,blockchain.get(blockchain.size()-1).getHash()));
		}
	}
	
	public static void printPrettyChainASJson() {
		String blkchain  = new GsonBuilder().setPrettyPrinting().create().toJson(blockchain);
		System.out.println(blkchain);
	}
	
	public static boolean isChainValid() {
		
		Blocks currentBlocks;
		Blocks prevBlocks;
		
		//get a string with '0'*difficulty
		String hashTarget = new String(new char[difficulty]).replace('\0','0');
		
		//looping through all the blocks
		for(int i=1; i<blockchain.size(); i++) {
			currentBlocks = blockchain.get(i);
			prevBlocks = blockchain.get(i-1);
			
			//comparing registered hash and calculated hash
			if(!currentBlocks.getHash().equals(currentBlocks.calcHash())) {
				System.out.println("Current Blocks Hashes are not equal");
				return false;
			}
			
			//comparing registered hash and calculated hash
			if(!prevBlocks.getHash().equals(prevBlocks.calcHash())) {
				System.out.println("Previous Blocks Hashes are not equal");
				return false;
			}
			
			//check has a solved hash for each block(mining)
			if(!currentBlocks.getHash().substring(0,difficulty).equals(hashTarget)) {
				System.out.println("The Blocks hasn't Mined");
				return false;
			}
			
		}
		return true;
	}
	
	public static void mineTheBlocks() {

		blockchain.get(blockchain.size()-1).mineBlock(difficulty);
	}
	
}

