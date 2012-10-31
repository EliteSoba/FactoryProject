/*
 * This class is the GUI for the Parts Manaager.  This will be 
 * instantiated in the general PartsManager class (which extends JFrame). 
 */





public class PartsManPanel extends JTabbedPane{
	
	// Data
	AddPanel addpanel;
	RemovePanel removePanel;
	
	
	// Methods
	
	public PartsManPanel(){
		addPanel = new AddPanel();
		removePanel = new RemovePanel();
		this.addTab("addPanel", addPanel);
		this.addTab("removePanel", removePanel);
	}
	
	
	// Internal Classes
	
	private class AddPanel extends JPanel implements ActionListener{
		
		// Data
		JLabel title;
		JLabel label1;
		JLabel label2;
		JLabel previewFrame;
		ImageIcon imagePreview;
		JTextField itemName;
		JComboBox imageSelection;
		JButton saveItem;
		
		//Methods
		
		public AddPanel(){
			title = new JLabel ("Parts Manager");
			label1 = new JLabel ("New Item : ");
			label2 = new JLabel ("Image : ");
			previewFrame = new JLabel();
			saveItem = new JButton ("Save Item");
		}
		
	}
	
	private class RemovePanel extends JPanel{
		
	}

}
