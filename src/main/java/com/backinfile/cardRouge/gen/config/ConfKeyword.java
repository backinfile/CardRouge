package com.backinfile.cardRouge.gen.config;

import com.alibaba.fastjson.*;
import java.util.*;


/**
 * 此文件是自动生成的 不要手动修改
 */
public class ConfKeyword extends ConfigBase {
    private static volatile Data _instance = null;

    public static ConfKeyword get(String id) {
        return getData().get(id);
    }

    public static Collection<ConfKeyword> getAll() {
        return getData().getAll();
    }

    private final String id;
    private final String name;
    private final String description;

    private ConfKeyword(String id, String name, String description) {
        this.id = id;
        this.name = name;
        this.description = description;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    static Data getData() {
        Data current = _instance;
        if (_instance == null) {
            synchronized (Data.class) {
                if (_instance == null) {
                    _instance = new Data();
                    getLogger().info("ConfKeyword load!");
                }
                current = _instance;
            }
        }
        return current;
    }

    static boolean clearIfChanged() {
        if (_instance != null) {
            synchronized (Data.class) {
                if (_instance != null) {
                    String data = readFileString("ConfKeyword.json");
                    String newHash = ConfigManager.md5(data);
                    if (!newHash.equals(_instance.contentHash)) {
                        _instance = null;
                        getLogger().info("ConfKeyword changed!");
                        return true;
                    }
                }
            }
        }
        return false;
    }

    private static class Data {
        private final Map<String, ConfKeyword> confMap = new HashMap<>();
        private final String contentHash;

        private Data() {
            String data = readFileString("ConfKeyword.json");
            this.contentHash = ConfigManager.md5(data);

            JSONArray jsonArray = JSON.parseArray(data);
            for (int i = 0; i < jsonArray.size(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                ConfKeyword _conf = new ConfKeyword(
                        jsonObject.getString("id"), 
                        jsonObject.getString("name"), 
                        jsonObject.getString("description")
                );
                this.confMap.put(_conf.id, _conf);
            }
        }

        public ConfKeyword get(String id) {
            ConfKeyword config = confMap.get(id);
            if (config == null) {
                getLogger().logConfigMissing(ConfKeyword.class.getSimpleName(), id);
            }
            return config;
        }

        public Collection<ConfKeyword> getAll() {
            return confMap.values();
        }
    }
}
