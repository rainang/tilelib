package com.github.rainang.tilelib.util;

import com.github.rainang.tilelib.geometry.AABB3;
import com.github.rainang.tilelib.geometry.Point;
import com.github.rainang.tilelib.geometry.Points;

public class OctTreeMap<E> extends AbstractPointTreeMap<E>
{
	private final AABB3 bounds;
	
	private final Node<E>[] nodes = (Node<E>[]) new Node[8];
	
	public <T> OctTreeMap(OctTreeMap<T> base)
	{
		this.bounds = base.bounds;
		base.keySet()
			.forEach(p -> put(p, null));
	}
	
	public OctTreeMap(int factor, boolean center)
	{
		factor = (int) Math.pow(2, factor);
		int min = center ? -factor / 2 : 0;
		this.bounds = new AABB3(Points.at(min, min, min), Points.at(factor, factor, factor));
	}
	
	OctTreeMap(AbstractPointTreeMap<E> parent, int index, Leaf<E> leaf)
	{
		AABB3 bounds = (AABB3) parent.bounds();
		
		int x = bounds.x();
		int y = bounds.y();
		int z = bounds.z();
		int w = bounds.width() / 2;
		int h = bounds.height() / 2;
		int l = bounds.length() / 2;
		
		if ((index | 1) == index)
			x += w;
		if ((index | 2) == index)
			y += h;
		if ((index | 4) == index)
			z += l;
		
		this.bounds = new AABB3(Points.at(x, y, z), Points.at(w, h, l));
		
		this.parent = parent;
		parent.nodes()[index] = this;
		nodes()[leafIndex(leaf.getKey())] = leaf;
	}
	
	@Override
	AbstractPointTreeMap<E> newInstance(AbstractPointTreeMap<E> parent, int index, Leaf<E> leaf)
	{
		return new OctTreeMap<>(parent, index, leaf);
	}
	
	@Override
	Node<E>[] nodes()
	{
		return nodes;
	}
	
	@Override
	public AABB3 bounds()
	{
		return bounds;
	}
	
	@Override
	public int dimensions()
	{
		return 3;
	}
	
	@Override
	public int leafIndex(Point key)
	{
		int i = key.x() < bounds().centerX() ? 0 : 1;
		i |= key.y() < bounds().centerY() ? 0 : 2;
		i |= key.z() < bounds().centerZ() ? 0 : 4;
		return i;
	}
}
