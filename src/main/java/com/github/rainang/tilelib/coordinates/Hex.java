package com.github.rainang.tilelib.coordinates;

import java.util.function.BiConsumer;

import static java.lang.Math.sqrt;

public class Hex
{
	public enum Orientation
	{
		POINTY(new double[] {
				sqrt(3.0),
				sqrt(3.0) / 2.0,
				0.0,
				3.0 / 2.0
		}, new double[] {
				sqrt(3.0) / 3.0,
				-1.0 / 3.0,
				0.0,
				2.0 / 3.0
		}, 0.5),
		FLAT(new double[] {
				3.0 / 2.0,
				0.0,
				sqrt(3.0) / 2.0,
				sqrt(3.0)
		}, new double[] {
				2.0 / 3.0,
				0.0,
				-1.0 / 3.0,
				sqrt(3.0) / 3.0
		}, 0);
		
		private final double[] f;
		private final double[] b;
		private final double startAngle;
		
		Orientation(double[] f, double[] b, double startAngle)
		{
			this.f = f;
			this.b = b;
			this.startAngle = startAngle;
		}
	}
	
	public static class Layout
	{
		public final Orientation orientation;
		
		public final CoordinateD size;
		
		public final CoordinateD origin;
		
		private final CoordinateD[] corners = new CoordinateD[6];
		
		public Layout(Orientation orientation, CoordinateD size, CoordinateD origin, double offset)
		{
			this.orientation = orientation;
			this.size = size;
			this.origin = origin;
			
			for (int i = 0; i < 6; i++)
			{
				double angle = Math.PI * (orientation.startAngle + i) / 3;
				double x = size.x() * Math.cos(angle);
				double y = size.y() * Math.sin(angle);
				
				if (offset != 0)
				{
					if (orientation == Orientation.POINTY)
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
				corners[i] = new CoordinateD(x, y);
			}
		}
		
		public CoordinateD hexToPixel(HexCoordinate hex)
		{
			double x = (orientation.f[0] * hex.x() + orientation.f[1] * hex.y()) * size.x() + origin.x();
			double y = (orientation.f[2] * hex.x() + orientation.f[3] * hex.y()) * size.y() + origin.y();
			
			return new CoordinateD(x, y);
		}
		
		public HexCoordinate pixelToHex(CoordinateD c)
		{
			double x = (c.x() - origin.x()) / size.x();
			double y = (c.y() - origin.y()) / size.y();
			double q = orientation.b[0] * x + orientation.b[1] * y;
			double r = orientation.b[2] * x + orientation.b[3] * y;
			return HexCoordinate.create(q, r, -q - r)
								.round();
		}
		
		public CoordinateD hexCorner(int corner)
		{
			return corners[corner];
		}
		
		public CoordinateD hexCorner(CoordinateD c, int corner)
		{
			return c.add(hexCorner(corner));
		}
		
		public void polygonCorners(HexCoordinate hex, BiConsumer<CoordinateD, Integer> consumer)
		{
			CoordinateD center = hexToPixel(hex);
			for (int i = 0; i < 6; i++)
				consumer.accept(hexCorner(center, i), i);
			consumer.accept(center, 6);
		}
	}
}
