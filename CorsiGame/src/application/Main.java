package application;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.CipherInputStream;
import javax.crypto.CipherOutputStream;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;



public class Main extends Application {
	private static final String ENCRYPTION_KEY = "123456781234";

	@Override
	public void start(Stage primaryStage) {
		try {


			//GameManager manny = new GameManager(new PlayerData(), primaryStage);
			//mainMenu menu = new MainMenu(primaryStage);
			//RegistrationMenu reggie = new RegistrationMenu(primaryStage);

			//PlayerData data = new PlayerData("User", "Pass", new Date(1,1,19), "City", "State", "Zip", "Diagnosis");
			//PlayerData data = new PlayerData();
			//FileManager fm = new FileManager();
			//fm.writeEncrypted(data.sendToString(), System.getProperty("user.dir") + "\\kung.foo");
			//data.readFromString(fm.decryptAndRead(System.getProperty("user.dir") + "\\kung.foo"));
			//ReturningPlayerMenu menu = new ReturningPlayerMenu(primaryStage);


			/*Test with serialization and no encryption
			FileOutputStream os = new FileOutputStream("test.ser");
			ObjectOutputStream objStream = new ObjectOutputStream(os);
			objStream.writeObject(new FeatureTestClass());
			objStream.close();

			ObjectInputStream objInStream = new ObjectInputStream(new FileInputStream("test.ser"));

			FeatureTestClass foo = (FeatureTestClass) objInStream.readObject();
			System.out.println(foo.x);
			System.out.println(foo.y);
			objInStream.close();*/
			
			testEncryptedSerialization();
			testDecryptedSerialization();



		} catch(Exception e) {
			e.printStackTrace();
		}
	}

	public static void testEncryptedSerialization()
	{
		try 
		{
			



			FileOutputStream os = new FileOutputStream("test.ser");

			Cipher cipher = Cipher.getInstance("AES");
			cipher.init(Cipher.ENCRYPT_MODE, new SecretKeySpec(Base64.getEncoder().encode(ENCRYPTION_KEY.getBytes()), "AES"));
			CipherOutputStream outputStream = new CipherOutputStream(os, cipher);

			ObjectOutputStream objStream = new ObjectOutputStream(outputStream);
			objStream.writeObject(new FeatureTestClass());
			objStream.close();

			outputStream.close();
		} 
		catch (IOException | NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException e) 
		{
			e.printStackTrace();
		}

	}
	
	public static void testDecryptedSerialization()
	{
		try 
		{

			
			Cipher cipher = Cipher.getInstance("AES");
			cipher.init(Cipher.DECRYPT_MODE, new SecretKeySpec(Base64.getEncoder().encode(ENCRYPTION_KEY.getBytes()), "AES"));
			
			// Data decrypted automatically during reading
			CipherInputStream inputStream = new CipherInputStream(new FileInputStream("test.ser"), cipher);
			ByteArrayOutputStream byteOutput = new ByteArrayOutputStream();
			
			ObjectInputStream objInStream = new ObjectInputStream(inputStream);
			FeatureTestClass foo = (FeatureTestClass) objInStream.readObject();
			
			System.out.println(foo.x);
			System.out.println(foo.y);


			inputStream.close();
			byteOutput.close();
			
		} 
		catch (InvalidKeyException | NoSuchAlgorithmException | NoSuchPaddingException | IOException | ClassNotFoundException e) 
		{
			e.printStackTrace();
		}
	}

	public static void testEventHandler(Object obj)
	{
		System.out.println("foo");
	}

	public static void main(String[] args) {
		launch(args);
	}
}
