package com.mraof.minestuck.util;

public class SubArray<T>
{
	private T[] array;
	private int start, length;

	public SubArray(T[] array)
	{
		this(array, 0, array.length);
	}

	private SubArray(T[] array, int start, int length)
	{
		this.array = array;
		this.start = start;
		this.length = length;
	}

	public void set(int i, T object)
	{
		i = checkIndex(i);
		array[start + i] = object;
	}

	private int checkIndex(int i)
	{
		if (i < 0)
			i += length;
		if (i < 0 || i >= length)
			throw new IndexOutOfBoundsException("Index: " + i + " | Length: " + length);
		return i;
	}

	public T get(int i)
	{
		i = checkIndex(i);
		return array[start + i];
	}

	public SubArray<T> sub(int start, int length)
	{
		start = checkIndex(start);
		length = checkLength(start, length);
		return new SubArray<>(array, this.start + start, length);
	}

	private int checkLength(int start, int length)
	{
		if (start + length < 0 || start + length >= this.length)
			throw new IndexOutOfBoundsException("Index, length: " + start + ", " + length + " | Length: " + this.length);
		return length;
	}

	public int length()
	{
		return length;
	}
}
