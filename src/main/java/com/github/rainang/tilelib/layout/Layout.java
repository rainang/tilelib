package com.github.rainang.tilelib.layout;

import com.github.rainang.tilelib.coordinates.Coordinate;
import com.github.rainang.tilelib.coordinates.CoordinateD;
import com.github.rainang.tilelib.coordinates.HexCoordinate;

import java.util.function.BiConsumer;

public abstract class Layout<C extends Coordinate>
{
	public final CoordinateD size;
	
	public final CoordinateD origin;
	
	final CoordinateD[] corners;
	
	public Layout(CoordinateD size, CoordinateD origin, int sides)
	{
		this.size = size;
		this.origin = origin;
		this.corners = new CoordinateD[sides];
	}
	
	public abstract CoordinateD toPixel(C c);
	
	public abstract Coordinate fromPixel(CoordinateD c);
	
	public CoordinateD corner(int corner)
	{
		return corners[corner];
	}
	
	public CoordinateD corner(CoordinateD c, int corner)
	{
		return c.add(corner(corner));
	}
	
	public void polygonCorners(C c, BiConsumer<CoordinateD, Integer> consumer)
	{
		CoordinateD center = toPixel(c);
		for (int i = 0; i < 6; i++)
			consumer.accept(corner(center, i), i);
		consumer.accept(center, 6);
	}
	
	public static class Quad extends Layout<Coordinate>
	{
		public Quad(CoordinateD size, CoordinateD origin)
		{
			super(size, origin, 4);
			corners[0] = CoordinateD.create(-size.x(), -size.y());
			corners[1] = CoordinateD.create(size.x(), -size.y());
			corners[2] = CoordinateD.create(size.x(), size.y());
			corners[3] = CoordinateD.create(-size.x(), size.y());
		}
		
		public CoordinateD toPixel(Coordinate c)
		{
			return CoordinateD.create(c.x() * size.x(), c.y() * size.y());
		}
		
		public Coordinate fromPixel(CoordinateD c)
		{
			return Coordinate.create((int) (c.x() / size.x()), (int) (c.y() / size.y()));
		}
	}
	
	public static class Hex extends Layout<HexCoordinate>
	{
		public final HexOrientation orientation;
		
		public Hex(HexOrientation orientation, CoordinateD size, CoordinateD origin, double offset)
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
				corners[i] = CoordinateD.create(x, y);
			}
		}
		
		@Override
		public CoordinateD toPixel(HexCoordinate hex)
		{
			double x = (orientation.f[0] * hex.x() + orientation.f[1] * hex.y()) * size.x() + origin.x();
			double y = (orientation.f[2] * hex.x() + orientation.f[3] * hex.y()) * size.y() + origin.y();
			
			return CoordinateD.create(x, y);
		}
		
		@Override
		public HexCoordinate fromPixel(CoordinateD c)
		{
			double x = (c.x() - origin.x()) / size.x();
			double y = (c.y() - origin.y()) / size.y();
			double q = orientation.b[0] * x + orientation.b[1] * y;
			double r = orientation.b[2] * x + orientation.b[3] * y;
			return HexCoordinate.create(q, r, -q - r)
								.round();
		}
	}
}