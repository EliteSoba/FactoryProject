import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

import javax.swing.*;

class PartsRobotClient extends JPanel implements ActionListener
{
	ArrayList<Nest> nests;
	AnimatedObject station;
	PartsRobot robot;
	static int frameWidth, frameHeight;

	public PartsRobotClient()
	{
		// Initialize array
		nests = new ArrayList<Nest>();
		// Add the kitting station
		station = new AnimatedObject(10,10,0,0,0,0,150,300,"kittingStation.png");
		// Add the parts robot
		robot = new PartsRobot(500,200,0,10,10,10,100,100,"robot1.png");
		robot.setDestination(210,10);
		// Add 8 nests
		for(int i = 0; i < 8; i++)
		{
			nests.add(new Nest(frameWidth-100,i*85+10,0,0,0,0,75,75,"nest3x3.png"));
		}
	}

	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);					// required to clear the screen
		Graphics2D g2 = (Graphics2D)g;
		// Draw all animated objects
		for(int i = 0; i < nests.size(); i++)
		{
			Nest currentNest = nests.get(i);
			// Draw nest
			g2.drawImage(currentNest.getImage(), currentNest.getX(), currentNest.getY(), currentNest.getImageWidth(), currentNest.getImageHeight(), this);
			// Draw items
			for(int j = 0; j < nests.get(i).getSize(); j++)
			{
				Item currentItem = currentNest
				g2.drawImage(nests.get(i).getItemAt(j).getImage(),)
			}
		}
		g2.drawImage(station.getImage(), station.getX(), station.getY(), station.getImageWidth(), station.getImageHeight(), this);
		System.out.println(robot.getAngle());
		Graphics2D g3 = (Graphics2D)g;
		g3.rotate(Math.toRadians(360-robot.getAngle()), robot.getX()+robot.getImageWidth()/2, robot.getY()+robot.getImageHeight()/2);
		g3.drawImage(robot.getImage(), robot.getX(), robot.getY(), robot.getImageWidth(), robot.getImageHeight(), this);
	}

	public static void main(String arg[])
	{
	   JFrame frame = new JFrame("Parts Robot Client");
	   frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	   frameWidth = 720;
	   frameHeight = 720;
	   frame.setSize(frameWidth, frameHeight);
	   
	   PartsRobotClient panel = new PartsRobotClient();
	   frame.setContentPane(panel);
	   frame.setVisible(true);
	   
	   new Timer(50, panel).start();
	}
	
	public void actionPerformed(ActionEvent e)
	{
		robot.move();				// Update position and angle of robot
		repaint();
	}
}