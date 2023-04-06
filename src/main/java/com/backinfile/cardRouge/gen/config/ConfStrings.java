package com.backinfile.cardRouge.gen.config;

import com.alibaba.fastjson.*;
import java.util.*;


/**
 * 此文件是自动生成的 不要手动修改
 */
public class ConfStrings extends ConfigBase {
    private static volatile Data _instance = null;

    public static ConfStrings get(String id) {
        return getData().get(id);
    }

    public static Collection<ConfStrings> getAll() {
        return getData().getAll();
    }

    private final String id;
    private final String text;
    private final List<String> texts;

    private ConfStrings(String id, String text, List<String> texts) {
        this.id = id;
        this.text = text;
        this.texts = texts;
    }

    public String getId() {
        return id;
    }

    public String getText() {
        return text;
    }

    public List<String> getTexts() {
        return texts;
    }

    static Data getData() {
        Data current = _instance;
        if (_instance == null) {
            synchronized (Data.class) {
                if (_instance == null) {
                    _instance = new Data();
                    getLogger().info("ConfStrings load!");
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
                    String data = readFileString("ConfStrings.json");
                    String newHash = ConfigManager.md5(data);
                    if (!newHash.equals(_instance.contentHash)) {
                        _instance = null;
                        getLogger().info("ConfStrings changed!");
                        return true;
                    }
                }
            }
        }
        return false;
    }

    private static class Data {
        private final Map<String, ConfStrings> confMap = new HashMap<>();
        private final String contentHash;

        private Data() {
            String data = readFileString("ConfStrings.json");
            this.contentHash = ConfigManager.md5(data);

            JSONArray jsonArray = JSON.parseArray(data);
            for (int i = 0; i < jsonArray.size(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                ConfStrings _conf = new ConfStrings(
                        jsonObject.getString("id"), 
                        jsonObject.getString("text"), 
                        parseStringList(jsonObject.getString("texts"))
                );
                this.confMap.put(_conf.id, _conf);
            }
        }

        public ConfStrings get(String id) {
            ConfStrings config = confMap.get(id);
            if (config == null) {
                getLogger().logConfigMissing(ConfStrings.class.getSimpleName(), id);
            }
            return config;
        }

        public Collection<ConfStrings> getAll() {
            return confMap.values();
        }
    }
}
