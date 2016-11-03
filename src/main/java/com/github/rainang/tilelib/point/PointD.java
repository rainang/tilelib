package com.github.rainang.tilelib.point;

import java.util.Arrays;

/**
 An double coordinate class. This is the base class for 2D and 3D double coordinates. This class stores its
 coordinates in a double array but is meant to be final.
 */
public class PointD
{
	/** A 2D origin constant */
	public static final PointD ORIGIN = PointD.create(0, 0);
	
	/** A 3D origin constant */
	public static final PointD ORIGIN_3 = PointD.create(0, 0, 0);
	
	final double[] coords;
	
	/**
	 Constructs double coordinates with the specified <code>array</code>.
	 
	 @param array the array containing the coordinates
	 */
	PointD(double[] array)
	{
		this.coords = array;
	}
	
	/**
	 Constructs an instance of 2D double coordinates.
	 
	 @param x the x-coordinate
	 @param y the y-coordinate
	 */
	PointD(double x, double y)
	{
		coords = new double[] {
				x,
				y
		};
	}
	
	/**
	 Constructs an instance of 3D double coordinates.
	 
	 @param x the x-coordinate
	 @param y the y-coordinate
	 @param z the z-coordinate
	 */
	PointD(double x, double y, double z)
	{
		coords = new double[] {
				x,
				y,
				z
		};
	}
	
	/**
	 Returns the x-coordinate.
	 
	 @return the x-coordinate
	 */
	public double x()
	{
		return coords[0];
	}
	
	/**
	 Returns the y-coordinate.
	 
	 @return the y-coordinate
	 */
	public double y()
	{
		return coords[1];
	}
	
	/**
	 Returns the z-coordinate.
	 
	 @return the z-coordinate
	 
	 @throws ArrayIndexOutOfBoundsException if no z-coordinate was specified on instantiation
	 */
	public double z()
	{
		return coords[2];
	}
	
	/**
	 Returns the <code>PointD</code> sum of this coordinate and <code>c</code>.
	 
	 @param c the <code>PointD</code> to add
	 
	 @return the <code>PointD</code> sum of this coordinate and <code>c</code>
	 */
	public PointD add(PointD c)
	{
		double[] array = new double[coords.length];
		for (int i = 0; i < array.length; i++)
			array[i] = coords[i] + c.coords[i];
		return new PointD(array);
	}
	
	/**
	 Returns the <code>PointD</code> difference of this coordinate and <code>c</code>.
	 
	 @param c the <code>PointD</code> to subtract
	 
	 @return the <code>PointD</code> difference of this coordinate and <code>c</code>
	 */
	public PointD sub(PointD c)
	{
		double[] array = new double[coords.length];
		for (int i = 0; i < array.length; i++)
			array[i] = coords[i] - c.coords[i];
		return new PointD(array);
	}
	
	/**
	 Returns this <code>PointD</code> scaled to <code>s</code>.
	 
	 @param s the double value to scale
	 
	 @return this <code>PointD</code> scaled to <code>s</code>.
	 */
	public PointD mul(double s)
	{
		double[] array = new double[coords.length];
		for (int i = 0; i < array.length; i++)
			array[i] = coords[i] * s;
		return new PointD(array);
	}
	
	/**
	 Returns a <code>Point</code> instance of this <code>PointD</code>.
	 
	 @return a <code>Point</code> instance of this <code>PointD</code>
	 */
	public Point asInt()
	{
		int[] array = new int[coords.length];
		for (int i = 0; i < array.length; i++)
			array[i] = (int) coords[i];
		return new Point(array);
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
		return o instanceof PointD ? Arrays.equals(coords, ((PointD) o).coords) : super.equals(o);
	}
	
	@Override
	public String toString()
	{
		return String.format("%s", Arrays.toString(coords));
	}
	
	/**
	 Constructs an instance of 2D double coordinates.
	 
	 @param x the x-coordinate
	 @param y the y-coordinate
	 
	 @return an instance of 2D double coordinates
	 */
	public static PointD create(double x, double y)
	{
		return new PointD(x, y);
	}
	
	/**
	 Constructs an instance of 3D double coordinates.
	 
	 @param x the x-coordinate
	 @param y the y-coordinate
	 @param z the z-coordinate
	 
	 @return an instance of 3D double coordinates
	 */
	public static PointD create(double x, double y, double z)
	{
		return new PointD(x, y, z);
	}
	
	/**
	 Constructs an instance of double hex coordinates.
	 
	 @param x the x-coordinate
	 @param y the y-coordinate
	 
	 @return an instance of double hex coordinates
	 */
	public static PointD createHex(double x, double y)
	{
		return new PointD(x, y, -x - y);
	}
}
