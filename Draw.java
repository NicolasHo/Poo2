import java.awt.Graphics;
import java.awt.Color;
import javax.swing.JPanel;
import java.util.concurrent.TimeUnit;
import java.util.*;
 

public class Draw extends JPanel {

	private Vector<Shape> sh;
	public Draw(Shape s)
	{
		sh= new Vector<Shape>();
		sh.add(s);
		sh.add(new Shape());
		sh.elementAt(1).position.x=400;
		sh.add(new Shape());
		sh.elementAt(2).position.y=250;
		sh.add(new Shape());
		sh.elementAt(3).position.x=400;
		sh.elementAt(3).position.y=250;
  		for (int i=0;i<1;i++)
  			sh.elementAt(0).test();
  		for (int i=0;i<2;i++)
  			sh.elementAt(1).test();
  		for (int i=0;i<3;i++)
  			sh.elementAt(2).test();
  		for (int i=0;i<4;i++)
  			sh.elementAt(3).test(); 
	}

  	public void paintComponent(Graphics g)
  	{

	  	for (Shape s: sh) 
	  	{
		    g.setColor(Color.BLUE); 
			for(Ligne l : s.ligne)
				g.drawLine((int)(s.position.x+l.pDepart.x), (int)(s.position.y+l.pDepart.y), (int)(s.position.x+l.pArrivee.x), (int)(s.position.y+l.pArrivee.y));
			
			for(Rectangle rect : s.rect)
			{
		    	g.setColor(Color.RED);
		    	g.drawRect ((int)(s.position.x+rect.min.x), (int)(s.position.y+rect.min.y), (int)(rect.max.x-rect.min.x), (int)(rect.max.y-rect.min.y)); 

			    g.setColor(Color.BLACK); 
				for(Ligne l : rect.ligne)
					g.drawLine((int)(s.position.x+l.pDepart.x), (int)(s.position.y+l.pDepart.y), (int)(s.position.x+l.pArrivee.x), (int)(s.position.y+l.pArrivee.y));
			}

  		}  
  	}             

}