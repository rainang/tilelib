package com.github.rainang.tilelib.util;

import com.github.rainang.tilelib.geometry.AABB;
import com.github.rainang.tilelib.geometry.Point;

import java.util.Collection;
import java.util.Map;
import java.util.Set;
import java.util.function.Consumer;

public interface PointTreeMap<E> extends Map<Point, E>, Node<E>
{
	// Query Operations
	
	AABB bounds();
	
	int dimensions();
	
	@Override
	int size();
	
	@Override
	boolean isEmpty();
	
	boolean isRoot();
	
	@Override
	boolean containsKey(Object key);
	
	@Override
	boolean containsValue(Object value);
	
	@Override
	E get(Object key);
	
	Leaf<E> getLeaf(Point key);
	
	PointTreeMap<E> getBranch(Point key);
	
	AbstractPointTreeMap<E> getParent();
	
	int leafIndex(Point key);
	
	int branchIndex();
	
	// Modification Operations
	
	@Override
	E put(Point key, E value);
	
	@Override
	E remove(Object key);
	
	// Bulk Operations
	
	@Override
	void putAll(Map<? extends Point, ? extends E> m);
	
	@Override
	void clear();
	
	void forEachNode(Consumer<? super Node<E>> consumer);
	
	void forEachLeaf(Consumer<? super Leaf<E>> consumer);
	
	void forEachBranch(Consumer<? super PointTreeMap<E>> consumer);
	
	void forEachLeafRecursive(Consumer<? super Leaf<E>> consumer);
	
	void forEachBranchRecursive(Consumer<? super PointTreeMap<E>> consumer);
	
	// Views
	
	@Override
	Set<Point> keySet();
	
	@Override
	Collection<E> values();
	
	@Override
	Set<Entry<Point, E>> entrySet();
	
	interface Leaf<E> extends Map.Entry<Point, E>, Node<E> {}
}
