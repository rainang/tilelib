package com.github.rainang.tilelib.board;

import com.github.rainang.tilelib.board.tile.TileShape;
import com.github.rainang.tilelib.point.MutablePoint;
import com.github.rainang.tilelib.point.Point;
import com.github.rainang.tilelib.point.PointD;

import java.util.function.Consumer;

public class HexFinder extends TileFinder
{
	public HexFinder()
	{
		super(TileShape.HEX);
	}
	
	@Override
	public void line(Point p1, Point p2, Consumer<MutablePoint> consumer)
	{
		tempD[0].set(EPSILON)
				.add(p1);
		tempD[1].set(EPSILON)
				.add(p2);
		
		int n = distance(p1, p2);
		System.out.println(n);
		double step = 1D / Math.max(n, 1);
		for (int i = 0; i <= n; i++)
		{
			double j = i * step;
			double k = 1 - j;
			double x = tempD[0].x() * k + tempD[1].x() * j;
			double y = tempD[0].y() * k + tempD[1].y() * j;
			tempD[2].set(x, y, -x - y);
			consumer.accept(round(tempD[2], temp[0]));
		}
	}
	
	@Override
	public void fan(Point p, int length, int direction, Consumer<MutablePoint> consumer)
	{
		for (int j = 0; j <= length; j++)
		{
			offset(temp[0].set(p), clamp(direction + 1), j);
			for (int i = 0; i <= length - j; i++)
				consumer.accept(offset(temp[1].set(temp[0]), direction, i));
		}
	}
	
	@Override
	public void range(Point p, int length, Consumer<MutablePoint> consumer)
	{
		for (int x = -length; x <= length; x++)
		{
			int y1 = Math.max(-length, -x - length);
			int y2 = Math.min(length, -x + length);
			for (int y = y1; y <= y2; y++)
				consumer.accept(temp[0].set(x + p.x(), y + p.y())
									   .setZ(-temp[0].x() - temp[0].y()));
		}
	}
	
	public void rangeIntersection(Point p1, int r1, Point p2, int r2, Consumer<MutablePoint> consumer)
	{
		int xMin = Math.max(p1.x() - r1, p2.x() - r2);
		int xMax = Math.min(p1.x() + r1, p2.x() + r2);
		int yMin = Math.max(p1.y() - r1, p2.y() - r2);
		int yMax = Math.min(p1.y() + r1, p2.y() + r2);
		int zMin = Math.max(p1.z() - r1, p2.z() - r2);
		int zMax = Math.min(p1.z() + r1, p2.z() + r2);
		
		for (int x = xMin; x <= xMax; x++)
		{
			int min = Math.max(yMin, -x - zMax);
			int max = Math.min(yMax, -x - zMin);
			for (int y = min; y <= max; y++)
				consumer.accept(temp[0].set(x, y, -x - y));
		}
	}
	
	public void ring(Point p, int direction, int radius, Consumer<MutablePoint> consumer)
	{
		offset(temp[0].set(p), direction, radius);
		for (int i = 0; i < 6; i++)
		{
			int k = clamp(i + direction + 2);
			for (int j = 0; j < radius; j++)
			{
				consumer.accept(temp[0]);
				offset(temp[0], k);
			}
		}
	}
	
	public void spiral(Point p, int direction, int radius, Consumer<MutablePoint> consumer)
	{
		for (int i = 1; i <= radius; i++)
			ring(p, direction, i, consumer);
	}
	
	public void rectangle(Point p, int l1, int l2, int direction, Consumer<MutablePoint> consumer)
	{
		temp[0].set(p);
		for (int j = 0; j <= l2; j++)
		{
			for (int i = 0; i <= l1; i++)
				consumer.accept(offset(temp[1].set(temp[0]), direction, i));
			int k = j % 2 + 1 + direction;
			offset(temp[0], clamp(k));
		}
	}
	
	public static final int EVEN = 1;
	public static final int ODD = -1;
	
	public int length(Point p)
	{
		return (Math.abs(p.x()) + Math.abs(p.y()) + Math.abs(p.z())) / 2;
	}
	
	public int distance(Point p1, Point p2)
	{
		return length(temp[0].set(p1)
							 .sub(p2));
	}
	
	public static MutablePoint rotate(MutablePoint p)
	{
		return p.set(-p.z(), -p.x(), -p.y());
	}
	
	public static MutablePoint rotateCCW(MutablePoint p)
	{
		return p.set(-p.y(), -p.z(), -p.x());
	}
	
	public static MutablePoint toOffsetCol(int offset, Point p, MutablePoint dest)
	{
		int col = p.x();
		int row = p.y() + (p.x() + offset * (p.x() & 1)) / 2;
		return dest.set(col, row);
	}
	
	public static MutablePoint toOffsetRow(int offset, Point p, MutablePoint dest)
	{
		int col = p.x() + (p.y() + offset * (p.y() & 1)) / 2;
		int row = p.y();
		return dest.set(col, row);
	}
	
	private static MutablePoint fromOffsetCol(int offset, Point p, MutablePoint dest)
	{
		int x = p.x();
		int y = p.y() - (p.x() + offset * (p.x() & 1)) / 2;
		return dest.set(x, y, -x - y);
	}
	
	private static MutablePoint fromOffsetRow(int offset, Point p, MutablePoint dest)
	{
		int x = p.x() - (p.y() + offset * (p.y() & 1)) / 2;
		int y = p.y();
		return dest.set(x, y, -x - y);
	}
	
	public static MutablePoint round(PointD p, MutablePoint dest)
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
		
		return dest.set(xi, yi, -xi - yi);
	}
}
