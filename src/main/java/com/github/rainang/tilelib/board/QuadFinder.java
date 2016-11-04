package com.github.rainang.tilelib.board;

import com.github.rainang.tilelib.board.tile.TileShape;
import com.github.rainang.tilelib.point.MutablePoint;
import com.github.rainang.tilelib.point.Point;

import java.util.function.Consumer;

public class QuadFinder extends PointFinder
{
	public QuadFinder()
	{
		super(TileShape.QUAD);
	}
	
	@Override
	public void line(Point p1, Point p2, Consumer<MutablePoint> consumer)
	{
		int dx = p2.x() - p1.x();
		int dy = p2.y() - p1.y();
		int xMin = Math.min(p1.x(), p2.x());
		int xMax = Math.max(p1.x(), p2.x());
		
		for (int x = xMin; x <= xMax; x++)
		{
			int y = p1.y() + dy * (x - p1.x()) / dx;
			consumer.accept(temp[0].set(x, y));
		}
	}
	
	@Override
	public void parallelogram(Point p, int length1, int length2, int direction, Consumer<MutablePoint> consumer)
	{
		for (int i = 0; i < length1; i++)
		{
			offset(temp[0].set(p), direction, i);
			consumer.accept(temp[0]);
			for (int j = 1; j < length2; j++)
			{
				offset(temp[0], clamp(direction + 1));
				consumer.accept(temp[0]);
			}
		}
	}
	
	@Override
	public void fan(Point p, int length, int direction, Consumer<MutablePoint> consumer)
	{
		
	}
	
	@Override
	public void range(Point p, int length, Consumer<MutablePoint> consumer)
	{
		
	}
}
