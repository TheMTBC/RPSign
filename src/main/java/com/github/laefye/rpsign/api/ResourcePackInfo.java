package com.github.laefye.rpsign.api;

import java.util.Arrays;
import java.util.HexFormat;

public class ResourcePackInfo {
    private String name;
    private String url;
    private byte[] hash;

    public ResourcePackInfo(String name, String url, byte[] hash) {
        this.name = name;
        this.url = url;
        this.hash = hash;
    }

    public ResourcePackInfo(String name, String url, String hash) {
        this(name, url, HexFormat.of().parseHex(hash));
    }

    @Override
    public String toString() {
        return "ResourcePackInfo{" +
                "name='" + name + '\'' +
                ", url='" + url + '\'' +
                ", hash=" + Arrays.toString(hash) +
                '}';
    }

    public byte[] getHash() {
        return hash;
    }

    public String getName() {
        return name;
    }

    public String getUrl() {
        return url;
    }

    public String getHexHash() {
        return HexFormat.of().formatHex(hash);
    }
}
