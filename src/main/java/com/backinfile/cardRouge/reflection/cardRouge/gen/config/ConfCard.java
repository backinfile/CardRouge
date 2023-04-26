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
public class ConfCard extends ConfigBase {
    private static volatile Data _instance = null;

    public static ConfCard get(int id) {
        return getData().get(id);
    }

    public static Collection<ConfCard> getAll() {
        return getData().getAll();
    }

    private final int id;
    private final String title;
    private final int family;
    private final int cardType;
    private final int startNumber;
    private final int rare;
    private final List<Integer> conditionOwnCards;
    private final int groupNumber;
    private final String subType;
    private final int cost;
    private final String description;
    private final List<Integer> linkCards;
    private final String image;
    private final String backImage;

    private ConfCard(int id, String title, int family, int cardType, int startNumber, int rare, List<Integer> conditionOwnCards, int groupNumber, String subType, int cost, String description, List<Integer> linkCards, String image, String backImage) {
        this.id = id;
        this.title = title;
        this.family = family;
        this.cardType = cardType;
        this.startNumber = startNumber;
        this.rare = rare;
        this.conditionOwnCards = conditionOwnCards;
        this.groupNumber = groupNumber;
        this.subType = subType;
        this.cost = cost;
        this.description = description;
        this.linkCards = linkCards;
        this.image = image;
        this.backImage = backImage;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public int getFamily() {
        return family;
    }

    public int getCardType() {
        return cardType;
    }

    public int getStartNumber() {
        return startNumber;
    }

    public int getRare() {
        return rare;
    }

    public List<Integer> getConditionOwnCards() {
        return conditionOwnCards;
    }

    public int getGroupNumber() {
        return groupNumber;
    }

    public String getSubType() {
        return subType;
    }

    public int getCost() {
        return cost;
    }

    public String getDescription() {
        return description;
    }

    public List<Integer> getLinkCards() {
        return linkCards;
    }

    public String getImage() {
        return image;
    }

    public String getBackImage() {
        return backImage;
    }

    static Data getData() {
        Data current = _instance;
        if (_instance == null) {
            synchronized (Data.class) {
                if (_instance == null) {
                    _instance = new Data();
                    getLogger().info("ConfCard load!");
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
                    String data = readFileString("ConfCard.json");
                    String newHash = ConfigManager.md5(data);
                    if (!newHash.equals(_instance.contentHash)) {
                        _instance = null;
                        getLogger().info("ConfCard changed!");
                        return true;
                    }
                }
            }
        }
        return false;
    }

    private static class Data {
        private final Map<Integer, ConfCard> confMap = new HashMap<>();
        private final String contentHash;

        private Data() {
            String data = readFileString("ConfCard.json");
            this.contentHash = ConfigManager.md5(data);

            JSONArray jsonArray = JSON.parseArray(data);
            for (int i = 0; i < jsonArray.size(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                ConfCard _conf = new ConfCard(
                        jsonObject.getInteger("id"), 
                        jsonObject.getString("title"), 
                        jsonObject.getInteger("family"), 
                        jsonObject.getInteger("cardType"), 
                        jsonObject.getInteger("startNumber"), 
                        jsonObject.getInteger("rare"), 
                        parseIntegerList(jsonObject.getString("conditionOwnCards")), 
                        jsonObject.getInteger("groupNumber"), 
                        jsonObject.getString("subType"), 
                        jsonObject.getInteger("cost"), 
                        jsonObject.getString("description"), 
                        parseIntegerList(jsonObject.getString("linkCards")), 
                        jsonObject.getString("image"), 
                        jsonObject.getString("backImage")
                );
                this.confMap.put(_conf.id, _conf);
            }
        }

        public ConfCard get(int id) {
            ConfCard config = confMap.get(id);
            if (config == null) {
                getLogger().logConfigMissing(ConfCard.class.getSimpleName(), id);
            }
            return config;
        }

        public Collection<ConfCard> getAll() {
            return confMap.values();
        }
    }
}
