package net.eoutech.vifi.ws.entity;

/**
 * Created by wei on 2017/11/24.
 */
public class TbPlace {
    private Integer ukKeyId;
    private String idxRegion;
    private String idxCode;
    private String idxNumber;
    private String idxLevel;

    public Integer getUkKeyId() {
        return ukKeyId;
    }

    public void setUkKeyId(Integer ukKeyId) {
        this.ukKeyId = ukKeyId;
    }

    public String getIdxRegion() {
        return idxRegion;
    }

    public void setIdxRegion(String idxRegion) {
        this.idxRegion = idxRegion;
    }

    public String getIdxCode() {
        return idxCode;
    }

    public void setIdxCode(String idxCode) {
        this.idxCode = idxCode;
    }

    public String getIdxNumber() {
        return idxNumber;
    }

    public void setIdxNumber(String idxNumber) {
        this.idxNumber = idxNumber;
    }

    public String getIdxLevel() {
        return idxLevel;
    }

    public void setIdxLevel(String idxLevel) {
        this.idxLevel = idxLevel;
    }
}
