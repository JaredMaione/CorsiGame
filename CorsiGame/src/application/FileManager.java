package application;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import javax.crypto.Cipher;
import javax.crypto.CipherInputStream;
import javax.crypto.CipherOutputStream;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

// This class is responsible for reading, writing, encrypting, and decrypting files
// It uses AES encryption where applicable
public final class FileManager 
{
	private static final String ENCRYPTION_KEY = "123456781234";
	
	public static final String GAME_FILES_FOLDER = "GameFiles";
	public static final String PLAYER_FILES_FOLDER = GAME_FILES_FOLDER + "\\Players";//"GameFiles\\Players";
	public static final String INFO_FILES_FOLDER = GAME_FILES_FOLDER + "\\Info"; 
	
	private static final String EXCEPTION_ALERT_TITLE = "Error!";
	
	private FileManager()
	{
		
	}
	
	public static String readTextFile(String path)
	{
		try 
		{
			String textToReturn = "";
			
			BufferedReader reader = new BufferedReader(new FileReader(new File(path)));
			
			String line = reader.readLine();
			
			while (line != null)
			{
				textToReturn += line;
				line = reader.readLine();
			}
			
			reader.close();
			
			return textToReturn;
			
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
		}
		
		return null;
	}
	
	public static void writeEncrypted(Object object, String path)
	{
		try 
		{
			Cipher cipher = Cipher.getInstance("AES");
			cipher.init(Cipher.ENCRYPT_MODE, new SecretKeySpec(Base64.getEncoder().encode(ENCRYPTION_KEY.getBytes()), "AES"));

			ObjectOutputStream outputStream = new ObjectOutputStream(new CipherOutputStream(new FileOutputStream(path), cipher));
			outputStream.writeObject(object);
			outputStream.close();
		} 
		catch (IOException | NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException e) 
		{
			displayErrorDialogBox(e.getMessage());
		}
	}
	
	public static void writeEncrypted(String data, String path)
	{
		try 
		{
			File dataFile = new File(path);
			Cipher cipher = Cipher.getInstance("AES");
			cipher.init(Cipher.ENCRYPT_MODE, new SecretKeySpec(Base64.getEncoder().encode(ENCRYPTION_KEY.getBytes()), "AES"));
			CipherOutputStream outputStream = new CipherOutputStream(new FileOutputStream(dataFile), cipher);
			
			// Data encrypted automatically during writing
			outputStream.write(data.getBytes());
			
			outputStream.close();
		} 
		catch (IOException | NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException e) 
		{
			displayErrorDialogBox(e.getMessage());
		}
	}
	
	public static Object decryptAndReadObj(String path)
	{
		Object objectToReturn = null;
		
		try 
		{
			Cipher cipher = Cipher.getInstance("AES");
			cipher.init(Cipher.DECRYPT_MODE, new SecretKeySpec(Base64.getEncoder().encode(ENCRYPTION_KEY.getBytes()), "AES"));
			
			// Data decrypted automatically during reading			
			ObjectInputStream inputStream = new ObjectInputStream(new CipherInputStream(new FileInputStream(path), cipher));
			objectToReturn =  inputStream.readObject();

			inputStream.close();
			
		} 
		catch (InvalidKeyException | NoSuchAlgorithmException | NoSuchPaddingException | IOException | ClassNotFoundException e) 
		{
			displayErrorDialogBox(e.getMessage());
		}
		
		return objectToReturn;
	}
	
	public static String decryptAndRead(String path)
	{
		try 
		{
			File dataFile = new File(path);
			Cipher cipher = Cipher.getInstance("AES");
			cipher.init(Cipher.DECRYPT_MODE, new SecretKeySpec(Base64.getEncoder().encode(ENCRYPTION_KEY.getBytes()), "AES"));
			
			// Data decrypted automatically during reading
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
			displayErrorDialogBox(e.getMessage());
		}
		
		return "";
	}
	
	private static void displayErrorDialogBox(String message)
	{
		Alert exceptionAlert = new Alert(AlertType.INFORMATION);

		exceptionAlert.setTitle(EXCEPTION_ALERT_TITLE);
		exceptionAlert.setHeaderText(message);
		exceptionAlert.showAndWait();
	}
}
