package customer.ca_louis1.zstructure;

import java.io.Serializable;
import java.util.Calendar;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ZPoHeader implements Serializable {

    private static final long serialVersionUID = -6529153019932518253L;
    private String PO_NO;
    private String FORM_TYPE;
    private String COMP_CD;
    private String VENDOR_ID;
    private String VENDOR_NAME;
    private String PUR_GRP;
    private String PUR_ORG;
    private String CURRENCY;
    private String REMARK;


    public ZPoHeader() {
        super();
        // TODO Auto-generated constructor stub
    }

    public ZPoHeader(String pO_NO, String fORM_TYPE, String cOMP_CD, String vENDOR_ID, String vENDOR_NAME,
            String pUR_GRP, String pUR_ORG, String cURRENCY, String rEMARK) {
        super();
        PO_NO = pO_NO;
        FORM_TYPE = fORM_TYPE;
        COMP_CD = cOMP_CD;
        VENDOR_ID = vENDOR_ID;
        VENDOR_NAME = vENDOR_NAME;
        PUR_GRP = pUR_GRP;
        PUR_ORG = pUR_ORG;
        CURRENCY = cURRENCY;
        REMARK = rEMARK;
    }

    @JsonProperty(value = "PO_NO")
    public String getPO_NO() {
        return PO_NO;
    }

    @JsonProperty(value = "PO_NO")
    public void setPO_NO(String pO_NO) {
        PO_NO = pO_NO;
    }

    @JsonProperty(value = "FORM_TYPE")
    public String getFORM_TYPE() {
        return FORM_TYPE;
    }

    @JsonProperty(value = "FORM_TYPE")
    public void setFORM_TYPE(String fORM_TYPE) {
        FORM_TYPE = fORM_TYPE;
    }

    @JsonProperty(value = "COMP_CD")
    public String getCOMP_CD() {
        return COMP_CD;
    }

    @JsonProperty(value = "COMP_CD")
    public void setCOMP_CD(String cOMP_CD) {
        COMP_CD = cOMP_CD;
    }
    @JsonProperty(value = "VENDOR_ID")
    public String getVENDOR_ID() {
        return VENDOR_ID;
    }
    @JsonProperty(value = "VENDOR_ID")
    public void setVENDOR_ID(String vENDOR_ID) {
        VENDOR_ID = vENDOR_ID;
    }
    @JsonProperty(value = "VENDOR_NAME")
    public String getVENDOR_NAME() {
        return VENDOR_NAME;
    }
    @JsonProperty(value = "VENDOR_NAME")
    public void setVENDOR_NAME(String vENDOR_NAME) {
        VENDOR_NAME = vENDOR_NAME;
    }
    @JsonProperty(value = "PUR_GRP")
    public String getPUR_GRP() {
        return PUR_GRP;
    }
    @JsonProperty(value = "PUR_GRP")
    public void setPUR_GRP(String pUR_GRP) {
        PUR_GRP = pUR_GRP;
    }
    @JsonProperty(value = "PUR_ORG")
    public String getPUR_ORG() {
        return PUR_ORG;
    }
    @JsonProperty(value = "PUR_ORG")
    public void setPUR_ORG(String pUR_ORG) {
        PUR_ORG = pUR_ORG;
    }
    @JsonProperty(value = "CURRENCY")
    public String getCURRENCY() {
        return CURRENCY;
    }
    @JsonProperty(value = "CURRENCY")
    public void setCURRENCY(String cURRENCY) {
        CURRENCY = cURRENCY;
    }
    @JsonProperty(value = "REMARK")
    public String getREMARK() {
        return REMARK;
    }
    @JsonProperty(value = "REMARK")
    public void setREMARK(String rEMARK) {
        REMARK = rEMARK;
    }

}