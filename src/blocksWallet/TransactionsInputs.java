package blocksWallet;

//used to reference the transaction outputs that have not been spent yet
public class TransactionsInputs {

	//Reference to transaction output
	//It is used to relevant transaction output
	//allowing miners to check ownership
	public String transactionOutputID;
	
	//contains the unspent transaction output
	public TransactionsOutputs UTXOS;
	
	public TransactionsInputs(String transactionOutputID) {
		this.transactionOutputID=transactionOutputID;
	}
	
}
