package com.nuance.util;

import java.util.Enumeration;
import java.util.NoSuchElementException;

public class ByteTokenizer implements Enumeration<byte[]> {

    private int currentPosition;
    private int newPosition;
    private int maxPosition;
    private byte[] data;
    private byte[] delimiters;
    private boolean retDelims;

    private static final byte[] CRLF = {0x0d, 0x0A};

    public ByteTokenizer(byte[] data, byte[] delim, boolean returnDelims) {
        this.currentPosition = 0;
        this.newPosition = -1;
        this.data = data;
        this.maxPosition = data.length;
        this.delimiters = delim;
        this.retDelims = returnDelims;
    }

    public ByteTokenizer(byte[] data, boolean returnDelims) {
        this(data, CRLF, returnDelims);
    }


    public ByteTokenizer(byte[] data, byte[] delim) {
        this(data, delim, false);
    }

    public ByteTokenizer(byte[] data) {
        this(data, CRLF, false);
    }

    @Override
    public boolean hasMoreElements() {
        newPosition = skipDelimiters(currentPosition);
        return newPosition < maxPosition;
    }

    @Override
    public byte[] nextElement() {
        currentPosition = (newPosition > 0) ? newPosition : skipDelimiters(currentPosition);

        newPosition = -1;

        if (currentPosition >= maxPosition) {
            throw new NoSuchElementException();
        }

        int start = currentPosition;
        currentPosition = scanToken(currentPosition);

        byte[] result = null;
        if (retDelims) {
            result = new byte[currentPosition - start + delimiters.length];
        } else {
            result = new byte[currentPosition - start];
        }

        int i = 0;
        if (retDelims) {
            for (int j = 0; j < delimiters.length; j++) {
                result[i++] = delimiters[j];
            }
        }

        for (; i < result.length; i++) {
            result[i] = data[start++];
        }
        return result;
    }

    private int skipDelimiters(int startPos) {
        if (this.data == null || this.data.length == 0) {
            throw new NullPointerException();
        }

        if (this.delimiters == null || this.delimiters.length == 0)
            throw new NullPointerException();

        int position = startPos;
        int delimPosition = 0;
        while (position < this.maxPosition && delimPosition < delimiters.length) {
            if (this.data[position] == this.delimiters[delimPosition]) {
                delimPosition++;
                position++;
            } else {
                break;
            }
        }
        return position;
    }

    private int scanToken(int startPos) {
        boolean hasToken = false;
        int position = startPos;
        int delimiterPosition = 0;
        while (position < maxPosition) {
            if (data[position++] == delimiters[delimiterPosition]) {
                delimiterPosition++;

                if (delimiterPosition == this.delimiters.length) {
                    hasToken = true;
                    break;
                }

            } else {
                delimiterPosition = 0;
                hasToken = false;
            }

        }

        if (hasToken) {
            position -= this.delimiters.length;
        }

        return position;
    }
}
