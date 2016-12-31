package com.github.rainang.tilelib.geometry;

import java.util.Arrays;

/**
 * An immutable point representing a location in 2- or 3-dimensional space, specified in double precision.
 *
 * @see Points
 */
public class PointD
{
	final double[] array;
	
	// CONSTRUCTORS
	
	PointD(double[] array)
	{
		this.array = Arrays.copyOf(array, array.length);
	}
	
	// VIEWS
	
	/**
	 * Returns an immutable instance of this point.
	 *
	 * @return an immutable instance of this point
	 */
	public PointD asImmutable()
	{
		return this;
	}
	
	/**
	 * Returns a mutable instance of this point.
	 *
	 * @return a mutable instance of this point
	 */
	public MutablePointD asMutable()
	{
		return new MutablePointD(array);
	}
	
	/**
	 * Returns an immutable instance of this point in integer precision.
	 *
	 * @return an immutable instance of this point in integer precision
	 */
	public Point asInt()
	{
		int[] a = new int[array.length];
		for (int i = 0; i < array.length; i++)
			a[i] = (int) array[i];
		return new Point(a);
	}
	
	/**
	 * Returns a mutable instance of this point in integer precision.
	 *
	 * @return a mutable instance of this point in integer precision
	 */
	public MutablePoint asIntMutable()
	{
		int[] a = new int[array.length];
		for (int i = 0; i < array.length; i++)
			a[i] = (int) array[i];
		return new MutablePoint(a);
	}
	
	// GETTERS
	
	/**
	 * Returns the x-coordinate of this point.
	 *
	 * @return the x-coordinate of this point
	 */
	public double x()
	{
		return array[0];
	}
	
	/**
	 * Returns the y-coordinate of this point.
	 *
	 * @return the y-coordinate of this point
	 */
	public double y()
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
	public double z()
	{
		return array[2];
	}
	
	// OVERRIDES
	
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
		return o instanceof PointD ? Arrays.equals(array, ((PointD) o).array) : super.equals(o);
	}
	
	@Override
	public String toString()
	{
		return String.format("P%s", Arrays.toString(array));
	}
}
