package blocksdev;

public class RunDemo {

	public static void main(String[] args) {
		Block genesisBlock = new Block("Genius Block", "0");
		System.out.println("Hash of Block1: " + genesisBlock.getHash());
		Block firstBlock = new Block("I am the first block", genesisBlock.getHash());
		System.out.println("Hash of Block2: " + firstBlock.getHash());
		Block secondBlock = new Block("I am the second block", firstBlock.getHash());
		System.out.println("Hash of Block3:" + secondBlock.getHash());

	}

}
