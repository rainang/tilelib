package com.github.rainang.tilelib.board.tile;

/**
 Interface for Tile classes that can hold weight for obstructing movement
 */
public interface Weighable
{
	int getTileWeight();
	
	default boolean isImpassable()
	{
		return getTileWeight() < 0;
	}
}
