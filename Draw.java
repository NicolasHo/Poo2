import java.awt.Graphics;
import java.awt.Color;
import javax.swing.JPanel; 
import java.util.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileNotFoundException;
import java.io.IOException;


public class Draw extends JPanel 
{
	private Placement sh;
	public Draw()
	{

		try
		{	
			Parser.lire("./svg/dessin_complex2.svg");
		}
		catch(IOException exc)
		{
			System.out.println("Erreur d'ouverture");
			return;
		}


		//sh=Parser.formes;

		sh=new Placement(Parser.formes);

	//	for (int i=0;i<1;i++)
	//		sh.elementAt(2).test();
		
	  	for (Shape shape: sh.s)
  			for (int i=0;i<0;i++)
  				shape.test();

		sh.replaceAll();

	}

  	public void paintComponent(Graphics g)
  	{
  		//double offset_x=0,offset_y=100;
	  	for (Shape s: sh.s) 
	  	{
		    g.setColor(Color.BLUE);
		    System.out.println("-----------------");	
		    //System.out.println("x:" + s.position.x +" y:" + s.position.y);	
	  		g.drawOval((int)s.position.x/5,(int)s.position.y/5,5,5);
		    g.setColor(Color.BLACK);
		   	System.out.println("x:" + s.min.x +" y:" + s.min.y);	
		   	System.out.println("x:" + s.max.x +" y:" + s.max.y);		
	  		g.drawOval((int)(s.position.x+s.max.x)/5,(int)(s.position.y+s.max.y)/5,5,5);
		    g.setColor(Color.RED);
	  		g.drawOval((int)(s.position.x+s.min.x)/5,(int)(s.position.y+s.min.y)/5,5,5);
		    g.setColor(Color.BLACK); 
			for(Ligne l : s.ligne)
				g.drawLine((int)(s.position.x+l.pDepart.x)/5, (int)(s.position.y+l.pDepart.y)/5,
				 (int)(s.position.x+l.pArrivee.x)/5, (int)(s.position.y+l.pArrivee.y)/5);
			
			for(Rectangle rect : s.rect)
			{
				/*
			    g.setColor(Color.BLACK); 
				for(Ligne l : rect.ligne)
					g.drawLine((int)(s.position.x+l.pDepart.x)/5, (int)(s.position.y+l.pDepart.y)/5,
					 (int)(s.position.x+l.pArrivee.x)/5, (int)(s.position.y+l.pArrivee.y)/5);
				*/
		    	g.setColor(Color.RED);
		    	g.drawRect ((int)(s.position.x+rect.min.x)/5, (int)(s.position.y+rect.min.y)/5,
		    	 (int)(rect.max.x-rect.min.x)/5, (int)(rect.max.y-rect.min.y)/5); 
			}

			/*
		    g.setColor(Color.BLUE);
			for(Ligne l : s.top)
				g.drawLine((int)(s.position.x+l.pDepart.x)/5, (int)(s.position.y+l.pDepart.y-10)/5,
				 (int)(s.position.x+l.pArrivee.x)/5, (int)(s.position.y+l.pArrivee.y-10)/5);
			
		    g.setColor(Color.BLUE);
			for(Ligne l : s.bot)
				g.drawLine((int)(s.position.x+l.pDepart.x)/5, (int)(s.position.y+l.pDepart.y+10)/5,
				 (int)(s.position.x+l.pArrivee.x)/5, (int)(s.position.y+l.pArrivee.y+10)/5);

			*/
  		}  
  	}             

}