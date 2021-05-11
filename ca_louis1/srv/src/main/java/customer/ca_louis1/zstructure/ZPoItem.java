package customer.ca_louis1.zstructure;

import java.io.Serializable;
import java.util.Calendar;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ZPoItem implements Serializable {

	private String POITEM_ID;
	private String PO_NO;
	private String ITEM_NO;
	private String MATERIAL_NO;
	private String MATERIAL_DESP;
    private double QTY;
    private String UNIT;
    private double UNIT_PRICE;
    private java.util.Calendar DELIVERY_DATE;
    private String FLAG;
	public ZPoItem() {
		super();
		// TODO Auto-generated constructor stub
	}
	public ZPoItem(String pOITEM_ID, String pO_NO, String iTEM_NO, String mATERIAL_NO, String mATERIAL_DESP, double qTY,
			String uNIT, double uNIT_PRICE, Calendar dELIVERY_DATE, String fLAG) {
		super();
		POITEM_ID = pOITEM_ID;
		PO_NO = pO_NO;
		ITEM_NO = iTEM_NO;
		MATERIAL_NO = mATERIAL_NO;
		MATERIAL_DESP = mATERIAL_DESP;
		QTY = qTY;
		UNIT = uNIT;
		UNIT_PRICE = uNIT_PRICE;
		DELIVERY_DATE = dELIVERY_DATE;
		FLAG = fLAG;
    }
    @JsonProperty(value = "POITEM_ID")
	public String getPOITEM_ID() {
		return POITEM_ID;
    }
    @JsonProperty(value = "POITEM_ID")
	public void setPOITEM_ID(String pOITEM_ID) {
		POITEM_ID = pOITEM_ID;
    }
    @JsonProperty(value = "PO_NO")
	public String getPO_NO() {
		return PO_NO;
    }
    @JsonProperty(value = "PO_NO")
	public void setPO_NO(String pO_NO) {
		PO_NO = pO_NO;
    }
    @JsonProperty(value = "ITEM_NO")
	public String getITEM_NO() {
		return ITEM_NO;
    }
    @JsonProperty(value = "ITEM_NO")
	public void setITEM_NO(String iTEM_NO) {
		ITEM_NO = iTEM_NO;
    }
    @JsonProperty(value = "MATERIAL_NO")
	public String getMATERIAL_NO() {
		return MATERIAL_NO;
    }
    @JsonProperty(value = "MATERIAL_NO")
	public void setMATERIAL_NO(String mATERIAL_NO) {
		MATERIAL_NO = mATERIAL_NO;
    }
    @JsonProperty(value = "MATERIAL_DESP")
	public String getMATERIAL_DESP() {
		return MATERIAL_DESP;
    }
    @JsonProperty(value = "MATERIAL_DESP")
	public void setMATERIAL_DESP(String mATERIAL_DESP) {
		MATERIAL_DESP = mATERIAL_DESP;
    }
    @JsonProperty(value = "QTY")
	public double getQTY() {
		return QTY;
    }
    @JsonProperty(value = "QTY")
	public void setQTY(double qTY) {
		QTY = qTY;
    }
    @JsonProperty(value = "UNIT")
	public String getUNIT() {
		return UNIT;
    }
    @JsonProperty(value = "UNIT")
	public void setUNIT(String uNIT) {
		UNIT = uNIT;
    }
    @JsonProperty(value = "UNIT_PRICE")
	public double getUNIT_PRICE() {
		return UNIT_PRICE;
    }
    @JsonProperty(value = "UNIT_PRICE")
	public void setUNIT_PRICE(double uNIT_PRICE) {
		UNIT_PRICE = uNIT_PRICE;
    }
    @JsonProperty(value = "DELIVERY_DATE")
	public java.util.Calendar getDELIVERY_DATE() {
		return DELIVERY_DATE;
    }
    @JsonProperty(value = "DELIVERY_DATE")
	public void setDELIVERY_DATE(java.util.Calendar dELIVERY_DATE) {
		DELIVERY_DATE = dELIVERY_DATE;
    }
    @JsonProperty(value = "FLAG")
	public String getFLAG() {
		return FLAG;
    }
    @JsonProperty(value = "FLAG")
	public void setFLAG(String fLAG) {
		FLAG = fLAG;
	}
    
}