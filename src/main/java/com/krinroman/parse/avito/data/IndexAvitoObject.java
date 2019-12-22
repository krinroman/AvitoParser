package com.krinroman.parse.avito.data;

import java.io.IOException;

public class IndexAvitoObject {
    private long id;
    private String url;

    public IndexAvitoObject(long id, String url) {
        this.id = id;
        this.url = url;
    }

    public long getId() {
        return id;
    }

    public String getUrl() {
        return url;
    }

    @Override
    public boolean equals(Object obj) {
        IndexAvitoObject indexAvitoObject = (IndexAvitoObject) obj;
        if(indexAvitoObject.getId() == this.getId()) return true;
        else return false;
    }
}
