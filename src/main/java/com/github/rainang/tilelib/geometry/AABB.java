package com.github.rainang.tilelib.geometry;

public class AABB
{
	final Point location;
	
	final Point dimensions;
	
	public AABB(Point location, Point dimensions)
	{
		this.location = location;
		this.dimensions = dimensions;
	}
	
	public Point min()
	{
		return location;
	}
	
	public Point max()
	{
		return Points.at(maxX(), maxY());
	}
	
	public Point dimensions()
	{
		return dimensions;
	}
	
	public int x()
	{
		return location.x();
	}
	
	public int y()
	{
		return location.y();
	}
	
	public int width()
	{
		return dimensions.x();
	}
	
	public int height()
	{
		return dimensions.y();
	}
	
	public int maxX()
	{
		return x() + width() - 1;
	}
	
	public int maxY()
	{
		return y() + height() - 1;
	}
	
	public int centerX()
	{
		return x() + width() / 2;
	}
	
	public int centerY()
	{
		return y() + height() / 2;
	}
	
	public boolean contains(Point p)
	{
		return p.x() >= x() && p.y() >= y() && p.x() <= maxX() && p.y() <= maxY();
	}
	
	@Override
	public String toString()
	{
		return String.format("AABB[%s, %s]", location, dimensions);
	}
}
