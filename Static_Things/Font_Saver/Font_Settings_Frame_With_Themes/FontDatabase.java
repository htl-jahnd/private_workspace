package Font_Settings_Frame_With_Themes;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.TreeSet;

import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.plaf.ColorUIResource;
import javax.swing.plaf.FontUIResource;



public class FontDatabase {

	private static FontDatabase instance;
	private Font font=new Font("AppleGothic",0,12);
	private Color foregroundColor=Color.BLACK;
	private Color backgroundColor = Color.LIGHT_GRAY;
	private String fontFilePath="";
	private TreeSet<Theme> themes;
	
	public static FontDatabase newInstance() {
		if(instance==null) {
			instance= new FontDatabase();
		}
		return instance;
	}
	
	private FontDatabase() {
	}

	
	
	
	public TreeSet<Theme> getThemes() {
		return themes;
	}

	public void setThemes(TreeSet<Theme> themes) {
		this.themes = themes;
	}
	
	public void doAddTheme(Theme t) throws ThemeException {
		if(!themes.contains(t))
			themes.add(t);
		else throw new ThemeException("Theme already exists");
	}

	public void removeTheme(Theme t) throws ThemeException {
		if(themes.contains(t))
			themes.remove(t);
		else throw new ThemeException("Theme does not exist");
	}
	
	public void initThemes() {
		themes= new TreeSet<Theme>();
		Theme t = new Theme("Start up",font, foregroundColor, backgroundColor);
		themes.add(t);
		t = new Theme("Standard", new Font("AppleGothic",Font.PLAIN,12), Color.BLACK, Color.WHITE);
		themes.add(t);
		t = new Theme("Dark", new Font("AppleGothic",Font.PLAIN,12), Color.WHITE, Color.BLACK);
		themes.add(t);
	}
	
	public void doSetTheme(Theme t) {
		font=t.getFont();
		foregroundColor=t.getForegroundColor();
		backgroundColor=t.getBackgroundColor();
	}
	
	public void doSetFontToContainer(Component comp)  {
        final FontUIResource res = new FontUIResource(font);
        UIManager.getLookAndFeelDefaults().put("Button.font", res);
        UIManager.getLookAndFeelDefaults().put("TextField.font", res);
        UIManager.getLookAndFeelDefaults().put("Label.font", res);
        UIManager.getLookAndFeelDefaults().put("CheckBox.font", res);
        UIManager.getLookAndFeelDefaults().put("ComboBox.font", res);
        UIManager.getLookAndFeelDefaults().put("RadioButton.font", res);
        UIManager.getLookAndFeelDefaults().put("MenuItem.font", res);
        UIManager.getLookAndFeelDefaults().put("Menu.font", res);
        UIManager.getLookAndFeelDefaults().put("List.font", res);
        SwingUtilities.updateComponentTreeUI(comp);
        
	}
	
	public void doSetForegroundColorToContainer(Component comp)  {
        final ColorUIResource res = new ColorUIResource(foregroundColor);
        UIManager.getLookAndFeelDefaults().put("Button.foreground", res);
        UIManager.getLookAndFeelDefaults().put("TextField.foreground", res);
        UIManager.getLookAndFeelDefaults().put("Label.foreground", res);
        UIManager.getLookAndFeelDefaults().put("CheckBox.foreground", res);
        UIManager.getLookAndFeelDefaults().put("ComboBox.foreground", res);
        UIManager.getLookAndFeelDefaults().put("RadioButton.foreground", res);
        UIManager.getLookAndFeelDefaults().put("MenuItem.foreground", res);
        UIManager.getLookAndFeelDefaults().put("Menu.foreground", res);
        UIManager.getLookAndFeelDefaults().put("List.foreground", res);
        SwingUtilities.updateComponentTreeUI(comp);
	}

	//BUTTONS AND MENU BAR ITEMS DONT WORK
	public void doSetBackgroundColorToContainer(Component comp)  {
        ColorUIResource res = new ColorUIResource(backgroundColor);
        UIManager.getLookAndFeelDefaults().put("TextField.background", res);
        UIManager.getLookAndFeelDefaults().put("Label.background", res);
        UIManager.getLookAndFeelDefaults().put("CheckBox.background", res);
        UIManager.getLookAndFeelDefaults().put("ComboBox.background", res);
        UIManager.getLookAndFeelDefaults().put("RadioButton.background", res);
        UIManager.getLookAndFeelDefaults().put("MenuItem.background", res);
        UIManager.getLookAndFeelDefaults().put("List.background", res);
        UIManager.getLookAndFeelDefaults().put("Panel.background", res);
        //SET BACKGROUND IMAGE TO BUTTON
//        UIManager.getLookAndFeelDefaults().put("Button.opaque", false);
        UIManager.put("Button.background", res);     
//        UIManager.getLookAndFeelDefaults().put("MenuBar.opaque", true);
//        UIManager.getLookAndFeelDefaults().put("MenuBar.background", res);
//        UIManager.getLookAndFeelDefaults().put("Menu.opaque", true);
//        UIManager.getLookAndFeelDefaults().put("Menu.background", res);
        SwingUtilities.updateComponentTreeUI(comp);
	}
	
	
	public void doWriteFontToFile() throws IOException, ClassNotFoundException {
 		FileOutputStream fs = new FileOutputStream(fontFilePath);
		ObjectOutputStream os = new ObjectOutputStream(fs);
		os.writeObject(font);
		os.writeObject(foregroundColor);
		os.writeObject(backgroundColor);
		os.flush();
 		os.close();
 		fs.close();
	}
	
	public void doReadFontFromFile() throws IOException, ClassNotFoundException {
		FileInputStream fs = new FileInputStream(fontFilePath);
		ObjectInputStream os = new ObjectInputStream(fs);
		font=(Font)os.readObject();
		foregroundColor=(Color)os.readObject();
		backgroundColor=(Color)os.readObject();
 		os.close();
 		fs.close();
	}
	
	public Font getFont() {
		return font;
	}

	public void setFont(Font font) {
		this.font = font;
	}
	

	public Color getBackgroundColor() {
		return backgroundColor;
	}

	public void setBackgroundColor(Color backgroundColor) {
		this.backgroundColor = backgroundColor;
	}

	public Color getForegroundColor() {
		return foregroundColor;
	}

	public void setForegroundColor(Color fontColor) {
		this.foregroundColor = fontColor;
	}
	
}
