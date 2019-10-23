package application;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.CipherInputStream;
import javax.crypto.CipherOutputStream;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;

public class FileManager 
{
	private final String ENCRYPTION_KEY = "123456781234";
	
	public FileManager()
	{
		
		//String encrypted = encrypt("i am the senate", ENCRYPTION_KEY);
		//String decrypted = decrypt(encrypted, ENCRYPTION_KEY);
		
		//System.out.println(encrypted);
		//System.out.println(decrypted);
		
		PlayerData testData = new PlayerData("user", "pass", new Date(1,1,01), "city", "state", "country", "diagnosis");
		writeEncrypted(testData.sendToString(), System.getProperty("user.dir") + "\\file.foo");
		System.out.println(decryptAndRead(System.getProperty("user.dir") + "\\file.foo"));
	}
	
	public void writeEncrypted(String data, String path)
	{
		try 
		{
			File dataFile = new File(path);
			Cipher cipher = Cipher.getInstance("AES");
			cipher.init(Cipher.ENCRYPT_MODE, new SecretKeySpec(Base64.getEncoder().encode(ENCRYPTION_KEY.getBytes()), "AES"));
			CipherOutputStream outputStream = new CipherOutputStream(new FileOutputStream(dataFile), cipher);
			outputStream.write(data.getBytes());
			
			outputStream.close();
		} 
		catch (IOException | NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public String decryptAndRead(String path)
	{
		try 
		{
			File dataFile = new File(path);
			Cipher cipher = Cipher.getInstance("AES");
			cipher.init(Cipher.DECRYPT_MODE, new SecretKeySpec(Base64.getEncoder().encode(ENCRYPTION_KEY.getBytes()), "AES"));
			CipherInputStream inputStream = new CipherInputStream(new FileInputStream(dataFile), cipher);
			ByteArrayOutputStream byteOutput = new ByteArrayOutputStream();
			
			int currentByte = 0;
			while ((currentByte = inputStream.read()) != -1)
			{
				byteOutput.write(currentByte);
			}
			
			inputStream.close();
			byteOutput.close();
			
			return byteOutput.toString();
		} 
		catch (InvalidKeyException | NoSuchAlgorithmException | NoSuchPaddingException | IOException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return "";
	}
	
	
	private byte[] encrypt(String message, String key)
	{
		try 
		{
			Cipher cipher = Cipher.getInstance("AES");
			cipher.init(Cipher.ENCRYPT_MODE, new SecretKeySpec(Base64.getEncoder().encode(key.getBytes()), "AES"));
			byte[] bytes = cipher.doFinal(message.getBytes());
			return bytes;
		} 
		catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException | IllegalBlockSizeException | BadPaddingException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return new byte[0];
	}
	
	private byte[] decrypt(byte[] encryptedMsg, String key)
	{
		try 
		{
			Cipher cipher = Cipher.getInstance("AES");
			cipher.init(Cipher.DECRYPT_MODE, new SecretKeySpec(Base64.getEncoder().encode(key.getBytes()), "AES"));
			byte[] bytes = cipher.doFinal(encryptedMsg);
			return bytes;
		} 
		catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException | IllegalBlockSizeException | BadPaddingException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return new byte[0];
	}
	
	
}
