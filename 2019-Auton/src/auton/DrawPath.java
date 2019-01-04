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
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;

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
	 * The index where the next path part will be added
	 */
	private int addAt;
	
	/**
	 * Default constructor that starts with an empty path.
	 */
	public DrawPath()
	{
		setUp();
	}
	
	/**
	 * Reads from a file to create a path from an already created path.
	 * @param file
	 */
	public DrawPath(File file)
	{
		setUp();
		
		try 
		{
			Scanner scan = new Scanner(file);
			
			while(scan.hasNext())
			{
				switch(scan.next())
				{
					case "Start" :
						path.add(new Start());
						break;
					case "Line" :
						path.add(new Line());
						break;
						
					case "Turn" :
						path.add(new Turn());
						break;
						
					case "Spline" :
						path.add(new Spline());
						break;
						
					case "Ultrasonic" :
						path.add(new Ultrasonic());
						break;
				}
				
				while(scan.hasNextInt())
				{
					path.get(path.size() - 1).addPoint(new Point(scan.nextInt(), scan.nextInt()));
				}
			}
			
			addAt = path.size();
			scan.close();
		} 
		catch (FileNotFoundException e) 
		{
			e.printStackTrace();
		}
		
		repaint();
	}
	
	/**
	 * Initializes all the needed components to make the graphics and path.
	 */
	private void setUp()
	{
		path = new ArrayList<PathPart>();
		addAt = 0;
		
		//Window set up
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
					new JButton("Export"),
					new JButton("Undo"),
					new JButton("Remove"),
					new JButton("Line"),
					new JButton("Turn"),
					new JButton("Spline"),
					new JButton("Ultrasonic")
				};
			
		int width = 100;
		int height = 50;
		int x = 0;
		int y = field.getHeight(null) + height / 2;
		
		//Does all button set up including setting the String 
		//command it sends when pushed and adding it to the window.
		for(JButton button : buttons)
		{
			if(button.getText().equals("Line"))
			{
				y += height;
				x = 0;
			}
			
			button.addActionListener(this);
			button.setActionCommand(button.getName());
			window.add(button);
			button.setVisible(true);
			button.setBounds(x, y, width, height);
			x += width;
			
			if(x > field.getWidth(null) - width)
			{
				x = 0;
				y += height;
			}
		}
		
		window.add(this);
		
		window.pack();
		window.setBounds(0, 0, field.getWidth(null), y + height * 2);
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
			System.out.println(p.describePath());
		}
	}
	
	/**
	 * Will return a the path code that could be used to run autonomous.
	 * 
	 * Discuss use in later meetings
	 */
	public void getPath(String name)
	{
		try 
		{
			if(path.get(path.size() - 1).getClass().equals(End.class))
			{
				throw new IllegalArgumentException("You must finish the path to export.");
			}
			
			PrintWriter out = new PrintWriter(name.replace(" ", "") + ".txt");
			for(PathPart part : path)
			{
				part.export(out);
			}

			out.close();
		} 
		catch (FileNotFoundException e) 
		{
			e.printStackTrace();
		}
		catch(IllegalArgumentException e)
		{
			JOptionPane.showMessageDialog(window, 
					e.getMessage() + "\nRemove or edit the path part.", 
					"Failed Sanity Check", JOptionPane.ERROR_MESSAGE);
		}
	}
	
	/**
	 * Returns whether the path has been finished. The path is finished if the 
	 * last path part is an End.
	 * @return
	 * 	True if the path is complete, false otherwise
	 */
	public boolean pathFinished()
	{
		if(path.size() > 0)
		{
			return path.get(addAt - 1).getClass().equals(End.class);
		}
		
		return false;
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
			if(!pathFinished()) // Does nothing if the path has been finished
			{
				switch(e.getActionCommand())
				{
					case "Finished" :
						//Can't finish if there's a path part that still needs points
						if(path.get(path.size() - 1).searchingForPoints())
						{
							throw new NullPointerException("All path parts must be"
									+ " complete before you can finish the path, "
									+ "try adding more points.");
						}
						
						//Confirms user is finished
						int result = JOptionPane.showConfirmDialog(window, "Are you finished?", "Finished", 
								JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
						
						if(result == JOptionPane.YES_OPTION)
						{
							//Adds end point to finishes path
							Point end = path.get(path.size() - 1).getEndPoint();
							path.add(new End(end));
							addAt++;
						}
						
						buttons[0].setActionCommand("Edit");
						buttons[0].setText("Edit");
						break;
						
					case "Undo" :
						//TODO make better and remove point from previous parts (turns)
						
						//Removes most recent point
						boolean remove = path.get(addAt - 1).removePoint();
						
						//Removes path part if it doesn't contain any points
						if(remove)
						{
							path.remove(addAt - 1);
							addAt--;
						}
						
						break;
						
					case "Remove" :
						if(path.get(addAt - 1).searchingForPoints())
						{
							//Won't allow part to be removed if there's 
							//a path part that is unfinished
							throw new NullPointerException("Finish the current"
									+ " path part to remove another.");
						}
						
						//TODO make selection for where to remove easier
						//Creates string of path parts with their index and 
						//description to be displayed during removal selection
						String[] options = new String[path.size()];
						for(int arrPos = 0; arrPos < path.size(); arrPos++)
						{
							options[arrPos] = arrPos + " " + path.get(arrPos).describePath();
						}
						
						//Choose path part for removal
						String index = (String) JOptionPane.showInputDialog(window, 
								"What path part would you like to remove?", 
								"Remove Path Part", JOptionPane.QUESTION_MESSAGE, null, 
								options, options[0]);
						
						if(index == null)
						{
							//Cancels remove if window was closed
							break;
						}
						
						//Reads int from string 
						Scanner scan = new Scanner(index);
						addAt = scan.nextInt();
						scan.close();
						
						path.remove(addAt);
						
						if(addAt >= path.size())
						{
							break;
						}
						
						//Changes finish button to connect so path cannot
						//be finished until path is connected.
						buttons[0].setText("Connect");
						buttons[0].setActionCommand("Connect");
						break;
						
					case "Connect" :
						for(int pos = path.size() - 1; pos > 0; pos--)
						{
							Point start = path.get(pos).getStartPoint();
							Point end = path.get(pos - 1).getEndPoint();
							
							//Checks if the start and end points of the two path parts are the same
							if(!start.equals(end))
							{
								Point[] after = path.get(pos).getPoints();
								Point[] before = path.get(pos - 1).getPoints();
								
								//Sets the end point of the last part inserted to
								//the start point of the next part.
								before[path.get(pos - 1).connectEnd()] = start;
								
								//Corrects next path part to match the last added
								//if it is dependent on the previous one.
								int addedPos = path.get(pos - 1).connectStart();
								for(int arrPos = path.get(pos).connectStart() - 1; arrPos >= 0; arrPos--)
								{
									after[arrPos] = before[addedPos];
									addedPos--;
								}
							}
						}
	
						//Finish button back to normal.
						buttons[0].setText("Finished");
						buttons[0].setActionCommand("Finished");
						addAt = path.size();
						break;
						
					case "Line" :
						Point startLine = path.get(addAt - 1).getEndPoint();
						
						path.add(addAt, new Line(startLine));
						addAt++;
						break;
						
					case "Turn" :
						Point startTurn = path.get(addAt - 1).getPoints()[0];
						Point centerTurn = path.get(addAt - 1).getEndPoint();
						
						path.add(addAt, new Turn(startTurn, centerTurn));
						addAt++;
						break;
						
					case "Spline" :
						Point startSpline = path.get((addAt - 1)).getEndPoint();
						
						path.add(addAt, new Spline(startSpline));
						addAt++;
						break;
						
					case "Ultrasonic" :
						Point startUltra = path.get((addAt - 1)).getEndPoint();
						
						path.add(addAt, new Ultrasonic(startUltra));
						addAt++;
						break;
				}
			}
			else if(e.getActionCommand().equals("Edit"))
			{
				path.remove(path.size() - 1);
				addAt--;
				buttons[0].setText("Finished");
				buttons[0].setActionCommand("Finished");
			}
			else if(e.getActionCommand().equals("Export"))
			{
				String fileName = JOptionPane.showInputDialog("Type in file name to be exported to.");
				
				if(fileName != null)
				{
					getPath(fileName.replace(" ", ""));
				}
			}
		}
		catch(IndexOutOfBoundsException ex)
		{
			// Notifies user of error probably from not placing a starting point
			JOptionPane.showMessageDialog(window, "Click on the map to place starting point.", 
					"Starting Point \n" + ex.getMessage(), JOptionPane.ERROR_MESSAGE);
		}
		catch(NullPointerException ex)
		{
			// Notifies user of error probably from not finishing a path part
			JOptionPane.showMessageDialog(window, 
					ex.getMessage(), "Finish Previous", 
					JOptionPane.ERROR_MESSAGE);
		}
		
		window.repaint();
	}
	
	@Override
	public void mouseClicked(MouseEvent e) 
	{
		if(e.getX() < field.getWidth(null) && e.getY() < field.getHeight(null))
		{
			if(addAt == 0) 
			{
				// Adds start point if there isn't one
				path.add(addAt, new Start(e.getPoint()));
				addAt++;
			}
			else 
			{
				// Adds point to the current and previous path part if they need it
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