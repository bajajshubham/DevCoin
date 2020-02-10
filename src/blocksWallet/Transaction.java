package blocksWallet;

import java.util.ArrayList;
import java.util.Arrays;
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
	
	@Override
	public String toString() {
		return "Transaction [transactionID=" + transactionID + ", sender=" + sender + ", recipent=" + recipent
				+ ", value=" + value + ", signature=" + Arrays.toString(signature) + ", timestamp=" + timestamp
				+ ", transactionsInputs=" + transactionsInputs + ", transactionsOutputs=" + transactionsOutputs + "]";
	}

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
	
	//returns sum of unspent transactions
	//OR
	//returns sum of inputs(UTXOs) values
	public float getInputValues() {
		float total = 0;
		for(TransactionsInputs inputs: transactionsInputs) {
			System.out.println("Transaction input: "+inputs);
			System.out.println("Input UTXO value: "+inputs.UTXOS.value);
			//skip if transaction
			//can't be found or not found
			if (inputs.UTXOS==null) continue;
			total += inputs.UTXOS.value;
		}
		System.out.println("getInputValues() Total: "+total);
		return total;
	}
	
	//returns sum of spent transactions
	//OR
	//returns sum of output transactions or outputs
	public float getOutputValues() {
		float total  = 0;
		
		for(TransactionsOutputs outputs: transactionsOutputs) {
			System.out.println("TransactionsOutput : "+outputs);
			System.out.println("TransactionsOutput Value: "+outputs.value);
			total+=outputs.value;
		}
		System.out.println("TransactionsOutputs Total: "+total);
		return total;
	}
	
	
	//Discarding inputs from UTXOs list meaning a transactionOutput can
	//only be used once as a tranasactionInput
	//The full value of inputs must be used
	//Thus sender gets change back to themselves or
	//sender sends change back to themselves
	//returns true if new transaction 
	//could be created
	public boolean processTransaction() {
		
		if(!verifySignature()) {
			System.out.println("#TRANSACTION signauter failed to verify");
			return false;
		}
		
		//gathering transaction inputs
		//It Make's sure they are unspent value or transactions
		for(TransactionsInputs inputs: transactionsInputs) {
			System.out.println("BlkChain.UTXOs.get(inputs.transactionOutputID) ---> "
		+BlkChain.UTXOs.get(inputs.transactionOutputID));
			inputs.UTXOS = BlkChain.UTXOs.get(inputs.transactionOutputID);
		}
		
		//__CHECK__
		//If transacationInputsValues < minimumTransaction return
		if(getInputValues()<BlkChain.minimumTransactionValue) {
			System.out.println("#TRANSACTION Inputs are too Small {"+getInputValues()+"}");
			return false;
		}
		
		//generating output transactions
		//getting leftOver or leftOver change or Value
		//Thus,get value of inputs then the left over change
		float leftOver = getInputValues() - value;
		System.out.println("Value: "+value+" leftOver: "+leftOver);
		transactionID = calcHash();
		//send's value to the recipent
		transactionsOutputs.add(new TransactionsOutputs(this.recipent, value,transactionID));
		//send change back to the sender
		transactionsOutputs.add(new TransactionsOutputs(this.sender, leftOver,transactionID));
		
		//TODO
		//adds output to the unspent list
		for(TransactionsOutputs outputs: transactionsOutputs) {
			BlkChain.UTXOs.put(outputs.id,outputs);
		}
		
		//removing spent transactions from unspent transaction list
		//Remove inputTransactions from UTXOs list now they are spent
		for(TransactionsInputs inputs:transactionsInputs) {
			//skip
			if(inputs.UTXOS==null) continue;
			BlkChain.UTXOs.remove(inputs.UTXOS.id);
		}
		
		return true;
	}
 }
