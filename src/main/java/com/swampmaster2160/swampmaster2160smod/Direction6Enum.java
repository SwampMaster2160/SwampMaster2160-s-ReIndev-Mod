package com.swampmaster2160.swampmaster2160smod;

public enum Direction6Enum {
	UP    (0, 0, 1, 0),
	NORTH (3, 0, 0, -1),
	EAST  (4, 1, 0, 0),
	SOUTH (2, 0, 0, 1),
	WEST  (5, -1, 0, 0),
	DOWN  (1, 0, -1, 0);

	public final int intValue;
	public final int xOffset;
	public final int yOffset;
	public final int zOffset;

	Direction6Enum(int intValueIn, int xOffsetIn, int yOffsetIn, int zOffsetIn) {
		intValue = intValueIn;
		xOffset = xOffsetIn;
		yOffset = yOffsetIn;
		zOffset = zOffsetIn;
	}

	public static Direction6Enum fromInt(int intValue) {
		switch (intValue) {
			case 0:
				return UP;
			case 1:
				return DOWN;
			case 2:
				return SOUTH;
			case 3:
				return NORTH;
			case 4:
				return EAST;
			case 5:
				return WEST;
			default:
				throw new IllegalArgumentException("Invalid direction value: " + intValue);
		}
	}

	public Direction6Enum opposite() {
		switch (this) {
			case UP:
				return DOWN;
			case NORTH:
				return SOUTH;
			case EAST:
				return WEST;
			case SOUTH:
				return NORTH;
			case WEST:
				return EAST;
			case DOWN:
				return UP;
		}
		return null;
	}
}
