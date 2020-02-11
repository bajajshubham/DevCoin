package blocksWallet;

import java.security.PublicKey;

//These when referenced as inputs in new transactions
//acts as proof value to spend
//It also shows the final amount sent to each party from transaction
public class TransactionsOutputs {

	public String id;
	// New owner of the value
	public PublicKey recipent;
	// amount of coins owned
	public float value;
	//
	// id of the transaction this output was created
	public String parentTransactionID;

	@Override
	public String toString() {
		return "TransactionsOutputs [id=" + id + ", recipent=" + Utility.getStringFromKey(recipent) + ", value=" + value
				+ ", parentTransactionID=" + parentTransactionID + "]";
	}

	public TransactionsOutputs(PublicKey recipent, float value, String parentTransactionID) {
		this.recipent = recipent;
		this.value = value;
		this.parentTransactionID = parentTransactionID;
		this.id = Utility.applySHA256(
				Utility.getStringFromKey(this.recipent) + Float.toString(this.value) + this.parentTransactionID);
	}

	// check coins belong to whom
	// A wallet checks it is for me or user
	// then add into my wallet
	// @UTXOs
	public boolean isMine(PublicKey publicKey) {
		return (recipent == publicKey);
	}
}
