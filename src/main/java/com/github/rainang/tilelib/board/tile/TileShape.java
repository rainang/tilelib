package com.github.rainang.tilelib.board.tile;

import com.github.rainang.tilelib.point.Point;

public enum TileShape
{
	QUAD(4, new Point[] {
			Point.create(0, -1),
			Point.create(1, 0),
			Point.create(0, 1),
			Point.create(-1, 0)
	}),
	HEX(6, new Point[] {
			Point.createHex(1, -1),
			Point.createHex(1, 0),
			Point.createHex(0, 1),
			Point.createHex(-1, 1),
			Point.createHex(-1, 0),
			Point.createHex(0, -1)
	});
	
	private final int sides;
	
	private final Point[] offsets;
	
	TileShape(int sides, Point[] offsets)
	{
		this.sides = sides;
		this.offsets = offsets;
	}
	
	public int getSides()
	{
		
		return sides;
	}
	
	public Point getOffset(int direction)
	{
		return offsets[direction];
	}
}
