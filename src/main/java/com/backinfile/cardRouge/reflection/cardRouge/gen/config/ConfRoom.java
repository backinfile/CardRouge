package com.backinfile.cardRouge.reflection.cardRouge.gen.config;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * 此文件是自动生成的 不要手动修改
 */
public class ConfRoom extends ConfigBase {
    private static volatile Data _instance = null;

    public static ConfRoom get(int id) {
        return getData().get(id);
    }

    public static Collection<ConfRoom> getAll() {
        return getData().getAll();
    }

    private final int id;
    private final int type;
    private final String title;
    private final String icon;
    private final List<Integer> conversitions;
    private final int battleId;

    private ConfRoom(int id, int type, String title, String icon, List<Integer> conversitions, int battleId) {
        this.id = id;
        this.type = type;
        this.title = title;
        this.icon = icon;
        this.conversitions = conversitions;
        this.battleId = battleId;
    }

    public int getId() {
        return id;
    }

    public int getType() {
        return type;
    }

    public String getTitle() {
        return title;
    }

    public String getIcon() {
        return icon;
    }

    public List<Integer> getConversitions() {
        return conversitions;
    }

    public int getBattleId() {
        return battleId;
    }

    static Data getData() {
        Data current = _instance;
        if (_instance == null) {
            synchronized (Data.class) {
                if (_instance == null) {
                    _instance = new Data();
                    getLogger().info("ConfRoom load!");
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
                    String data = readFileString("ConfRoom.json");
                    String newHash = ConfigManager.md5(data);
                    if (!newHash.equals(_instance.contentHash)) {
                        _instance = null;
                        getLogger().info("ConfRoom changed!");
                        return true;
                    }
                }
            }
        }
        return false;
    }

    private static class Data {
        private final Map<Integer, ConfRoom> confMap = new HashMap<>();
        private final String contentHash;

        private Data() {
            String data = readFileString("ConfRoom.json");
            this.contentHash = ConfigManager.md5(data);

            JSONArray jsonArray = JSON.parseArray(data);
            for (int i = 0; i < jsonArray.size(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                ConfRoom _conf = new ConfRoom(
                        jsonObject.getInteger("id"), 
                        jsonObject.getInteger("type"), 
                        jsonObject.getString("title"), 
                        jsonObject.getString("icon"), 
                        parseIntegerList(jsonObject.getString("conversitions")), 
                        jsonObject.getInteger("battleId")
                );
                this.confMap.put(_conf.id, _conf);
            }
        }

        public ConfRoom get(int id) {
            ConfRoom config = confMap.get(id);
            if (config == null) {
                getLogger().logConfigMissing(ConfRoom.class.getSimpleName(), id);
            }
            return config;
        }

        public Collection<ConfRoom> getAll() {
            return confMap.values();
        }
    }
}
