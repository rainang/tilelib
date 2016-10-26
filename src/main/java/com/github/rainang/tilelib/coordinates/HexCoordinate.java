package com.github.rainang.tilelib.coordinates;

import java.util.Arrays;

public class HexCoordinate extends Coordinate
{
	public static final HexCoordinate ORIGIN = HexCoordinate.create(0, 0);
	
	public static final int EVEN = 1;
	public static final int ODD = -1;
	
	HexCoordinate(int x, int y)
	{
		super(x, y, -x - y);
	}
	
	@Override
	public HexCoordinate add(Coordinate c)
	{
		return new HexCoordinate(x() + c.x(), y() + c.y());
	}
	
	@Override
	public HexCoordinate sub(Coordinate c)
	{
		return new HexCoordinate(x() - c.x(), y() - c.y());
	}
	
	@Override
	public HexCoordinate mul(int s)
	{
		return new HexCoordinate(x() * s, y() * s);
	}
	
	@Override
	public Double asDouble()
	{
		return new HexCoordinate.Double(x(), y(), z());
	}
	
	public Coordinate toOffsetCol(int offset)
	{
		int col = x();
		int row = y() + (x() + offset * (x() & 1)) / 2;
		return new Coordinate(col, row);
	}
	
	public Coordinate toOffsetRow(int offset)
	{
		int col = x() + (y() + offset * (y() & 1)) / 2;
		int row = y();
		return new Coordinate(col, row);
	}
	
	public int length()
	{
		return (Math.abs(x()) + Math.abs(y()) + Math.abs(z())) / 2;
	}
	
	public int distance(HexCoordinate hex)
	{
		return sub(hex).length();
	}
	
	public HexCoordinate rotate()
	{
		return new HexCoordinate(-z(), -x());
	}
	
	public HexCoordinate rotateCCW()
	{
		return new HexCoordinate(-y(), -z());
	}
	
	private static HexCoordinate fromOffsetCol(int offset, Coordinate c)
	{
		int x = c.x();
		int y = c.y() - (c.x() + offset * (c.x() & 1)) / 2;
		return new HexCoordinate(x, y);
	}
	
	private static HexCoordinate fromOffsetRow(int offset, Coordinate c)
	{
		int x = c.x() - (c.y() + offset * (c.y() & 1)) / 2;
		int y = c.y();
		return new HexCoordinate(x, y);
	}
	
	public static HexCoordinate create(int x, int y)
	{
		return new HexCoordinate(x, y);
	}
	
	public static HexCoordinate create(int x, int y, int z)
	{
		assert x + y + z == 0;
		return new HexCoordinate(x, y);
	}
	
	public static Double create(double x, double y)
	{
		return new Double(x, y, -x - y);
	}
	
	public static Double create(double x, double y, double z)
	{
		assert x + y + z == 0;
		return new Double(x, y, z);
	}
	
	public static class Double extends CoordinateD
	{
		public static final HexCoordinate.Double ORIGIN = HexCoordinate.create(0D, 0D);
		
		Double(double[] coords)
		{
			super(coords);
		}
		
		private Double(double x, double y, double z)
		{
			super(x, y, z);
		}
		
		@Override
		public HexCoordinate.Double add(CoordinateD hex)
		{
			return HexCoordinate.create(x() + hex.x(), y() + hex.y());
		}
		
		@Override
		public HexCoordinate.Double sub(CoordinateD hex)
		{
			return HexCoordinate.create(x() - hex.x(), y() - hex.y());
		}
		
		@Override
		public HexCoordinate.Double mul(double m)
		{
			return HexCoordinate.create(x() * m, y() * m);
		}
		
		@Override
		public HexCoordinate asInt()
		{
			return round();
		}
		
		public HexCoordinate round()
		{
			int xi = (int) Math.round(x());
			int yi = (int) Math.round(y());
			int zi = (int) Math.round(z());
			
			double xd = Math.abs(xi - x());
			double yd = Math.abs(yi - y());
			double zd = Math.abs(zi - z());
			
			if (xd > yd && xd > zd)
				xi = -yi - zi;
			else if (yd > zd)
				yi = -xi - zi;
			
			return new HexCoordinate(xi, yi);
		}
	}
	
	@Override
	public String toString()
	{
		return String.format("Hex%s", Arrays.toString(coords));
	}
}
