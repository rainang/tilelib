package com.github.rainang.tilelib.layout;

import com.github.rainang.tilelib.board.HexFinder;
import com.github.rainang.tilelib.point.Point;
import com.github.rainang.tilelib.point.PointD;

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
	
	public abstract PointD toPixel(Point p);
	
	public abstract Point fromPixel(PointD p);
	
	public PointD corner(int corner)
	{
		return corners[corner];
	}
	
	public PointD corner(PointD p, int corner)
	{
		return p.add(corner(corner));
	}
	
	public void polygonCorners(Point p, BiConsumer<PointD, Integer> consumer)
	{
		PointD center = toPixel(p);
		for (int i = 0; i < 6; i++)
			consumer.accept(corner(center, i), i);
		consumer.accept(center, 6);
	}
	
	public static class Quad extends Layout
	{
		public Quad(PointD size, PointD origin)
		{
			super(size, origin, 4);
			corners[0] = PointD.create(-size.x(), -size.y());
			corners[1] = PointD.create(size.x(), -size.y());
			corners[2] = PointD.create(size.x(), size.y());
			corners[3] = PointD.create(-size.x(), size.y());
		}
		
		public PointD toPixel(Point p)
		{
			return PointD.create(p.x() * size.x(), p.y() * size.y());
		}
		
		public Point fromPixel(PointD p)
		{
			return Point.create((int) (p.x() / size.x()), (int) (p.y() / size.y()));
		}
	}
	
	public static class Hex extends Layout
	{
		public final HexOrientation orientation;
		
		public Hex(HexOrientation orientation, PointD size, PointD origin, double offset)
		{
			super(size, origin, 6);
			this.orientation = orientation;
			
			for (int i = 0; i < 6; i++)
			{
				double angle = Math.PI * (orientation.startAngle + i) / 3;
				double x = size.x() * Math.cos(angle);
				double y = size.y() * Math.sin(angle);
				
				if (offset != 0)
				{
					if (orientation == HexOrientation.POINTY)
					{
						double d = size.x() < 0 ? -offset : offset;
						if (i == 0 || i == 5)
							x += d;
						else if (i == 2 || i == 3)
							x -= d;
						d = size.y() < 0 ? -offset : offset;
						if (i == 0 || i == 1 || i == 2)
							y += d;
						else
							y -= d;
					} else
					{
						double d = size.x() < 0 ? -offset : offset;
						if (i == 0 || i == 1 || i == 5)
							x += d;
						else
							x -= d;
						d = size.y() < 0 ? -offset : offset;
						if (i == 1 || i == 2)
							y += d;
						else if (i == 4 || i == 5)
							y -= d;
					}
				}
				corners[i] = PointD.create(x, y);
			}
		}
		
		@Override
		public PointD toPixel(Point p)
		{
			double x = (orientation.f[0] * p.x() + orientation.f[1] * p.y()) * size.x() + origin.x();
			double y = (orientation.f[2] * p.x() + orientation.f[3] * p.y()) * size.y() + origin.y();
			
			return PointD.create(x, y);
		}
		
		@Override
		public Point fromPixel(PointD p)
		{
			double x = (p.x() - origin.x()) / size.x();
			double y = (p.y() - origin.y()) / size.y();
			double q = orientation.b[0] * x + orientation.b[1] * y;
			double r = orientation.b[2] * x + orientation.b[3] * y;
			return HexFinder.round(PointD.createHex(q, r));
		}
	}
}