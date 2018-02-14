package com.didispace;

import static org.junit.Assert.*;

import org.junit.Test;

import com.spring.boot.web.HelloController;

public class TestPurpose {

	@Test
	public void test() {
		fail("Not yet implemented");
	}
	
	@Test
    public void testSayHello() {
        assertEquals("Hello,World!",new HelloController().index());
    }

}
