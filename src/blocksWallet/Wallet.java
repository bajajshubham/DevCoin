package blocksWallet;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.spec.ECGenParameterSpec;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

//TODO
//Keeping a record of transaction history

public class Wallet {

	public PublicKey publicKey;
	private PrivateKey privateKey;

	public HashMap<String, TransactionsOutputs> UTXOs = new HashMap<>();

	public Wallet() {
		generateKeyPair();
	}

	public PrivateKey getPrivateKey() {
		return privateKey;
	}

	// TODO WHY EACH OUTPUT IS PRINTING TWICE WITH DIFF_VALUES
	// generates public and private key pair
	// using EllipticCurveCryptography
	public void generateKeyPair() {

		try {

			// ECDSA is Algorithm
			// BC is provider
			KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("ECDSA", "BC");
			// System.out.println("KeyPairGenerator: "+keyPairGenerator);

			// SHA1PRNG is Algorithm
			SecureRandom secureRandom = SecureRandom.getInstance("SHA1PRNG");
			// System.out.println("SecureRandom: "+secureRandom);

			// TODO do not knew about it
			ECGenParameterSpec ecGenParameterSpec = new ECGenParameterSpec("prime192v1");
			// System.out.println("ECGenParameterSpec: "+ecGenParameterSpec);

			// Initializing key generator and generating a key pair
			// algorithm parameter specification and secureRandom are parameters here
			keyPairGenerator.initialize(ecGenParameterSpec, secureRandom);
			// System.out.println("KeyPairGenerator: "+keyPairGenerator);

			KeyPair keyPair = keyPairGenerator.generateKeyPair();
			// System.out.println("KeyPair: "+keyPair);
			// getting and setting public && private keys from key-pair-generator
			privateKey = keyPair.getPrivate();
			publicKey = keyPair.getPublic();

			// System.out.println("PublicKey: "+publicKey);
			// System.out.println("PrivateKey: "+privateKey);

		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (NoSuchProviderException e) {
			e.printStackTrace();
		} catch (Exception exception) {
			exception.printStackTrace();
		}

	}

	// returns the unspent balance
	// Store the UTXO's owned by this wallet
	// in its UTXOs list
	public float getBalance() {
		float total = 0;

		for (Map.Entry<String, TransactionsOutputs> item : BlkChain.UTXOs.entrySet()) {
			// System.out.println("Get Map.Entry<String, TransactionsOutputs> itemValue:
			// "+item.getValue());
			TransactionsOutputs UTXO = item.getValue();
			// If coin belongs to wallet
			// add it to list of
			// unspent transactions
			if (UTXO.isMine(publicKey)) {
				// append
				UTXOs.put(UTXO.id, UTXO);
				// System.out.println("GetBalance TransactionOutputs Value: "+UTXO.value);
				total += UTXO.value;
			}
		}

		// System.out.println("Get Balance Total: "+total);
		return total;
	}

	// generates a new transactions
	// from this.wallet or this wallet
	// that is send funds
	public Transaction sendFunds(PublicKey _recipient, float value) {

		// check first enough balance
		// to proceed transaction
		if (getBalance() < value) {
			System.out.println("#TRANSACTION Not Enough Funds to send, Transaction Discarded ");
			return null;
		}

		ArrayList<TransactionsInputs> transactionsInputs = new ArrayList<>();
		float total = 0;

		for (Map.Entry<String, TransactionsOutputs> item : UTXOs.entrySet()) {

			TransactionsOutputs UTXO = item.getValue();
			total += UTXO.value;
			transactionsInputs.add(new TransactionsInputs(UTXO.id));

			if (total > value)
				break;
		}

		return (new Transaction(publicKey, _recipient, value, transactionsInputs));
	}

}
