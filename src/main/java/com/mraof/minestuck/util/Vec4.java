package com.mraof.minestuck.util;

import net.minecraft.util.math.Vec3d;

public class Vec4
{
	private final double x, y, z;
	private final int dim;

	public Vec4(double x, double y, double z, int dim)
	{
		this.x = x;
		this.y = y;
		this.z = z;
		this.dim = dim;
	}

	public double getX()
	{
		return x;
	}

	public double getY()
	{
		return y;
	}

	public double getZ()
	{
		return z;
	}

	public int getDimension()
	{
		return dim;
	}

	public Vec3d getPosition()
	{
		return new Vec3d(x, y, z);
	}

	@Override
	public boolean equals(Object obj)
	{
		if (!(obj instanceof Vec4))
			return false;
		Vec4 other = (Vec4) obj;

		return this.x == other.x && this.y == other.y && this.z == other.z && this.dim == other.dim;
	}
}
