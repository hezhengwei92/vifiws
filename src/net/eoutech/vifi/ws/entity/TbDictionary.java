package net.eoutech.vifi.ws.entity;

/**
 * Created by wei on 2017/11/24.
 */
public class TbDictionary {
    private Integer ukKeyId;
    private String ukKeyWord;
    private String keyValue;
    private String keyType;

    public Integer getUkKeyId() {
        return ukKeyId;
    }

    public void setUkKeyId(Integer ukKeyId) {
        this.ukKeyId = ukKeyId;
    }

    public String getUkKeyWord() {
        return ukKeyWord;
    }

    public void setUkKeyWord(String ukKeyWord) {
        this.ukKeyWord = ukKeyWord;
    }

    public String getKeyValue() {
        return keyValue;
    }

    public void setKeyValue(String keyValue) {
        this.keyValue = keyValue;
    }

    public String getKeyType() {
        return keyType;
    }

    public void setKeyType(String keyType) {
        this.keyType = keyType;
    }
}
