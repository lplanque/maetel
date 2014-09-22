package com.lplanque.maetel.function;

import org.junit.Assert;
import org.junit.Test;

import com.lplanque.maetel.core.Function;
import com.lplanque.maetel.util.UpdateVersion;

public class UpdateVersionTest {

	@Test public void doIt() {
		
		Function<String, String> f0 = new UpdateVersion();
		Function<String, String> f1 = new UpdateVersion(1);
		Function<String, String> f2 = new UpdateVersion(2);
		Function<String, String> f3 = new UpdateVersion(3);
		
		Assert.assertEquals("4", f0.apply("3"));
		Assert.assertEquals("4-SNAPSHOT", f0.apply("3-SNAPSHOT"));
		
		Assert.assertEquals("4.0.0", f0.apply("3.0.1"));
		Assert.assertEquals("4.0.0-SNAPSHOT", f0.apply("3.0.1-SNAPSHOT"));
		
		Assert.assertEquals("3.1.0", f1.apply("3.0.1"));
		Assert.assertEquals("3.1.0-SNAPSHOT", f1.apply("3.0.1-SNAPSHOT"));
		
		Assert.assertEquals("3.0.2", f2.apply("3.0.1"));
		Assert.assertEquals("3.0.2-SNAPSHOT", f2.apply("3.0.1-SNAPSHOT"));
		
		Assert.assertEquals("3.0.1", f3.apply("3.0.1"));
		Assert.assertEquals("3.0.1-SNAPSHOT", f3.apply("3.0.1-SNAPSHOT"));
		
		Assert.assertNull(f0.apply(null));		
	}
}
