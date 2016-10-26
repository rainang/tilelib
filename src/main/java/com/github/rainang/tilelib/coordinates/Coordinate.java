package com.github.rainang.tilelib.coordinates;

import java.util.Arrays;

/**
 An integer coordinate class. This is the base class for 2D and 3D integer coordinates. This class stores its
 coordinates in an integer array but is meant to be final.
 */
public class Coordinate
{
	/** A 2D origin constant */
	public static final Coordinate ORIGIN = Coordinate.create(0, 0);
	
	/** A 3D origin constant */
	public static final Coordinate ORIGIN_3 = Coordinate.create(0, 0, 0);
	
	final int[] coords;
	
	/**
	 Constructs integer coordinates with the specified <code>array</code>.
	 
	 @param array the array containing the coordinates
	 */
	Coordinate(int[] array)
	{
		this.coords = array;
	}
	
	/**
	 Constructs an instance of 2D integer coordinates.
	 
	 @param x the x-coordinate
	 @param y the y-coordinate
	 */
	Coordinate(int x, int y)
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
	Coordinate(int x, int y, int z)
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
	 Returns the <code>Coordinate</code> sum of this coordinate and <code>c</code>.
	 
	 @param c the <code>Coordinate</code> to add
	 
	 @return the <code>Coordinate</code> sum of this coordinate and <code>c</code>
	 */
	public Coordinate add(Coordinate c)
	{
		int[] array = new int[coords.length];
		for (int i = 0; i < array.length; i++)
			array[i] = coords[i] + c.coords[i];
		return new Coordinate(array);
	}
	
	/**
	 Returns the <code>Coordinate</code> difference of this coordinate and <code>c</code>.
	 
	 @param c the <code>Coordinate</code> to subtract
	 
	 @return the <code>Coordinate</code> difference of this coordinate and <code>c</code>
	 */
	public Coordinate sub(Coordinate c)
	{
		int[] array = new int[coords.length];
		for (int i = 0; i < array.length; i++)
			array[i] = coords[i] - c.coords[i];
		return new Coordinate(array);
	}
	
	/**
	 Returns this <code>Coordinate</code> scaled to <code>s</code>.
	 
	 @param s the integer value to scale
	 
	 @return this <code>Coordinate</code> scaled to <code>s</code>.
	 */
	public Coordinate mul(int s)
	{
		int[] array = new int[coords.length];
		for (int i = 0; i < array.length; i++)
			array[i] = coords[i] * s;
		return new Coordinate(array);
	}
	
	/**
	 Returns a <code>CoordinateD</code> instance of this <code>Coordinate</code>.
	 
	 @return a <code>CoordinateD</code> instance of this <code>Coordinate</code>
	 */
	public CoordinateD asDouble()
	{
		double[] array = new double[coords.length];
		for (int i = 0; i < array.length; i++)
			array[i] = coords[i];
		return new CoordinateD(array);
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
		return o instanceof Coordinate ? Arrays.equals(coords, ((Coordinate) o).coords) : super.equals(o);
	}
	
	@Override
	public String toString()
	{
		return String.format("Pt%s", Arrays.toString(coords));
	}
	
	/**
	 Constructs an instance of 2D integer coordinates.
	 
	 @param x the x-coordinate
	 @param y the y-coordinate
	 */
	public static Coordinate create(int x, int y)
	{
		return new Coordinate(x, y);
	}
	
	/**
	 Constructs an instance of 3D integer coordinates.
	 
	 @param x the x-coordinate
	 @param y the y-coordinate
	 @param z the z-coordinate
	 */
	public static Coordinate create(int x, int y, int z)
	{
		return new Coordinate(x, y, z);
	}
}
