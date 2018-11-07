package Font_Settings_Frame_With_Themes;

import java.awt.Color;
import java.awt.Font;

public class Theme implements Comparable<Theme> {

	private Font font;
	private Color foregroundColor;
	private Color backgroundColor;
	private String name;
	
	
	
	public Theme(String name, Font font, Color foregroundColor, Color backgroundColor) {
		super();
		this.name = name;
		this.font = font;
		this.foregroundColor = foregroundColor;
		this.backgroundColor = backgroundColor;
	}
	
	
	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public Font getFont() {
		return font;
	}
	public void setFont(Font font) {
		this.font = font;
	}
	public Color getForegroundColor() {
		return foregroundColor;
	}
	public void setForegroundColor(Color foregroundColor) {
		this.foregroundColor = foregroundColor;
	}
	public Color getBackgroundColor() {
		return backgroundColor;
	}
	public void setBackgroundColor(Color backgroundColor) {
		this.backgroundColor = backgroundColor;
	}


	@Override
	public int compareTo(Theme o) {
//		int ret = 0;
//		ret=this.font.getFontName().compareTo(o.getFont().getFontName());
//		if(ret == 0) {
//			ret= foregroundColor.getRGB() - o.getForegroundColor().getRGB();
//			if(ret == 0) {
//				ret= backgroundColor.getRGB() - o.getBackgroundColor().getRGB();
//			}
//		}
//		return ret;
		
		return name.compareTo(o.getName());
		
	}


	@Override
	public String toString() {
		return name;
	}
	
	
	
	
}
