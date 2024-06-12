package com.oblivioussp.spartanweaponry.compat.shouldersurfing;

import com.github.exopandora.shouldersurfing.client.ShoulderInstance;

public class ShoulderSurfingCompat
{
	public static boolean isShoulderSurfing()
	{
		return ShoulderInstance.getInstance().doShoulderSurfing();
	}
}
