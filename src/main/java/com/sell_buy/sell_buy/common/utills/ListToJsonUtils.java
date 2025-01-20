package com.sell_buy.sell_buy.common.utills;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ListToJsonUtils {

    private static final ObjectMapper objectMapper;

    static {
        objectMapper = new ObjectMapper();
    }

    public static String convertListToJson(List<String> list) throws JsonProcessingException {
        return objectMapper.writeValueAsString(list);
    }
}
