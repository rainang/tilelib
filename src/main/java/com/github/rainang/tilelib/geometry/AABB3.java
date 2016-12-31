package com.github.rainang.tilelib.geometry;

public class AABB3 extends AABB
{
	public AABB3(Point location, Point dimensions)
	{
		super(location, dimensions);
	}
	
	@Override
	public Point max()
	{
		return Points.at(maxX(), maxY(), maxZ());
	}
	
	public int z()
	{
		return location.z();
	}
	
	public int length()
	{
		return dimensions.z();
	}
	
	public int maxZ()
	{
		return z() + length() - 1;
	}
	
	public int centerZ()
	{
		return z() + length() / 2;
	}
	
	public boolean contains(Point p)
	{
		return super.contains(p) && p.z() >= z() && p.z() <= maxZ();
	}
}
