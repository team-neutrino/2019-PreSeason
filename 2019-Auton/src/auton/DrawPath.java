package auton;

import java.awt.BasicStroke;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

/**
 * The class that will be used to create a path for autonomous. A user will be able
 * to create a path by creating parts of the path and adding points. The path parts
 * will then be used to generate the code for autonomous.
 * 
 * @author JoelNeppel
 *
 */
public class DrawPath extends JComponent implements MouseListener, ActionListener
{
	/**
	 * The multiplier to convert pixels to inches
	 */
	public static final double INCHPIXEL = 0.71;
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * The window that will display all buttons and graphics
	 */
	private JFrame window;
	
	/**
	 * Array list that contains all the parts of the path that 
	 * will be driven
	 */
	private ArrayList<PathPart> path;
	
	/**
	 * Array of all the buttons that will be used to draw the path
	 */
	private JButton[] buttons;
	
	/**
	 * The image of the field
	 */
	private Image field;
	
	/**
	 * True if the user is finished drawing a path, false if not
	 */
	private boolean finished;
	
	/**
	 * Default constructor that starts with an empty path.
	 */
	public DrawPath()
	{
		setUp();
	}
	
	/**
	 * Constructor that uses given path parts to create the start of a path.
	 * @param parts
	 * 	The path parts that will be used to begin the path
	 */
	public DrawPath(PathPart[] parts)
	{
		setUp();
		
		for(PathPart p : parts)
		{
			path.add(p);
		}
	}

	/**
	 * Initializes all the needed components to make the graphics and path.
	 */
	private void setUp()
	{
		path = new ArrayList<PathPart>();

		window = new JFrame("Draw Path");
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setVisible(true);
		addMouseListener(this);
		
		//Gets the field image
		try 
		{
			field = ImageIO.read(new File("fieldDrawing.jpg"));
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
		}
		
		/*
		 * Makes all the buttons, if you created a new PathPart subclass you must
		 * add the button here and add the action in the switch in the 
		 * ActionPerformed method.
		 */
		buttons = new JButton[]
				{
					new JButton("Finished"),
					new JButton("Undo"),
					new JButton("Line"),
					new JButton("Turn"),
					new JButton("Spline"),
					new JButton("Ultrasonic")
				};
			
		int x = 0;
		int y = field.getHeight(null) + 50;
		int width = 100;
		int height = 50;
		
		//Does all button set up including setting the String 
		//command it sends when pushed and adding it to the window.
		for(JButton button : buttons)
		{
			button.addActionListener(this);
			button.setActionCommand(button.getName());
			window.add(button);
			button.setVisible(true);
			button.setBounds(x, y, width, height);
			x += width;
		}
		
		window.add(this);
		
		window.pack();
		window.setBounds(0, 0, field.getWidth(null), field.getHeight(null) + 300);
	}
	
	@Override
	public void paintComponent(Graphics g)
	{
		g.drawImage(field, 0, 0, null);
		
		Graphics2D g2d = (Graphics2D) g;
		g2d.setStroke(new BasicStroke(3));
		
		for(PathPart p : path)
		{
			p.drawPathPart(g);
		}
	}

	/**
	 * Drives the robot following the created path using the drivePath method
	 * from each part of the path.
	 * 
	 * Discuss use in later meetings
	 */
	public void drivePath()
	{
		for(PathPart p : path)
		{
			p.drivePath();
		}
	}
	
	/**
	 * Will return a the path code that could be used to run autonomous.
	 * 
	 * Discuss use in later meetings
	 */
	public void getPath()
	{
		//TODO write file/system print/decide later
	}
	
	/**
	 * Returns whether the path has been finished.
	 * @return
	 * 	True if the path is complete, false otherwise
	 */
	public boolean pathFinished()
	{
		return finished;
	}
	
	/**
	 * Removes the most recent point.
	 */
	private void removeLastPoint()
	{
		boolean remove = path.get(path.size() - 1).removePoint();
		
		if(remove)
		{
			path.remove(path.size() - 1);
		}
	}
	
	@Override
	public void actionPerformed(ActionEvent e) 
	{
		/*
		 * Any PathPart subclass that was created will need to have the 
		 * action command (The string entered when creating the button) added
		 * to the cases that will then create a new path part of that type. 
		 */
		
		try // Attempts to do command
		{
			if(!finished) // Does nothing if the path has been finished
			{
				switch(e.getActionCommand())
				{
					case "Finished" :
						if(path.get(path.size() - 1).getLastPoint() == null)
						{
							throw new NullPointerException();
						}
						
						int result = JOptionPane.showConfirmDialog(window, "Are you finished?", "Finished", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
						
						if(result == JOptionPane.YES_OPTION)
						{
							finished = true;
						}
						
						break;
						
					case "Undo" :
						removeLastPoint();
						break;
						
					case "Line" :
						Point startLine = path.get(path.size() - 1).getLastPoint();
						
						path.add(new Line(startLine));
						break;
						
					case "Turn" :
						Point startTurn = path.get(path.size() - 1).getPoints()[0];
						Point centerTurn = path.get(path.size() - 1).getLastPoint();
						
						path.add(new Turn(startTurn, centerTurn));
						break;
						
					case "Spline" :
						Point startSpline = path.get((path.size() - 1)).getLastPoint();
						
						path.add(new Spline(startSpline));
						break;
						
					case "Ultrasonic" :
						Point startUltra = path.get((path.size() - 1)).getLastPoint();
						
						path.add(new Ultrasonic(startUltra));
						break;
				}
			}
			
		}
		catch(IndexOutOfBoundsException ex)// Notifies user of error probably from not placing a starting point
		{
			JOptionPane.showMessageDialog(window, "Click on the map to place starting point.", "Starting Point", JOptionPane.ERROR_MESSAGE);
		}
		catch(NullPointerException ex)// Notifies user of error probably from not finishing a path part
		{
			JOptionPane.showMessageDialog(window, "Try clicking the map to finish placing points for previous path part.", "Finish Previous", JOptionPane.ERROR_MESSAGE);
		}
		
		window.repaint();
	}
	
	@Override
	public void mouseClicked(MouseEvent e) 
	{
		if(e.getX() < field.getWidth(null) && e.getY() < field.getHeight(null))
		{
			if(path.size() == 0) // Adds start point if there isn't one
			{
				path.add(new Start(e.getPoint()));
			}
			else // Adds point to any path  part that needs a point
			{
				for(int arrPos = 0; arrPos < path.size(); arrPos++)
				{
					if(path.get(arrPos).searchingForPoints())
					{
						path.get(arrPos).addPoint(e.getPoint());
					}
				}
			}
			
			window.repaint();
		}
	}

	@Override
	public void mousePressed(MouseEvent e) {}

	@Override
	public void mouseReleased(MouseEvent e) {}

	@Override
	public void mouseEntered(MouseEvent e) {}
	
	@Override
	public void mouseExited(MouseEvent e) {}
}