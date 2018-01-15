import java.awt.Graphics;
import java.awt.Color;
import javax.swing.JPanel; 
import java.util.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileNotFoundException;
import java.io.IOException;


class Placement extends JPanel 
{
	public Vector<Shape> s;

	private boolean show_rect;
	private boolean show_point;
	private boolean show_limit;
	private int algo;


	public Placement()
	{
		s= new Vector<Shape>();
		show_point=false;
		show_rect=false;
		show_limit=false;
		algo=1;
	}


	public Placement(Vector<Shape> vct_s)
	{
		s=vct_s;
		show_point=false;
		show_rect=false;
		order();
	}

	private void order()
	{
		int ord=0,id=-1;
		double tmp;
		Vector<Shape> vct_s= new Vector<Shape>();
		for(Shape shape: s)
		{
			shape.order=-1;
			shape.taille=(shape.max.x-shape.min.x)*(shape.max.y-shape.min.y);
		}

		for (int i=0;i<s.size();i++) 
		{
			tmp=-1;
			for (int j=0;j<s.size();j++) 
			{
				if (s.elementAt(j).order == -1 && tmp<s.elementAt(j).taille)
				{
					tmp=s.elementAt(j).taille;
					id=j;
				}
			}
			s.elementAt(id).order=ord++;
			vct_s.add(s.elementAt(id));
		}
		s=vct_s;

	}

	public void setAlgo(int a)
	{
		algo=a;
		replaceAll();
		repaint();
	}

	public void setLimit(boolean b)
	{
		show_limit=b;
		repaint();
	}

	public void setRect(boolean b)
	{
		show_rect=b;
		repaint();
	}

	public void setPoint(boolean b)
	{
		show_point=b;
		repaint();
	}

	public void setFic(String path, boolean ext)
	{
		Parser p= new Parser(path);
		if(path!="")
		{
			try
			{	
				p.lire();
			}
			catch(IOException exc)
			{
				System.out.println("Erreur d'ouverture");
				return;
			}		
			if(ext)
				s.addAll(p.formes);
			else
				s=p.formes;

			order();

		  	for (Shape shape: s)
	  			for (int i=0;i<3;i++)
	  				shape.test();

			replaceAll();
			repaint();
		}
	}

	public void replaceAll()
	{
		Vector<Shape> vct_s=new Vector<Shape>();
		for(Shape shape: s)
			replace(vct_s, shape);
	}

	private void replace(Vector<Shape> vct_s, Shape sh)
	{
  		double offset_x=99999999,offset_y=0;
  		double height=(3543.0-(sh.max.y-sh.min.y))/1400.0;
  		//double height=3543.0/6.0;
  		for (int i=0;i<1400;i++) 
  		{
  			double tmp_x=0;
  			double tmp_y=height*i;
  			for(Shape shape: vct_s)
			{
				if(algo==1)
				{
					if ((shape.position.y+shape.min.y)<(height*i+sh.max.y-sh.min.y)
						&& (shape.position.y+shape.max.y)>height*i) 
					{
						if(tmp_x<(shape.position.x+shape.max.x))
						{
							tmp_x=shape.position.x+shape.max.x;
						}
					}
				}
				else if(algo==2)
				{
					if ((shape.position.y+shape.min.y)<(height*i+sh.max.y-sh.min.y)
						&& (shape.position.y+shape.max.y)>height*i) 
					{
						for(Rectangle r1:shape.rect)
						{
							for(Rectangle r2: sh.rect)
							{			
								if ((shape.position.y+r1.min.y)<(height*i+(-1)*sh.min.y+r2.max.y)
									&& (shape.position.y+r1.max.y)>(height*i+(-1)*sh.min.y+r2.min.y)) 
								{
									if(tmp_x<(shape.position.x+r1.max.x))
									{
										tmp_x=shape.position.x+r1.max.x;
									}
								}
							}
						}
					}
				}
			}
			if(tmp_x<offset_x)
			{
				offset_x=tmp_x;
				offset_y=tmp_y;
			}
  		}
		sh.setP(offset_x,offset_y);
		vct_s.add(sh);
	}

  	public void paintComponent(Graphics g)
  	{
		super.paintComponent(g);

	  	for (Shape sh: s)
	  	{
		  	if (show_point) 
		  	{
			    g.setColor(Color.BLUE);
			    //System.out.println("-----------------");	
			    //System.out.println("x:" + s.position.x +" y:" + s.position.y);	
		  		g.drawOval((int)sh.position.x/5,(int)sh.position.y/5,5,5);
			    g.setColor(Color.BLACK);
			   	//System.out.println("x:" + sh.min.x +" y:" + sh.min.y);	
			   	//System.out.println("x:" + sh.max.x +" y:" + sh.max.y);		
		  		g.drawOval((int)(sh.position.x+sh.max.x)/5,(int)(sh.position.y+sh.max.y)/5,5,5);
			    g.setColor(Color.RED);
		  		g.drawOval((int)(sh.position.x+sh.min.x)/5,(int)(sh.position.y+sh.min.y)/5,5,5);	
	  		}
		    g.setColor(Color.BLACK); 
			for(Ligne l : sh.ligne)
			{
			/*
				if(Point.equal(l.pDepart, l.tireur1))
				{
		    		g.setColor(Color.BLACK); 
			*/
					g.drawLine((int)(sh.position.x+l.pDepart.x)/5, (int)(sh.position.y+l.pDepart.y)/5,
				 		(int)(sh.position.x+l.pArrivee.x)/5, (int)(sh.position.y+l.pArrivee.y)/5);
			/*	}
				else
				{
		   	 		g.setColor(Color.RED); 
					g.drawLine((int)(s.position.x+l.pDepart.x)/5, (int)(s.position.y+l.pDepart.y)/5,
				 		(int)(s.position.x+l.pArrivee.x)/5, (int)(s.position.y+l.pArrivee.y)/5);
				}
			*/
			}
			if (show_rect)
			{
				g.setColor(Color.RED);
				if(algo==1)
				{
				    	g.drawRect ((int)(sh.position.x+sh.min.x)/5, (int)(sh.position.y+sh.min.y)/5,
				    	 (int)(sh.max.x-sh.min.x)/5, (int)(sh.max.y-sh.min.y)/5); 
				}
				else if(algo==2)
				{
					for(Rectangle rect : sh.rect)
					{
						/*
					    g.setColor(Color.BLACK); 
						for(Ligne l : rect.ligne)
							g.drawLine((int)(s.position.x+l.pDepart.x)/5, (int)(s.position.y+l.pDepart.y)/5,
							 (int)(s.position.x+l.pArrivee.x)/5, (int)(s.position.y+l.pArrivee.y)/5);
						*/
				    	g.drawRect ((int)(sh.position.x+rect.min.x)/5, (int)(sh.position.y+rect.min.y)/5,
				    	 (int)(rect.max.x-rect.min.x)/5, (int)(rect.max.y-rect.min.y)/5);

				    	if (show_limit)
						{
		   					g.setColor(Color.BLUE); 
							g.drawLine((int)(sh.position.x+rect.max.x)/5, (int)(sh.position.y+rect.min.y)/5,
				 			(int)(sh.position.x+rect.max.x)/5, (int)(sh.position.y+rect.max.y)/5);
		   					g.setColor(Color.RED); 
						}

					}
				}	
			}

			
			if (show_limit)
			{
			    g.setColor(Color.GREEN);
				for(Ligne l : sh.top)
					g.drawLine((int)(sh.position.x+l.pDepart.x)/5, (int)(sh.position.y+l.pDepart.y-10)/5,
					 (int)(sh.position.x+l.pArrivee.x)/5, (int)(sh.position.y+l.pArrivee.y-10)/5);
				
			    g.setColor(Color.ORANGE);
				for(Ligne l : sh.bot)
					g.drawLine((int)(sh.position.x+l.pDepart.x)/5, (int)(sh.position.y+l.pDepart.y+10)/5,
					 (int)(sh.position.x+l.pArrivee.x)/5, (int)(sh.position.y+l.pArrivee.y+10)/5);
			}
			
  		}  
  	}  
}