package blocksWallet;

import java.util.ArrayList;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.Date;

public class Transaction {
	
	//Transaction is open to block_chain 
	
	//Hash of the transaction
	public String transactionID;
	//sender public key
	public PublicKey sender;
	//recipients public key
	public PublicKey recipent;
	public float value;
	//prevention and tampering proof
	public byte[] signature;
	public long timestamp;
	
	//list of IN's && OUT's
	//reference to previous transactions
	public ArrayList<TransactionsInputs> transactionsInputs = new ArrayList<>();
	//outputs are refereed as inputs in __NEW_TRANSACTION__
	//shows the amount received by relevant address
	public ArrayList<TransactionsOutputs> transactionsOutputs = new ArrayList<>();
	
	//This not open to block-chain
	//count of how many or total transactions
	private static int sequence=0;
	
	public Transaction(PublicKey sender,PublicKey recipent,float value,ArrayList<TransactionsInputs> transactionsInputs) {
		this.sender=sender;
		this.recipent=recipent;
		this.value=value;
		this.transactionsInputs=transactionsInputs;
		timestamp = new Date().getTime();
	}
	
	//calculation of  hash of transaction
	//which is used as id of transaction occurred
	private String calcHash() {
		//to avoid same hash for 2 identical transactions
		sequence+=1;
		//TODO
		return Utility.applySHA256(Utility.getStringFromKey(sender)+
				Utility.getStringFromKey(recipent)+
				Float.toString(value)+
				timestamp+
				sequence);
	}
	
	//generating/verifying signatures
	//IN's and OUT's in reality are also used as data 
	public void generateSignature(PrivateKey key) {
		String inp = Utility.getStringFromKey(sender)+
				Utility.getStringFromKey(recipent)+
				Float.toString(value)+
				timestamp;
		//System.out.println("Inp: "+inp);
		signature = Utility.applyECDSASign(key, inp);
	}
	
	//verifying transactions
	//In reality IN's and OUT's play a role also
	public boolean verifySignature() {
		String inp = Utility.getStringFromKey(sender)+
					 Utility.getStringFromKey(recipent)+
					 Float.toString(value)+
					 timestamp;
		//System.out.println("Inp verify: "+inp);
		return Utility.verifyECDSASign(sender, inp, signature);
	}
	
	
 }
