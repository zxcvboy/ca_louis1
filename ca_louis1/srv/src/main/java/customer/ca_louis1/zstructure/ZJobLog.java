package customer.ca_louis1.zstructure;

import java.io.Serializable;
import java.util.Calendar;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ZJobLog implements Serializable {

    private String executeTime;
    private String formNo;
    private String status;
    private String message;

    public ZJobLog() {
        super();
        // TODO Auto-generated constructor stub
    }

    public ZJobLog(String Status, String Message, String ExecuteTime, String FormNo) 
    {
        super();
        status = Status;
        message = Message;
        executeTime = ExecuteTime;
        formNo = FormNo;
    }

    @JsonProperty(value = "FormNo")
    public String getFormNo() {
        return formNo;
    }
    @JsonProperty(value = "FormNo")  
    public void setFormNo(String FormNo) {
        this.formNo = FormNo;
    }
    @JsonProperty(value = "ExecuteTime")
    public String getExecuteTime() {
        return executeTime;
    }
    @JsonProperty(value = "ExecuteTime")  
    public void setExecuteTime(String ExecuteTime) {
        this.executeTime = ExecuteTime;
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
