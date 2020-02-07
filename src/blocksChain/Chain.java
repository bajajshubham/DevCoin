package blocksChain;

import java.util.ArrayList;
import com.google.gson.GsonBuilder;

public class Chain {
	
	private static ArrayList<Block> blockchain = new ArrayList<>();
	
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
	
}
