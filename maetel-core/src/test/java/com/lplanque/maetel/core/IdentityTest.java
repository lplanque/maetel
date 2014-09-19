package com.lplanque.maetel.core;

import org.junit.Assert;
import org.junit.Test;

public class IdentityTest {

	@Test public void doIt() {
		final Object o = new Object();
		Assert.assertEquals(o, new Identity<Object>().apply(o));
	}
}
