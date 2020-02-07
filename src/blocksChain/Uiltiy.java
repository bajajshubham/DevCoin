package blocksChain;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

public class Uiltiy {

	
	//applying SHA256 to String and returning hash  or digital signature as result
	public static String applySHA256(String data) {
		
		//hash as Hexadecimal container
		StringBuffer hexString  = new StringBuffer();
		
		try {
			MessageDigest digest = MessageDigest.getInstance("SHA-256");
			
//			System.out.println("Digest is: "+digest);
			
			//Applies SHA256 to supplied data
			byte[] hash = digest.digest(data.getBytes("UTF-8"));
			
//			System.out.println("hash in bytes[]: "+hash);
//			System.out.println("hash as array: "+Arrays.toString(hash));
			
			for(int i=0; i<hash.length; i++) {
				String hex = Integer.toHexString(0xff & hash[i]);
//				System.out.println("hex is: "+hex);
				if(hex.length()==1) hexString.append('0');
//				System.out.println("Hex String is:"+hexString.toString());
				hexString.append(hex);
//				System.out.println("Hex String is:"+hexString.toString());
			}
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}catch(Exception e) {
			e.printStackTrace();
		}
//		System.out.println("String: "+hexString.toString());
		return hexString.toString();
	}
}
