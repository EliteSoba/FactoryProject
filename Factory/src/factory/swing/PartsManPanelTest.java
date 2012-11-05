import javax.swing.JFrame;

/* Written by : Marc Mendiola
 *  Last edited 11/4/12 10:58 PM
 * This class is used solely as a unit test for KitAssManPanel
*/

public class PartsManPanelTest extends JFrame{

	PartsManPanel partsManPanel;
	
	public PartsManPanelTest() {
		partsManPanel = new PartsManPanel();
		this.add(partsManPanel);
	}
	public static void main(String[] args) {
		PartsManPanelTest test = new PartsManPanelTest();
		test.setSize(300, 300);
		test.setVisible(true);
		test.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		test.setResizable(false);
	}

}
