package com.github.rainang.tilelib.tile.layout;

import com.github.rainang.tilelib.geometry.MutablePoint;
import com.github.rainang.tilelib.geometry.MutablePointD;
import com.github.rainang.tilelib.geometry.Point;
import com.github.rainang.tilelib.geometry.PointD;
import com.github.rainang.tilelib.tile.TileShape;

import java.util.function.BiConsumer;

public interface Layout
{
	TileShape getShape();
	
	PointD getSize();
	
	PointD getOrigin();
	
	PointD getCorner(int corner);
	
	MutablePointD getCorner(PointD p, int corner, MutablePointD dest);
	
	void getPolygonCorners(Point p, BiConsumer<Integer, MutablePointD> consumer);
	
	MutablePointD toPixel(Point p, MutablePointD dest);
	
	MutablePoint fromPixel(PointD p, MutablePoint dest);
}
