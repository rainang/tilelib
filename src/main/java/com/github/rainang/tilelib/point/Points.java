package com.github.rainang.tilelib.point;

/**
 * This class consists exclusively of static factory and utility methods for all point classes.
 * <p>
 * All point classes have package private constructors and are effectively final. They can only be instantiated through
 * this class.</p>
 *
 * @see Point
 * @see MutablePoint
 * @see PointD
 * @see MutablePointD
 */
public final class Points
{
	// Suppresses default constructor, ensuring non-instantiability.
	private Points()
	{
		throw new IllegalStateException();
	}
	
	/**
	 * An immutable 2-dimensional integer point origin constant
	 */
	public static final Point ORIGIN = at(0, 0);
	
	/**
	 * An immutable 3-dimensional integer point origin constant
	 */
	public static final Point ORIGIN_Z = at(0, 0, 0);
	
	/**
	 * An immutable 2-dimensional double point origin constant
	 */
	public static final PointD ORIGIN_D = doubleAt(0, 0);
	
	/**
	 * An immutable 3-dimensional double point origin constant
	 */
	public static final PointD ORIGIN_DZ = doubleAt(0, 0, 0);
	
	/**
	 * Returns an immutable 2-dimensional point specified in integer precision.
	 *
	 * @param x the x-coordinate
	 * @param y the y-coordinate
	 *
	 * @return an immutable 2-dimensional point specified in integer precision
	 */
	public static Point at(int x, int y)
	{
		return new Point(new int[] {
				x,
				y
		});
	}
	
	/**
	 * Returns an immutable 3-dimensional point specified in integer precision.
	 *
	 * @param x the x-coordinate
	 * @param y the y-coordinate
	 * @param z the z-coordinate
	 *
	 * @return an immutable 3-dimensional point specified in integer precision
	 */
	public static Point at(int x, int y, int z)
	{
		return new Point(new int[] {
				x,
				y,
				z
		});
	}
	
	/**
	 * Returns an immutable 3-dimensional hex point specified in integer precision.
	 *
	 * @param x the x-coordinate
	 * @param y the y-coordinate
	 *
	 * @return an immutable 3-dimensional hex point specified in integer precision
	 */
	public static Point hexAt(int x, int y)
	{
		return at(x, y, -x - y);
	}
	
	/**
	 * Returns a mutable 2-dimensional point specified in integer precision.
	 *
	 * @param x the x-coordinate
	 * @param y the y-coordinate
	 *
	 * @return a mutable 2-dimensional point specified in integer precision
	 */
	public static MutablePoint mutableAt(int x, int y)
	{
		return new MutablePoint(new int[] {
				x,
				y
		});
	}
	
	/**
	 * Returns a mutable 3-dimensional point specified in integer precision.
	 *
	 * @param x the x-coordinate
	 * @param y the y-coordinate
	 * @param z the z-coordinate
	 *
	 * @return a mutable 3-dimensional point specified in integer precision
	 */
	public static MutablePoint mutableAt(int x, int y, int z)
	{
		return new MutablePoint(new int[] {
				x,
				y,
				z
		});
	}
	
	/**
	 * Returns a mutable 2-dimensional hex point specified in integer precision.
	 *
	 * @param x the x-coordinate
	 * @param y the y-coordinate
	 *
	 * @return a mutable 2-dimensional hex point specified in integer precision
	 */
	public static MutablePoint mutableHexAt(int x, int y)
	{
		return mutableAt(x, y, -x - y);
	}
	
	/**
	 * Returns a mutable 2-dimensional point at <code>(0,0)</code> specified in integer precision.
	 *
	 * @return a mutable 2-dimensional point at <code>(0,0)</code> specified in integer precision
	 */
	public static MutablePoint origin()
	{
		return mutableAt(0, 0);
	}
	
	/**
	 * Returns a mutable 3-dimensional point at <code>(0,0,0)</code> specified in integer precision.
	 *
	 * @return a mutable 3-dimensional point at <code>(0,0,0)</code> specified in integer precision
	 */
	public static MutablePoint originZ()
	{
		return mutableAt(0, 0, 0);
	}
	
	/**
	 * Returns an immutable 2-dimensional point specified in double precision.
	 *
	 * @param x the x-coordinate
	 * @param y the y-coordinate
	 *
	 * @return an immutable 2-dimensional point specified in double precision
	 */
	public static PointD doubleAt(double x, double y)
	{
		return new PointD(new double[] {
				x,
				y
		});
	}
	
	/**
	 * Returns an immutable 3-dimensional point specified in double precision.
	 *
	 * @param x the x-coordinate
	 * @param y the y-coordinate
	 * @param z the z-coordinate
	 *
	 * @return an immutable 3-dimensional point specified in double precision
	 */
	public static PointD doubleAt(double x, double y, double z)
	{
		return new PointD(new double[] {
				x,
				y,
				z
		});
	}
	
	/**
	 * Returns an immutable 3-dimensional hex point specified in double precision.
	 *
	 * @param x the x-coordinate
	 * @param y the y-coordinate
	 *
	 * @return an immutable 3-dimensional hex point specified in double precision
	 */
	public static PointD doubleHexAt(double x, double y)
	{
		return doubleAt(x, y, -x - y);
	}
	
	/**
	 * Returns a mutable 2-dimensional point specified in double precision.
	 *
	 * @param x the x-coordinate
	 * @param y the y-coordinate
	 *
	 * @return a mutable 2-dimensional point specified in double precision
	 */
	public static MutablePointD mutableDoubleAt(double x, double y)
	{
		return new MutablePointD(new double[] {
				x,
				y
		});
	}
	
	/**
	 * Returns a mutable 3-dimensional point specified in double precision.
	 *
	 * @param x the x-coordinate
	 * @param y the y-coordinate
	 * @param z the z-coordinate
	 *
	 * @return a mutable 3-dimensional point specified in double precision
	 */
	public static MutablePointD mutableDoubleAt(double x, double y, double z)
	{
		return new MutablePointD(new double[] {
				x,
				y,
				z
		});
	}
	
	/**
	 * Returns a mutable 2-dimensional hex point specified in double precision.
	 *
	 * @param x the x-coordinate
	 * @param y the y-coordinate
	 *
	 * @return a mutable 2-dimensional hex point specified in double precision
	 */
	public static MutablePointD mutableDoubleHexAt(double x, double y)
	{
		return mutableDoubleAt(x, y, -x - y);
	}
	
	/**
	 * Returns a mutable 2-dimensional point at <code>(0,0)</code> specified in double precision.
	 *
	 * @return a mutable 2-dimensional point at <code>(0,0)</code> specified in double precision
	 */
	public static MutablePointD doubleOrigin()
	{
		return mutableDoubleAt(0, 0);
	}
	
	/**
	 * Returns a mutable 3-dimensional point at <code>(0,0,0)</code> specified in double precision.
	 *
	 * @return a mutable 3-dimensional point at <code>(0,0,0)</code> specified in double precision
	 */
	public static MutablePointD doubleOriginZ()
	{
		return mutableDoubleAt(0, 0, 0);
	}
	
	/**
	 * Returns true if point <code>p</code> is a hex point. More formally, this returns true if <code>x + y + z =
	 * 0</code>.
	 *
	 * @param p the point to check
	 *
	 * @return true if point <code>p</code> is a hex point
	 */
	public static boolean isHexPoint(Point p)
	{
		return p != null && p.array.length == 3 && p.x() + p.y() == -p.z();
	}
	
	/**
	 * Returns true if point <code>p</code> is a hex point. More formally, this returns true if <code>x + y + z =
	 * 0</code>.
	 *
	 * @param p the point to check
	 *
	 * @return true if point <code>p</code> is a hex point
	 */
	public static boolean isHexPoint(PointD p)
	{
		return p != null && p.array.length == 3 && p.x() + p.y() == -p.z();
	}
}
