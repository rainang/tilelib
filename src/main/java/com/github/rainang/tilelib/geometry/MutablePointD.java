package com.github.rainang.tilelib.geometry;

import java.util.Arrays;

/**
 A mutable point representing a location in 2- or 3-dimensional space, specified in double precision.
 *
 * @see Points
 */
public class MutablePointD extends PointD
{
	// Constructors
	
	MutablePointD(double[] array)
	{
		super(array);
	}
	
	// Views
	
	@Override
	public PointD asImmutable()
	{
		return new PointD(array);
	}
	
	// Setters
	
	/**
	 Sets the x-coordinate.
	 
	 @param x the x-coordinate
	 
	 @return this <code>MutablePointD</code>
	 */
	public MutablePointD setX(double x)
	{
		array[0] = x;
		return this;
	}
	
	/**
	 Sets the y-coordinate.
	 
	 @param y the y-coordinate
	 
	 @return this <code>MutablePointD</code>
	 */
	public MutablePointD setY(double y)
	{
		array[1] = y;
		return this;
	}
	
	/**
	 Sets the z-coordinate.
	 
	 @param z the z-coordinate
	 
	 @return this <code>MutablePointD</code>
	 
	 @throws ArrayIndexOutOfBoundsException if this is a 2-dimensional point
	 */
	public MutablePointD setZ(double z)
	{
		array[2] = z;
		return this;
	}
	
	/**
	 Sets all values of this point to <code>n</code>.
	 
	 @param n the value to set
	 
	 @return this <code>MutablePointD</code>
	 */
	public MutablePointD set(double n)
	{
		Arrays.fill(array, n);
		return this;
	}
	
	/**
	 Sets the x and y coordinates.
	 
	 @param x the x-coordinate
	 @param y the y-coordinate
	 
	 @return this <code>MutablePointD</code>
	 */
	public MutablePointD set(double x, double y)
	{
		array[0] = x;
		array[1] = y;
		return this;
	}
	
	/**
	 Sets the x, y, and z coordinates.
	 
	 @param x the x-coordinate
	 @param y the y-coordinate
	 @param z the z-coordinate
	 
	 @return this <code>MutablePointD</code>
	 
	 @throws ArrayIndexOutOfBoundsException if this is a 2-dimensional point
	 */
	public MutablePointD set(double x, double y, double z)
	{
		array[0] = x;
		array[1] = y;
		array[2] = z;
		return this;
	}
	
	/**
	 Sets the values of this point to the values of the specified point <code>p</code>.
	 
	 @param p the point to set this point's values to
	 
	 @return this <code>MutablePointD</code>
	 
	 @throws ArrayIndexOutOfBoundsException if this is a 3-dimensional point and <code>p</code> is not
	 */
	public MutablePointD set(PointD p)
	{
		System.arraycopy(p.array, 0, array, 0, array.length);
		return this;
	}
	
	// Operations
	
	/**
	 Negates the values of this point.
	 
	 @return this <code>MutablePointD</code>
	 */
	public MutablePointD negate()
	{
		for (int i = 0; i < array.length; i++)
			array[i] = -array[i];
		return this;
	}
	
	/**
	 Adds the values of this point by the specified point <code>p</code>.
	 
	 @param p the point to add
	 
	 @return this <code>MutablePointD</code>
	 
	 @throws ArrayIndexOutOfBoundsException if this is a 3-dimensional point and <code>p</code> is not
	 */
	public MutablePointD add(PointD p)
	{
		for (int i = 0; i < array.length; i++)
			array[i] += p.array[i];
		return this;
	}
	
	/**
	 Subtracts the values of this point by the specified point <code>p</code>.
	 
	 @param p the point to subtract
	 
	 @return this <code>MutablePointD</code>
	 
	 @throws ArrayIndexOutOfBoundsException if this is a 3-dimensional point and <code>p</code> is not
	 */
	public MutablePointD sub(PointD p)
	{
		for (int i = 0; i < array.length; i++)
			array[i] -= p.array[i];
		return this;
	}
	
	/**
	 Multiplies the values of this point by the specified point <code>p</code>.
	 
	 @param p the point to multiply
	 
	 @return this <code>MutablePointD</code>
	 
	 @throws ArrayIndexOutOfBoundsException if this is a 3-dimensional point and <code>p</code> is not
	 */
	public MutablePointD mul(PointD p)
	{
		for (int i = 0; i < array.length; i++)
			array[i] *= p.array[i];
		return this;
	}
	
	/**
	 Adds the values of this point by the specified point <code>p</code>.
	 
	 @param p the point to add
	 
	 @return this <code>MutablePointD</code>
	 
	 @throws ArrayIndexOutOfBoundsException if this is a 3-dimensional point and <code>p</code> is not
	 */
	public MutablePointD add(Point p)
	{
		for (int i = 0; i < array.length; i++)
			array[i] += p.array[i];
		return this;
	}
	
	/**
	 Subtracts the values of this point by the specified point <code>p</code>.
	 
	 @param p the point to subtract
	 
	 @return this <code>MutablePointD</code>
	 
	 @throws ArrayIndexOutOfBoundsException if this is a 3-dimensional point and <code>p</code> is not
	 */
	public MutablePointD sub(Point p)
	{
		for (int i = 0; i < array.length; i++)
			array[i] += p.array[i];
		return this;
	}
	
	/**
	 Multiplies the values of this point by the specified point <code>p</code>.
	 
	 @param p the point to multiply
	 
	 @return this <code>MutablePointD</code>
	 
	 @throws ArrayIndexOutOfBoundsException if this is a 3-dimensional point and <code>p</code> is not
	 */
	public MutablePointD mul(Point p)
	{
		for (int i = 0; i < array.length; i++)
			array[i] *= p.array[i];
		return this;
	}
	
	/**
	 Translates the x-coordinate by <code>x</code>.
	 
	 @param x the x translation
	 
	 @return this <code>MutablePointD</code>
	 */
	public MutablePointD translateX(double x)
	{
		array[0] += x;
		return this;
	}
	
	/**
	 Translates the y-coordinate by <code>y</code>.
	 
	 @param y the y translation
	 
	 @return this <code>MutablePointD</code>
	 */
	public MutablePointD translateY(double y)
	{
		array[1] += y;
		return this;
	}
	
	/**
	 Translates the z-coordinate by <code>z</code>.
	 
	 @param z the z translation
	 
	 @return this <code>MutablePointD</code>
	 
	 @throws ArrayIndexOutOfBoundsException if this is a 2-dimensional point
	 */
	public MutablePointD translateZ(double z)
	{
		array[2] += z;
		return this;
	}
	
	/**
	 Translates the values of this point by <code>n</code>.
	 
	 @param n the value to translate
	 
	 @return this <code>MutablePointD</code>
	 */
	public MutablePointD translate(double n)
	{
		for (int i = 0; i < array.length; i++)
			array[i] += n;
		return this;
	}
	
	/**
	 Translates the x and y coordinates.
	 
	 @param x the x translation
	 @param y the y translation
	 
	 @return this <code>MutablePointD</code>
	 */
	public MutablePointD translate(double x, double y)
	{
		array[0] += x;
		array[1] += y;
		return this;
	}
	
	/**
	 Translates the x, y, and z coordinates.
	 
	 @param x the x translation
	 @param y the y translation
	 @param z the z translation
	 
	 @return this <code>MutablePointD</code>
	 
	 @throws ArrayIndexOutOfBoundsException if this is a 2-dimensional point
	 */
	public MutablePointD translate(double x, double y, double z)
	{
		array[0] += x;
		array[1] += y;
		array[2] += z;
		return this;
	}
	
	/**
	 Scales the x-coordinate by <code>x</code>.
	 
	 @param x the x scale
	 
	 @return this <code>MutablePointD</code>
	 */
	public MutablePointD scaleX(double x)
	{
		array[0] *= x;
		return this;
	}
	
	/**
	 Scales the y-coordinate by <code>y</code>.
	 
	 @param y the y scale
	 
	 @return this <code>MutablePointD</code>
	 */
	public MutablePointD scaleY(double y)
	{
		array[1] *= y;
		return this;
	}
	
	/**
	 Scales the z-coordinate by <code>z</code>.
	 
	 @param z the z scale
	 
	 @return this <code>MutablePointD</code>
	 
	 @throws ArrayIndexOutOfBoundsException if this is a 2-dimensional point
	 */
	public MutablePointD scaleZ(double z)
	{
		array[2] *= z;
		return this;
	}
	
	/**
	 Scales the values of this point by <code>n</code>.
	 
	 @param n the value to scale
	 
	 @return this <code>MutablePointD</code>
	 */
	public MutablePointD scale(double n)
	{
		for (int i = 0; i < array.length; i++)
			array[i] *= n;
		return this;
	}
	
	/**
	 Scales the x and y coordinate.
	 
	 @param x the x scale
	 @param y the y scale
	 
	 @return this <code>MutablePointD</code>
	 */
	public MutablePointD scale(double x, double y)
	{
		array[0] *= x;
		array[1] *= y;
		return this;
	}
	
	/**
	 Scales the x, y, and z coordinate.
	 
	 @param x the x scale
	 @param y the y scale
	 @param z the z scale
	 
	 @return this <code>MutablePointD</code>
	 
	 @throws ArrayIndexOutOfBoundsException if this is a 2-dimensional point
	 */
	public MutablePointD scale(double x, double y, double z)
	{
		array[0] *= x;
		array[1] *= y;
		array[2] *= z;
		return this;
	}
	
	// Overrides
	
	@Override
	public String toString()
	{
		return String.format("MP%s", Arrays.toString(array));
	}
}
