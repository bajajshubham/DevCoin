package blocksWallet;

import java.io.UnsupportedEncodingException;
import java.security.Key;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Base64;
import java.security.PrivateKey;
import java.security.Signature;
import java.security.PublicKey;

public class Utility {

	// applying SHA256 to String and returning hash or digital signature as result
	public static String applySHA256(String data) {

		// hash as Hexadecimal container
		StringBuffer hexString = new StringBuffer();

		try {
			MessageDigest digest = MessageDigest.getInstance("SHA-256");
			// Applies SHA256 to supplied data
			byte[] hash = digest.digest(data.getBytes("UTF-8"));

			for (int i = 0; i < hash.length; i++) {
				String hex = Integer.toHexString(0xff & hash[i]);
				if (hex.length() == 1)
					hexString.append('0');
				hexString.append(hex);
			}
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return hexString.toString();
	}

	// return encoded string from key
	public static String getStringFromKey(Key key) {
		// System.out.println("Key.getEncoded() -> "+key.getEncoded());
		// System.out.println("Res ->
		// "+Base64.getEncoder().encodeToString(key.getEncoded()) );
		return Base64.getEncoder().encodeToString(key.getEncoded());
	}

	// generate signature
	// Applying ECDSA Signature and returns results as bytes i.e. signature
	public static byte[] applyECDSASign(PrivateKey privateKey, String inp) {

		Signature dsa;
		// 8bits == 1byte
		byte[] output = new byte[0];

		try {

			// algorithm and provider
			dsa = Signature.getInstance("ECDSA", "BC");
			// System.out.println("dsa: "+dsa);

			dsa.initSign(privateKey);
			// System.out.println("Input.getBytes() -> "+inp.getBytes());

			byte[] strByte = inp.getBytes();
			dsa.update(strByte);
			// System.out.println("dsa after update: "+dsa);

			byte[] realSign = dsa.sign();
			// System.out.println("dsa sign {output} -> "+realSign);

			output = realSign;
		} catch (Exception e) {
			e.printStackTrace();
		}

		return output;
	}

	// verify signature
	public static boolean verifyECDSASign(PublicKey publicKey, String inp, byte[] signature) {
		boolean res = false;
		try {

			// algorithm and provider
			Signature ecdsaVerifySign = Signature.getInstance("ECDSA", "BC");
			ecdsaVerifySign.initVerify(publicKey);
			ecdsaVerifySign.update(inp.getBytes());
			res = ecdsaVerifySign.verify(signature);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return res;
	}

	//Tacks in array of transactions 
	//&&
	//returns a merkle root
	public static String getMerkleRoot(ArrayList<Transaction> transactions) {
		
		int count = transactions.size();
		ArrayList<String> previousTreeLayer = new ArrayList<String>();
		
		for(Transaction transaction:transactions) {
			previousTreeLayer.add(transaction.transactionID);
		}
		
		ArrayList<String> treeLayer = previousTreeLayer;
		
		while(count >  1) {
			treeLayer = new ArrayList<String>();
			for(int i=1; i<previousTreeLayer.size(); i++) {
				treeLayer.add(applySHA256(previousTreeLayer.get(i-1)) + previousTreeLayer.get(i));
			}
			count = treeLayer.size();
			previousTreeLayer = treeLayer;
		}
		
		return ( (treeLayer.size() == 1) ? treeLayer.get(0) : "" );
	}

}
