package blocksChain;

public class RunDemo {

	public static void main(String[] args) {
		Chain.addBlock("Gb");
		Chain.printPrettyChainASJson();
		Chain.mineTheBlock();
//		Chain.addBlock("FBlockabcdefghijklmnopqrstuvwxyzabcdefghijklmnopqrstuvwxyzabcdefghijklmnopqrstuvwxyz");
//		Chain.mineTheBlock();
//		Chain.addBlock("Sblockabcdefghijklmnopqrstuvwxyzabcdefghijklmnopqrstuvwxyzabcdefghijklmnopqrstuvwxyz");
//		Chain.mineTheBlock();
		Chain.printPrettyChainASJson();
		
		System.out.println("Is chain Valid: "+Chain.isChainValid());
	}

}
