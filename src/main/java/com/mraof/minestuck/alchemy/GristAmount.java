package com.mraof.minestuck.alchemy;

/**
 * Container for a GristType + integer combination that might be useful when iterating through a GristSet.
 */
public class GristAmount
{
	private Grist type;
	private int amount;

	public GristAmount(Grist type, int amount)
	{
		this.type = type;
		this.amount = amount;
	}

	public Grist getType()
	{
		return type;
	}

	public int getAmount()
	{
		return amount;
	}

	/**
	 * @return a value estimate for this grist amount
	 */
	public float getValue()
	{
		return type.getValue() * amount;
	}

	@Override
	public int hashCode()
	{
		return type.hashCode() + new Integer(amount).hashCode();
	}

	@Override
	public boolean equals(Object obj)
	{
		if (!(obj instanceof GristAmount))
			return false;
		GristAmount grist = (GristAmount) obj;
		return this.type == grist.type && this.amount == grist.amount;
	}

	@Override
	public String toString()
	{
		return "gristAmount:[type=" + type.getName() + ",amount=" + amount + "]";
	}

}
