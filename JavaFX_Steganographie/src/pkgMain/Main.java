package pkgMain;
	
import java.io.File;
import pkgData.Steganography;

//TODO UI

//public class Main extends Application {
//	@Override
//	public void start(Stage primaryStage) {
//		try {
//			AnchorPane root = (AnchorPane)FXMLLoader.load(getClass().getResource("ressources/GUI_Main.fxml"));
//			Scene scene = new Scene(root);
//			primaryStage.setScene(scene);
//			primaryStage.show();
//		} catch(Exception e) {
//			e.printStackTrace();
//		}
//	}
//	
//	public static void main(String[] args) {
//		launch(args);
//	}
//}

public class Main {
	
	public static void main(String[] args)  {
		try
		{
			File f1=new File(new File("").getAbsolutePath() + "/src/pkgMain/ressources/test_img.jpg");
			
			File f2=new File(new File("").getAbsolutePath() + "/src/pkgMain/ressources/test_img1.jpg");
			
			Steganography s = new Steganography();
			s.encode(f1,f2, "Testakjnkjnsdf");
			System.out.println(s.decode(f2));
		} catch (Exception e)
		{
			e.printStackTrace();
		}
	}
}
