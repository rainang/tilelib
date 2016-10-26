package com.github.rainang.tilelib.coordinates;

import java.util.function.Consumer;

import static com.github.rainang.tilelib.coordinates.HexCoordinate.create;

public class HexFinder
{
	private final HexCoordinate[] OFFSETS = new HexCoordinate[] {
			new HexCoordinate(1, -1),
			new HexCoordinate(1, 0),
			new HexCoordinate(0, 1),
			new HexCoordinate(-1, 1),
			new HexCoordinate(-1, 0),
			new HexCoordinate(0, -1)
	};
	
	public int clamp(int direction)
	{
		return direction < 0 ? direction % 6 + 6 : direction % 6;
	}
	
	public HexCoordinate offset(int direction)
	{
		return OFFSETS[direction];
	}
	
	public HexCoordinate offset(HexCoordinate hex, int direction)
	{
		return hex.add(offset(direction));
	}
	
	public HexCoordinate offset(HexCoordinate hex, int direction, int distance)
	{
		return hex.add(offset(direction).mul(distance));
	}
	
	public void line(HexCoordinate hex1, HexCoordinate hex2, Consumer<HexCoordinate> consumer)
	{
		HexCoordinate.Double eps = HexCoordinate.create(1e-6, 1e-6);
		HexCoordinate.Double hd1 = hex1.asDouble()
									   .add(eps);
		HexCoordinate.Double hd2 = hex2.asDouble()
									   .add(eps);
		
		int n = hex1.distance(hex2);
		double step = 1D / Math.max(n, 1);
		
		for (int i = 0; i <= n; i++)
		{
			double j = i * step;
			double k = 1 - j;
			consumer.accept(create(hd1.x() * k + hd2.x() * j, hd1.y() * k + hd2.y() * j).round());
		}
	}
	
	public void parallelogram(HexCoordinate hex, int side1, int side2, int direction, Consumer<HexCoordinate> consumer)
	{
		CoordinateSupplier<HexCoordinate> supplier;
		
		switch (direction)
		{
		default:
		case 0:
			supplier = (i, j) -> create(i, -j).add(hex);
			break;
		case 1:
			supplier = (i, j) -> create(j, i - j).add(hex);
			break;
		case 2:
			supplier = (i, j) -> create(j - i, i).add(hex);
			break;
		case 3:
			supplier = (i, j) -> create(-i, j).add(hex);
			break;
		case 4:
			supplier = (i, j) -> create(-j, j - i).add(hex);
			break;
		case 5:
			supplier = (i, j) -> create(i - j, -i).add(hex);
			break;
		}
		
		for (int i = 0; i <= side1; i++)
			for (int j = 0; j <= side2; j++)
				consumer.accept(supplier.create(i, j));
	}
	
	public void rectangle(HexCoordinate hex, int side1, int side2, int direction, Consumer<HexCoordinate> consumer)
	{
		CoordinateSupplier<HexCoordinate> supplier;
		
		switch (direction)
		{
		default:
		case 0:
			supplier = (i, j) -> create(j, i).add(hex);
			break;
		case 1:
			supplier = (i, j) -> create(i, -i - j).add(hex);
			break;
		case 2:
			supplier = (i, j) -> create(-i - j, j).add(hex);
			break;
		case 3:
			supplier = (i, j) -> create(i, j).add(hex);
			break;
		case 4:
			supplier = (i, j) -> create(-i - j, i).add(hex);
			break;
		case 5:
			supplier = (i, j) -> create(j, -i - j).add(hex);
			break;
		}
		
		for (int r = 0; r <= side2; r++)
		{
			int r_offset = r / 2;
			for (int q = -r_offset; q <= side1 - r_offset; q++)
				consumer.accept(supplier.create(q, r));
		}
	}
	
	public void triangle(HexCoordinate hex, int size, int direction, Consumer<HexCoordinate> consumer)
	{
		CoordinateSupplier<HexCoordinate> supplier;
		
		switch (direction)
		{
		default:
		case 0:
			supplier = (i, j) -> create(i, -j).add(hex);
			break;
		case 1:
			supplier = (i, j) -> create(j, i - j).add(hex);
			break;
		case 2:
			supplier = (i, j) -> create(-j, i).add(hex);
			break;
		case 3:
			supplier = (i, j) -> create(-i, j).add(hex);
			break;
		case 4:
			supplier = (i, j) -> create(-j, j - i).add(hex);
			break;
		case 5:
			supplier = (i, j) -> create(j, -i).add(hex);
			break;
		}
		
		for (int i = 0; i <= size; i++)
			for (int j = 0; j <= i; j++)
				consumer.accept(supplier.create(i, j));
	}
	
	public void range(HexCoordinate hex, int radius, Consumer<HexCoordinate> consumer)
	{
		for (int x = -radius; x <= radius; x++)
		{
			int y1 = Math.max(-radius, -x - radius);
			int y2 = Math.min(radius, -x + radius);
			for (int y = y1; y <= y2; y++)
				consumer.accept(create(x + hex.x(), y + hex.y()));
		}
	}
	
	public void rangeIntersection(HexCoordinate hex1, int radius1, HexCoordinate hex2, int radius2,
								  Consumer<HexCoordinate> consumer)
	{
		int xMin = Math.max(hex1.x() - radius1, hex2.x() - radius2);
		int xMax = Math.min(hex1.x() + radius1, hex2.x() + radius2);
		int yMin = Math.max(hex1.y() - radius1, hex2.y() - radius2);
		int yMax = Math.min(hex1.y() + radius1, hex2.y() + radius2);
		int zMin = Math.max(hex1.z() - radius1, hex2.z() - radius2);
		int zMax = Math.min(hex1.z() + radius1, hex2.z() + radius2);
		
		for (int x = xMin; x <= xMax; x++)
		{
			int min = Math.max(yMin, -x - zMax);
			int max = Math.min(yMax, -x - zMin);
			for (int y = min; y <= max; y++)
				consumer.accept(create(x, y));
		}
	}
	
	public void ring(HexCoordinate hex, int direction, int radius, Consumer<HexCoordinate> consumer)
	{
		hex = offset(hex, direction, radius);
		for (int i = 0; i < 6; i++)
		{
			int k = clamp(i + direction + 2);
			for (int j = 0; j < radius; j++)
			{
				consumer.accept(hex);
				hex = offset(hex, k);
			}
		}
	}
	
	public void spiral(HexCoordinate hex, int direction, int radius, Consumer<HexCoordinate> consumer)
	{
		for (int i = 1; i <= radius; i++)
			ring(hex, direction, i, consumer);
	}
}
