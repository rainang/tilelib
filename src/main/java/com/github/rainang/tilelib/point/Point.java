package com.github.rainang.tilelib.point;

import java.util.Arrays;

/**
 An integer coordinate class. This is the base class for 2D and 3D integer coordinates. This class stores its
 coordinates in an integer array but is meant to be final.
 */
public class Point
{
	/** A 2D origin constant */
	public static final Point ORIGIN = Point.create(0, 0);
	
	/** A 3D origin constant */
	public static final Point ORIGIN_3 = Point.create(0, 0, 0);
	
	final int[] coords;
	
	/**
	 Constructs integer coordinates with the specified <code>array</code>.
	 
	 @param array the array containing the coordinates
	 */
	Point(int[] array)
	{
		this.coords = array;
	}
	
	/**
	 Constructs an instance of 2D integer coordinates.
	 
	 @param x the x-coordinate
	 @param y the y-coordinate
	 */
	Point(int x, int y)
	{
		coords = new int[] {
				x,
				y
		};
	}
	
	/**
	 Constructs an instance of 3D integer coordinates.
	 
	 @param x the x-coordinate
	 @param y the y-coordinate
	 @param z the z-coordinate
	 */
	Point(int x, int y, int z)
	{
		coords = new int[] {
				x,
				y,
				z
		};
	}
	
	/**
	 Returns the x-coordinate.
	 
	 @return the x-coordinate
	 */
	public int x()
	{
		return coords[0];
	}
	
	/**
	 Returns the y-coordinate.
	 
	 @return the y-coordinate
	 */
	public int y()
	{
		return coords[1];
	}
	
	/**
	 Returns the z-coordinate.
	 
	 @return the z-coordinate
	 
	 @throws ArrayIndexOutOfBoundsException if no z-coordinate was specified on instantiation
	 */
	public int z()
	{
		return coords[2];
	}
	
	/**
	 Returns the <code>Point</code> sum of this coordinate and <code>c</code>.
	 
	 @param c the <code>Point</code> to add
	 
	 @return the <code>Point</code> sum of this coordinate and <code>c</code>
	 */
	public Point add(Point c)
	{
		int[] array = new int[coords.length];
		for (int i = 0; i < array.length; i++)
			array[i] = coords[i] + c.coords[i];
		return new Point(array);
	}
	
	/**
	 Returns the <code>Point</code> difference of this coordinate and <code>c</code>.
	 
	 @param c the <code>Point</code> to subtract
	 
	 @return the <code>Point</code> difference of this coordinate and <code>c</code>
	 */
	public Point sub(Point c)
	{
		int[] array = new int[coords.length];
		for (int i = 0; i < array.length; i++)
			array[i] = coords[i] - c.coords[i];
		return new Point(array);
	}
	
	/**
	 Returns this <code>Point</code> scaled to <code>s</code>.
	 
	 @param s the integer value to scale
	 
	 @return this <code>Point</code> scaled to <code>s</code>.
	 */
	public Point mul(int s)
	{
		int[] array = new int[coords.length];
		for (int i = 0; i < array.length; i++)
			array[i] = coords[i] * s;
		return new Point(array);
	}
	
	/**
	 Returns a <code>PointD</code> instance of this <code>Point</code>.
	 
	 @return a <code>PointD</code> instance of this <code>Point</code>
	 */
	public PointD asDouble()
	{
		double[] array = new double[coords.length];
		for (int i = 0; i < array.length; i++)
			array[i] = coords[i];
		return new PointD(array);
	}
	
	@Override
	public int hashCode()
	{
		long bits = Double.doubleToLongBits(coords[0]);
		bits ^= Double.doubleToLongBits(coords[1]) * 31;
		return (((int) bits) ^ ((int) (bits >> 32)));
	}
	
	@Override
	public boolean equals(Object o)
	{
		return o instanceof Point ? Arrays.equals(coords, ((Point) o).coords) : super.equals(o);
	}
	
	@Override
	public String toString()
	{
		return String.format("%s", Arrays.toString(coords));
	}
	
	/**
	 Constructs an instance of 2D integer coordinates.
	 
	 @param x the x-coordinate
	 @param y the y-coordinate
	 
	 @return an instance of 2D integer coordinates
	 */
	public static Point create(int x, int y)
	{
		return new Point(x, y);
	}
	
	/**
	 Constructs an instance of 3D integer coordinates.
	 
	 @param x the x-coordinate
	 @param y the y-coordinate
	 @param z the z-coordinate
	 
	 @return an instance of 3D integer coordinates
	 */
	public static Point create(int x, int y, int z)
	{
		return new Point(x, y, z);
	}
	
	/**
	 Constructs an instance of hex coordinates.
	 
	 @param x the x-coordinate
	 @param y the y-coordinate
	 
	 @return an instance of hex coordinates
	 */
	public static Point createHex(int x, int y)
	{
		return new Point(x, y, -x - y);
	}
}
