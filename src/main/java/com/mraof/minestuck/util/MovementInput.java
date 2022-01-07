package com.mraof.minestuck.util;

public class MovementInput
{
	private final float moveStrafe;
	private final float moveForward;
	private final boolean jump;
	private final boolean sneak;

	public MovementInput(float moveStrafe, float moveForward, boolean jump, boolean sneak)
	{
		this.moveStrafe = moveStrafe;
		this.moveForward = moveForward;
		this.jump = jump;
		this.sneak = sneak;
	}

	public float getMoveStrafe()
	{
		return moveStrafe;
	}

	public float getMoveForward()
	{
		return moveForward;
	}

	public boolean isJumping()
	{
		return jump;
	}

	public boolean isSneaking()
	{
		return sneak;
	}

	@Override
	public boolean equals(Object obj)
	{
		if (!(obj instanceof MovementInput))
			return false;
		MovementInput other = (MovementInput) obj;

		return this.moveStrafe == other.moveStrafe && this.moveForward == other.moveForward && this.jump == other.jump && this.sneak == other.sneak;
	}
}
