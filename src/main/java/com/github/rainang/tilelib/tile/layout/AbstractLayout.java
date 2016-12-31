package com.github.rainang.tilelib.tile.layout;

import com.github.rainang.tilelib.geometry.MutablePoint;
import com.github.rainang.tilelib.geometry.MutablePointD;
import com.github.rainang.tilelib.geometry.Point;
import com.github.rainang.tilelib.geometry.PointD;

import java.util.function.BiConsumer;
import java.util.function.BiFunction;

public abstract class AbstractLayout
{
	private final PointD size;
	
	private final PointD origin;
	
	private final PointD[] corners;
	
	private final BiFunction<Point, MutablePointD, MutablePointD> toPixel;
	
	private final BiFunction<PointD, MutablePoint, MutablePoint> fromPixel;
	
	public AbstractLayout(PointD size, PointD origin, PointD[] corners, BiFunction<Point, MutablePointD,
			MutablePointD> toPixel, BiFunction<PointD, MutablePoint, MutablePoint> fromPixel)
	{
		this.size = size;
		this.origin = origin;
		this.corners = corners;
		this.toPixel = toPixel;
		this.fromPixel = fromPixel;
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
