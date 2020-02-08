package blocksWallet;

import java.util.Date;

public class Blocks {

		//Number of milliseconds since 1/1/1970
		private long timestamp;
		private String data;
		//current block hash
		private String hash;
		//previous block hash
		private String prevBlockHash;
		private static Blocks genesisBlock=null;
		//use long here
		private long nonce;
		
		
		//no role of default constructor here as this is block 		
		public Blocks(String data, String prevBlockHash) {
			this.data = data;
			this.prevBlockHash=prevBlockHash;
			this.timestamp = new Date().getTime();
			this.hash = calcHash();
		}
		
		public String getHash() {
			return hash;
		}
		
		public String getData() {
			return data;
		}
		
		public static Blocks getGenesisBlock() {
			return genesisBlock;
		}
		
		public static void setGenesisBlock(Blocks gBlock) {
			genesisBlock = gBlock;
		}
		
		public String calcHash() {
			return Utility.applySHA256(prevBlockHash+Long.toString(timestamp)+
					Long.toString(nonce)+data);
		}
		
		public void mineBlock(int difficulty) {
			//'0'*difficulty
			String target = new String(new char[difficulty]).replace('\0', '0');
			//System.out.println("Target String: "+target);
			
			//System.out.println("Before hash: "+hash);
			while(!hash.substring(0,difficulty).equals(target)) {
				nonce++;
				hash = calcHash();
				//System.out.println("After Calchash: "+hash);
			}
			//System.out.println("After Hash: "+hash);
			System.out.println("Block Mined:"+hash);
		}
		
}
