package com.swampmaster2160.swampmaster2160smod;

/**
 * An enum for a value that can be true, false, floating or in an error state.
 */
public enum TriStateStateEnum {
	/**
	 * A value for when there is no true or false source for a tri-state block, a unknowen state.
	 */
	FLOATING(0),
	TRUE(1),
	FALSE(2),
	/**
	 * A value for an erroneous, eg. there are both true and false sources for a tri-state block or some gate depends on itself.
	 */
	ERROR(3);

	/**
	 * The value as an integer to be used as a block metadata.
	 */
	public final int intValue;

	TriStateStateEnum(int intValueIn) {
		intValue = intValueIn;
	}

	/**
	 * Convert an integer block metadata value to a tri-state value.
	 * @param value The block metadata
	 * @return The tri-state value for the metadata
	 */
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

	/**
	 * Used for when there are multiple sources for a tri-state value block with no logic gates in the way them.
	 * @param other The other state to combine with
	 * @return
	 */
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

	/**
	 * Get the logical negation of this state.
	 */
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

	public TriStateStateEnum or(TriStateStateEnum other) {
		if (this == TriStateStateEnum.ERROR || other == TriStateStateEnum.ERROR) return TriStateStateEnum.ERROR;
		if (this == TriStateStateEnum.TRUE || other == TriStateStateEnum.TRUE) return TriStateStateEnum.TRUE;
		if (this == TriStateStateEnum.FALSE && other == TriStateStateEnum.FALSE) return TriStateStateEnum.FALSE;
		return TriStateStateEnum.FLOATING;
	}
}
