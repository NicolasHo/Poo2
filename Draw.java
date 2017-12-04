import java.awt.Graphics;
import java.awt.Color;
import javax.swing.JPanel; 
import java.util.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileNotFoundException;
import java.io.IOException;


public class Draw extends JPanel {

	private Vector<Shape> sh;
	public Draw(Shape s)
	{

		try
		{	
			Parser.lire("./svg/dessin_tetris2.svg");
		}
		catch(IOException exc)
		{
			System.out.println("Erreur d'ouverture");
			return;
		}


		sh=Parser.formes;


	//	for (int i=0;i<1;i++)
	//		sh.elementAt(2).test();
		
	  	for (Shape shape: sh)
  			for (int i=0;i<1;i++)
  				shape.test();
		
		/*
		sh= new Vector<Shape>();
		sh.add(s);
		sh.elementAt(0).position.y=20;
		sh.add(new Shape());
		sh.elementAt(1).position.x=400;
		sh.elementAt(1).position.y=20;
		sh.add(new Shape());
		sh.elementAt(2).position.y=250;
		sh.add(new Shape());
		sh.elementAt(3).position.x=400;
		sh.elementAt(3).position.y=250;
  		for (int i=0;i<2;i++)
  			sh.elementAt(6).test();
  		for (int i=0;i<2;i++)
  			sh.elementAt(1).test();
  		for (int i=0;i<3;i++)
  			sh.elementAt(2).test();
  		for (int i=0;i<4;i++)
  			sh.elementAt(3).test(); */
	}

  	public void paintComponent(Graphics g)
  	{
  		double offset_x=0,offset_y=100;
	  	for (Shape s: sh) 
	  	{
	  		
		    g.setColor(Color.GREEN); 
			for(Ligne l : s.ligne)
				g.drawLine((int)(offset_x+s.position.x+l.pDepart.x)/5, (int)(offset_y+s.position.y+l.pDepart.y)/5,
				 (int)(offset_x+s.position.x+l.pArrivee.x)/5, (int)(offset_y+s.position.y+l.pArrivee.y)/5);
				
			for(Rectangle rect : s.rect)
			{
				/*
			    g.setColor(Color.BLACK); 
				for(Ligne l : rect.ligne)
					g.drawLine((int)(offset_x+s.position.x+l.pDepart.x)/5, (int)(offset_y+s.position.y+l.pDepart.y)/5,
					 (int)(offset_x+s.position.x+l.pArrivee.x)/5, (int)(offset_y+s.position.y+l.pArrivee.y)/5);
				*/
		    	g.setColor(Color.RED);
		    	g.drawRect ((int)(offset_x+s.position.x+rect.min.x)/5, (int)(offset_y+s.position.y+rect.min.y)/5,
		    	 (int)(rect.max.x-rect.min.x)/5, (int)(rect.max.y-rect.min.y)/5); 
			}

			
		    g.setColor(Color.BLUE);
			for(Ligne l : s.top)
				g.drawLine((int)(offset_x+s.position.x+l.pDepart.x)/5, (int)(offset_y+s.position.y+l.pDepart.y-10)/5,
				 (int)(offset_x+s.position.x+l.pArrivee.x)/5, (int)(offset_y+s.position.y+l.pArrivee.y-10)/5);
			
			for(Ligne l : s.bot)
				g.drawLine((int)(offset_x+s.position.x+l.pDepart.x)/5, (int)(offset_y+s.position.y+l.pDepart.y+10)/5,
				 (int)(offset_x+s.position.x+l.pArrivee.x)/5, (int)(offset_y+s.position.y+l.pArrivee.y+10)/5);
			
			
			offset_x+=800;
			if(offset_x>4000)
			{
				offset_x=0;
				offset_y+=800;
			}

  		}  
  	}             

}