import javax.swing.JFrame;

/* Written by : Marc Mendiola
 *  Last edited 11/4/12 10:58 PM
 * This class is used solely as a unit test for KitAssManPanel
*/
public class KitAssManPanelTest extends JFrame{

	KitAssManPanel panel;
	
	public KitAssManPanelTest(){
		panel = new KitAssManPanel();
		this.add(panel);
	}
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		KitAssManPanelTest test = new KitAssManPanelTest();
		test.setSize(300,300);
		test.setVisible(true);
		test.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		test.setResizable(false);	

	}

}
