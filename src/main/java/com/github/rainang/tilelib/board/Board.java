package com.github.rainang.tilelib.board;

import com.github.rainang.tilelib.board.tile.Tile;
import com.github.rainang.tilelib.point.MutablePoint;
import com.github.rainang.tilelib.point.Point;
import com.github.rainang.tilelib.point.Points;

import java.util.*;
import java.util.function.Function;

public class Board<T extends Tile>
{
	private final Map<Point, T> tileMap;
	
	public Board(Set<Point> points, Function<Point, T> tileSupplier)
	{
		Map<Point, T> map = new HashMap<>();
		points.forEach(c -> map.put(c, tileSupplier.apply(c)));
		tileMap = Collections.unmodifiableMap(map);
	}
	
	public Map<Point, T> getTileMap()
	{
		return tileMap;
	}
	
	public Set<Point> getCoordinates()
	{
		return tileMap.keySet();
	}
	
	public Collection<T> getTiles()
	{
		return tileMap.values();
	}
	
	public T getTile(Point pos)
	{
		return tileMap.get(pos);
	}
	
	public boolean contains(Point pos)
	{
		return tileMap.containsKey(pos);
	}
	
	public static abstract class Builder<T extends Tile, B extends Board<T>> implements javafx.util.Builder<B>
	{
		private final TileFinder finder;
		
		private final Set<Point> points = new HashSet<>();
		
		Builder(TileFinder finder)
		{
			this.finder = finder;
		}
		
		public TileFinder getFinder()
		{
			return finder;
		}
		
		public boolean addCoordinates(Collection<Point> points)
		{
			return this.points.addAll(points);
		}
		
		public boolean removeCoordinates(Collection<Point> points)
		{
			return this.points.removeAll(points);
		}
		
		public boolean addCoordinate(Point point)
		{
			return points.add(point.asImmutable());
		}
		
		public boolean removeCoordinate(Point point)
		{
			return points.remove(point);
		}
		
		public Set<Point> getPoints()
		{
			return Collections.unmodifiableSet(points);
		}
		
		public boolean validateArena()
		{
			List<Point> coords = new ArrayList<>(points);
			Queue<Point> search = new ArrayDeque<>();
			
			search.offer(coords.get(0));
			
			MutablePoint temp = coords.get(0)
									  .asMutable();
			while (!search.isEmpty())
			{
				Point p = search.poll();
				coords.remove(p);
				for (int i = 0; i < 6; i++)
				{
					finder.offset(temp.set(p), i);
					if (!search.contains(temp) && coords.contains(temp))
						search.offer(temp.asImmutable());
				}
			}
			
			return coords.isEmpty();
		}
	}
	
	public static <T extends Tile> Board.Builder<T, Board<T>> defaultHexBuilder(Function<Point, T> tileSupplier)
	{
		return new Builder<T, Board<T>>(new HexFinder())
		{
			@Override
			public Board<T> build()
			{
				return new Board<>(getPoints(), tileSupplier);
			}
			
			@Override
			public boolean validateArena()
			{
				if (getPoints().removeIf(p -> !Points.isHexPoint(p)))
					throw new IllegalStateException("Hex boards can only accept hex points");
				return super.validateArena();
			}
		};
	}
	
	public static <T extends Tile> Board.Builder<T, Board<T>> defaultQuadBuilder(Function<Point, T> tileSupplier)
	{
		return new Builder<T, Board<T>>(new QuadFinder())
		{
			@Override
			public Board<T> build()
			{
				return new Board<>(getPoints(), tileSupplier);
			}
		};
	}
	
	public static <B extends Board<T>, T extends Tile> Board.Builder<T, Board<T>> customHexBuilder
			(Function<Set<Point>, B> boardSupplier)
	{
		return new Builder<T, Board<T>>(new HexFinder())
		{
			@Override
			public B build()
			{
				return boardSupplier.apply(getPoints());
			}
			
			@Override
			public boolean validateArena()
			{
				if (getPoints().removeIf(p -> !Points.isHexPoint(p)))
					throw new IllegalStateException("Hex boards can only accept hex points");
				return super.validateArena();
			}
		};
	}
	
	public static <B extends Board<T>, T extends Tile> Board.Builder<T, Board<T>> customQuadBuilder
			(Function<Set<Point>, B> boardSupplier)
	{
		return new Builder<T, Board<T>>(new QuadFinder())
		{
			@Override
			public B build()
			{
				return boardSupplier.apply(getPoints());
			}
		};
	}
}
