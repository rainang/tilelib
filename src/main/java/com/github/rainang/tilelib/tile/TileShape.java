package com.github.rainang.tilelib.tile;

import com.github.rainang.tilelib.geometry.Point;
import com.github.rainang.tilelib.geometry.Points;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public enum TileShape
{
	QUAD(4, new Point[] {
			Points.at(0, 1),
			Points.at(1, 0),
			Points.at(0, -1),
			Points.at(-1, 0)
	}, new Point[] {
			Points.at(1, 1),
			Points.at(1, -1),
			Points.at(-1, -1),
			Points.at(-1, 1)
	}),
	HEX(6, new Point[] {
			Points.hexAt(0, 1),
			Points.hexAt(1, 0),
			Points.hexAt(1, -1),
			Points.hexAt(0, -1),
			Points.hexAt(-1, 0),
			Points.hexAt(-1, 1)
	}, new Point[] {
			Points.hexAt(1, 1),
			Points.hexAt(2, -1),
			Points.hexAt(1, -2),
			Points.hexAt(-1, -1),
			Points.hexAt(-2, 1),
			Points.hexAt(-1, 2)
	});
	
	private final int sides;
	
	private final List<Point> laterals;
	
	private final List<Point> diagonals;
	
	TileShape(int sides, Point[] laterals, Point[] diagonals)
	{
		this.sides = sides;
		this.laterals = Collections.unmodifiableList(Arrays.asList(laterals));
		this.diagonals = Collections.unmodifiableList(Arrays.asList(diagonals));
	}
	
	public int getSides()
	{
		return sides;
	}
	
	public List<Point> getLateralOffsets()
	{
		return laterals;
	}
	
	public List<Point> getDiagonalOffsets()
	{
		return diagonals;
	}
	
	public Point getLateralOffset(int direction)
	{
		return laterals.get(direction);
	}
	
	public Point getDiagonalOffset(int direction)
	{
		return diagonals.get(direction);
	}
}
