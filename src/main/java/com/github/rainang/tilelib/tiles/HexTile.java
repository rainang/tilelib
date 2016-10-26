package com.github.rainang.tilelib.tiles;

import com.github.rainang.tilelib.coordinates.HexCoordinate;

public interface HexTile extends Tile
{
	@Override
	HexCoordinate getPos();
}
