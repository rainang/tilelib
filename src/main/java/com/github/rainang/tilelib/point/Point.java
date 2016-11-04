package com.github.rainang.tilelib.point;

import java.util.Arrays;

/**
 * An immutable point representing a location in 2- or 3-dimensional space, specified in integer precision.
 *
 * @see Points
 */
public class Point
{
	public static final Point ORIGIN = Point.create(0, 0);
	
	/**
	 * A constant for 3-dimensional point at <code>(0,0,0)</code>
	 */
	@Deprecated
	public static final Point ORIGIN_3 = Point.create(0, 0, 0);
	
	final int[] array;
	
	// CONSTRUCTORS
	
	/**
	 * Constructs an immutable 2-dimensional point.
	 *
	 * @param x the x-coordinate
	 * @param y the y-coordinate
	 *
	 * @return an immutable 2-dimensional point
	 */
	@Deprecated
	public static Point create(int x, int y)
	{
		return new Point(new int[] {
				x,
				y
		});
	}
	
	/**
	 * Constructs an immutable 3-dimensional point.
	 *
	 * @param x the x-coordinate
	 * @param y the y-coordinate
	 * @param z the z-coordinate
	 *
	 * @return an immutable 3-dimensional point
	 */
	@Deprecated
	public static Point create(int x, int y, int z)
	{
		return new Point(new int[] {
				x,
				y,
				z
		});
	}
	
	/**
	 * Constructs an immutable 3-dimensional hex point.
	 *
	 * @param x the x-coordinate
	 * @param y the y-coordinate
	 *
	 * @return an immutable 3-dimensional hex point
	 */
	@Deprecated
	public static Point createHex(int x, int y)
	{
		return new Point(new int[] {
				x,
				y,
				-x - y
		});
	}
	
	Point(int[] array)
	{
		this.array = Arrays.copyOf(array, array.length);
	}
	
	// VIEWS
	
	/**
	 * Returns an immutable instance of this point.
	 *
	 * @return an immutable instance of this point
	 */
	public Point asImmutable()
	{
		return this;
	}
	
	/**
	 * Returns a mutable instance of this point.
	 *
	 * @return a mutable instance of this point
	 */
	public MutablePoint asMutable()
	{
		return new MutablePoint(array);
	}
	
	/**
	 * Returns an immutable instance of this point in double precision.
	 *
	 * @return an immutable instance of this point in double precision
	 */
	public PointD asDouble()
	{
		double[] a = new double[array.length];
		for (int i = 0; i < array.length; i++)
			a[i] = array[i];
		return new PointD(a);
	}
	
	/**
	 * Returns a mutable instance of this point in double precision.
	 *
	 * @return a mutable instance of this point in double precision
	 */
	public MutablePointD asDoubleMutable()
	{
		double[] a = new double[array.length];
		for (int i = 0; i < array.length; i++)
			a[i] = array[i];
		return new MutablePointD(a);
	}
	
	// GETTERS
	
	/**
	 * Returns the x-coordinate of this point.
	 *
	 * @return the x-coordinate of this point
	 */
	public int x()
	{
		return array[0];
	}
	
	/**
	 * Returns the y-coordinate of this point.
	 *
	 * @return the y-coordinate of this point
	 */
	public int y()
	{
		return array[1];
	}
	
	/**
	 * Returns the z-coordinate of this point.
	 *
	 * @return the z-coordinate of this point
	 *
	 * @throws ArrayIndexOutOfBoundsException if this is a 2-dimensional point
	 */
	public int z()
	{
		return array[2];
	}
	
	// OVERRIDES
	
	@Override
	public int hashCode()
	{
		long bits = Double.doubleToLongBits(array[0]);
		bits ^= Double.doubleToLongBits(array[1]) * 31;
		return (((int) bits) ^ ((int) (bits >> 32)));
	}
	
	@Override
	public boolean equals(Object o)
	{
		return o instanceof Point ? Arrays.equals(array, ((Point) o).array) : super.equals(o);
	}
	
	@Override
	public String toString()
	{
		return String.format("P%s", Arrays.toString(array));
	}
}
