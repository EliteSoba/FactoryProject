
public class UnitTest extends JFrame{

	PartsManPanel partsManPanel;
	
	public UnitTest() {
		partsManPanel = new PartsManPanel();
		this.add(partsManPanel);
	}
	public static void main(String[] args) {
		UnitTest test = new UnitTest();
		test.setSize(800, 800);
		test.setVisible(true);
		test.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

}
