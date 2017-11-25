import java.util.*;

class Ligne
{
	public Point pDepart;
	public Point pArrivee;
	public Point tireur1;
	public Point tireur2;
	public Point min;
	public Point max;

	public Ligne(Point point1, Point point2, Point t1, Point t2)
	{
		pDepart=point1;
		pArrivee=point2;
		tireur1=t1;
		tireur2=t2;
		min=Point.min2(pDepart,pArrivee);
		max=Point.max2(pDepart,pArrivee);
	}
	public Ligne(double x1, double y1, double x2, double y2)
	{
		pDepart=new Point(x1,y1);
		pArrivee=new Point(x2,y2);
		min=Point.min2(pDepart,pArrivee);
		max=Point.max2(pDepart,pArrivee);
	}
	public Ligne()
	{

	}

	public double abscisse(double a)
	{
		double coeffD=(max.x-min.x)/(max.y-min.y);
		double val=(min.x+coeffD*(a-min.y));
		if (val<min.x)
			val=min.x;
		if (val>max.x)
			val=max.x;
		return  val;
	}

	public double ordonnee(double a)
	{
		double coeffD=(max.y-min.y)/(max.x-min.x);
		double val=(min.y+coeffD*(a-min.x));
		if (val<min.y)
			val=min.y;
		if (val>max.y)
			val=max.y;
		return val;
	}

	public void affiche()
	{
		System.out.println("[" + pDepart.affiche() + " " + pArrivee.affiche() + " " + tireur1.affiche() + " " + tireur2.affiche() + "]");
	}

	public Ligne clone()
	{
		Ligne l =new Ligne();
		l.pDepart=pDepart.clone();
		l.pArrivee=pArrivee.clone();
		//l.tireur1=tireur1.clone();;
		//l.tireur2=tireur2.clone();;
		l.min=min.clone();
		l.max=max.clone();
		return l;
	}

	public static Vector<Ligne> vect_clone(Vector<Ligne> lignes)
	{
		Vector<Ligne> l=new Vector<Ligne>();
		for (Ligne ligne:lignes)
			l.add(ligne.clone());
		return l;
	}
}