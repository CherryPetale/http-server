package net.kuroppi.Impl;

import org.junit.Assert;
import org.junit.Test;

import net.kuroppi.HttpHeader;
import net.kuroppi.impl.HttpHeaderImpl;

public class HttpHeaderImplTest {
    
    @Test
    public void test(){
        HttpHeader header = new HttpHeaderImpl("Host", "kuroppi.net");
		Assert.assertEquals("Host", header.getKey());
        Assert.assertEquals("kuroppi.net", header.getValue());
        
        try {
			new HttpHeaderImpl("Host", null);
			Assert.fail("null value should throw NullPointerException");
		} catch (NullPointerException npe) {
        }
        
        try {
			new HttpHeaderImpl(null, "kuroppi.net");
			Assert.fail("null name should throw NullPointerException");
		} catch (NullPointerException npe) {
        }

        StringBuilder buf = new StringBuilder();
		for (int i = 0; i < 8192; i++) {
			buf.append('.');
		}
		String str8192 = buf.toString();
		String str8193 = str8192 + ".";

		header = new HttpHeaderImpl(str8192, str8192);
		Assert.assertEquals(str8192, header.getKey());
		Assert.assertEquals(str8192, header.getValue());

		try {
			new HttpHeaderImpl("Host", str8193);
			Assert.fail("should reject too long value");
		} catch (IllegalArgumentException npe) {
		}

		try {
			new HttpHeaderImpl(str8193, "kuroppi.net");
			Assert.fail("should reject too long name");
		} catch (IllegalArgumentException npe) {
		}
    }

}