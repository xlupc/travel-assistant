package com.travelassistant.util;

import java.util.UUID;

public class GuidUtil {
    public static final String GenerateGUID(){
        UUID uuid = UUID.randomUUID();
        return uuid.toString();
    }
}
