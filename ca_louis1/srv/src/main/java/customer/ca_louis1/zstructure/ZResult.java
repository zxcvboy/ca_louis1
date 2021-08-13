package customer.ca_louis1.zstructure;

import java.io.Serializable;
import java.util.Calendar;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ZResult implements Serializable {

    private String status;
    private String message;

    public ZResult() {
        super();
        // TODO Auto-generated constructor stub
    }

    public ZResult(String Status, String Message) {
        super();
        status = Status;
        message = Message;
    }
    @JsonProperty(value = "status")
    public String getStatus() {
        return status;
    }
    @JsonProperty(value = "status")  
    public void setStatus(String status) {
        this.status = status;
    }
    @JsonProperty(value = "message")
    public String getMessage() {
        return message;
    }
    @JsonProperty(value = "message")    
    public void setMessage(String message) {
        this.message = message;
    }
    
}    
