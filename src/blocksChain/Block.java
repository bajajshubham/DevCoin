package blocksChain;

import java.util.Date;

public class Block {

		//Number of milliseconds since 1/1/1970
		private long timestamp;
		private String data;
		//current block hash
		private String hash;
		//previous block hash
		private String prevBlockHash;
		private static Block genesisBlock=null;
		//no role of default constructor here as this is block 
		
		public Block(String data, String prevBlockHash) {
			this.data = data;
			this.prevBlockHash=prevBlockHash;
			this.timestamp = new Date().getTime();
			this.hash = calcHash();
		}
		
		public String getHash() {
			return hash;
		}
		
		public static Block getGenesisBlock() {
			return genesisBlock;
		}
		
		public static void setGenesisBlock(Block gBlock) {
			genesisBlock = gBlock;
		}
		
		public String calcHash() {
			return Uiltiy.applySHA256(prevBlockHash+Long.toString(timestamp)+data);
		}
		
}
