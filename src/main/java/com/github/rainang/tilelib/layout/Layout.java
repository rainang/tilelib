package com.github.rainang.tilelib.layout;

import com.github.rainang.tilelib.board.HexFinder;
import com.github.rainang.tilelib.point.MutablePoint;
import com.github.rainang.tilelib.point.MutablePointD;
import com.github.rainang.tilelib.point.Point;
import com.github.rainang.tilelib.point.PointD;
import com.github.rainang.tilelib.point.Points;

import java.util.function.BiConsumer;

public abstract class Layout
{
	public final PointD size;
	
	public final PointD origin;
	
	final PointD[] corners;
	
	public Layout(PointD size, PointD origin, int sides)
	{
		this.size = size;
		this.origin = origin;
		this.corners = new PointD[sides];
	}
	
	public abstract MutablePointD toPixel(Point p, MutablePointD dest);
	
	public abstract MutablePoint fromPixel(PointD p, MutablePoint dest);
	
	public PointD corner(int corner)
	{
		return corners[corner];
	}
	
	public MutablePointD corner(PointD p, int corner, MutablePointD dest)
	{
		return dest.set(p)
				   .add(corner(corner));
	}
	
	public void polygonCorners(Point p, BiConsumer<Integer, MutablePointD> consumer)
	{
		PointD pd = toPixel(p, p.asDoubleMutable());
		MutablePointD temp = toPixel(p, p.asDoubleMutable());
		for (int i = 0; i < 6; i++)
			consumer.accept(i, corner(pd, i, temp));
		consumer.accept(6, temp.set(pd));
	}
	
	public static class Quad extends Layout
	{
		public Quad(PointD size, PointD origin)
		{
			super(size, origin, 4);
			corners[0] = Points.doubleAt(-size.x(), -size.y());
			corners[1] = Points.doubleAt(size.x(), -size.y());
			corners[2] = Points.doubleAt(size.x(), size.y());
			corners[3] = Points.doubleAt(-size.x(), size.y());
		}
		
		@Override
		public MutablePointD toPixel(Point p, MutablePointD dest)
		{
			return dest.set(p.x() * size.x(), p.y() * size.y());
		}
		
		@Override
		public MutablePoint fromPixel(PointD p, MutablePoint dest)
		{
			return dest.set((int) (p.x() / size.x()), (int) (p.y() / size.y()));
		}
	}
	
	public static class Hex extends Layout
	{
		private final MutablePointD temp = Points.doubleOriginZ();
		
		public final HexOrientation orientation;
		
		public Hex(HexOrientation orientation, PointD size, PointD origin)
		{
			super(size, origin, 6);
			this.orientation = orientation;
			
			for (int i = 0; i < 6; i++)
			{
				double angle = Math.PI * (orientation.startAngle + i) / 3;
				double x = size.x() * Math.cos(angle);
				double y = size.y() * Math.sin(angle);
				corners[i] = Points.doubleHexAt(x, y);
			}
		}
		
		@Override
		public MutablePointD toPixel(Point p, MutablePointD dest)
		{
			double x = (orientation.f[0] * p.x() + orientation.f[1] * p.y()) * size.x() + origin.x();
			double y = (orientation.f[2] * p.x() + orientation.f[3] * p.y()) * size.y() + origin.y();
			
			return dest.set(x, y);
		}
		
		@Override
		public MutablePoint fromPixel(PointD p, MutablePoint dest)
		{
			double x = (p.x() - origin.x()) / size.x();
			double y = (p.y() - origin.y()) / size.y();
			double q = orientation.b[0] * x + orientation.b[1] * y;
			double r = orientation.b[2] * x + orientation.b[3] * y;
			return HexFinder.round(temp.set(q, r, -q - r), dest);
		}
	}
}