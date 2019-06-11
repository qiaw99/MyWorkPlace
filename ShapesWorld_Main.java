import java.awt.Color;
import java.awt.Graphics;
import java.awt.Polygon;
import java.util.ArrayList;
import java.util.Random;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import java.awt.Graphics;
import java.awt.Polygon;
import java.util.ArrayList;
import java.util.Random;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

class ShapeFrame extends JFrame {
	
	public static final long serialVersionUID = 1;
	 
    ShapesPanel shapesPanel = new ShapesPanel(new Dimension(500,400));
    ShapesList allShapes = shapesPanel.allShapes;

    public ShapeFrame( String args[] ) {
    	
        super("_.-'-._.-'-._.-'-._  Shapes World  _.-'-._.-'-._.-'-._");
        this.add( shapesPanel, "Center" );
        this.setResizable(false);
        this.setLocation(400, 50);
        this.add( createButtonsPanel(args), "South" );
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); 
        this.pack();
        this.setVisible( true );
    }
    
    JPanel createButtonsPanel( String args[] ){
    	 JPanel shapeButtons = new JPanel( new FlowLayout() );  
    	 for ( int i=0; i<args.length; i++ ) {
            JButton shapeButton = new JButton( args[i] );
            shapeButton.addActionListener( new ButtonsActionListener() );
            shapeButtons.add( shapeButton );
        }        
    	if ( args.length == 0 ) {
            JLabel l= new JLabel("Die Namen der Shape-Klassen sollen als Argumente eingebeben werden");
            shapeButtons.add(l);
        }      
        return shapeButtons;
    } 
    
    public class ButtonsActionListener implements ActionListener{
    	
        public void actionPerformed( ActionEvent aevt ) {
    	    String name = ShapesWorld_Main.class.getName();
    	    String newName = name.replace("ShapesWorld_Main",aevt.getActionCommand());
            try {
                  Class c = Class.forName( newName );
                  if ( c != null ) {
                	  Shape shape = (Shape) c.getConstructor().newInstance();
                	  shape.setShapesWorld(shapesPanel);
                      allShapes.addShape( shape );
                  }
            } catch ( Exception e ) {
                System.out.println( e.getMessage() );
            }
        }// end of actionPerformed
        
    } // end of class ButtonsActionListener

    public class ShapesPanel extends JPanel implements ShapesWorld, Runnable {
    	
    	public static final long serialVersionUID = 1;
    	
        Color bgcolor = new Color(0,0,120);
        Dimension dim;
        int[][] stars;
        
        public ShapesList allShapes = new ShapesList();
        private Thread anim;
        private Shape selected = null;

        public ShapesPanel( Dimension dim ) {
        	this.setBackground(bgcolor);
        	this.dim = dim;
        	this.setSize(dim);
            stars = createStars();
            this.setFocusable(true);
            this.addMouseListener( new ShapeMouseMotionListener() );
    		this.addKeyListener(new ShapeKeyListener());
            anim = new Thread( this );
            anim.start();
        }
        
        public Dimension getPreferredSize() { 
        	return dim;  
        }
         
        public void paintComponent(Graphics g){
        	 super.paintComponent(g);
        	 this.paintStars(g);
             g.translate(+dim.width/2, +dim.height/2);
             Shape[] shapes = allShapes.getShapes();
             for (int i=0; i<shapes.length; i++) {
                     if ( shapes[i] == selected ) {
                         g.setColor( Color.red );
                     } else {
                         g.setColor( shapes[i].getColor()  );
                     }
                     shapes[i].draw(g);
             } // end of for
         } // end of paintComponent
          
        // paints some stars on the background
        
         public void paintStars(Graphics g){
        	 g.setColor(Color.LIGHT_GRAY);
        	 for( int i=0; i<stars.length-2; i+=3 ){
        		 g.fillOval(stars[i][0], stars[i][1], 1, 1);
        		 g.fillOval(stars[i+1][0], stars[i+1][1], 2, 2);
        	 }
         }
         
         // create some random stars to be painted on the background after each update
         
         public int[][] createStars(){
        	 Random rand = new Random();
        	 int[][] stars = new int[600][2];
        	 for( int[] s: stars ){
        		 s[0] = rand.nextInt(dim.width);
        		 s[1] = rand.nextInt(dim.height);
        	 }
        	 return stars;
         }

        /* the following methods are the implementation of the ShapesWorld-interface */  
         
    	public int getMin_X() { 
    	    int width = this.dim.width;
    		return  -width/2; 
    	}
    	
    	public int getMax_X() { 
    	    int width = this.dim.width;       	
    		return  width/2; 
    	}
    	
    	public int getMin_Y() { 
    	    int height = this.dim.height;       	
    		return  -height/2; 
    	}
    	
    	public int getMax_Y() { 
    	    int height = this.dim.height;        	
    		return  height/2; 
    	}
    	
    	public Shape getClosestShape( Shape myShape ) {
    	    return allShapes.getClosest( myShape );
    	}
    	
    	public void addShape( Shape newShape ) {
    	    newShape.setShapesWorld( this );
    	    allShapes.addShape( newShape );
    	}       
    	
    	public void removeShape( Shape toBeRemoved ) {
    	    allShapes.removeShape( toBeRemoved );
    	    System.out.println("removeShape");
    	}
    			
     // implementation of the Runnable-Interface		
    	public void run() {
    	    while ( anim == Thread.currentThread() ) {
    	        try {
    	              Thread.sleep(Animation.sleep_time/2);
    	        } catch ( InterruptedException ie ) {
    	             System.out.println( ie.getMessage() );
    	        }
    	        repaint();
    	    }
    	} // end of run
    	
    	
    	/* We use inner classes for the implementation of the Listener classes */
    	
    	class ShapeMouseMotionListener implements MouseListener {
    		
    		public void mouseClicked( MouseEvent evt ) {
    		    double x = evt.getX()-dim.width/2.0;
    		    double y = evt.getY()-dim.height/2.0;
    		    selected = allShapes.hit( x, y );
    		    if ( selected != null ) {
    		        selected.userClicked( x, y );
    		    }
    		    requestFocusInWindow(true);
    		    System.out.println(x+y);
    		}			
    		public void mousePressed(MouseEvent me) {}
    		public void mouseEntered(MouseEvent arg0) {}
    		public void mouseExited(MouseEvent arg0) {}
    		public void mouseReleased(MouseEvent me) {}
    		
    	}// end of class ShapeMouseMotionListener
    	
    	class ShapeKeyListener implements KeyListener {		
    		public void keyTyped(KeyEvent e) {
    			if(selected != null)
    				selected.userTyped(e.getKeyChar());
    			System.out.println(e.getKeyChar());
    		}
    		public void keyPressed(KeyEvent e) {}
    		public void keyReleased(KeyEvent e) {}
    		
    	}// end of class ShapeKeyListener

		public Color getBackgroundColor() {
			return bgcolor;
		}
    	
    }// end of class ShapesPanel
    	
} // end of class ShapeWorld

interface Animation {
	
	public static int sleep_time = 30;
	
	/** The play-Method will be called every 30 milliseconds */
    public void play();
}
class CrazyWalker implements Shape, Animation {
	
	double radius;
	int step;
	Point center;
	Random rand;
	ShapesWorld theWorld;	   
	Color color;
	
	
	public CrazyWalker() {
		super();
		this.rand = new Random();
		this.radius = 8;
		this.step = 10;
		this.center = new Point(0,0);
		this.color = Color.magenta;
	}

	@Override
	public void play() {
		 int d = rand.nextInt(4);
		 int xd=0, yd=0;
         switch (d) {
             case 0:
	                 xd = 1; yd = 1; break;
             case 1:
                 	 xd = 1; yd = -1; break;
             case 2:
                 	 xd = -1; yd = 1; break;
             case 3:
                 	 xd = -1; yd = -1; break;
         }
         
		Footprint fp = new Footprint();
		
		fp.moveTo(this.getCenter().x, this.getCenter().y);
		theWorld.addShape(fp);
		double newX = this.center.x + rand.nextDouble()*step*xd;
		double newY = this.center.y + rand.nextDouble()*step*yd;
		this.moveTo( (int) newX, (int) newY);		
	}

	@Override
	public void draw(Graphics g) {
		g.setColor(color);
		g.drawOval((int) center.x, (int) center.y, (int) radius, (int) radius);
	}

	@Override
	public boolean contains(double x, double y) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public double getRadius() {
		// TODO Auto-generated method stub
		return radius;
	}

	@Override
	public Color getColor() {
		// TODO Auto-generated method stub
		return color;
	}

	@Override
	public Point getCenter() {
		// TODO Auto-generated method stub
		return center;
	}

	@Override
	public void setShapesWorld(ShapesWorld theWorld) {
		this.theWorld = theWorld;
	}

	@Override
	public void userClicked(double at_X, double at_Y) {
	}

	@Override
	public void userTyped(char key) {
	}

	@Override
	public void moveTo(double x, double y) {
		center.x = x;
		center.y = y;
	}

}
class Footprint implements Shape, Animation {
	double radius;
	Point center;
	Color color;
	ShapesWorld world;
	
	public Footprint(){
			super();
			this.radius = 6;
			this.center = new Point(0,0);
			this.color = Color.WHITE;
	}
	
	public void draw(Graphics g) {
		g.setColor(color);
		g.drawOval((int) center.x, (int) center.y, (int) radius/2, (int) (radius) );
	}

	public boolean contains(double x, double y) {
		return false;
	}

	public double getRadius() {
		return radius;
	}

	public Color getColor() {
		return color;
	}

	public Point getCenter() {
		return center;
	}

	public void setShapesWorld(ShapesWorld theWorld) {
         this.world = theWorld;
	}

	public void userClicked(double at_X, double at_Y) {        
	}

	public void userTyped(char key) {
	}

	public void moveTo(double x, double y) {
		center.x = x;
		center.y = y;
	}

	public void play() {
	}
	
}// end of class Footprint
class Around implements Shape, Animation {

	   double radius;
	   Point center;
	   Random rand;
	   
	   Color color;
	   ShapesWorld welt;
	   double velocity = 2;

	   public Around() {
		   this.radius = 25;
		   this.color = Color.CYAN; 
		   this.center = new Point();
		   this.rand = new Random();
	   }

	   public Color getColor()
	   { return color; }
	   
	   public void moveTo(double x, double y){
		  	center.x = (int) x;
		  	center.y = (int) y;
	   }

	   public void setShapesWorld( ShapesWorld theWorld )
	   { 
		   this.welt = theWorld;
	   }   

	   public void draw(Graphics g) {
		    g.setColor(color);
		    fillTriangle(g, center.x-radius, center.y-radius, radius*2, radius*2);
	   }
	   
	   public void fillTriangle(Graphics g, double x, double y, double w, double h){
	   	int[] x_coords = { (int) (x+w/2), (int) (x), (int) (x+w) };
			   int[] y_coords = { (int) (y), (int) (y+h), (int) (y+h) };
			   Polygon p = new Polygon(x_coords, y_coords, 3);
			   g.fillPolygon(p);
	   }
	   
	   

	   public Point getCenter() {
		   return center;
	   }

	   public void userClicked(double atX, double atY){ 
		   this.radius += 2;
		   this.welt.addShape(new Around());
	   }
	   
	   public void userTyped(char key){
		   System.out.println("key");
	   }

	   // implement the Animation-Interface
	   public void play()
	   {
		    if ( (center.x-radius)<=welt.getMax_X() )
		    	center.x = center.x + velocity;
		    else
		    	center.x = welt.getMin_X()+radius;
	   }

		public boolean contains(double x, double y) {
			if (x<(center.x-radius) || x>center.x+radius || y<(center.y-radius) || y>(center.y+radius))
			    return false;
			else
				return true;
		}
		
		public double getRadius() {
			return radius;
		}

	} // end of class Around
class Point {
	
	double x, y;
		
	public Point(double x, double y){
		this.x = x;
		this.y = y;
	}
	
	public Point(){
		this(0, 0);
	}
	
	public void translate( double dx, double dy){
		x += dx; y += dy;
	}
	
	public void rotate( double angle){
		x = (int) (x*Math.cos(angle) - y*Math.sin(angle));
		y = (int) (x*Math.sin(angle) + y*Math.cos(angle));
	}
	
} // end of Point class
/** the Shape "interface" includes the methods that the Shape objects need in order to work with the 
 *  other classes. 
 *  A class that implements this interface must define at least all methods given here.
 */

interface Shape {	
        
        /** 
         * the draw method gets a graphical context g so that the shape-objects can 
         * paint themselves using the methods of g.
         */        
        public void draw(Graphics g);
        
        /** checks if a (x, y) point is inside the Shape object. */
        public boolean contains(double x, double y);

        /**
         * returns the radius of the circle that surrounds the shape object 
         */
        public double getRadius();
        
        /**
         * returns the color of the shape object.
         */
        public Color getColor();
        
        /**
         * returns the center of the Shape-object using floating-point numbers for 
         * the x- and y-coordinates (double).
         */
        public Point getCenter();

        /**
         * this method is always called after a Shape-object has been created.
         * If you want to have access to the ShapeWorld-object to get information about the 
         * other Shape-objects, you need to store this reference as an instance variable of
         * your Shape-class.
         */
        public void setShapesWorld( ShapesWorld theWorld );
        
        /**
         * this method is called when the user clicks the mouse on a Shape-object. 
         * The at_X and at_Y parameters correspond to the (x, y)-coordinates of the mouse 
         * click.
         */
        public void userClicked( double at_X, double at_Y );
        
        /**
         * this method is called when the user presses the mouse selecting a Shape-object and 
         * then presses a key. In key we get the pressed key.
        */               
        public void userTyped(char key);
        
        /**
         * the Shape-object moves to the (x,y)-coordinates.
         */
        public void moveTo(double x, double y);

}// end of the Shape Interface
//import java.util.Vector;

class ShapesList extends Thread{
	
 ArrayList<Shape> shapes; 
 
 public ShapesList(){
 	this.shapes = new ArrayList<Shape>();
 	this.start();
 }

 public synchronized void addShape( Shape newShape ) {
     for ( Shape s: this.shapes ) {
     	if ( s.equals(newShape))
               return;
     }
     shapes.add(newShape);
 } // end of addShape

 public synchronized void removeShape( Shape shapeToBeRemoved ) {
      shapes.remove(shapeToBeRemoved);
 } // end of removeShape

 public synchronized Shape[] getShapes() {
     Shape[] only_shapes = new Shape[shapes.size()];
     for ( int i=0; i<shapes.size(); i++) {
         only_shapes[i] = shapes.get(i);
     }
     return only_shapes;
 }// end of getShapes

 public synchronized Shape hit( double x, double y ) {        
     for ( Shape shape: shapes ) {
         if ( shape.contains(x, y) ) {
                 return shape;
         }// if
      }// for
      return null;
 }// hit

 public synchronized Shape getClosest( Shape myShape ) {
     Shape closest = null;
     double x = myShape.getCenter().x;
     double y = myShape.getCenter().y;
     double min_dist = 0, new_dist = 0;
     double x2, y2, dx, dy;

     for ( Shape shape: shapes ) {
         if ( shape == myShape ) { continue; }
         x2 = shape.getCenter().x;
         y2 = shape.getCenter().y;
         dx = Math.abs(x-x2);
         dy = Math.abs(y-y2);
         new_dist = Math.sqrt(dx*dx + dy*dy) - (shape.getRadius()+myShape.getRadius());
         if ( closest == null ) {
                 closest = shape;
                 min_dist = new_dist;
         } else if ( new_dist < min_dist ) {
                 closest = shape;
                 min_dist = new_dist;
         }
     }//for
     return closest;
 }//end of getClosest
 
 public void run(){
     while ( true ) {
     	for(int i=0; i<shapes.size(); i++)
     		((Animation) shapes.get(i)).play();
         try {
             Thread.sleep( Animation.sleep_time );
         } catch ( InterruptedException ie ) {
             System.out.println( ie.getMessage() );
         }
     }
 }

}// End of class ShapesList
/**
 * This interface contains the methods that a Shape object can perform through its ShapesWorld-reference.
 */
interface ShapesWorld
{
        /**
         * returns the smallest visible x-coordinate of the ShapesWorld-object
         */
        public int getMin_X();

        /**
         * returns the smallest visible y-coordinate of the ShapesWorld-object
         */
        public int getMin_Y();

        /**
         * returns the biggest visible x-coordinate of the ShapesWorld-object
         */
        public int getMax_X();

        /**
         * returns the biggest visible y-coordinate of the ShapesWorld-object
         */
        public int getMax_Y();
        
        /**
         * returns the background color of the ShapesWorld-object
         */
        public Color getBackgroundColor();

        /**
         * returns the reference of the closest object to myShape. If only the myShape object
         * exists in the ShapesWorld-object it returns the null constant.
         */
        public Shape getClosestShape( Shape myShape );

        /**
         * a new shape object is added to the ShapesWorld-object
         */
        public void addShape( Shape aNewShape );

         /**
          * removes a shape from the ShapesWorld-object
          */
        public void removeShape( Shape toBeRemoved );
        
} // end of the ShapesWorld interface

class GoAndBack implements Shape,Animation{
	double radius;
	Point center;
    Random rand;
   
    Color color;
    ShapesWorld welt;
    double velocity = 2;

   public GoAndBack() {	
	   this.radius = 25;
	   this.color = Color.CYAN; 
	   this.center = new Point();
	   this.rand = new Random();
   }

   public Color getColor()
   { return color; }
   
   public void moveTo(double x, double y){
	  	center.x = (int) x;
	  	center.y = (int) y;
   }

   public void setShapesWorld( ShapesWorld theWorld )
   { 
	   this.welt = theWorld;
   }   

   public void draw(Graphics g) {
	    g.setColor(color);
	    fillTriangle(g, center.x-radius, center.y-radius, radius*2, radius*2);
   }
   
   public void fillTriangle(Graphics g, double x, double y, double w, double h){
   	int[] x_coords = { (int) (x+w/2), (int) (x), (int) (x+w) };
		   int[] y_coords = { (int) (y), (int) (y+h), (int) (y+h) };
		   Polygon p = new Polygon(x_coords, y_coords, 3);
		   g.fillPolygon(p);
   }
   
   public Point getCenter() {
	   return center;
   }

   public void userClicked(double atX, double atY){ 
	   this.radius += 2;
	   this.welt.addShape(new GoAndBack());
   }
   
   public void userTyped(char key){
	   System.out.println("key");
   }

   // implement the Animation-Interface
   public void play(){
	   	int i=0;
	    if ( (center.y+radius)<welt.getMax_Y() && (i==0) )
	 	   center.y = center.y + velocity;
	    if((center.y+radius)==welt.getMax_Y())
	    	i=1;
	    if((center.y-radius>welt.getMin_Y()&& (i==1)))
	    	center.y=center.y-velocity;
	    if((center.y+radius)==welt.getMin_Y())
	    	i=0;
   }

	public boolean contains(double x, double y) {
		if (y<(center.y-radius) || y>center.y+radius || x<(center.x-radius) || x>(center.x+radius))
		    return false;
		else
			return true;
	}
	
	public double getRadius() {
		return radius;
	}
}
public class ShapesWorld_Main {
	/**
	 * @param args 
	 * has the name of the shape classes, we want to use in the ShapesWorld-object.
	 */
	public static void main(String[] args) 
	{
		if (args.length==0) {
			System.err.println("Bitte Shape-Klassennamen als Argumente eingeben");
		}
		new ShapeFrame(args);
	}
} // end of class ShapesWorld_Main
