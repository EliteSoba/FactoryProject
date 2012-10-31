import java.awt.event.*;
import javax.swing.*;

/*
 * This class is the GUI for the Parts Manaager.  This will be 
 * instantiated in the general PartsManager class (which extends JFrame). 
 */


public class PartsManPanel extends JTabbedPane{
	
	// Data
	AddPanel addPanel;
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
		JPanel row1;
		JPanel row2;
		JPanel row3;
		JPanel row4;
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
			this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
	
			row1 = new JPanel();
			row1.setLayout(new BoxLayout(row1, BoxLayout.X_AXIS));
			row2 = new JPanel();
			row2.setLayout(new BoxLayout(row2, BoxLayout.X_AXIS));
			row3 = new JPanel();
			row3.setLayout(new BoxLayout(row3, BoxLayout.X_AXIS));
			row4 = new JPanel();
			row4.setLayout(new BoxLayout(row4, BoxLayout.X_AXIS));
			title = new JLabel ("Parts Manager");
			label1 = new JLabel ("New Item : ");
			itemName = new JTextField(8);
			label2 = new JLabel ("Image : ");
			imageSelection = new JComboBox();
			imageSelection.addItem("Image1");
			imageSelection.addItem("Image2");
			imageSelection.addItem("Image3");
			
			previewFrame = new JLabel();
			saveItem = new JButton ("Save Item");
			
			row1.add(title);
			row2.add(label1);
			row2.add(itemName);
			row3.add(label2);
			row3.add(imageSelection);
			row3.add(previewFrame);
			row4.add(saveItem);
			this.add(row1);
			this.add(row2);
			this.add(row3);
			this.add(row4);
		
		}

		@Override
		public void actionPerformed(ActionEvent arg0) {
			// TODO Auto-generated method stub
			
		}
		
	}
	
	private class RemovePanel extends JPanel{
		
	}

}
