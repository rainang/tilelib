package com.github.rainang.tilelib.point;

import java.util.Arrays;

/**
 A mutable point representing a location in 2- or 3-dimensional space, specified in integer precision.
 *
 * @see Points
 */
public class MutablePoint extends Point
{
	// CONSTRUCTORS
	
	/**
	 Constructs a mutable 2-dimensional point.
	 
	 @param x the x-coordinate
	 @param y the y-coordinate
	 
	 @return a mutable 2-dimensional point
	 */
	public static MutablePoint create(int x, int y)
	{
		return new MutablePoint(new int[] {
				x,
				y
		});
	}
	
	/**
	 Constructs a mutable 3-dimensional point.
	 
	 @param x the x-coordinate
	 @param y the y-coordinate
	 @param z the z-coordinate
	 
	 @return a mutable 3-dimensional point
	 */
	public static MutablePoint create(int x, int y, int z)
	{
		return new MutablePoint(new int[] {
				x,
				y,
				z
		});
	}
	
	/**
	 Constructs a mutable 3-dimensional hex point.
	 
	 @param x the x-coordinate
	 @param y the y-coordinate
	 
	 @return a mutable 3-dimensional hex point
	 */
	public static MutablePoint createHex(int x, int y)
	{
		return new MutablePoint(new int[] {
				x,
				y,
				-x - y
		});
	}
	
	MutablePoint(int[] array)
	{
		super(array);
	}
	
	// VIEWS
	
	/**
	 Returns an immutable instance of this point.
	 
	 @return an immutable instance of this point
	 */
	public Point asImmutable()
	{
		return new Point(array);
	}
	
	// SETTERS
	
	/**
	 Sets the x-coordinate.
	 
	 @param x the x-coordinate
	 
	 @return this <code>MutablePoint</code>
	 */
	public MutablePoint setX(int x)
	{
		array[0] = x;
		return this;
	}
	
	/**
	 Sets the y-coordinate.
	 
	 @param y the y-coordinate
	 
	 @return this <code>MutablePoint</code>
	 */
	public MutablePoint setY(int y)
	{
		array[1] = y;
		return this;
	}
	
	/**
	 Sets the z-coordinate.
	 
	 @param z the z-coordinate
	 
	 @return this <code>MutablePoint</code>
	 
	 @throws ArrayIndexOutOfBoundsException if this is a 2-dimensional point
	 */
	public MutablePoint setZ(int z)
	{
		array[2] = z;
		return this;
	}
	
	/**
	 Sets all values of this point to <code>n</code>.
	 
	 @param n the value to set
	 
	 @return this <code>MutablePoint</code>
	 */
	public MutablePoint set(int n)
	{
		Arrays.fill(array, n);
		return this;
	}
	
	/**
	 Sets the x and y coordinates.
	 
	 @param x the x-coordinate
	 @param y the y-coordinate
	 
	 @return this <code>MutablePoint</code>
	 */
	public MutablePoint set(int x, int y)
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
	 
	 @return this <code>MutablePoint</code>
	 
	 @throws ArrayIndexOutOfBoundsException if this is a 2-dimensional point
	 */
	public MutablePoint set(int x, int y, int z)
	{
		array[0] = x;
		array[1] = y;
		array[2] = z;
		return this;
	}
	
	/**
	 Sets the values of this point to the values of the specified point <code>p</code>.
	 
	 @param p the point to set this point's values to
	 
	 @return this <code>MutablePoint</code>
	 
	 @throws ArrayIndexOutOfBoundsException if this is a 3-dimensional point and <code>p</code> is not
	 */
	public MutablePoint set(Point p)
	{
		System.arraycopy(p.array, 0, array, 0, array.length);
		return this;
	}
	
	// OPERATIONS
	
	/**
	 Negates the values of this point.
	 
	 @return this <code>MutablePoint</code>
	 */
	public MutablePoint negate()
	{
		for (int i = 0; i < array.length; i++)
			array[i] = -array[i];
		return this;
	}
	
	/**
	 Adds the values of this point by the specified point <code>p</code>.
	 
	 @param p the point to add
	 
	 @return this <code>MutablePoint</code>
	 
	 @throws ArrayIndexOutOfBoundsException if this is a 3-dimensional point and <code>p</code> is not
	 */
	public MutablePoint add(Point p)
	{
		for (int i = 0; i < array.length; i++)
			array[i] += p.array[i];
		return this;
	}
	
	/**
	 Subtracts the values of this point by the specified point <code>p</code>.
	 
	 @param p the point to subtract
	 
	 @return this <code>MutablePoint</code>
	 
	 @throws ArrayIndexOutOfBoundsException if this is a 3-dimensional point and <code>p</code> is not
	 */
	public MutablePoint sub(Point p)
	{
		for (int i = 0; i < array.length; i++)
			array[i] -= p.array[i];
		return this;
	}
	
	/**
	 Multiplies the values of this point by the specified point <code>p</code>.
	 
	 @param p the point to multiply
	 
	 @return this <code>MutablePoint</code>
	 
	 @throws ArrayIndexOutOfBoundsException if this is a 3-dimensional point and <code>p</code> is not
	 */
	public MutablePoint mul(Point p)
	{
		for (int i = 0; i < array.length; i++)
			array[i] *= p.array[i];
		return this;
	}
	
	/**
	 Translates the x-coordinate by <code>x</code>.
	 
	 @param x the x translation
	 
	 @return this <code>MutablePoint</code>
	 */
	public MutablePoint translateX(int x)
	{
		array[0] += x;
		return this;
	}
	
	/**
	 Translates the y-coordinate by <code>y</code>.
	 
	 @param y the y translation
	 
	 @return this <code>MutablePoint</code>
	 */
	public MutablePoint translateY(int y)
	{
		array[1] += y;
		return this;
	}
	
	/**
	 Translates the z-coordinate by <code>z</code>.
	 
	 @param z the z translation
	 
	 @return this <code>MutablePoint</code>
	 
	 @throws ArrayIndexOutOfBoundsException if this is a 2-dimensional point
	 */
	public MutablePoint translateZ(int z)
	{
		array[2] += z;
		return this;
	}
	
	/**
	 Translates the values of this point by <code>n</code>.
	 
	 @param n the value to translate
	 
	 @return this <code>MutablePoint</code>
	 */
	public MutablePoint translate(int n)
	{
		for (int i = 0; i < array.length; i++)
			array[i] += n;
		return this;
	}
	
	/**
	 Translates the x and y coordinates.
	 
	 @param x the x translation
	 @param y the y translation
	 
	 @return this <code>MutablePoint</code>
	 */
	public MutablePoint translate(int x, int y)
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
	 
	 @return this <code>MutablePoint</code>
	 
	 @throws ArrayIndexOutOfBoundsException if this is a 2-dimensional point
	 */
	public MutablePoint translate(int x, int y, int z)
	{
		array[0] += x;
		array[1] += y;
		array[2] += z;
		return this;
	}
	
	/**
	 Scales the x-coordinate by <code>x</code>.
	 
	 @param x the x scale
	 
	 @return this <code>MutablePoint</code>
	 */
	public MutablePoint scaleX(int x)
	{
		array[0] *= x;
		return this;
	}
	
	/**
	 Scales the y-coordinate by <code>y</code>.
	 
	 @param y the y scale
	 
	 @return this <code>MutablePoint</code>
	 */
	public MutablePoint scaleY(int y)
	{
		array[1] *= y;
		return this;
	}
	
	/**
	 Scales the z-coordinate by <code>z</code>.
	 
	 @param z the z scale
	 
	 @return this <code>MutablePoint</code>
	 
	 @throws ArrayIndexOutOfBoundsException if this is a 2-dimensional point
	 */
	public MutablePoint scaleZ(int z)
	{
		array[2] *= z;
		return this;
	}
	
	/**
	 Scales the values of this point by <code>n</code>.
	 
	 @param n the value to scale
	 
	 @return this <code>MutablePoint</code>
	 */
	public MutablePoint scale(int n)
	{
		for (int i = 0; i < array.length; i++)
			array[i] *= n;
		return this;
	}
	
	/**
	 Scales the x and y coordinate.
	 
	 @param x the x scale
	 @param y the y scale
	 
	 @return this <code>MutablePoint</code>
	 */
	public MutablePoint scale(int x, int y)
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
	 
	 @return this <code>MutablePoint</code>
	 
	 @throws ArrayIndexOutOfBoundsException if this is a 2-dimensional point
	 */
	public MutablePoint scale(int x, int y, int z)
	{
		array[0] *= x;
		array[1] *= y;
		array[2] *= z;
		return this;
	}
	
	// OVERRIDES
	
	@Override
	public String toString()
	{
		return String.format("MP%s", Arrays.toString(array));
	}
}
