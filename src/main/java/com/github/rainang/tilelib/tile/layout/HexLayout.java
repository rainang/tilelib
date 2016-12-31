package com.github.rainang.tilelib.tile.layout;

import com.github.rainang.tilelib.geometry.MutablePoint;
import com.github.rainang.tilelib.geometry.MutablePointD;
import com.github.rainang.tilelib.geometry.Point;
import com.github.rainang.tilelib.geometry.PointD;
import com.github.rainang.tilelib.geometry.Points;
import com.github.rainang.tilelib.tile.HexFinder;
import com.github.rainang.tilelib.tile.TileShape;

import java.util.function.BiFunction;

public final class HexLayout extends AbstractLayout
{
	private final HexOrientation orientation;
	
	HexLayout(PointD size, PointD origin, PointD[] corners, BiFunction<Point, MutablePointD, MutablePointD> toPixel,
			  BiFunction<PointD, MutablePoint, MutablePoint> fromPixel, HexOrientation orientation)
	{
		super(size, origin, corners, toPixel, fromPixel);
		this.orientation = orientation;
	}
	
	public TileShape getShape()
	{
		return TileShape.HEX;
	}
	
	public HexOrientation getOrientation()
	{
		return orientation;
	}
	
	public static HexLayout create(PointD size, PointD origin, HexOrientation orientation)
	{
		HexOrientation o = orientation == HexOrientation.POINTY ? HexOrientation.POINTY : HexOrientation.FLAT;
		PointD[] corners = new PointD[6];
		for (int i = 0; i < 6; i++)
		{
			double angle = Math.PI * (o.startAngle + i) / 3;
			double x = size.x() * Math.cos(angle);
			double y = size.y() * Math.sin(angle);
			corners[i] = Points.doubleHexAt(x, y);
		}
		BiFunction<Point, MutablePointD, MutablePointD> toPixel = (p, dest) ->
		{
			double x = (o.f[0] * p.x() + o.f[1] * p.y()) * size.x() + origin.x();
			double y = (o.f[2] * p.x() + o.f[3] * p.y()) * size.y() + origin.y();
			
			return dest.set(x, y);
		};
		MutablePointD temp = Points.doubleOriginZ();
		BiFunction<PointD, MutablePoint, MutablePoint> fromPixel = (p, dest) ->
		{
			double x = (p.x() - origin.x()) / size.x();
			double y = (p.y() - origin.y()) / size.y();
			double q = o.b[0] * x + o.b[1] * y;
			double r = o.b[2] * x + o.b[3] * y;
			return HexFinder.round(temp.set(q, r, -q - r), dest);
		};
		return new HexLayout(size, origin, corners, toPixel, fromPixel, orientation);
	}
}
