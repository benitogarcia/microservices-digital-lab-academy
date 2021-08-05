package com.microservices.academy.product.util;

import lombok.Data;
import org.apache.catalina.LifecycleState;

import java.util.List;
import java.util.Map;

@Data
public class ErrorMessage {

    private String code;
    private List<Map<String, String>> messages;
}
