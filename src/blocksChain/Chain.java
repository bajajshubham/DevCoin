package blocksChain;

import java.util.ArrayList;
import com.google.gson.GsonBuilder;

public class Chain {
	
	private static ArrayList<Block> blockchain = new ArrayList<>();
	//problem it is limited to size of String hash or max index-1
	private static int difficulty=6;
		
	public static int getDifficulty() {
		return difficulty;
	}

	public static void setDifficulty(int difficulty) {
		Chain.difficulty = difficulty;
	}

	public static void addBlock(String data) {
		if(Block.getGenesisBlock()==null) {
			Block gBlock = new Block(data,"0");
			Block.setGenesisBlock(gBlock);
			blockchain.add(gBlock);
		}else {
			blockchain.add(new Block(data,blockchain.get(blockchain.size()-1).getHash()));
		}
	}
	
	public static void printPrettyChainASJson() {
		String blkchain  = new GsonBuilder().setPrettyPrinting().create().toJson(blockchain);
		System.out.println(blkchain);
	}
	
	public static boolean isChainValid() {
		
		Block currentBlock;
		Block prevBlock;
		
		//get a string with '0'*difficulty
		String hashTarget = new String(new char[difficulty]).replace('\0','0');
		
		//looping through all the blocks
		for(int i=1; i<blockchain.size(); i++) {
			currentBlock = blockchain.get(i);
			prevBlock = blockchain.get(i-1);
			
			//comparing registered hash and calculated hash
			if(!currentBlock.getHash().equals(currentBlock.calcHash())) {
				System.out.println("Current Block Hashes are not equal");
				return false;
			}
			
			//comparing registered hash and calculated hash
			if(!prevBlock.getHash().equals(prevBlock.calcHash())) {
				System.out.println("Previous Block Hashes are not equal");
				return false;
			}
			
			//check has a solved hash for each block(mining)
			if(!currentBlock.getHash().substring(0,difficulty).equals(hashTarget)) {
				System.out.println("The Block hasn't Mined");
				return false;
			}
			
		}
		return true;
	}
	
	public static void mineTheBlock() {

		blockchain.get(blockchain.size()-1).mineBlock(difficulty);
	}
	
}
