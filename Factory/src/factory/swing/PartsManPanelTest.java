import javax.swing.JFrame;


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
