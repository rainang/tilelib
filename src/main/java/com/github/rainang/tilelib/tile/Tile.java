package com.github.rainang.tilelib.tile;

import com.github.rainang.tilelib.geometry.Point;

public class Tile
{
	private final Point pos;
	
	public Tile(Point pos)
	{
		this.pos = pos;
	}
	
	public Point getPos()
	{
		return pos;
	}
	
	@Override
	public String toString()
	{
		return String.format("%s%s", getClass().getSimpleName(), getPos());
	}
}
