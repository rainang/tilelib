package com.github.rainang.tilelib.util;

import com.github.rainang.tilelib.geometry.Point;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.function.BiFunction;
import java.util.function.BiPredicate;
import java.util.function.Consumer;

abstract class AbstractPointTreeMap<E> implements PointTreeMap<E>
{
	AbstractPointTreeMap<E> parent;
	
	abstract AbstractPointTreeMap<E> newInstance(AbstractPointTreeMap<E> parent, int index, Leaf<E> leaf);
	
	abstract Node<E>[] nodes();
	
	// Query Operations
	
	@Override
	public int size()
	{
		int i = 0;
		
		for (Node<E> node : nodes())
			if (node instanceof Leaf)
				i++;
			else if (node instanceof PointTreeMap)
				i += ((PointTreeMap) node).size();
		
		return i;
	}
	
	@Override
	public boolean isEmpty()
	{
		for (Node node : nodes())
			if (node != null)
				return false;
		
		return true;
	}
	
	@Override
	public boolean isRoot()
	{
		return parent == null;
	}
	
	@Override
	public boolean containsKey(Object key)
	{
		return match((b, l) -> l.getKey()
								.equals(key));
	}
	
	@Override
	public boolean containsValue(Object value)
	{
		return match((b, l) -> l.getValue()
								.equals(value));
	}
	
	@Override
	public E get(Object key)
	{
		return find(key, (b, l) -> l.getKey()
									.equals(key) ? l.getValue() : null);
	}
	
	@Override
	public AbstractPointTreeMap<E> getParent()
	{
		return parent;
	}
	
	@Override
	public Leaf<E> getLeaf(Point key)
	{
		return find(key, (b, l) -> l.getKey()
									.equals(key) ? l : null);
	}
	
	@Override
	public PointTreeMap<E> getBranch(Point key)
	{
		return find(key, (b, l) -> l.getKey()
									.equals(key) ? b : null);
	}
	
	private <T> T find(Object key, BiFunction<AbstractPointTreeMap<E>, Leaf<E>, T> biFunction)
	{
		for (Node<E> node : nodes())
			if (node instanceof Leaf)
			{
				Leaf<E> leaf = (Leaf<E>) node;
				if (leaf.getKey()
						.equals(key))
					return biFunction.apply(this, leaf);
			}
		
		for (Node<E> node : nodes())
			if (node instanceof AbstractPointTreeMap)
				return ((AbstractPointTreeMap<E>) node).find(key, biFunction);
		
		return null;
	}
	
	private boolean match(BiPredicate<AbstractPointTreeMap<E>, Leaf<E>> biPredicate)
	{
		for (Node<E> node : nodes())
			if (node instanceof Leaf)
				if (biPredicate.test(this, (Leaf<E>) node))
					return true;
		
		for (Node<E> node : nodes())
			if (node instanceof AbstractPointTreeMap)
				return ((AbstractPointTreeMap<E>) node).match(biPredicate);
		
		return false;
	}
	
	@Override
	public int branchIndex()
	{
		for (int i = 0; i < parent.nodes().length; i++)
			if (parent.nodes()[i] == this)
				return i;
		return -1;
	}
	
	// Modification Operations
	
	@Override
	public E put(Point key, E value)
	{
		return bounds().contains(key) ? put_(key, value) : null;
	}
	
	private E put_(Point key, E value)
	{
		int i = leafIndex(key);
		
		if (nodes()[i] == null)
		{
			nodes()[i] = new Leaf<>(key, value);
			return null;
		}
		
		if (nodes()[i] instanceof Leaf)
		{
			Leaf<E> leaf = (Leaf<E>) nodes()[i];
			if (leaf.getKey()
					.equals(key))
				return leaf.setValue(value);
			nodes()[i] = newInstance(this, i, (Leaf<E>) nodes()[i]);
		}
		
		return ((AbstractPointTreeMap<E>) nodes()[i]).put_(key, value);
	}
	
	@Override
	public E remove(Object key)
	{
		int i = leafIndex((Point) key);
		
		if (nodes()[i] == null)
			return null;
		
		if (nodes()[i] instanceof Leaf)
		{
			Leaf<E> leaf = (Leaf<E>) nodes()[i];
			if (leaf.getKey()
					.equals(key))
			{
				nodes()[i] = null;
				retract();
				return leaf.getValue();
			}
			return null;
		}
		
		return ((AbstractPointTreeMap<E>) nodes()[i]).remove(key);
	}
	
	private void retract()
	{
		if (isRoot())
			return;
		
		Node<E> result = this;
		
		for (Node<E> node : nodes())
			if (node != null)
				if (node instanceof AbstractPointTreeMap || result instanceof Leaf)
					return;
				else
					result = node;
		
		parent.nodes()[branchIndex()] = result;
		parent.retract();
		parent = null;
	}
	
	@Override
	public void putAll(Map<? extends Point, ? extends E> m)
	{
		m.forEach(this::put);
	}
	
	@Override
	public void clear()
	{
		for (int i = 0; i < nodes().length; i++)
			nodes()[i] = null;
	}
	
	@Override
	public Set<Point> keySet()
	{
		Set<Point> set = new HashSet<>();
		forEachLeafRecursive(l -> set.add(l.getKey()));
		return set;
	}
	
	@Override
	public Collection<E> values()
	{
		Set<E> set = new HashSet<>();
		forEachLeafRecursive(l -> set.add(l.getValue()));
		return set;
	}
	
	@Override
	public Set<Entry<Point, E>> entrySet()
	{
		Set<Entry<Point, E>> set = new HashSet<>();
		forEachLeafRecursive(set::add);
		return set;
	}
	
	@Override
	public void forEachNode(Consumer<? super Node<E>> consumer)
	{
		for (Node<E> node : nodes())
			consumer.accept(node);
	}
	
	@Override
	public void forEachLeaf(Consumer<? super PointTreeMap.Leaf<E>> consumer)
	{
		for (Node<E> node : nodes())
			if (node != null)
				if (node instanceof Leaf)
					consumer.accept((Leaf<E>) node);
	}
	
	@Override
	public void forEachLeafRecursive(Consumer<? super PointTreeMap.Leaf<E>> consumer)
	{
		forEachLeaf(consumer);
		forEachBranchRecursive(b -> b.forEachLeaf(consumer));
	}
	
	@Override
	public void forEachBranch(Consumer<? super PointTreeMap<E>> consumer)
	{
		for (Node<E> node : nodes())
			if (node != null && node instanceof PointTreeMap)
				consumer.accept((PointTreeMap<E>) node);
	}
	
	@Override
	public void forEachBranchRecursive(Consumer<? super PointTreeMap<E>> consumer)
	{
		forEachBranch(b ->
		{
			consumer.accept(b);
			b.forEachBranch(consumer);
		});
	}
	
	@Override
	public String toString()
	{
		return String.format("[%s]", Arrays.toString(nodes()));
	}
	
	static class Leaf<E> implements PointTreeMap.Leaf<E>
	{
		private final Point key;
		
		private E value;
		
		Leaf(Point key, E value)
		{
			this.key = key;
			this.value = value;
		}
		
		@Override
		public Point getKey()
		{
			return key;
		}
		
		@Override
		public E getValue()
		{
			return value;
		}
		
		@Override
		public E setValue(E value)
		{
			E oldValue = this.value;
			this.value = value;
			return oldValue;
		}
		
		@Override
		public int hashCode()
		{
			return key.hashCode();
		}
		
		@Override
		public boolean equals(Object o)
		{
			return o instanceof Point ? key.equals(o) : o instanceof Leaf && key.equals(((Leaf) o).key);
		}
		
		@Override
		public String toString()
		{
			return String.format("[%s=%s]", key, value);
		}
	}
}
