package com.github.laefye.rpsign.api;

import com.github.laefye.rpsign.RPSign;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.google.gson.stream.MalformedJsonException;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.util.function.Consumer;

public abstract class Service {
    private final HttpClient client = HttpClient.newBuilder().build();
    public abstract ResourcePackInfo getResourcePack(String unique);

    public JsonElement request(String url) {
        var r = HttpRequest.newBuilder(URI.create(url))
                .GET()
                .build();
        try {
            var result =  client.send(r, HttpResponse.BodyHandlers.ofString(StandardCharsets.UTF_8)).body();
            return JsonParser.parseString(result);
        } catch (IOException | InterruptedException ignored) {
        }
        return null;
    }

    public void getResourcePack(String unique, RPSign plugin, Consumer<ResourcePackInfo> callback) {
        new Thread(() -> {
            var pack = getResourcePack(unique);
            plugin.getServer().getScheduler().runTaskLater(plugin, () -> callback.accept(pack), 1L);
        }).start();
    }
}
