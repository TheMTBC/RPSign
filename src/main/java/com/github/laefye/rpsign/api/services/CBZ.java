package com.github.laefye.rpsign.api.services;

import com.github.laefye.rpsign.api.ResourcePackInfo;
import com.github.laefye.rpsign.api.Service;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

public class CBZ extends Service {
    private static final String url = "https://cbz.picraft.ru/public/api/resourcepack/";

    @Override
    public ResourcePackInfo getResourcePack(String unique) {
        var response = request(url + URLEncoder.encode(unique, StandardCharsets.UTF_8));
        if (response == null) {
            return null;
        }
        if (!response.isJsonObject()) {
            return null;
        }
        var json = response.getAsJsonObject();
        if (json.has("error")) {
            return null;
        }
        return new ResourcePackInfo(json.get("name").getAsString(), json.get("url").getAsString(), json.get("hash").getAsString());
    }
}
