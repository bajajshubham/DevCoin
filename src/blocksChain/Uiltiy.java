package blocksChain;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Uiltiy {

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
}
