package com.github.rainang.tilelib.layout;

import com.github.rainang.tilelib.board.HexFinder;
import com.github.rainang.tilelib.board.tile.TileShape;
import com.github.rainang.tilelib.point.MutablePoint;
import com.github.rainang.tilelib.point.MutablePointD;
import com.github.rainang.tilelib.point.Point;
import com.github.rainang.tilelib.point.PointD;
import com.github.rainang.tilelib.point.Points;

import java.util.function.BiConsumer;
import java.util.function.BiFunction;

public final class Layout
{
	private final TileShape shape;
	
	private final TileOrientation orientation;
	
	private final PointD size;
	
	private final PointD origin;
	
	private final PointD[] corners;
	
	private final BiFunction<Point, MutablePointD, MutablePointD> toPixel;
	
	private final BiFunction<PointD, MutablePoint, MutablePoint> fromPixel;
	
	public Layout(PointD size, PointD origin, TileShape shape, TileOrientation orientation)
	{
		this.shape = shape;
		this.orientation = orientation;
		this.size = size;
		this.origin = origin;
		
		switch (shape)
		{
		default:
		case QUAD:
			this.corners = new PointD[] {
					Points.doubleAt(-size.x(), -size.y()),
					Points.doubleAt(size.x(), -size.y()),
					Points.doubleAt(size.x(), size.y()),
					Points.doubleAt(-size.x(), size.y())
			};
			this.toPixel = (p, dest) -> dest.set(p.x() * size.x(), p.y() * size.y());
			this.fromPixel = (p, dest) -> dest.set((int) (p.x() / size.x()), (int) (p.y() / size.y()));
			break;
		case HEX:
			HexOrientation o = orientation == TileOrientation.POINTY ? HexOrientation.POINTY : HexOrientation.FLAT;
			this.corners = new PointD[6];
			for (int i = 0; i < 6; i++)
			{
				double angle = Math.PI * (o.startAngle + i) / 3;
				double x = size.x() * Math.cos(angle);
				double y = size.y() * Math.sin(angle);
				corners[i] = Points.doubleHexAt(x, y);
			}
			this.toPixel = (p, dest) ->
			{
				double x = (o.f[0] * p.x() + o.f[1] * p.y()) * size.x() + origin.x();
				double y = (o.f[2] * p.x() + o.f[3] * p.y()) * size.y() + origin.y();
				
				return dest.set(x, y);
			};
			MutablePointD temp = Points.doubleOriginZ();
			this.fromPixel = (p, dest) ->
			{
				double x = (p.x() - origin.x()) / size.x();
				double y = (p.y() - origin.y()) / size.y();
				double q = o.b[0] * x + o.b[1] * y;
				double r = o.b[2] * x + o.b[3] * y;
				return HexFinder.round(temp.set(q, r, -q - r), dest);
			};
			break;
		}
	}
	
	public TileShape getShape()
	{
		return shape;
	}
	
	public TileOrientation getOrientation()
	{
		return orientation;
	}
	
	public PointD getSize()
	{
		return size;
	}
	
	public PointD getOrigin()
	{
		return origin;
	}
	
	public PointD corner(int corner)
	{
		return corners[corner];
	}
	
	public MutablePointD corner(PointD p, int corner, MutablePointD dest)
	{
		return dest.set(p)
				   .add(corner(corner));
	}
	
	public void polygonCorners(Point p, BiConsumer<Integer, MutablePointD> consumer)
	{
		PointD pd = toPixel(p, p.asDoubleMutable());
		MutablePointD temp = toPixel(p, p.asDoubleMutable());
		for (int i = 0; i < 6; i++)
			consumer.accept(i, corner(pd, i, temp));
		consumer.accept(6, temp.set(pd));
	}
	
	public MutablePointD toPixel(Point p, MutablePointD dest)
	{
		return toPixel.apply(p, dest);
	}
	
	public MutablePoint fromPixel(PointD p, MutablePoint dest)
	{
		return fromPixel.apply(p, dest);
	}
}