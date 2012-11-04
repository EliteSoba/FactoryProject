import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

/*
* This class is the GUI for the Parts Manaager. This will be
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

		JLabel title;
		JLabel label1;
		JLabel label2;
		JLabel previewFrame;
		JTextField itemName;
		JComboBox imageSelection;
		JButton saveItem;

		//Methods

		public AddPanel(){
			this.setLayout(new GridBagLayout());
			GridBagConstraints c = new GridBagConstraints();
				
			
			title = new JLabel ("Parts Manager");
			label1 = new JLabel ("New Item : ");
			itemName = new JTextField(8);
			label2 = new JLabel ("Image : ");
			imageSelection = new JComboBox();
			imageSelection.addItem("Item1");
			imageSelection.addItem("Item2");
			imageSelection.addActionListener(this);
			previewFrame = new JLabel();
			ImageIcon imagePreview = new ImageIcon("/Users/mfmendiola/Documents/workspace/LocalSwing/src/Item1.jpg");
			previewFrame.setIcon(imagePreview);
			
			saveItem = new JButton ("Save Item");
	
			c.gridx = 0;
			c.gridy = 0;
			c.fill = GridBagConstraints.VERTICAL;
			c.fill = GridBagConstraints.HORIZONTAL;
			c.insets = new Insets(0,0,10,0);
			this.add(title, c);
			c.fill = GridBagConstraints.VERTICAL;
			c.fill = GridBagConstraints.HORIZONTAL;
			c.gridx = 0;
			c.gridy = 1;
			this.add(label1, c);
			c.fill = GridBagConstraints.VERTICAL;
			c.fill = GridBagConstraints.HORIZONTAL;
			c.gridx = 1;
			c.gridy = 1;
			this.add(itemName, c);
			c.fill = GridBagConstraints.VERTICAL;
			c.fill = GridBagConstraints.HORIZONTAL;
			c.gridx = 0;
			c.gridy = 2;
			this.add(label2, c);
			c.gridx = 1;
			c.gridy = 2;
			c.fill = GridBagConstraints.VERTICAL;
			c.fill = GridBagConstraints.HORIZONTAL;
			this.add(imageSelection, c);
			c.gridx = 2;
			c.gridy = 2;
			c.fill = GridBagConstraints.VERTICAL;
			c.fill = GridBagConstraints.HORIZONTAL;
			this.add(previewFrame, c);
			c.gridx = 0;
			c.gridy = 3;
			c.fill = GridBagConstraints.VERTICAL;
			c.fill = GridBagConstraints.HORIZONTAL;
			this.add(saveItem, c);

		}
		
		public void updatePicture(String item){
			ImageIcon imagePreview = new ImageIcon("/Users/mfmendiola/Documents/workspace/LocalSwing/src/" + item + ".jpg");
			previewFrame.setIcon(imagePreview);
		}
		@Override
		public void actionPerformed(ActionEvent ae) {
			 JComboBox cb = (JComboBox)ae.getSource();
		     String petName = (String)cb.getSelectedItem();
		     updatePicture(petName);

		}

	}

	private class RemovePanel extends JPanel implements ActionListener{
		// Data

				JLabel title;
				JLabel label1;
				JLabel previewFrame;
				JComboBox imageSelection;
				JButton removeItem;

				//Methods

				public RemovePanel(){
					this.setLayout(new GridBagLayout());
					GridBagConstraints c = new GridBagConstraints();
						
					
					title = new JLabel ("Parts Manager");
					label1 = new JLabel ("Item : ");
					imageSelection = new JComboBox();
					imageSelection.addItem("Item1");
					imageSelection.addItem("Item2");
					imageSelection.addActionListener(this);
					previewFrame = new JLabel();
					ImageIcon imagePreview = new ImageIcon("/Users/mfmendiola/Documents/workspace/LocalSwing/src/Item1.jpg");
					previewFrame.setIcon(imagePreview);
					
					removeItem = new JButton ("Remove Item");
			
					c.gridx = 0;
					c.gridy = 0;
					c.fill = GridBagConstraints.VERTICAL;
					c.fill = GridBagConstraints.HORIZONTAL;
					c.insets = new Insets(0,0,10,0);
					this.add(title, c);
					c.fill = GridBagConstraints.VERTICAL;
					c.fill = GridBagConstraints.HORIZONTAL;
					c.gridx = 0;
					c.gridy = 1;
					this.add(label1, c);
					c.fill = GridBagConstraints.VERTICAL;
					c.fill = GridBagConstraints.HORIZONTAL;
					c.gridx = 1;
					c.gridy = 1;
					this.add(imageSelection, c);
					c.fill = GridBagConstraints.VERTICAL;
					c.fill = GridBagConstraints.HORIZONTAL;
					c.gridx = 2;
					c.gridy = 1;
					this.add(previewFrame, c);
					c.gridx = 0;
					c.gridy = 2;
					c.fill = GridBagConstraints.VERTICAL;
					c.fill = GridBagConstraints.HORIZONTAL;
					this.add(removeItem, c);

				}
				
				public void updatePicture(String item){
					ImageIcon imagePreview = new ImageIcon("/Users/mfmendiola/Documents/workspace/LocalSwing/src/" + item + ".jpg");
					previewFrame.setIcon(imagePreview);
				}
				@Override
				public void actionPerformed(ActionEvent ae) {
					 JComboBox cb = (JComboBox)ae.getSource();
				     String petName = (String)cb.getSelectedItem();
				     updatePicture(petName);

				}

	}

}