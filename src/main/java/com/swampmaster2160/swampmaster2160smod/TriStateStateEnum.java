package com.swampmaster2160.swampmaster2160smod;

public enum TriStateStateEnum {
	FLOATING(0),
	TRUE(1),
	FALSE(2),
	ERROR(3);

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
			case 3:
				return ERROR;
			default:
				throw new IllegalArgumentException("Invalid tri-state value: " + value);
		}
	}

	public TriStateStateEnum combine(TriStateStateEnum other) {
		if (this.intValue == other.intValue) {
			return this;
		}
		if (this.intValue == FLOATING.intValue) {
			return other;
		}
		if (other.intValue == FLOATING.intValue) {
			return this;
		}
		return ERROR;
	}

	public TriStateStateEnum not() {
		switch (this) {
			case TRUE:
				return FALSE;
			case FALSE:
				return TRUE;
			default:
				return this;
		}
	}
}
