package customer.ca_louis1.zstructure;

import java.io.Serializable;
import java.util.Calendar;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ZPoForm implements Serializable {

    private ZPoHeader PO_HEADER;
    private List<ZPoItem> PO_ITEMS;

    public ZPoForm() {
        super();
        // TODO Auto-generated constructor stub
    }

    public ZPoForm(ZPoHeader pO_HEADER, List<ZPoItem> pO_ITEMS) {
        super();
        PO_HEADER = pO_HEADER;
        PO_ITEMS = pO_ITEMS;
    }

    @JsonProperty(value = "PO_HEADER")
    public ZPoHeader getPO_HEADER() {
        return PO_HEADER;
    }

    @JsonProperty(value = "PO_HEADER")
    public void setPO_HEADER(ZPoHeader pO_HEADER) {
        PO_HEADER = pO_HEADER;
    }

    @JsonProperty(value = "PO_ITEMS")
    public List<ZPoItem> getPO_ITEMS() {
        return PO_ITEMS;
    }

    @JsonProperty(value = "PO_ITEMS")
    public void setPO_ITEMS(List<ZPoItem> pO_ITEMS) {
        PO_ITEMS = pO_ITEMS;
    }

}