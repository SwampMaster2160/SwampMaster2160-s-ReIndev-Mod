package com.swampmaster2160.swampmaster2160smod;

public enum TriStateStateEnum {
	FLOATING(0),
	TRUE(1),
	FALSE(2);

	public final int intValue;

	TriStateStateEnum(int intValueIn) {
		intValue = intValueIn;
	}

	public static TriStateStateEnum fromInt(int value) {
		switch (value) {
			case 0:
				return FLOATING;
			case 1:
				return TRUE;
			case 2:
				return FALSE;
			default:
				throw new IllegalArgumentException("Invalid tri-state value: " + value);
		}
	}
}
