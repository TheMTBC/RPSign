package com.github.laefye.rpsign.api.services;

import com.github.laefye.rpsign.api.Service;
import com.github.laefye.rpsign.api.ResourcePackInfo;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

public class Modrinth extends Service {
    private static final String url = "https://api.modrinth.com/v2/version/";

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
        var pack = json.get("files").getAsJsonArray().get(0).getAsJsonObject();
        return new ResourcePackInfo(json.get("name").getAsString(), pack.get("url").getAsString(), pack.get("hashes").getAsJsonObject().get("sha1").getAsString());
    }
}
