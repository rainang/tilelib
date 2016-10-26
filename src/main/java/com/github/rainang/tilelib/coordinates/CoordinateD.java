package com.github.rainang.tilelib.coordinates;

import java.util.Arrays;

/**
 An double coordinate class. This is the base class for 2D and 3D double coordinates. This class stores its
 coordinates in a double array but is meant to be final.
 */
public class CoordinateD
{
	/** A 2D origin constant */
	public static final CoordinateD ORIGIN = CoordinateD.create(0, 0);
	
	/** A 3D origin constant */
	public static final CoordinateD ORIGIN_3 = CoordinateD.create(0, 0, 0);
	
	final double[] coords;
	
	/**
	 Constructs double coordinates with the specified <code>array</code>.
	 
	 @param array the array containing the coordinates
	 */
	CoordinateD(double[] array)
	{
		this.coords = array;
	}
	
	/**
	 Constructs an instance of 2D double coordinates.
	 
	 @param x the x-coordinate
	 @param y the y-coordinate
	 */
	CoordinateD(double x, double y)
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
	CoordinateD(double x, double y, double z)
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
	 Returns the <code>CoordinateD</code> sum of this coordinate and <code>c</code>.
	 
	 @param c the <code>CoordinateD</code> to add
	 
	 @return the <code>CoordinateD</code> sum of this coordinate and <code>c</code>
	 */
	public CoordinateD add(CoordinateD c)
	{
		double[] array = new double[coords.length];
		for (int i = 0; i < array.length; i++)
			array[i] = coords[i] + c.coords[i];
		return new CoordinateD(array);
	}
	
	/**
	 Returns the <code>CoordinateD</code> difference of this coordinate and <code>c</code>.
	 
	 @param c the <code>CoordinateD</code> to subtract
	 
	 @return the <code>CoordinateD</code> difference of this coordinate and <code>c</code>
	 */
	public CoordinateD sub(CoordinateD c)
	{
		double[] array = new double[coords.length];
		for (int i = 0; i < array.length; i++)
			array[i] = coords[i] - c.coords[i];
		return new CoordinateD(array);
	}
	
	/**
	 Returns this <code>CoordinateD</code> scaled to <code>s</code>.
	 
	 @param s the double value to scale
	 
	 @return this <code>CoordinateD</code> scaled to <code>s</code>.
	 */
	public CoordinateD mul(double s)
	{
		double[] array = new double[coords.length];
		for (int i = 0; i < array.length; i++)
			array[i] = coords[i] * s;
		return new CoordinateD(array);
	}
	
	/**
	 Returns a <code>Coordinate</code> instance of this <code>CoordinateD</code>.
	 
	 @return a <code>Coordinate</code> instance of this <code>CoordinateD</code>
	 */
	public Coordinate asInt()
	{
		int[] array = new int[coords.length];
		for (int i = 0; i < array.length; i++)
			array[i] = (int) coords[i];
		return new Coordinate(array);
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
		return o instanceof CoordinateD ? Arrays.equals(coords, ((CoordinateD) o).coords) : super.equals(o);
	}
	
	@Override
	public String toString()
	{
		return String.format("Pt%s", Arrays.toString(coords));
	}
	
	/**
	 Constructs an instance of 2D double coordinates.
	 
	 @param x the x-coordinate
	 @param y the y-coordinate
	 */
	public static CoordinateD create(double x, double y)
	{
		return new CoordinateD(x, y);
	}
	
	/**
	 Constructs an instance of 3D double coordinates.
	 
	 @param x the x-coordinate
	 @param y the y-coordinate
	 @param z the z-coordinate
	 */
	public static CoordinateD create(double x, double y, double z)
	{
		return new CoordinateD(x, y, z);
	}
}
