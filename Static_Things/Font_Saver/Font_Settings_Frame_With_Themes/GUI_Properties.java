package Font_Settings_Frame_With_Themes;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.FileDialog;
import java.awt.Font;
import java.awt.Frame;
import java.awt.Graphics2D;
import java.awt.GraphicsEnvironment;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.GridLayout;
import java.awt.GridBagLayout;

import javax.swing.ButtonGroup;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JScrollPane;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.ListCellRenderer;
import javax.swing.JComboBox;
import javax.swing.JSlider;
import javax.swing.JRadioButton;
import javax.swing.ScrollPaneConstants;
import javax.swing.UIManager;
import javax.swing.JCheckBox;
import javax.swing.JColorChooser;

import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Hashtable;
import javax.swing.SwingConstants;
import javax.swing.Icon;

public class GUI_Properties extends JFrame implements ActionListener {
	
	private FontDatabase db;
	private static GUI_Properties instance =null;
	private Color currentForegroundColor;
	private Color currentBackgroundColor;
	private ImageIcon[] icons;
	private String[] colorNames;
	private Integer[] arrayComboBox={1,2,3,4,5,6,7,8,9,10,11,12};
	
	
	
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JScrollPane scrollPaneProperties;
	private JPanel panelProperties;
	private JLabel lblFont;
	private JComboBox<String> comboBoxFont;
	private JLabel lblFontSize;
	private JSlider sliderFontSize;
	private JScrollPane scrollPaneButtons;
	private JPanel panelButtons;
	private JButton btnCancel;
	private JButton btnApplyAndClose;
	private JLabel lblFontStyle;
	private JPanel panelFontStyles;
	private JCheckBox chckbxBold;
	private JCheckBox chckbxItalic;
	private JButton btnApply;
	private JLabel lblFontColor;
	private JLabel lblBackgroundColor;
	private JComboBox<String> comboBoxForegroundColor;
	private JComboBox<?> comboBoxBackgroundColor;
	private JButton btnForegroundColorPicker;
	private JButton btnBackgroundColorPicker;
	private JLabel lblTheme;
	private JComboBox<Theme> comboBoxThemes;
	private JButton btnTableThemes;
	private Font currentFont;
	private DefaultComboBoxModel<Theme> modThemes = new DefaultComboBoxModel<Theme>();
	



	private GUI_Properties() {
		setBounds(100, 100, 600, 472);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));
		contentPane.add(getScrollPaneProperties(), BorderLayout.CENTER);
		contentPane.add(getScrollPaneButtons(), BorderLayout.SOUTH);
		db=FontDatabase.newInstance();
		
		createColors();
		doFillFields();
	}
	
	public static GUI_Properties newInstance() {
		if(instance == null) {
			instance =new GUI_Properties();
		}
		return instance;
	}
	
	/********************************
	 * 			GUI THINGS			*
	 * 			  @return			*
	 ********************************/
	
	
	private JScrollPane getScrollPaneProperties() {
		if (scrollPaneProperties == null) {
			scrollPaneProperties = new JScrollPane();
			scrollPaneProperties.setBorder(null);
			scrollPaneProperties.setViewportView(getPanelProperties());
		}
		return scrollPaneProperties;
	}
	private JPanel getPanelProperties() {
		if (panelProperties == null) {
			panelProperties = new JPanel();
			panelProperties.setBorder(null);
			GridBagLayout gbl_panelProperties = new GridBagLayout();
			gbl_panelProperties.columnWidths = new int[]{170, 250, 45}; 
			gbl_panelProperties.rowHeights = new int[]{36, 30, 36, 30,36, 30,36, 30, 36, 30, 36};
			gbl_panelProperties.columnWeights = new double[]{0.0, 0.0, 0.0};
			gbl_panelProperties.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0};
			panelProperties.setLayout(gbl_panelProperties);
			GridBagConstraints gbc_lblTheme = new GridBagConstraints();
			gbc_lblTheme.anchor = GridBagConstraints.WEST;
			gbc_lblTheme.insets = new Insets(0, 0, 5, 5);
			gbc_lblTheme.gridx = 0;
			gbc_lblTheme.gridy = 0;
			panelProperties.add(getLblTheme(), gbc_lblTheme);
			GridBagConstraints gbc_comboBoxThemes = new GridBagConstraints();
			gbc_comboBoxThemes.insets = new Insets(0, 0, 5, 5);
			gbc_comboBoxThemes.fill = GridBagConstraints.HORIZONTAL;
			gbc_comboBoxThemes.gridx = 1;
			gbc_comboBoxThemes.gridy = 0;
			panelProperties.add(getComboBoxThemes(), gbc_comboBoxThemes);
			GridBagConstraints gbc_btnTableThemes = new GridBagConstraints();
			gbc_btnTableThemes.insets = new Insets(0, 0, 5, 0);
			gbc_btnTableThemes.gridx = 2;
			gbc_btnTableThemes.gridy = 0;
			panelProperties.add(getBtnTableThemes(), gbc_btnTableThemes);
			GridBagConstraints gbc_lblFont = new GridBagConstraints();
			gbc_lblFont.anchor = GridBagConstraints.WEST;
			gbc_lblFont.fill = GridBagConstraints.VERTICAL;
			gbc_lblFont.insets = new Insets(0, 0, 5, 5);
			gbc_lblFont.gridx = 0;
			gbc_lblFont.gridy = 2;
			panelProperties.add(getLblFont(), gbc_lblFont);
			GridBagConstraints gbc_comboBoxFont = new GridBagConstraints();
			gbc_comboBoxFont.fill = GridBagConstraints.BOTH;
			gbc_comboBoxFont.insets = new Insets(0, 0, 5, 5);
			gbc_comboBoxFont.gridx = 1;
			gbc_comboBoxFont.gridy = 2;
			panelProperties.add(getComboBoxFont(), gbc_comboBoxFont);
			GridBagConstraints gbc_lblFontSize = new GridBagConstraints();
			gbc_lblFontSize.anchor = GridBagConstraints.WEST;
			gbc_lblFontSize.fill = GridBagConstraints.VERTICAL;
			gbc_lblFontSize.insets = new Insets(0, 0, 5, 5);
			gbc_lblFontSize.gridx = 0;
			gbc_lblFontSize.gridy = 4;
			panelProperties.add(getLblFontSize(), gbc_lblFontSize);
			GridBagConstraints gbc_sliderFontSize = new GridBagConstraints();
			gbc_sliderFontSize.fill = GridBagConstraints.BOTH;
			gbc_sliderFontSize.insets = new Insets(0, 0, 5, 5);
			gbc_sliderFontSize.gridx = 1;
			gbc_sliderFontSize.gridy = 4;
			panelProperties.add(getSliderFontSize(), gbc_sliderFontSize);
			GridBagConstraints gbc_lblFontStyle = new GridBagConstraints();
			gbc_lblFontStyle.anchor = GridBagConstraints.WEST;
			gbc_lblFontStyle.fill = GridBagConstraints.VERTICAL;
			gbc_lblFontStyle.insets = new Insets(0, 0, 5, 5);
			gbc_lblFontStyle.gridx = 0;
			gbc_lblFontStyle.gridy = 6;
			panelProperties.add(getLblFontStyle(), gbc_lblFontStyle);
			GridBagConstraints gbc_panelFontStyles = new GridBagConstraints();
			gbc_panelFontStyles.fill = GridBagConstraints.BOTH;
			gbc_panelFontStyles.insets = new Insets(0, 0, 5, 5);
			gbc_panelFontStyles.gridx = 1;
			gbc_panelFontStyles.gridy = 6;
			panelProperties.add(getPanelFontStyles(), gbc_panelFontStyles);
			GridBagConstraints gbc_lblFontColor = new GridBagConstraints();
			gbc_lblFontColor.anchor = GridBagConstraints.WEST;
			gbc_lblFontColor.insets = new Insets(0, 0, 5, 5);
			gbc_lblFontColor.gridx = 0;
			gbc_lblFontColor.gridy = 8;
			panelProperties.add(getLblFontColor(), gbc_lblFontColor);
			GridBagConstraints gbc_comboBoxFontColor = new GridBagConstraints();
			gbc_comboBoxFontColor.fill = GridBagConstraints.HORIZONTAL;
			gbc_comboBoxFontColor.insets = new Insets(0, 0, 5, 5);
			gbc_comboBoxFontColor.gridx = 1;
			gbc_comboBoxFontColor.gridy = 8;
			panelProperties.add(getComboBoxForegroundColor(), gbc_comboBoxFontColor);
			GridBagConstraints gbc_btnFontColorPicker = new GridBagConstraints();
			gbc_btnFontColorPicker.insets = new Insets(0, 0, 5, 0);
			gbc_btnFontColorPicker.gridx = 2;
			gbc_btnFontColorPicker.gridy = 8;
			panelProperties.add(getBtnForegroundColorPicker(), gbc_btnFontColorPicker);
			GridBagConstraints gbc_lblBackgroundColor = new GridBagConstraints();
			gbc_lblBackgroundColor.anchor = GridBagConstraints.WEST;
			gbc_lblBackgroundColor.insets = new Insets(0, 0, 5, 5);
			gbc_lblBackgroundColor.gridx = 0;
			gbc_lblBackgroundColor.gridy = 10;
			panelProperties.add(getLblBackgroundColor(), gbc_lblBackgroundColor);
			GridBagConstraints gbc_comboBoxBackgroundColor = new GridBagConstraints();
			gbc_comboBoxBackgroundColor.fill = GridBagConstraints.HORIZONTAL;
			gbc_comboBoxBackgroundColor.insets = new Insets(0, 0, 5, 5);
			gbc_comboBoxBackgroundColor.gridx = 1;
			gbc_comboBoxBackgroundColor.gridy = 10;
			panelProperties.add(getComboBoxBackgroundColor(), gbc_comboBoxBackgroundColor);
			GridBagConstraints gbc_btnBackgroundColorPicker = new GridBagConstraints();
			gbc_btnBackgroundColorPicker.insets = new Insets(0, 0, 5, 0);
			gbc_btnBackgroundColorPicker.gridx = 2;
			gbc_btnBackgroundColorPicker.gridy = 10;
			panelProperties.add(getBtnBackgroundColorPicker(), gbc_btnBackgroundColorPicker);
		}
		return panelProperties;
	}
	
	private JLabel getLblFont() {
		if (lblFont == null) {
			lblFont = new JLabel("Font:");
		}
		return lblFont;
	}
	private JComboBox<String> getComboBoxFont() {
		if (comboBoxFont == null) {
			GraphicsEnvironment graphEnviron = GraphicsEnvironment.getLocalGraphicsEnvironment();
			Font[] allFonts = graphEnviron.getAllFonts();
			comboBoxFont = new JComboBox<String>();
			for(Font f : allFonts) {
				comboBoxFont.addItem(f.getName());
			}		
			comboBoxFont.addActionListener(this);
		}
		return comboBoxFont;
	}
	private JLabel getLblFontSize() {
		if (lblFontSize == null) {
			lblFontSize = new JLabel("Font Size:");
		}
		return lblFontSize;
	}
	private JSlider getSliderFontSize() {
		if (sliderFontSize == null) {
			final int MAX = 30;
			final int MIN = 10;
			sliderFontSize = new JSlider(JSlider.HORIZONTAL,10,30, 15);
			sliderFontSize.setMajorTickSpacing(10);
			sliderFontSize.setMinorTickSpacing(2);
			sliderFontSize.setSnapToTicks(true);
			sliderFontSize.setMaximum(MAX);
			sliderFontSize.setMinimum(MIN);
			sliderFontSize.setPaintTicks(true);
			sliderFontSize.setPaintLabels(true);
			Hashtable<Integer, JLabel> labelTable = new Hashtable<Integer, JLabel>();
			for(int i =MIN; i<=MAX; i+=2) {
				labelTable.put(new Integer(i), new JLabel(Integer.toString(i)));
			}
			sliderFontSize.setLabelTable(labelTable);			
		}
		return sliderFontSize;
	}
	private JScrollPane getScrollPaneButtons() {
		if (scrollPaneButtons == null) {
			int w=this.getWidth();
			scrollPaneButtons = new JScrollPane(){
				private static final long serialVersionUID = 1L;
				@Override
			    public Dimension getPreferredSize() {
			        return new Dimension(w-50, 50);
			    }
			};
			scrollPaneButtons.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
			scrollPaneButtons.setViewportBorder(null);
			scrollPaneButtons.setBorder(null);
			scrollPaneButtons.setViewportView(getPanelButtons());
		}
		return scrollPaneButtons;
	}
	private JPanel getPanelButtons() {
		if (panelButtons == null) {
			panelButtons = new JPanel();
			panelButtons.setBorder(null);
			GridBagLayout gbl_panelButtons = new GridBagLayout();
			gbl_panelButtons.columnWidths = new int[]{100, 100,123,5, 232};
			gbl_panelButtons.rowHeights = new int[]{0};
			gbl_panelButtons.columnWeights = new double[]{0.0, 0.0, 0.0, Double.MIN_VALUE};
			gbl_panelButtons.rowWeights = new double[]{0.0};
			panelButtons.setLayout(gbl_panelButtons);
			GridBagConstraints gbc_btnCancel = new GridBagConstraints();
			gbc_btnCancel.fill = GridBagConstraints.BOTH;
			gbc_btnCancel.insets = new Insets(0, 0, 0, 5);
			gbc_btnCancel.gridx = 0;
			gbc_btnCancel.gridy = 0;
			panelButtons.add(getBtnCancel(), gbc_btnCancel);
			GridBagConstraints gbc_btnApply = new GridBagConstraints();
			gbc_btnApply.insets = new Insets(0, 0, 0, 5);
			gbc_btnApply.gridx = 2;
			gbc_btnApply.gridy = 0;
			panelButtons.add(getBtnApply(), gbc_btnApply);
			GridBagConstraints gbc_btnApplyAndClose = new GridBagConstraints();
			gbc_btnApplyAndClose.fill = GridBagConstraints.BOTH;
			gbc_btnApplyAndClose.gridx = 4;
			gbc_btnApplyAndClose.gridy = 0;
			panelButtons.add(getBtnApplyAndClose(), gbc_btnApplyAndClose);
		}
		return panelButtons;
	}
	private JButton getBtnCancel() {
		if (btnCancel == null) {
			btnCancel = new JButton("Cancel");
			btnCancel.setPreferredSize(new Dimension(50,30));
			btnCancel.addActionListener(this);
		}
		return btnCancel;
	}
	private JButton getBtnApplyAndClose() {
		if (btnApplyAndClose == null) {
			btnApplyAndClose = new JButton("Apply and Close");
			btnApplyAndClose.setPreferredSize(new Dimension(50,20));
			btnApplyAndClose.addActionListener(this);
		}
		return btnApplyAndClose;
	}
	private JLabel getLblFontStyle() {
		if (lblFontStyle == null) {
			lblFontStyle = new JLabel("Font Style:");
		}
		return lblFontStyle;
	}
	private JPanel getPanelFontStyles() {
		if (panelFontStyles == null) {
			panelFontStyles = new JPanel();
			panelFontStyles.setLayout(new GridLayout(1, 2, 0, 0));
			panelFontStyles.add(getChckbxBold());
			panelFontStyles.add(getChckbxItalic());
		}
		return panelFontStyles;
	}
	private JCheckBox getChckbxBold() {
		if (chckbxBold == null) {
			chckbxBold = new JCheckBox("Bold");
		}
		return chckbxBold;
	}
	private JCheckBox getChckbxItalic() {
		if (chckbxItalic == null) {
			chckbxItalic = new JCheckBox("Italic");
		}
		return chckbxItalic;
	}
	
	private JButton getBtnForegroundColorPicker() {
		if (btnForegroundColorPicker == null) {
			btnForegroundColorPicker = new JButton(UIManager.getIcon("Tree.collapsedIcon"));
			btnForegroundColorPicker.addActionListener(this);
		}
		return btnForegroundColorPicker;
	}
	private JButton getBtnBackgroundColorPicker() {
		if (btnBackgroundColorPicker == null) {
			btnBackgroundColorPicker = new JButton(UIManager.getIcon("Tree.collapsedIcon"));
			btnBackgroundColorPicker.addActionListener(this);
		}
		return btnBackgroundColorPicker;
	}
	
	private JButton getBtnApply() {
		if (btnApply == null) {
			btnApply = new JButton("Apply");
			btnApply.addActionListener(this);
		}
		return btnApply;
	}
	private JLabel getLblFontColor() {
		if (lblFontColor == null) {
			lblFontColor = new JLabel("Font Color:");
		}
		return lblFontColor;
	}
	private JLabel getLblBackgroundColor() {
		if (lblBackgroundColor == null) {
			lblBackgroundColor = new JLabel("Background Color:");
		}
		return lblBackgroundColor;
	}
	private JComboBox<String> getComboBoxForegroundColor() {
		if (comboBoxForegroundColor == null) {
			ComboBoxRenderar rendrar = new ComboBoxRenderar();

			comboBoxForegroundColor = new JComboBox(arrayComboBox);
			comboBoxForegroundColor.setRenderer(rendrar);
			comboBoxForegroundColor.addActionListener(this);
		}
		return comboBoxForegroundColor;
	}
	private JComboBox<?> getComboBoxBackgroundColor() {
		if (comboBoxBackgroundColor == null) {
			ComboBoxRenderar rendrar = new ComboBoxRenderar();
			comboBoxBackgroundColor = new JComboBox(arrayComboBox);
			comboBoxBackgroundColor.setRenderer(rendrar);
			comboBoxBackgroundColor.addActionListener(this);
		}
		return comboBoxBackgroundColor;
	}
	
	private JLabel getLblTheme() {
		if (lblTheme == null) {
			lblTheme = new JLabel("Theme:");
		}
		return lblTheme;
	}
	private JComboBox<Theme> getComboBoxThemes() {
		if (comboBoxThemes == null) {
			comboBoxThemes = new JComboBox<Theme>();
			comboBoxThemes.setModel(modThemes);
			comboBoxThemes.addActionListener(this);
		}
		return comboBoxThemes;
	}
	private JButton getBtnTableThemes() {
		if (btnTableThemes == null) {
			btnTableThemes = new JButton(UIManager.getIcon("Tree.collapsedIcon"));
		}
		return btnTableThemes;
	}
	
	

	/****************************
	 * 		NON-GUI THINGS		*
	 ****************************/
	
	
	@Override
	public void actionPerformed(ActionEvent e) {
		try {
			 if (e.getSource() == btnApplyAndClose) {
				doApply();
				this.dispose();
			}
			else if(e.getSource() == btnCancel) {
				this.dispose();
			}
			else if(e.getSource() == btnApply) {
				 
				doApply();
			}
			
			//Color and theme things
			else if(e.getSource() == btnForegroundColorPicker) {
				Color c = JColorChooser.showDialog(null, "Choose a Color", currentForegroundColor);
			      if (c != null) {
			    	  currentForegroundColor=c;
			    	  doSetComboBoxForegroundColor();
			    	  doSetCurrentTheme();
			      }
			}
			else if(e.getSource() == btnBackgroundColorPicker) {
				Color c = JColorChooser.showDialog(null, "Choose a Color", currentBackgroundColor);
			      if (c != null) {
			    	  currentBackgroundColor=c;
			    	  doSetComboBoxBackgroundColor();
			    	  doSetCurrentTheme();
			      }
			}
			else if(e.getSource() == comboBoxForegroundColor) {
				if(comboBoxForegroundColor.getSelectedIndex() < 11) {
					currentForegroundColor=doGetColorFromComboBox(comboBoxForegroundColor);
					doSetCurrentTheme();
				}
				
			}
			else if(e.getSource() == comboBoxBackgroundColor) {
				if(comboBoxBackgroundColor.getSelectedIndex()<11) {
					currentBackgroundColor=doGetColorFromComboBox(comboBoxBackgroundColor);
					doSetCurrentTheme();
				}
				
			}
			else if(e.getSource() == comboBoxThemes) {
				Theme t =(Theme)comboBoxThemes.getSelectedItem();
				currentForegroundColor = t.getForegroundColor();
				currentBackgroundColor = t.getBackgroundColor();
				doSetComboBoxBackgroundColor();
				doSetComboBoxForegroundColor();
				comboBoxFont.setSelectedItem(t.getFont().getName());
				chckbxBold.setSelected(t.getFont().isBold());
				chckbxItalic.setSelected(t.getFont().isItalic());
				sliderFontSize.setValue(t.getFont().getSize());
			}
			else if(e.getSource() == comboBoxFont) {
				int style=0;
				if(chckbxBold.isSelected() && chckbxItalic.isSelected()) {
					style = Font.BOLD+Font.ITALIC;
				}
				else if(chckbxBold.isSelected()) {
					style=Font.BOLD;
				}
				else if(chckbxItalic.isSelected()) {
					style=Font.ITALIC;
				}
				else if(!chckbxItalic.isSelected() && !chckbxBold.isSelected()) {
					style = Font.PLAIN;
				}
				
				currentFont=new Font((String) comboBoxFont.getSelectedItem(),style, sliderFontSize.getValue());
				doSetCurrentTheme();
			}
			
		}
		//handle all other exceptions
		catch(Exception ex) {
			JOptionPane.showMessageDialog(null, ex.getMessage(), "ERROR", JOptionPane.ERROR_MESSAGE);
		}
		
		
	}
	
	///////FIXME OESNT WORK
	private void doSetCurrentTheme() {
		Theme tmp = new Theme("temp", currentFont, currentForegroundColor, currentBackgroundColor);
		for(Theme t :db.getThemes() ) {
			if(currentBackgroundColor == t.getBackgroundColor() && currentFont == t.getFont() && currentForegroundColor == t.getForegroundColor()) {
				System.out.println("701");
				comboBoxThemes.setSelectedItem(t);
			}
		}
	}

	private void doSetComboBoxBackgroundColor() {
		int idx = getIndexOfColor(currentBackgroundColor);
		if(idx== -1) {
			comboBoxBackgroundColor.setSelectedIndex(11);
		}
		else comboBoxBackgroundColor.setSelectedIndex(idx);
		
	}
	
	private void doSetComboBoxForegroundColor() {
		int idx = getIndexOfColor(currentForegroundColor);
		if(idx== -1) {
			comboBoxForegroundColor.setSelectedIndex(11);
		}
		else comboBoxForegroundColor.setSelectedIndex(idx);
		
	}

	private void doApply() throws Exception {
		doSetColorsInDatabase();
		doSetFontInDatabase();
		db.doWriteFontToFile();
		doSetUIAttributesToFrames();
	}
		
	//sets the saved UI attributes (font, foreground - and background color) to all frames
	private void doSetUIAttributesToFrames() {
		//set attributes to current frame
		db.doSetForegroundColorToContainer(contentPane);
		db.doSetBackgroundColorToContainer(contentPane);
		db.doSetFontToContainer(contentPane);
		
		//set attributes to main gui
//		db.doSetFontToContainer(GUI_Main.newInstance().getContentPane());
//		db.doSetBackgroundColorToContainer(GUI_Main.newInstance().getContentPane());
//		db.doSetForegroundColorToContainer(GUI_Main.newInstance().getContentPane());
//		
//		//check if history gui is visible, if yes, set attributes
//		if(GUI_History.getInstance()!= null) {
//			db.doSetBackgroundColorToContainer(GUI_History.newInstance().getContentPane());
//			db.doSetForegroundColorToContainer(GUI_History.newInstance().getContentPane());
//			db.doSetFontToContainer(GUI_History.newInstance().getContentPane());
//		}
		
	}

	
	//saves the choosen color in the database
	private void doSetColorsInDatabase() {
		db.setForegroundColor(currentForegroundColor);
		db.setBackgroundColor(currentBackgroundColor);		
	}
	
	private void doSetFontInDatabase() throws IOException, ClassNotFoundException {
		int style=0;
		if(chckbxBold.isSelected() && chckbxItalic.isSelected()) {
			style = Font.BOLD+Font.ITALIC;
		}
		else if(chckbxBold.isSelected()) {
			style=Font.BOLD;
		}
		else if(chckbxItalic.isSelected()) {
			style=Font.ITALIC;
		}
		else if(!chckbxItalic.isSelected() && !chckbxBold.isSelected()) {
			style = Font.PLAIN;
		}
		int size = sliderFontSize.getValue();
		final Font fnt = new Font((String) comboBoxFont.getSelectedItem(), style, size);
	
		db.setFont(fnt);
		
	}
	
	private Color doGetColorFromComboBox(JComboBox box) {
		int c =box.getSelectedIndex();
		switch(c) {
		case 0:
			return Color.BLACK;
		case 1:
			return Color.WHITE;
		case 2:
			return Color.RED;
		case 3: 
			return Color.BLUE;
		case 4: 
			return Color.DARK_GRAY;
		case 5: 
			return Color.GREEN;
		case 6:
			return Color.MAGENTA;
		case 7: 
			return Color.LIGHT_GRAY;
		case 8:
			return Color.ORANGE;
		case 9:
			return Color.PINK;
		case 10:
			return Color.YELLOW;
		}
		return null;

	}
	
	private int getIndexOfColor(Color c) {
		if(c.getRGB()== Color.BLACK.getRGB()) {
			return 0;
		}
		else if(c.getRGB()== Color.WHITE.getRGB()) {
			return 1;
		}
		else if(c.getRGB()== Color.RED.getRGB()) {
			return 2;
		}
		else if(c.getRGB()== Color.BLUE.getRGB()) {
			return 3;
		}
		else if(c.getRGB()== Color.DARK_GRAY.getRGB()) {
			return 4;
		}
		else if(c.getRGB()== Color.GREEN.getRGB()) {
			return 5;
		}
		else if(c.getRGB()== Color.MAGENTA.getRGB()) {
			return 6;
		}
		else if(c.getRGB()== Color.LIGHT_GRAY.getRGB()) {
			return 7;
		}
		else if(c.getRGB()== Color.ORANGE.getRGB()) {
			return 8;
		}
		else if(c.getRGB()== Color.PINK.getRGB()) {
			return 9;
		}
		else if(c.getRGB()== Color.YELLOW.getRGB()) {
			return 10;
		}
		return -1;
	}


	
	private void doFillFields() {
		currentFont= db.getFont();
		if(currentFont.isBold()) {
			chckbxBold.setSelected(true);
		}
		if(currentFont.isItalic()) {
			chckbxItalic.setSelected(true);
		}
		comboBoxFont.setSelectedItem(currentFont.getName());
		sliderFontSize.setValue(currentFont.getSize());
		
		//Set foreground color
		currentForegroundColor= db.getForegroundColor();
		int c = getIndexOfColor(currentForegroundColor);
		if(c!=-1) {
			comboBoxForegroundColor.setSelectedIndex(c);
		}
		else comboBoxForegroundColor.setSelectedIndex(0);
		
		//set background color
		currentBackgroundColor =db.getBackgroundColor();
		 c = getIndexOfColor(currentBackgroundColor);
		if(c!=-1) {
			comboBoxBackgroundColor.setSelectedIndex(c);
		}
		else comboBoxBackgroundColor.setSelectedIndex(1);
		
		//fill themes model
		doRefreshModelThemes();
	}
		
	
	
	
	private void doRefreshModelThemes() {
		modThemes.removeAllElements();
		for(Theme t : db.getThemes()) {
			modThemes.addElement(t);
		}
	}

	private void createColors() {
		icons = new ImageIcon[12];
		colorNames = new String[12];
		int width=50;
		int height=50;

		//BLACK
		BufferedImage bImg = new BufferedImage(width,height, BufferedImage.TYPE_INT_RGB);
		Graphics2D graphics = bImg.createGraphics();
		graphics.setPaint(Color.BLACK);
		graphics.fillRect(0, 0, bImg.getWidth(), bImg.getHeight());
		icons[0] = new ImageIcon(bImg);
		colorNames[0] ="Black";
		//WHITE
		bImg = new BufferedImage(width,height, BufferedImage.TYPE_INT_RGB);
		graphics = bImg.createGraphics();
		graphics.setPaint(Color.WHITE);
		graphics.fillRect(0, 0, bImg.getWidth(), bImg.getHeight());
		icons[1] = new ImageIcon(bImg);
		colorNames[1] ="White";
		//RED
		bImg = new BufferedImage(width,height, BufferedImage.TYPE_INT_RGB);
		graphics = bImg.createGraphics();
		graphics.setPaint(Color.RED);
		graphics.fillRect(0, 0, bImg.getWidth(), bImg.getHeight());
		icons[2] = new ImageIcon(bImg);
		colorNames[2] ="Red";
		//BLUE
		bImg = new BufferedImage(width,height, BufferedImage.TYPE_INT_RGB);
		graphics = bImg.createGraphics();
		graphics.setPaint(Color.BLUE);
		graphics.fillRect(0, 0, bImg.getWidth(), bImg.getHeight());
		icons[3] = new ImageIcon(bImg);
		colorNames[3] ="Blue";
		//DARK_GRAY
		bImg = new BufferedImage(width,height, BufferedImage.TYPE_INT_RGB);
		graphics = bImg.createGraphics();
		graphics.setPaint(Color.DARK_GRAY);
		graphics.fillRect(0, 0, bImg.getWidth(), bImg.getHeight());
		icons[4] = new ImageIcon(bImg);
		colorNames[4] ="Dark Gray";
		//GREEN
		bImg = new BufferedImage(width,height, BufferedImage.TYPE_INT_RGB);
		graphics = bImg.createGraphics();
		graphics.setPaint(Color.GREEN);
		graphics.fillRect(0, 0, bImg.getWidth(), bImg.getHeight());
		icons[5] = new ImageIcon(bImg);
		colorNames[5] ="Green";
		//MAGENTA
		bImg = new BufferedImage(width,height, BufferedImage.TYPE_INT_RGB);
		graphics = bImg.createGraphics();
		graphics.setPaint(Color.MAGENTA);
		graphics.fillRect(0, 0, bImg.getWidth(), bImg.getHeight());
		icons[6] = new ImageIcon(bImg);
		colorNames[6] ="Magenta";
		//LIGHT_GRAY
		bImg = new BufferedImage(width,height, BufferedImage.TYPE_INT_RGB);
		graphics = bImg.createGraphics();
		graphics.setPaint(Color.LIGHT_GRAY);
		graphics.fillRect(0, 0, bImg.getWidth(), bImg.getHeight());
		icons[7] = new ImageIcon(bImg);
		colorNames[7] ="Light Gray";
		//ORANGE
		bImg = new BufferedImage(width,height, BufferedImage.TYPE_INT_RGB);
		graphics = bImg.createGraphics();
		graphics.setPaint(Color.ORANGE);
		graphics.fillRect(0, 0, bImg.getWidth(), bImg.getHeight());
		icons[8] = new ImageIcon(bImg);
		colorNames[8] ="Orange";
		//PINK
		bImg = new BufferedImage(width,height, BufferedImage.TYPE_INT_RGB);
		graphics = bImg.createGraphics();
		graphics.setPaint(Color.PINK);
		graphics.fillRect(0, 0, bImg.getWidth(), bImg.getHeight());
		icons[9] = new ImageIcon(bImg);
		colorNames[9] ="Pink";
		//YELLOW
		bImg = new BufferedImage(width,height, BufferedImage.TYPE_INT_RGB);
		graphics = bImg.createGraphics();
		graphics.setPaint(Color.YELLOW);
		graphics.fillRect(0, 0, bImg.getWidth(), bImg.getHeight());
		icons[10] = new ImageIcon(bImg);
		colorNames[10] ="Yellow";
		
		//OTHER
		bImg = new BufferedImage(width,height, BufferedImage.TYPE_INT_RGB);
		graphics = bImg.createGraphics();
		graphics.fillRect(0, 0, bImg.getWidth(), bImg.getHeight());
		icons[11] = new ImageIcon(bImg);
		colorNames[11] ="Other";
	}
	

	
	public class ComboBoxRenderar extends JLabel implements ListCellRenderer {
		private static final long serialVersionUID = 1L;

		@Override
	    public Component getListCellRendererComponent(JList list, 
	                                                  Object value, 
	                                                  int index, 
	                                                  boolean isSelected, 
	                                                  boolean cellHasFocus) {
	      int offset = ((Integer)value) - 1 ;

	      ImageIcon icon = icons[offset];
	      String name = colorNames[offset];

	      setIcon(icon);
	      setText("       "+name);

	      return this;
	    }

}
}







