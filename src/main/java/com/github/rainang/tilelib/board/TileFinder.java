package com.github.rainang.tilelib.board;

import com.github.rainang.tilelib.point.Point;

import java.util.function.Consumer;

public abstract class TileFinder
{
	protected abstract int sides();
	
	protected abstract Point[] getNeighbors();
	
	public int clamp(int direction)
	{
		return direction < 0 ? direction % sides() + sides() : direction % sides();
	}
	
	public Point offset(int direction)
	{
		return getNeighbors()[direction];
	}
	
	public Point offset(Point p, int direction)
	{
		return p.add(offset(direction));
	}
	
	public Point offset(Point p, int direction, int distance)
	{
		return p.add(offset(direction).mul(distance));
	}
	
	public abstract void line(Point p1, Point p2, Consumer<Point> consumer);
	
	public void parallelogram(Point p, int length1, int length2, int direction, Consumer<Point> consumer)
	{
		for (int i = 0; i <= length1; i++)
		{
			Point p1 = offset(p, direction, i);
			consumer.accept(p1);
			for (int j = 1; j <= length2; j++)
			{
				Point p2 = offset(p1, clamp(direction + 1), j);
				consumer.accept(p2);
			}
		}
	}
	
	public abstract void fan(Point p, int length, int direction, Consumer<Point> consumer);
	
	public abstract void range(Point p, int length, Consumer<Point> consumer);
}
