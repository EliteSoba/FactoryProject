// to test FactoryProdManPanel.java
package factory.swing;

import java.util.*;
import java.awt.*;

import javax.swing.*;

public class FactoryProdManager extends JFrame {
	ArrayList<String> kitNameList;
	FactoryProdManPanel myPanel;

	public FactoryProdManager() {
		kitNameList = new ArrayList<String>();
		kitNameList.add("kit 1");
		kitNameList.add("kit 2");
		kitNameList.add("kit 3");
		
		
	//	ArrayList<Integer> numbers = {1,2,3,4,5,10,15,20,30,40,50,60,70,80,90,100,150,200,250,500};

		myPanel = new FactoryProdManPanel(kitNameList);
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
		kitNameList.add(kitName);
		myPanel.addKit(kitName);
	}

}
