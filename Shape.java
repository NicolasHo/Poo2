import java.util.*;

public class Shape
{	
	public Vector<Ligne> ligne;
	public Vector<Rectangle> rect;
	public Vector<Rectangle> creux; 
	public Vector<Ligne> top; 
	public Vector<Ligne> bot; 
	public Point max;
	public Point position;

	private int sh;
	private static int sh_g=0;

	public Shape()
	{
		sh=sh_g++;
		init_tmp();
		setTop();
		setBot();
	}

	public Shape(Vector<Ligne> l)
	{
		sh=sh_g++;
		ligne=l;
		rect=new Vector<Rectangle>();
		rect.add(new Rectangle(l));
		max=rect.elementAt(0).max;
		//position=rect.elementAt(0).min.clone();
		System.out.println("x:"+rect.elementAt(0).min.x+" y:"+rect.elementAt(0).min.y);
		//position=new Point(rect.elementAt(0).min.x,rect.elementAt(0).min.y);
		position=new Point((-1)*rect.elementAt(0).min.x,(-1)*rect.elementAt(0).min.y);
		setTop();
		setBot();
	}



	public void test()
	{
		System.out.println("test :" + sh);
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


	private void init_tmp()
	{
		ligne=new Vector<Ligne>();
		rect= new Vector<Rectangle>();
		position= new Point(0,0);

		ligne.add(new Ligne(0,0,200,70));
		ligne.add(new Ligne(200,70,300,200));
		ligne.add(new Ligne(300,200,50,150));
		ligne.add(new Ligne(50,150,50,150));
		ligne.add(new Ligne(50,150,0,0));

		rect.add(new Rectangle(ligne));

		max=rect.elementAt(0).max;	
	}

	public Shape Clone()
	{
		Shape s=new Shape();
		s.ligne=Ligne.vect_clone(ligne);
		s.rect=Rectangle.vect_clone(rect);
		//s.creux=(Vector)creux.clone();
		s.max=max.clone();
		s.position=position.clone();
		return s;
	}

	
}