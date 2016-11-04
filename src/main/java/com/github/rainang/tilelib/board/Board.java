package com.github.rainang.tilelib.board;

import com.github.rainang.tilelib.board.tile.TileShape;
import com.github.rainang.tilelib.point.Point;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class Board
{
	private final TileShape shape;
	
	private final PointFinder finder;
	
	private final Set<Point> points;
	
	protected Board(BoardFactory factory)
	{
		this.shape = factory.shape;
		this.finder = factory.finder;
		this.points = Collections.unmodifiableSet(new HashSet<>(factory.points));
	}
	
	public TileShape getShape()
	{
		return shape;
	}
	
	public PointFinder getFinder()
	{
		return finder;
	}
	
	public Set<Point> getPoints()
	{
		return points;
	}
	
	public boolean contains(Point pos)
	{
		return points.contains(pos);
	}
	
	public class Layer<T>
	{
		private final Map<Point, T> map = new HashMap<>();
		
		public Set<Point> getPoints()
		{
			return map.keySet();
		}
		
		public Collection<T> getObjects()
		{
			return Collections.unmodifiableCollection(map.values());
		}
		
		public T getObject(Point pos)
		{
			return map.get(pos);
		}
		
		public T putObject(Point pos, T obj)
		{
			if (obj == null || !points.contains(pos))
				return null;
			return map.put(pos, obj);
		}
		
		public T removeObject(Point pos)
		{
			return map.remove(pos);
		}
		
		public boolean contains(Point pos)
		{
			return map.containsKey(pos);
		}
		
		public boolean contains(T obj)
		{
			return map.containsValue(obj);
		}
	}
}
