package com.github.rainang.tilelib.tile;

import com.github.rainang.tilelib.geometry.MutablePoint;
import com.github.rainang.tilelib.geometry.MutablePointD;
import com.github.rainang.tilelib.geometry.Point;
import com.github.rainang.tilelib.geometry.PointD;
import com.github.rainang.tilelib.geometry.Points;

import java.util.Arrays;
import java.util.function.Consumer;

public abstract class PointFinder
{
	public static final PointD EPSILON = Points.doubleHexAt(1e-6, 1e-6);
	
	private final TileShape tileShape;
	
	final MutablePoint[] temp = new MutablePoint[2];
	
	final MutablePointD[] tempD = new MutablePointD[3];
	
	PointFinder(TileShape tileShape)
	{
		this.tileShape = tileShape;
		Arrays.setAll(temp, i -> Points.mutableAt(0, 0, 0));
		Arrays.setAll(tempD, i -> Points.mutableDoubleAt(0, 0, 0));
	}
	
	public TileShape getTileShape()
	{
		return tileShape;
	}
	
	public int clamp(int direction)
	{
		int i = direction % tileShape.getSides();
		return direction < 0 ? i + tileShape.getSides() : i;
	}
	
	// OFFSETS
	
	public Point offset(int direction)
	{
		return getTileShape().getOffset(direction);
	}
	
	public MutablePoint offset(MutablePoint p, int direction)
	{
		return p.add(offset(direction));
	}
	
	public MutablePoint offset(MutablePoint p, int direction, int distance)
	{
		return p.add(offset(direction).asMutable()
									  .scale(distance));
	}
	
	// SHAPES
	
	public abstract void line(Point p1, Point p2, Consumer<MutablePoint> consumer);
	
	public void parallelogram(Point p, int length1, int length2, int direction, Consumer<MutablePoint> consumer)
	{
		for (int i = 0; i <= length1; i++)
		{
			offset(temp[0].set(p), direction, i);
			consumer.accept(temp[0]);
			for (int j = 1; j <= length2; j++)
			{
				offset(temp[0], clamp(direction + 1));
				consumer.accept(temp[0]);
			}
		}
	}
	
	public abstract void fan(Point p, int length, int direction, Consumer<MutablePoint> consumer);
	
	public abstract void range(Point p, int length, Consumer<MutablePoint> consumer);
}
