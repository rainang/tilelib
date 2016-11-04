package com.github.rainang.tilelib.board;

import com.github.rainang.tilelib.board.tile.TileShape;
import com.github.rainang.tilelib.point.MutablePoint;
import com.github.rainang.tilelib.point.Point;
import com.github.rainang.tilelib.point.Points;

import java.util.*;
import java.util.function.Function;
import java.util.function.Predicate;

public class BoardFactory implements javafx.util.Builder<Board>
{
	final TileShape shape;
	
	final PointFinder finder;
	
	final Set<Point> points = new HashSet<>();
	
	private final Function<BoardFactory, Board> boardSupplier;
	
	private Predicate<Point> validation;
	
	public BoardFactory(TileShape shape)
	{
		this(shape, Board::new);
	}
	
	public BoardFactory(TileShape shape, Function<BoardFactory, Board> boardSupplier)
	{
		this.shape = shape;
		this.finder = shape == TileShape.QUAD ? new QuadFinder() : new HexFinder();
		this.boardSupplier = boardSupplier;
		this.validation = shape == TileShape.QUAD ? Points::is2Dimensional : Points::isHexPoint;
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
		return validation.test(point) && points.add(point.asImmutable());
	}
	
	public boolean removeCoordinate(Point point)
	{
		return points.remove(point);
	}
	
	public Set<Point> getPoints()
	{
		return Collections.unmodifiableSet(points);
	}
	
	public boolean isSingular()
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
	
	public void addPointValidation(Predicate<Point> predicate)
	{
		validation = validation.and(predicate);
	}
	
	public Board build()
	{
		return boardSupplier.apply(this);
	}
}