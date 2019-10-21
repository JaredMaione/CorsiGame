package application;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;

public class FileManager 
{
	public FileManager()
	{
		
		String encrypted = encrypt("i am the senate", "123456781234");
		String decrypted = decrypt(encrypted, "123456781234");
		
		System.out.println(encrypted);
		System.out.println(decrypted);
	}
	
	private String encrypt(String message, String key)
	{
		try 
		{
			Cipher cipher = Cipher.getInstance("AES");
			cipher.init(Cipher.ENCRYPT_MODE, new SecretKeySpec(Base64.getEncoder().encode(key.getBytes()), "AES"));
			byte[] bytes = cipher.doFinal(message.getBytes());
			return new String(bytes);
		} 
		catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException | IllegalBlockSizeException | BadPaddingException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "";
	}
	
	private String decrypt(String message, String key)
	{
		try 
		{
			Cipher cipher = Cipher.getInstance("AES");
			cipher.init(Cipher.DECRYPT_MODE, new SecretKeySpec(Base64.getEncoder().encode(key.getBytes()), "AES"));
			byte[] bytes = cipher.doFinal(message.getBytes());
			return new String(bytes);
		} 
		catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException | IllegalBlockSizeException | BadPaddingException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return "";
	}
	
	
}
