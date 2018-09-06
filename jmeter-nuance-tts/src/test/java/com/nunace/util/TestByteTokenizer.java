package com.nunace.util;

import com.nuance.util.ByteTokenizer;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class TestByteTokenizer {

    @Test
    public void test_1() {
        byte[] data = {0x0d, 0x0a, 0x01, 0x02, 0x0d, 0x0a, 0x03, 0x04, 0x0d, 0x0a};

        List<byte[]> result = runCase(data);
        Assert.assertEquals(result.size(), 2);
        Assert.assertArrayEquals(result.get(0), new byte[]{0x01, 0x02});
        Assert.assertArrayEquals(result.get(1), new byte[]{0x03, 0x04});
    }

    @Test
    public void test_2() {
        byte[] data = {0x0d, 0x0a, 0x01, 0x02, 0x0d, 0x0a, 0x03, 0x04};
        List<byte[]> result = runCase(data);
        Assert.assertEquals(result.size(), 2);
        Assert.assertArrayEquals(result.get(0), new byte[]{0x01, 0x02});
        Assert.assertArrayEquals(result.get(1), new byte[]{0x03, 0x04});
    }

    @Test
    public void test_3() {
        byte[] data = {0x0d, 0x0a, 0x01, 0x02, 0x0d, 0x0a, 0x0d, 0x0a, 0x03, 0x04, 0x0d, 0x0a};
        List<byte[]> result = runCase(data);
        Assert.assertEquals(result.size(), 3);
        Assert.assertArrayEquals(result.get(0), new byte[]{0x01, 0x02});
        Assert.assertArrayEquals(result.get(1), new byte[]{});
        Assert.assertArrayEquals(result.get(2), new byte[]{0x03, 0x04});
    }

    @Test
    public void test_4() {
        byte[] data = {0x0d, 0x0a, 0x01, 0x02, 0x0d, 0x0a, 0x03, 0x04, 0x0d, 0x0a};

        List<byte[]> result = runCase(data, true);
        Assert.assertEquals(result.size(), 2);
        Assert.assertArrayEquals(result.get(0), new byte[]{0x0d, 0x0a, 0x01, 0x02});
        Assert.assertArrayEquals(result.get(1), new byte[]{0x0d, 0x0a, 0x03, 0x04});
    }

    @Test
    public void test_5() {
        byte[] data = {0x0d, 0x0a, 0x01, 0x02, 0x0d, 0x0a, 0x03, 0x04};
        List<byte[]> result = runCase(data,true);
        Assert.assertEquals(result.size(), 2);
        Assert.assertArrayEquals(result.get(0), new byte[]{0x0d, 0x0a,0x01, 0x02});
        Assert.assertArrayEquals(result.get(1), new byte[]{0x0d, 0x0a,0x03, 0x04});
    }

    @Test
    public void test_6() {
        byte[] data = {0x0d, 0x0a, 0x01, 0x02, 0x0d, 0x0a, 0x0d, 0x0a, 0x03, 0x04, 0x0d, 0x0a};
        List<byte[]> result = runCase(data, true);
        Assert.assertEquals(result.size(), 3);
        Assert.assertArrayEquals(result.get(0), new byte[]{0x0d, 0x0a,0x01, 0x02});
        Assert.assertArrayEquals(result.get(1), new byte[]{0x0d, 0x0a});
        Assert.assertArrayEquals(result.get(2), new byte[]{0x0d, 0x0a,0x03, 0x04});
    }

    private List<byte[]> runCase(byte[] data) {
        return runCase(data, false);
    }

    private List<byte[]> runCase(byte[] data, boolean returnDelims) {
        ByteTokenizer byteTokenizer = new ByteTokenizer(data, returnDelims);
        List<byte[]> result = new ArrayList<>();
        while (byteTokenizer.hasMoreElements()) {
            result.add(byteTokenizer.nextElement());
        }
        return result;
    }
}
