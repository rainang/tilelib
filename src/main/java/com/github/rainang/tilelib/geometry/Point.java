package com.github.rainang.tilelib.geometry;

import java.util.Arrays;

/**
 * An immutable point representing a location in 2- or 3-dimensional space, specified in integer precision.
 *
 * @see Points
 */
public class Point
{
	final int[] array;
	
	// Constructors
	
	Point(int[] array)
	{
		this.array = Arrays.copyOf(array, array.length);
	}
	
	// Views
	
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
	
	// Getters
	
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
	
	// Overrides
	
	// TODO: 2016-12-31 Fix hashing for 3-dimensional point
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
