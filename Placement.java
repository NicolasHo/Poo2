import java.util.*;

class Placement 
{
	public Vector<Shape> s;

	public Placement(Vector<Shape> vct_s)
	{
		s=vct_s;
		//s=order(vct_s);
	}


	private Vector<Shape> order(Vector<Shape> vct_s)
	{
		return vct_s;
		/*
		sh=new Vector<Shape>();
		for(Shape shape: vct_s)
			shape.order=shape.rect.elementAt(0).x*shape.rect.elementAt(0).y;
		for (int i=0;i<vct_s.size();i++) 
		{
			if () {
				
			}
		}

		return sh;
		*/
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
  		double height=(3543.0-(sh.max.y-sh.min.y)*1.5)/500.0;
  		//double height=3543.0/6.0;
  		for (int i=0;i<500;i++) 
  		{
  			double tmp_x=0;
  			double tmp_y=height*i;
  			for(Shape shape: vct_s)
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
			if(tmp_x<offset_x)
			{
				offset_x=tmp_x;
				offset_y=tmp_y;
			}
  		}
		sh.setP(offset_x,offset_y);
		vct_s.add(sh);
	}


}