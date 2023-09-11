package com.swampmaster2160.swampmaster2160smod;

public enum Direction6Enum {
	UP    (0, 0, 1, 0),
	NORTH (1, 0, 0, -1),
	EAST  (2, 1, 0, 0),
	SOUTH (3, 0, 0, 1),
	WEST  (4, -1, 0, 0),
	DOWN  (5, 0, -1, 0);

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
				return NORTH;
			case 2:
				return EAST;
			case 3:
				return SOUTH;
			case 4:
				return WEST;
			case 5:
				return DOWN;
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
