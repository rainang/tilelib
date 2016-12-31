package com.github.rainang.tilelib.tile.layout;

import com.github.rainang.tilelib.geometry.PointD;
import com.github.rainang.tilelib.geometry.Points;
import com.github.rainang.tilelib.tile.TileShape;

public final class QuadLayout extends AbstractLayout
{
	public QuadLayout(PointD size, PointD origin)
	{
		super(size, origin, new PointD[] {
				Points.doubleAt(-size.x(), -size.y()),
				Points.doubleAt(size.x(), -size.y()),
				Points.doubleAt(size.x(), size.y()),
				Points.doubleAt(-size.x(), size.y())
		}, (p, dest) -> dest.set(p.x() * size.x(), p.y() * size.y()), (p, dest) -> dest.set((int) (p.x() / size.x()),
				(int) (p.y() / size.y())));
	}
	
	public TileShape getShape()
	{
		return TileShape.QUAD;
	}
	
	public static QuadLayout create(PointD size, PointD origin)
	{
		return new QuadLayout(size, origin);
	}
}
