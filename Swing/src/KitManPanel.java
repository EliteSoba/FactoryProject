
import java.util.*;
import java.awt.*;
import java.awt.event.*;

import javax.swing.*;
import java.io.*;
import javax.swing.BoxLayout;
import javax.swing.JLabel.*;

import javax.swing.JTabbedPane;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JFrame;
import javax.swing.JComponent;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.KeyEvent;

public class KitManPanel extends JFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	JButton wageWar = new JButton ("Wage War");
	JTextField hScore = new JTextField("Your current score is 0");
	
	//for panel 2
	JButton button1 = new JButton("Button 1");
	JButton button2 = new JButton("Button 2");
	JButton button3 = new JButton("Button 3");
	
	JTextField text1 = new JTextField( "Text 1");
	JTextField text2 = new JTextField( "Text 2");
	JTextField text3 = new JTextField( "Text 3");
	
	JLabel label1 = new JLabel ("Label 1");
	JLabel label2 = new JLabel ("Label 2");
	JLabel label3 = new JLabel ("Label 3");
	
	//for panel 3
	
	//for panel 4
	
	JLabel label11 = new JLabel ("Label 11");
	JLabel label22 = new JLabel ("Label 22");
	JLabel label33 = new JLabel ("Label 33");
	
	JTextField text11 = new JTextField( "Text 11");
	JTextField text22 = new JTextField( "Text 22");
	JTextField text33 = new JTextField( "Text 33");
	
	
	JPanel p1 = new JPanel();
	JPanel p2 = new JPanel();
	JPanel p3 = new JPanel();
	JPanel p4 = new JPanel();

	public KitManPanel(){
		setLayout(new GridLayout(2,2));
		/*add(p1);
		
		p1.setLayout (new BoxLayout(p1, BoxLayout.LINE_AXIS)); //left to right
		
		JRadioButton b1 = new JRadioButton("Button 1");
		b1.setMnemonic(KeyEvent.VK_B);
		b1.setActionCommand("Button 1");
		b1.setSelected(true);
		
		JRadioButton b2 = new JRadioButton("Button 2");
		b2.setMnemonic(KeyEvent.VK_C);
		b2.setActionCommand("Button 2");
		
		JRadioButton b3 = new JRadioButton("Button 3");
		b3.setMnemonic(KeyEvent.VK_D);
		b3.setActionCommand("Button 3");
		
		p1.add(b1);
		p1.add(b2);
		p1.add(b3);
		*/
		add(p2);
		
		p2.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		
		c.fill = GridBagConstraints.VERTICAL;
		c.gridx = 0;
		c.gridy = 0;
		p2.add(button1, c);
		
		c.fill = GridBagConstraints.VERTICAL;
		c.gridx = 1;
		c.gridy = 0;
		p2.add(button2, c);
		
		c.fill = GridBagConstraints.VERTICAL;
		c.gridx = 2;
		c.gridy = 0;
		p2.add(button3, c);
		
		c.fill = GridBagConstraints.VERTICAL;
		c.gridx = 0;
		c.gridy = 1;
		p2.add(text1, c);
		
		c.fill = GridBagConstraints.VERTICAL;
		c.gridx = 1;
		c.gridy = 1;
		p2.add(text2, c);
		
		c.fill = GridBagConstraints.VERTICAL;
		c.gridx = 2;
		c.gridy = 1;
		p2.add(text3, c);
		
		c.fill = GridBagConstraints.VERTICAL;
		c.gridx = 0;
		c.gridy = 2;
		p2.add(label1, c);
		
		c.fill = GridBagConstraints.VERTICAL;
		c.gridx = 1;
		c.gridy = 2;
		p2.add(label2, c);
		
		c.fill = GridBagConstraints.VERTICAL;
		c.gridx = 2;
		c.gridy = 2;
		p2.add(label3, c);
		
		add(p3);
		
		p3.setLayout( new BorderLayout() );
		
		/*Icon ii = new ImageIcon("meme.jpg");
		JLabel labelImage = new JLabel(ii);
		
		JScrollPane scrollPane=new JScrollPane(); 
		scrollPane.getViewport().add(labelImage);
		
		p3.add(scrollPane , BorderLayout.CENTER);	*/	
		
		add(p4);
		
		JTabbedPane tabbedPane = new JTabbedPane();
		JPanel p11 = new JPanel();
		p11.setLayout(new FlowLayout());
		p11.add(label11);
		p11.add(text11);
		
		JPanel p22 = new JPanel();
		p22.setLayout(new FlowLayout());
		p22.add(label22);
		p22.add(text22);
		
		JPanel p33 = new JPanel();
		p33.setLayout(new FlowLayout());
		p33.add(label33);
		p33.add(text33);
		
		tabbedPane.addTab("Tab 1", p11);
		tabbedPane.addTab("Tab 2", p22);
		tabbedPane.addTab("Tab 3", p33);
		
		p4.add(tabbedPane);	
	}
	
	public static void main (String[] args){
		KitManPanel l = new KitManPanel();
		l.repaint();
		l.setVisible(true);
		l.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		l.setSize(700,700);
		l.repaint(); 
	}
		
}
