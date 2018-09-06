package com.nunace.util;

import com.nuance.util.HTTPImpl;
import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public class TestHTTPImpl {
    @Test
    public void test() throws IOException {
        URL url = new URL("http://ptsv2.com/t/w6zjx-1532063362/post");
//        HTTPImpl hi = new HTTPImpl(url);
//        byte[] result = hi.sendPostData("This is dump test");
//        String str = new String(result, StandardCharsets.UTF_8);
//        Assert.assertEquals(str, "Thank you for this dump. I hope you have a lovely day!");
    }
}
