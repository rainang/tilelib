package com.github.rainang.tilelib.coordinates;

@FunctionalInterface
public interface CoordinateSupplier<C extends Coordinate>
{
	C create(int i, int j);
}
