// includes factory production manager panel
import java.util.*;
import java.awt.*;
import javax.swing.*;
import java.awt.event.*;

public class FactoryProdManager extends JFrame {
	private static final long serialVersionUID = 6598851462962389770L;
	ArrayList<String> kitList;
	FactoryProdManPanel myPanel;

	public FactoryProdManager() {
		kitList = new ArrayList<String>();
		kitList.add("kit 1");
		kitList.add("kit 2");
		kitList.add("kit 3");
		
		
	//	ArrayList<Integer> numbers = {1,2,3,4,5,10,15,20,30,40,50,60,70,80,90,100,150,200,250,500};

		myPanel = new FactoryProdManPanel();
	//	myPanel = new FactoryProdManPanel();
		add(myPanel);
		setSize(300,600);
		setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
	}
	
	public static void main(String[] args) {
		
		FactoryProdManager FPM = new FactoryProdManager();
		// String kitName = "testkit";
		//FPM.addBox(testkit);
		//FPM.removeBox(testkit);

	}
	
	public void removeBox (String kitName) {	
		myPanel.removeKit(kitName);
	}
	
	public void addBox(String kitName) {
		kitList.add(kitName);
		myPanel.addKit(kitName);
	}
	
	public class FactoryProdManPanel extends JPanel implements ActionListener {
		JComboBox kitNameBox;
		JSpinner spinner;
		JButton submitButton;
		JTextArea messageBox;
		
		public FactoryProdManPanel() { // manager has arraylist of kitnames available
			kitNameBox = new JComboBox();
			for (int i = 0; i < kitList.size(); i++)
				kitNameBox.addItem(kitList.get(i));
			kitNameBox.setPreferredSize(new Dimension(225,25));
			
			setLayout(new GridBagLayout());
			GridBagConstraints c = new GridBagConstraints();
			c.gridy = 1;
			c.gridx = 1;
			c.gridwidth = 4;
			c.gridheight = 1;
			c.insets = new Insets(0,0,25,0); 
			add(new JLabel("Factory Production Manager"),c);
			
			
			c.insets = new Insets(10,0,10,0);
			c.gridy = 2;
			c.anchor = GridBagConstraints.LINE_START;
			add(new JLabel("Submit New Batch Order:"),c);
			
			c.gridy = 3;
			c.anchor = GridBagConstraints.CENTER;
			for(int i = 0; i < kitList.size();i++) {
				kitNameBox.setSelectedIndex(i);
				kitNameBox.addActionListener(this);
			}
			kitNameBox.setSelectedIndex(0);
			add(kitNameBox,c);

			c.anchor = GridBagConstraints.LINE_END;
			c.gridy = 4;
			c.gridx = 2;
			c.gridwidth = 1;
			add(new JLabel("Quantity"),c);
			
			c.gridx = 4;
			SpinnerNumberModel qntyModel = new SpinnerNumberModel(1,1,500,1);
			spinner = new JSpinner(qntyModel);
			add(spinner,c);
			
			c.gridy = 5;
			c.gridx = 4;
			submitButton = new JButton("Submit");
			submitButton.addActionListener(this);
			add(submitButton,c);
			
			c.gridy = 6;
			c.gridx = 1;
			c.gridwidth = 4;
			messageBox = new JTextArea("System Messages\n",10,20);
			add(new JScrollPane(messageBox),c);
					
		}
			
		public void actionPerformed(ActionEvent ae) {
			
			if (ae.getSource().equals(submitButton)) {
				if (kitNameBox.getSelectedItem() == null)
					messageBox.append("No kit selected.\n");
				else
				messageBox.append("Order Submitted.\n     Details: " + spinner.getValue() + " units of " + (String)kitNameBox.getSelectedItem() + "\n" );
			}
		}
		
		public void addKit(String kitName) {	
			kitNameBox.addItem(kitName);	
		}
		
		public void removeKit(String kitName) {
			kitNameBox.removeItem(kitName);
		}
	}

}
