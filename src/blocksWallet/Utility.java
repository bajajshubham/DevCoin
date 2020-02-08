package blocksWallet;
import java.io.UnsupportedEncodingException;
import java.security.Key;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

public class Utility {

	
	//applying SHA256 to String and returning hash  or digital signature as result
	public static String applySHA256(String data) {
		
		//hash as Hexadecimal container
		StringBuffer hexString  = new StringBuffer();
		
		try {
			MessageDigest digest = MessageDigest.getInstance("SHA-256");
			//Applies SHA256 to supplied data
			byte[] hash = digest.digest(data.getBytes("UTF-8"));
			
			for(int i=0; i<hash.length; i++) {
				String hex = Integer.toHexString(0xff & hash[i]);
				if(hex.length()==1) hexString.append('0');
				hexString.append(hex);
			}
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}catch(Exception e) {
			e.printStackTrace();
		}
		return hexString.toString();
	}

	public static String getStringFromKey(Key key) {
		//System.out.println("Key.getEncoded() -> "+key.getEncoded());
		//System.out.println("Res -> "+Base64.getEncoder().encodeToString(key.getEncoded()) );
		return Base64.getEncoder().encodeToString(key.getEncoded());
	}


}

