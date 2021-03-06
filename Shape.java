import java.util.*;

public class Shape
{	
	public Vector<Ligne> ligne;
	public Vector<Rectangle> rect;
	public Vector<Rectangle> creux; 
	public Vector<Ligne> top; 
	public Vector<Ligne> bot; 
	public Point max;
	public Point min;
	public Point position;

	public int order;
	public double taille;

	private int sh;
	private static int sh_g=0;

	public Shape()
	{
	}

	public Shape(Vector<Ligne> l)
	{
		sh=sh_g++;
		ligne=Ligne.conv_curve(l);
		rect=new Vector<Rectangle>();
		rect.add(new Rectangle(ligne));
		min=rect.elementAt(0).min.clone();
		max=rect.elementAt(0).max.clone();
		//position=rect.elementAt(0).min.clone();
		System.out.println("x:"+rect.elementAt(0).min.x+" y:"+rect.elementAt(0).min.y);
		//position=new Point(rect.elementAt(0).min.x,rect.elementAt(0).min.y);
		position=new Point((-1)*rect.elementAt(0).min.x,(-1)*rect.elementAt(0).min.y);
		setTop();
		setBot();
	}

	public void test()
	{
		//System.out.println("test :" + sh);
		Vector<Rectangle> tmp=new Vector<Rectangle>();
		Point moy= new Point(0,0);
		for (Rectangle r:rect)
			moy.moyenne(r.min, r.max);
		moy.x=moy.x/rect.size();
		moy.y=moy.y/rect.size();
		int i=0;
		for (Rectangle r:rect)
		{
			tmp.addAll(r.subdiviser_rect(moy));
		}
		rect=tmp;
		setTop();
		setBot();
	}

	public void setP(double x, double y)
	{
		position.set((-1)*min.x+x,(-1)*min.y+y);
	}

	private void setTop()
	{
		Vector<Ligne> new_t=new Vector<Ligne>();
		int i;
		boolean ins;
		for (Rectangle r:rect)
		{

			ins=true;
			for (i=0;i<new_t.size();i++) 
			{
				if (r.min.x<new_t.elementAt(i).max.x && r.max.x>new_t.elementAt(i).min.x) 
				{
					if (r.min.y > new_t.elementAt(i).min.y) 
					{
						ins=false;
					}	
				}
			}
			if (ins)
				if(i==new_t.size())
					new_t.add(new Ligne(r.min,new Point(r.max.x, r.min.y)));
				//else
				//	insert(new_t,i,r);
			
		}
		top=new_t;
	}

	private void setBot()
	{
		Vector<Ligne> new_t=new Vector<Ligne>();
		int i;
		boolean ins;
		for (Rectangle r:rect)
		{

			ins=true;
			for (i=0;i<new_t.size();i++) 
			{
				if (r.min.x<new_t.elementAt(i).max.x && r.max.x>new_t.elementAt(i).min.x) 
				{
					if (r.max.y < new_t.elementAt(i).max.y) 
					{
						ins=false;
					}	
				}
			}
			if (ins)
				if(i==new_t.size())
					new_t.add(new Ligne(new Point(r.min.x, r.max.y), r.max));
				//else
				//	insert(new_t,i,r);
			
		}
		bot=new_t;
	}

	private  Vector<Ligne> insert(Vector<Ligne> tab, int i, Ligne val)
	{
		Vector<Ligne> new_t=new Vector<Ligne>();
		for(int j=0; j<tab.size();j++)
		{
			if (j == i)
				new_t.add(val);
	 		new_t.add(tab.elementAt(j));
		}
		return new_t;
	}

	public Shape Clone()
	{
		Shape s=new Shape();
		s.ligne=Ligne.vect_clone(ligne);
		s.rect=Rectangle.vect_clone(rect);
		//s.creux=(Vector)creux.clone();
		s.max=max;
		s.min=min;
		s.position=position.clone();
		return s;
	}
}