package com.github.rainang.tilelib.board;

import com.github.rainang.tilelib.point.Point;

import java.util.function.Consumer;

public class QuadFinder extends TileFinder
{
	private final Point[] OFFSETS = new Point[] {
			Point.create(0, -1),
			Point.create(1, 0),
			Point.create(0, 1),
			Point.create(-1, 0)
	};
	
	@Override
	protected int sides()
	{
		return 4;
	}
	
	@Override
	protected Point[] getNeighbors()
	{
		return OFFSETS;
	}
	
	@Override
	public void line(Point p1, Point p2, Consumer<Point> consumer)
	{
		int dx = p2.x() - p1.x();
		int dy = p2.y() - p1.y();
		int xMin = Math.min(p1.x(), p2.x());
		int xMax = Math.max(p1.x(), p2.x());
		
		for (int x = xMin; x <= xMax; x++)
		{
			int y = p1.y() + dy * (x - p1.x()) / dx;
			consumer.accept(Point.create(x, y));
		}
	}
	
	@Override
	public void parallelogram(Point p, int length1, int length2, int direction, Consumer<Point> consumer)
	{
		for (int i = 0; i < length1; i++)
		{
			Point c1 = offset(p, direction);
			consumer.accept(c1);
			for (int j = 1; j < length2; j++)
			{
				Point c2 = offset(c1, clamp(direction + 1));
				consumer.accept(c2);
			}
		}
	}
	
	@Override
	public void fan(Point p, int length, int direction, Consumer<Point> consumer)
	{
		
	}
	
	@Override
	public void range(Point p, int length, Consumer<Point> consumer)
	{
		
	}
}
