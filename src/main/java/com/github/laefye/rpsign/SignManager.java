package com.github.laefye.rpsign;

import com.github.laefye.rpsign.api.ResourcePackInfo;
import com.github.laefye.rpsign.utils.Vec3i;
import com.google.gson.JsonParser;
import com.google.gson.annotations.JsonAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import org.bukkit.block.Block;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class SignManager {
    private final RPSign plugin;

    public SignManager(RPSign plugin) {
        this.plugin = plugin;
    }

    public void put(Block block, ResourcePackInfo info) {
        var file = new File(plugin.getDataFolder(), "signs/" + Vec3i.of(block).hashCode());
        if (info == null) {
            if (file.exists()) {
                file.delete();
            }
            return;
        }
        try {
            var writer = new JsonWriter(new FileWriter(file));
            writer.beginObject()
                    .name("name")
                    .value(info.getName())
                    .name("url")
                    .value(info.getUrl())
                    .name("hash")
                    .value(info.getHexHash())
                    .endObject()
                    .close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public ResourcePackInfo get(Block block) {
        var file = new File(plugin.getDataFolder(), "signs/" + Vec3i.of(block).hashCode());
        if (!file.exists()) {
            return null;
        }
        try {
            var parse = JsonParser.parseReader(new FileReader(file)).getAsJsonObject();
            return new ResourcePackInfo(
                    parse.get("name").getAsString(),
                    parse.get("url").getAsString(),
                    parse.get("hash").getAsString()
            );
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
