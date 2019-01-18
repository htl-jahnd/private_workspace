package pkgMain;
	
import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.internet.AddressException;

import javafx.application.Application;
import javafx.stage.Stage;
import pkgData.EmailUtil;
import pkgData.GMailer;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.fxml.FXMLLoader;


public class Main extends Application {
	@Override
	public void start(Stage primaryStage) {
		try {
			AnchorPane root = (AnchorPane)FXMLLoader.load(getClass().getResource("ressources/EmailSpammer.fxml"));
			Scene scene = new Scene(root);
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
//		launch(args);
		
		
		final String fromEmail = "email.spam.konto@gmail.com"; //requires valid gmail id
		final String password = "SpamKonto123"; // correct password for gmail id
		final String[] toEmail = new String [1];
		toEmail[0] = "david.jahn2000@gmail.com";
		
		GMailer g = new GMailer(fromEmail, password, toEmail);
		try
		{
			g.sendMail();
		} catch (Exception e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("sent");
		
		
		
	}
}
