package com.github.laefye.rpsign.api;

import com.github.laefye.rpsign.api.services.CBZ;
import com.github.laefye.rpsign.api.services.Modrinth;

import java.util.HashMap;

public class ServiceManager {
    private final HashMap<String, Service> apiHashMap = new HashMap<>();

    public ServiceManager() {
        apiHashMap.put("modrinth", new Modrinth());
        apiHashMap.put("cbz", new CBZ());
    }

    public Service get(String name) {
        return apiHashMap.get(name);
    }
}
