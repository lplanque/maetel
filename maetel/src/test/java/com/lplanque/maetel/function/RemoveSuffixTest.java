package com.lplanque.maetel.function;

import org.junit.Assert;
import org.junit.Test;

import com.lplanque.maetel.util.RemoveSuffix;

public class RemoveSuffixTest {
	
	@Test public void doIt() {
		
		final RemoveSuffix f = new RemoveSuffix("-SNAPSHOT");
		
		String x = "1.0-SNAPSHOT";
		String y = f.apply(x);
		Assert.assertEquals("1.0", y);
		
		x = "1.0-TOTO";
		y = f.apply(x);
		Assert.assertEquals(x, y);
		
		final RemoveSuffix id = new RemoveSuffix(null);
		
		x = "A string";
		y = id.apply(x);
		Assert.assertEquals(x, y);
	}
}
