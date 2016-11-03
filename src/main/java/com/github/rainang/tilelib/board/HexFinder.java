package com.github.rainang.tilelib.board;

import com.github.rainang.tilelib.point.Point;
import com.github.rainang.tilelib.point.PointD;

import java.util.function.Consumer;

public class HexFinder extends TileFinder
{
	private final Point[] OFFSETS = new Point[] {
			Point.createHex(1, -1),
			Point.createHex(1, 0),
			Point.createHex(0, 1),
			Point.createHex(-1, 1),
			Point.createHex(-1, 0),
			Point.createHex(0, -1)
	};
	
	@Override
	protected int sides()
	{
		return 6;
	}
	
	@Override
	protected Point[] getNeighbors()
	{
		return OFFSETS;
	}
	
	@Override
	public void line(Point p1, Point p2, Consumer<Point> consumer)
	{
		PointD eps = PointD.createHex(1e-6, 1e-6);
		PointD hd1 = p1.asDouble()
					   .add(eps);
		PointD hd2 = p2.asDouble()
					   .add(eps);
		
		int n = distance(p1, p2);
		double step = 1D / Math.max(n, 1);
		
		for (int i = 0; i <= n; i++)
		{
			double j = i * step;
			double k = 1 - j;
			consumer.accept(round(PointD.createHex(hd1.x() * k + hd2.x() * j, hd1.y() * k + hd2.y() * j)));
		}
	}
	
	@Override
	public void fan(Point p, int length, int direction, Consumer<Point> consumer)
	{
		for (int j = 0; j <= length; j++)
		{
			for (int i = 0; i <= length - j; i++)
				consumer.accept(offset(p, direction, i));
			p = offset(p, clamp(direction + 1));
		}
	}
	
	@Override
	public void range(Point p, int length, Consumer<Point> consumer)
	{
		for (int x = -length; x <= length; x++)
		{
			int y1 = Math.max(-length, -x - length);
			int y2 = Math.min(length, -x + length);
			for (int y = y1; y <= y2; y++)
				consumer.accept(Point.createHex(x + p.x(), y + p.y()));
		}
	}
	
	public void rangeIntersection(Point hex1, int radius1, Point hex2, int radius2, Consumer<Point> consumer)
	{
		int xMin = Math.max(hex1.x() - radius1, hex2.x() - radius2);
		int xMax = Math.min(hex1.x() + radius1, hex2.x() + radius2);
		int yMin = Math.max(hex1.y() - radius1, hex2.y() - radius2);
		int yMax = Math.min(hex1.y() + radius1, hex2.y() + radius2);
		int zMin = Math.max(hex1.z() - radius1, hex2.z() - radius2);
		int zMax = Math.min(hex1.z() + radius1, hex2.z() + radius2);
		
		for (int x = xMin; x <= xMax; x++)
		{
			int min = Math.max(yMin, -x - zMax);
			int max = Math.min(yMax, -x - zMin);
			for (int y = min; y <= max; y++)
				consumer.accept(Point.createHex(x, y));
		}
	}
	
	public void ring(Point hex, int direction, int radius, Consumer<Point> consumer)
	{
		hex = offset(hex, direction, radius);
		for (int i = 0; i < 6; i++)
		{
			int k = clamp(i + direction + 2);
			for (int j = 0; j < radius; j++)
			{
				consumer.accept(hex);
				hex = offset(hex, k);
			}
		}
	}
	
	public void spiral(Point hex, int direction, int radius, Consumer<Point> consumer)
	{
		for (int i = 1; i <= radius; i++)
			ring(hex, direction, i, consumer);
	}
	
	public void rectangle(Point p, int length1, int length2, int direction, Consumer<Point> consumer)
	{
		for (int j = 0; j <= length2; j++)
		{
			for (int i = 0; i <= length1; i++)
				consumer.accept(offset(p, direction, i));
			int k = j % 2 == 0 ? direction + 1 : direction + 2;
			p = offset(p, clamp(k));
		}
	}
	
	public static final int EVEN = 1;
	public static final int ODD = -1;
	
	public static int length(Point p)
	{
		return (Math.abs(p.x()) + Math.abs(p.y()) + Math.abs(p.z())) / 2;
	}
	
	public static int distance(Point p1, Point p2)
	{
		return length(p1.sub(p2));
	}
	
	public static Point rotate(Point p)
	{
		return Point.createHex(-p.z(), -p.x());
	}
	
	public static Point rotateCCW(Point p)
	{
		return Point.createHex(-p.y(), -p.z());
	}
	
	public static Point toOffsetCol(int offset, Point p)
	{
		int col = p.x();
		int row = p.y() + (p.x() + offset * (p.x() & 1)) / 2;
		return Point.create(col, row);
	}
	
	public static Point toOffsetRow(int offset, Point p)
	{
		int col = p.x() + (p.y() + offset * (p.y() & 1)) / 2;
		int row = p.y();
		return Point.create(col, row);
	}
	
	private static Point fromOffsetCol(int offset, Point p)
	{
		int x = p.x();
		int y = p.y() - (p.x() + offset * (p.x() & 1)) / 2;
		return Point.createHex(x, y);
	}
	
	private static Point fromOffsetRow(int offset, Point p)
	{
		int x = p.x() - (p.y() + offset * (p.y() & 1)) / 2;
		int y = p.y();
		return Point.createHex(x, y);
	}
	
	public static Point round(PointD p)
	{
		int xi = (int) Math.round(p.x());
		int yi = (int) Math.round(p.y());
		int zi = (int) Math.round(p.z());
		
		double xd = Math.abs(xi - p.x());
		double yd = Math.abs(yi - p.y());
		double zd = Math.abs(zi - p.z());
		
		if (xd > yd && xd > zd)
			xi = -yi - zi;
		else if (yd > zd)
			yi = -xi - zi;
		
		return Point.createHex(xi, yi);
	}
}
