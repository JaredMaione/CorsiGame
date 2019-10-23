package application;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
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
	private final String ENCRYPTION_KEY = "123456781234";
	
	public FileManager()
	{
		
		String encrypted = encrypt("i am the senate", ENCRYPTION_KEY);
		String decrypted = decrypt(encrypted, ENCRYPTION_KEY);
		
		System.out.println(encrypted);
		System.out.println(decrypted);
		
		PlayerData testData = new PlayerData("user", "pass", new Date(1,1,01), "city", "state", "country", "diagnosis");
		writeEncrypted(testData.sendToString(), System.getProperty("user.dir") + "\\file.txt");
	}
	
	public void writeEncrypted(String data, String path)
	{
		try 
		{
			File dataFile = new File(path);
			FileWriter fileWriter = new FileWriter(dataFile);
			fileWriter.write(encrypt(path, ENCRYPTION_KEY));
			
			fileWriter.close();
		} 
		catch (IOException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public String decryptAndRead(String path)
	{
		File dataFile = new File(path);
		FileReader 
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
	
	private String decrypt(String encryptedMsg, String key)
	{
		try 
		{
			Cipher cipher = Cipher.getInstance("AES");
			cipher.init(Cipher.DECRYPT_MODE, new SecretKeySpec(Base64.getEncoder().encode(key.getBytes()), "AES"));
			byte[] bytes = cipher.doFinal(encryptedMsg.getBytes());
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
