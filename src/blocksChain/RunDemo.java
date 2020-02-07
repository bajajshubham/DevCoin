package blocksChain;

public class RunDemo {

	public static void main(String[] args) {
	
		Chain.addBlock("GenssisBlock");
		Chain.addBlock("FBlock");
		Chain.addBlock("SBlock");
		Chain.printBlockChainAsJson();

	}

}
