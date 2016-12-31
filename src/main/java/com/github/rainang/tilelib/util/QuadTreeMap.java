package com.github.rainang.tilelib.util;

import com.github.rainang.tilelib.geometry.AABB;
import com.github.rainang.tilelib.geometry.Point;
import com.github.rainang.tilelib.geometry.Points;

public class QuadTreeMap<E> extends AbstractPointTreeMap<E>
{
	private final AABB bounds;
	
	private final Node<E>[] nodes = (Node<E>[]) new Node[4];
	
	public <T> QuadTreeMap(QuadTreeMap<T> base)
	{
		this.bounds = base.bounds;
		base.keySet()
			.forEach(p -> put(p, null));
	}
	
	public QuadTreeMap(int factor, boolean center)
	{
		factor = (int) Math.pow(2, factor);
		int min = center ? -factor / 2 : 0;
		this.bounds = new AABB(Points.at(min, min), Points.at(factor, factor));
	}
	
	QuadTreeMap(AbstractPointTreeMap<E> parent, int index, Leaf<E> leaf)
	{
		AABB bounds = parent.bounds();
		
		int x = bounds.x();
		int y = bounds.y();
		int w = bounds.width() / 2;
		int h = bounds.height() / 2;
		
		if ((index | 1) == index)
			x += w;
		if ((index | 2) == index)
			y += h;
		
		this.bounds = new AABB(Points.at(x, y), Points.at(w, h));
		
		this.parent = parent;
		parent.nodes()[index] = this;
		nodes()[leafIndex(leaf.getKey())] = leaf;
	}
	
	@Override
	AbstractPointTreeMap<E> newInstance(AbstractPointTreeMap<E> parent, int index, Leaf<E> leaf)
	{
		return new QuadTreeMap<>(parent, index, leaf);
	}
	
	@Override
	Node<E>[] nodes()
	{
		return nodes;
	}
	
	@Override
	public AABB bounds()
	{
		return bounds;
	}
	
	@Override
	public int dimensions()
	{
		return 2;
	}
	
	@Override
	public int leafIndex(Point key)
	{
		int i = key.x() < bounds().centerX() ? 0 : 1;
		i |= key.y() < bounds().centerY() ? 0 : 2;
		return i;
	}
}
