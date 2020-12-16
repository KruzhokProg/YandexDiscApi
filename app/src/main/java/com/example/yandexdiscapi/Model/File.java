package com.example.yandexdiscapi.Model;

import com.google.gson.annotations.SerializedName;

public class File {
    private String name;
    private String created;
    private String mime_type;
    private String media_type;
    @SerializedName("file")
    private String file_url;
    private String preview;
    private String path;

    public File(String name, String created, String mime_type, String media_type, String file_url, String preview, String path) {
        this.name = name;
        this.created = created;
        this.mime_type = mime_type;
        this.media_type = media_type;
        this.file_url = file_url;
        this.preview = preview;
        this.path = path;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCreated() {
        return created;
    }

    public void setCreated(String created) {
        this.created = created;
    }

    public String getMime_type() {
        return mime_type;
    }

    public void setMime_type(String mime_type) {
        this.mime_type = mime_type;
    }

    public String getMedia_type() {
        return media_type;
    }

    public void setMedia_type(String media_type) {
        this.media_type = media_type;
    }

    public String getFile_url() {
        return file_url;
    }

    public void setFile_url(String file_url) {
        this.file_url = file_url;
    }

    public String getPreview() {
        return preview;
    }

    public void setPreview(String preview) {
        this.preview = preview;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
}
