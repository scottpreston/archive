package com.scottsbots.test.core.utils;

import com.scottsbots.core.utils.Utils;

import junit.framework.TestCase;

public class TestUtils extends TestCase {

	public void testAllCase() {
		Utils.pause(100);
		Utils.log("test");
		Utils.logger("test2");
		Utils.loggerErr("test3");
		Utils.printJavaEnv();
		assertEquals("", Utils.toAscii(null));
		assertEquals("a", Utils.toAscii(new byte[]{97}));
	}

}
