package com.nuance.util;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class HttpResult {
    private String httpMessage;
    private int httpCode;
    private byte[] result;
}
