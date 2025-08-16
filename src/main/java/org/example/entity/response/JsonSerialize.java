package org.example.entity.response;

import org.json.JSONObject;

public interface JsonSerialize<T> {
    T fromJsonObject(JSONObject jsonObject);

    JSONObject toJsonObject();
}
