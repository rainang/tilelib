package com.github.rainang.tilelib.board.tile;

import com.github.rainang.tilelib.point.Point;
import com.github.rainang.tilelib.point.Points;

public enum TileShape
{
	QUAD(4, new Point[] {
			Points.at(0, -1),
			Points.at(1, 0),
			Points.at(0, 1),
			Points.at(-1, 0)
	}),
	HEX(6, new Point[] {
			Points.hexAt(1, -1),
			Points.hexAt(1, 0),
			Points.hexAt(0, 1),
			Points.hexAt(-1, 1),
			Points.hexAt(-1, 0),
			Points.hexAt(0, -1)
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
