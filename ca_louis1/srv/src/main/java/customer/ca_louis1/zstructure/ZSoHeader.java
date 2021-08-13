package customer.ca_louis1.zstructure;
import com.fasterxml.jackson.annotation.JsonProperty;
/* Time: 2021-08-04 15:43:16 @author freecodeformat.com @website http://www.freecodeformat.com/json2javabean.php */
public class ZSoHeader {

    private int id;
    @JsonProperty("customerId")
    private String customerid;
    @JsonProperty("employeeId")
    private int employeeid;
    @JsonProperty("orderDate")
    private String orderdate;
    @JsonProperty("requiredDate")
    private String requireddate;
    @JsonProperty("shipVia")
    private int shipvia;
    private double freight;
    @JsonProperty("shipName")
    private String shipname;
    @JsonProperty("shipAddress")
    private String shipaddress;
    @JsonProperty("shipCity")
    private String shipcity;
    @JsonProperty("shipPostalCode")
    private String shippostalcode;
    @JsonProperty("shipCountry")
    private String shipcountry;
    @JsonProperty("shipRegion")
    private String shipregion;
    
    public void setId(int id) {
         this.id = id;
     }
     public int getId() {
         return id;
     }

    public void setCustomerid(String customerid) {
         this.customerid = customerid;
     }
     public String getCustomerid() {
         return customerid;
     }

    public void setEmployeeid(int employeeid) {
         this.employeeid = employeeid;
     }
     public int getEmployeeid() {
         return employeeid;
     }

    public void setOrderdate(String orderdate) {
         this.orderdate = orderdate;
     }
     public String getOrderdate() {
         return orderdate;
     }

    public void setRequireddate(String requireddate) {
         this.requireddate = requireddate;
     }
     public String getRequireddate() {
         return requireddate;
     }

    public void setShipvia(int shipvia) {
         this.shipvia = shipvia;
     }
     public int getShipvia() {
         return shipvia;
     }

    public void setFreight(double freight) {
         this.freight = freight;
     }
     public double getFreight() {
         return freight;
     }

    public void setShipname(String shipname) {
         this.shipname = shipname;
     }
     public String getShipname() {
         return shipname;
     }

    public void setShipaddress(String shipaddress) {
         this.shipaddress = shipaddress;
     }
     public String getShipaddress() {
         return shipaddress;
     }

    public void setShipcity(String shipcity) {
         this.shipcity = shipcity;
     }
     public String getShipcity() {
         return shipcity;
     }

    public void setShippostalcode(String shippostalcode) {
         this.shippostalcode = shippostalcode;
     }
     public String getShippostalcode() {
         return shippostalcode;
     }

    public void setShipcountry(String shipcountry) {
         this.shipcountry = shipcountry;
     }
     public String getShipcountry() {
         return shipcountry;
     }
	public String getShipregion() {
		return shipregion;
	}
	public void setShipregion(String shipregion) {
		this.shipregion = shipregion;
	}

}