import java.util.*;

public class Shape
{	
	public Vector<Ligne> ligne;
	public Vector<Rectangle> rect;
	public Vector<Rectangle> creux; 
	public Point max;
	public Point position;

	public Shape()
	{
		init_tmp();
	}

	public Shape(Vector<Ligne> l)
	{
		ligne=l;
		rect=new Vector<Rectangle>();
		rect.add(new Rectangle(l));
		max=rect.elementAt(0).max;
		position=rect.elementAt(0).min;
	}

	public void test()
	{
		Vector<Rectangle> tmp=new Vector<Rectangle>();
		Point moy= new Point(0,0);
		for (Rectangle r:rect)
			moy.moyenne(r.min, r.max);
		moy.x=moy.x/rect.size();
		moy.y=moy.y/rect.size();
		for (Rectangle r:rect)
			tmp.addAll(r.subdiviser_rect(moy));
		rect=tmp;
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