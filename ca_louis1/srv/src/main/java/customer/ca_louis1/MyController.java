package customer.ca_louis1;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import com.nimbusds.jose.util.JSONObjectUtils;
import com.sap.cds.Result;
import com.sap.cds.Row;
import com.sap.cds.Struct;
import com.sap.cds.feature.xsuaa.XsuaaUserInfo;
import com.sap.cds.ql.Select;
import com.sap.cds.ql.Upsert;
import com.sap.cds.ql.cqn.CqnSelect;
import com.sap.cds.ql.cqn.CqnUpsert;
import com.sap.cds.services.ErrorStatuses;
import com.sap.cds.services.ServiceException;
import com.sap.cds.services.persistence.PersistenceService;
import com.sap.cloud.sdk.cloudplatform.connectivity.DestinationAccessor;
import com.sap.cloud.sdk.cloudplatform.connectivity.HttpClientAccessor;
import com.sap.cloud.sdk.cloudplatform.connectivity.HttpClientFactory;
import com.sap.cloud.sdk.cloudplatform.connectivity.HttpDestination;
import com.sap.cloud.sdk.services.scp.workflow.cf.api.WorkflowDefinitionsApi;
import com.sap.cloud.sdk.services.scp.workflow.cf.api.WorkflowInstancesApi;
import com.sap.cloud.sdk.services.scp.workflow.cf.model.WorkflowDefinition;
import com.sap.cloud.sdk.services.scp.workflow.cf.model.WorkflowInstance;
import com.sap.cloud.sdk.services.scp.workflow.cf.model.WorkflowInstanceStartPayload;
import com.sap.cloud.security.xsuaa.token.Token;

import org.apache.http.HttpEntity;
import org.apache.http.HttpHeaders;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cds.gen.z.sap.com.cap.odata.PoHeader;
import cds.gen.z.sap.com.cap.odata.PoHeader_;
import cds.gen.z.sap.com.cap.odata.PoItems;
import cds.gen.z.sap.com.cap.odata.PoItems_;
import cds.gen.z.sap.com.cap.odata.Products;
import cds.gen.z.sap.com.cap.odata.Products_;
import customer.ca_louis1.zstructure.ZJobLog;
import customer.ca_louis1.zstructure.ZPoForm;
import customer.ca_louis1.zstructure.ZPoHeader;
import customer.ca_louis1.zstructure.ZPoItem;
import customer.ca_louis1.zstructure.ZResult;
import customer.ca_louis1.zstructure.ZSoHeader;
import net.minidev.json.JSONUtil;

import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@RestController

@RequestMapping(value = "/rest")
public class MyController {
    @Autowired
    PersistenceService db;
    @Autowired
    XsuaaUserInfo xsuaaUserInfo;
    @Autowired
    JwtDecoder jwtDecoder;

    @PreAuthorize("permitAll()")
    @GetMapping("/test")
    @ResponseBody
    public String SayHello() {

        return "Hello Rest";
    }

    @PreAuthorize("permitAll()")
    @GetMapping("/getCurrentUser")
    @ResponseBody
    public Token getCurrentUser(@AuthenticationPrincipal Token token) {

        return token;
    }

    @PreAuthorize("permitAll()")
    @RequestMapping(value = "/getAllProducts", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.GET)
    // @ResponseBody
    public List<Products> getAllBooks() {

        
        List<Products> rtn = new ArrayList();
        CqnSelect sel = Select.from(Products_.class);

        Result rs = db.run(sel);

        for (Row row : rs) {
            Products tmp = row.as(Products.class);
            rtn.add(tmp);
        }
        

        return rtn;

    }
     @PreAuthorize("permitAll()")  
    @GetMapping(value = "/PO/getPO_Form", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody  
    public ZPoForm getPO_Form(@RequestParam("FORM_NO") String FORM_NO) {  
    	
          try {
              
            return getPo(FORM_NO);
        	
			
		} catch (Exception e) {
			e.printStackTrace();
			 throw new RuntimeException(e); 
		} 
         
    }
    @PreAuthorize("permitAll()")
    @PostMapping(value = "/PO/createPO_Form", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ZResult updateDeptUI5(@RequestBody ZPoForm inputPO) {  
        
    	ZResult rtn = new ZResult();
    	 try {  
    			
			 rtn = createPO(inputPO); 				
       } catch (Exception e) {  
           rtn.setStatus("E");
           rtn.setMessage(e.getMessage());
       }  
    	 return rtn;
    }
    @PreAuthorize("permitAll()")  
    @GetMapping(value = "/job/SO_JOB", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody  
    public List<ZJobLog> SO_JOB() { 

        List<ZJobLog> rtn = new ArrayList();
        Date now = new Date();
        String Status ="";
        String Message="scheduler job started";
        String ExecuteTime = now.toGMTString();
        String FormNo="";
        rtn.add(new ZJobLog(Status, Message, ExecuteTime, FormNo));
        try {
              String  definitionId = "po_approval";
              JSONObject context = new JSONObject();
              context.put("FORM_TYPE", "PO");
              context.put("PO_NO", "0000023");
              //context.toString();
                 WorkflowInstance rtn_startWorkflow = startWorkflow(definitionId,context);
               if(rtn_startWorkflow!=null)
               {
                  rtn.add( new ZJobLog(Status,"啟動流程成功 Id:"+rtn_startWorkflow.getId()+", "+rtn_startWorkflow.toString(), new Date().toGMTString(), context.toString()));
               }
              List<ZSoHeader> so_list = getSOList();
              for(int i=0;i<so_list.size();i++)
              {
                    rtn.add(new ZJobLog(Status, so_list.get(i).toString(), new Date().toGMTString(), so_list.get(i).getId()+""));  
              }
         
			
		} catch (Exception e) {
			e.printStackTrace();
			 throw new RuntimeException(e); 
        } 
        return rtn;
         
    }
    public WorkflowInstance startWorkflow(String definitionId, JSONObject context) {
        HttpDestination httpDestination_workflow = DestinationAccessor.getDestination("dest_workflow_runtime2").asHttp();
        WorkflowInstance rtn =null;
        try {
                WorkflowInstanceStartPayload body = new WorkflowInstanceStartPayload();
                body.setDefinitionId(definitionId);
                HashMap<String, Object> map = new Gson().fromJson(context.toString(), HashMap.class);
                 //Object tmp = new WorkflowInstancesApi(httpDestination_workflow).getInstanceContext("ec2c0b63-f9ad-11eb-af32-eeee0a9e1247");
                 body.setContext(map);
                 WorkflowInstance tmp_workflowInstance = new WorkflowInstancesApi(httpDestination_workflow).startInstance(body);
                rtn =tmp_workflowInstance;
               
                
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            
        }
         return rtn;

    }
    @PreAuthorize("permitAll()")
    @GetMapping("/getWorkflowDefinitions")
    @ResponseBody
    public List<WorkflowDefinition> getWorkflowDefinitions(@AuthenticationPrincipal Token token) {
        HttpDestination httpDestination_workflow = DestinationAccessor.getDestination("dest_workflow_runtime2").asHttp();
        List<WorkflowDefinition> rtn = new ArrayList();
        try {
                List<WorkflowDefinition> workflowDefinitions = new WorkflowDefinitionsApi(httpDestination_workflow).queryDefinitions();
                return workflowDefinitions;
                
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return rtn;

    }
    public ZResult createPO(ZPoForm inputPO)
    {   
            ZResult rtn = new ZResult();
            try {  
                   ZPoHeader input_PO_HEADER = inputPO.getPO_HEADER();
                   List<ZPoItem> input_PO_ITEMS = inputPO.getPO_ITEMS();

                   if(input_PO_HEADER!=null)
                   {
                        PoHeader tmp_PO_HEADER = PoHeader.create();
                        tmp_PO_HEADER =convertPO_HEADER(tmp_PO_HEADER,input_PO_HEADER);
                       
                        CqnUpsert upsert_PO_HEADER = Upsert.into(PoHeader_.class).entry(tmp_PO_HEADER);
                        
                        db.run(upsert_PO_HEADER);
                   }
                   if(input_PO_ITEMS!=null)
                   {
                        for(int i=0;i<input_PO_ITEMS.size();i++)
                        {
                            ZPoItem input_PO_ITEM_i = input_PO_ITEMS.get(i);
                            PoItems tmp_PO_ITEM = PoItems.create();
                            tmp_PO_ITEM = convertPO_ITEM(tmp_PO_ITEM,input_PO_ITEM_i);
                             CqnUpsert upsert_PO_ITEM = Upsert.into(PoItems_.class).entry(tmp_PO_ITEM);
                             db.run(upsert_PO_ITEM);
                        }
                   }
                   rtn.setStatus("S");
                   rtn.setMessage("");
                  	
            } catch (Exception e) {  
                rtn.setStatus("E");
                   rtn.setMessage(e.getMessage());
            }  
                return rtn;
    }
    private PoHeader convertPO_HEADER(PoHeader tmp_PO_HEADER, ZPoHeader input_PO_HEADER)
    {
        tmp_PO_HEADER.setCompCd(input_PO_HEADER.getCOMP_CD());
        tmp_PO_HEADER.setCurrency(input_PO_HEADER.getCURRENCY());
        tmp_PO_HEADER.setFormType(input_PO_HEADER.getFORM_TYPE());
        //tmp_PO_HEADER.setPoId();
        tmp_PO_HEADER.setPoNo(input_PO_HEADER.getPO_NO());
        tmp_PO_HEADER.setPurGrp(input_PO_HEADER.getPUR_GRP());
        tmp_PO_HEADER.setPurOrg(input_PO_HEADER.getPUR_ORG());
        tmp_PO_HEADER.setRemark(input_PO_HEADER.getREMARK());
        tmp_PO_HEADER.setVendorId(input_PO_HEADER.getVENDOR_ID());
        tmp_PO_HEADER.setVendorName(input_PO_HEADER.getVENDOR_NAME());

        return tmp_PO_HEADER;
    }
    private PoItems convertPO_ITEM(PoItems tmp_PO_Item, ZPoItem input_PO_ITEM_i)
    {
        if(input_PO_ITEM_i.getDELIVERY_DATE()!=null)
        {
            tmp_PO_Item.setDeliveryDate(input_PO_ITEM_i.getDELIVERY_DATE().toInstant());
        }
        
        tmp_PO_Item.setFlag(input_PO_ITEM_i.getFLAG());
        tmp_PO_Item.setItemNo(Integer.valueOf(input_PO_ITEM_i.getITEM_NO()));
        tmp_PO_Item.setMaterialDesp(input_PO_ITEM_i.getMATERIAL_DESP());
        tmp_PO_Item.setMaterialNo(input_PO_ITEM_i.getMATERIAL_NO());
       tmp_PO_Item.setPoNo(input_PO_ITEM_i.getPO_NO());
       //tmp_PO_Item.setPoitemId();
       tmp_PO_Item.setQty(new BigDecimal(input_PO_ITEM_i.getQTY()));
       tmp_PO_Item.setUnit(input_PO_ITEM_i.getUNIT());
       tmp_PO_Item.setUnitPrice(new BigDecimal(input_PO_ITEM_i.getUNIT_PRICE()));
    
        return tmp_PO_Item;
    }
    public ZPoForm getPo(java.lang.String PO_NO)
    {
        ZPoForm rtn = new ZPoForm();
        PoHeader tmp_header = getPoHeader(PO_NO);
        List<PoItems> tmp_items = getPoItems(PO_NO);

        ZPoHeader pO_HEADER = new ZPoHeader();
        if(tmp_header!=null)
        {
            pO_HEADER.setCOMP_CD(tmp_header.getCompCd());
            pO_HEADER.setCURRENCY(tmp_header.getCurrency());
            pO_HEADER.setFORM_TYPE(tmp_header.getFormType());
            pO_HEADER.setPO_NO(tmp_header.getPoNo());
            pO_HEADER.setPUR_GRP(tmp_header.getPurGrp());
            pO_HEADER.setPUR_ORG(tmp_header.getPurOrg());
            pO_HEADER.setREMARK(tmp_header.getRemark());
            pO_HEADER.setVENDOR_ID(tmp_header.getVendorId());
            pO_HEADER.setVENDOR_NAME(tmp_header.getVendorName());
        }
        rtn.setPO_HEADER(pO_HEADER);
        List<ZPoItem> pO_ITEMS = new ArrayList<>();
        for(int i=0;i<tmp_items.size();i++)
        {
            ZPoItem pO_ITEM = new ZPoItem();
            PoItems tmp_item = tmp_items.get(i);
            pO_ITEM.setDELIVERY_DATE(instantTocalendar(tmp_item.getDeliveryDate()));
            pO_ITEM.setFLAG(tmp_item.getFlag());
            pO_ITEM.setITEM_NO(tmp_item.getItemNo()+"");
            pO_ITEM.setMATERIAL_DESP(tmp_item.getMaterialDesp());
            pO_ITEM.setMATERIAL_NO(tmp_item.getMaterialNo());
            pO_ITEM.setPOITEM_ID(tmp_item.getPoitemId());
            pO_ITEM.setPO_NO(tmp_item.getPoNo());
            pO_ITEM.setQTY(tmp_item.getQty().doubleValue());
            pO_ITEM.setUNIT(tmp_item.getUnit());
            pO_ITEM.setUNIT_PRICE(tmp_item.getUnitPrice().doubleValue());
            pO_ITEMS.add(pO_ITEM);
        }    
        rtn.setPO_ITEMS(pO_ITEMS);
        return rtn;
    }

    public PoHeader getPoHeader(java.lang.String PO_NO) {

        PoHeader rtn = PoHeader.create();
        try {

            // query po header
            CqnSelect sel_from = Select.from(PoHeader_.class)
                    .where(b -> (b.PO_NO().eq(PO_NO)));
				    Result rs_tmp = db.run(sel_from);
                    for (Row row : rs_tmp) {
                        rtn = row.as(PoHeader.class);
                       
                    }
			
			} 
			catch (Exception e) 
			{
				e.printStackTrace();
			}
			finally 
			{
					  
			}
		 	 return rtn;
			
		}
     public List<PoItems> getPoItems(java.lang.String PO_NO) {

        List<PoItems> rtn = new ArrayList<>();
        try {

            // query po header
            CqnSelect sel_from = Select.from(PoItems_.class)
                    .where(b -> (b.PO_NO().eq(PO_NO)));
				    Result rs_tmp = db.run(sel_from);
                    for (Row row : rs_tmp) {
                        PoItems tmp = row.as(PoItems.class);
                       rtn.add(tmp);
                    }
			
			} 
			catch (Exception e) 
			{
				e.printStackTrace();
			}
			finally 
			{
					  
			}
		 	 return rtn;
			
        }
    private Calendar instantTocalendar(Instant input)
    {
        if(input!=null)
        {
            //Instant instant = Instant.now();
            ZonedDateTime zdt = ZonedDateTime.ofInstant(input, ZoneId.systemDefault());
            Calendar cal1 = GregorianCalendar.from(zdt);
            return cal1;
        }
       else
       {
           return null;
       }
    }
    private List<ZSoHeader> getSOList()
    {
        HttpDestination httpDestination_Northwind_OrderList = DestinationAccessor.getDestination("Northwind_OrderList").asHttp();
        List<ZSoHeader> rtn = new ArrayList();
        HttpClient hc =  HttpClientAccessor.getHttpClient(httpDestination_Northwind_OrderList);
        HttpGet request = new HttpGet("/orders.json");
        
            // add request headers
            // request.addHeader("custom-key", "mkyong");
            // request.addHeader(HttpHeaders.USER_AGENT, "Googlebot");

            HttpResponse response;
            try {
                response = hc.execute(request);
                HttpEntity entity = response.getEntity();
                if (entity != null) {
                    // return it as a String
                    String result = EntityUtils.toString(entity);
                    //rtn = result;
                    // parsing JSON
                JSONObject js_result = new JSONObject(result); //Convert String to JSON Object

                    JSONArray entityList = js_result.getJSONArray("results");
                    for(int i=0;i<entityList.length();i++)
                    {
                        JSONObject tmpEntity = entityList.getJSONObject(i).getJSONObject("order");
                        ObjectMapper objectMapper=new ObjectMapper();
                        ZSoHeader tmp_ZSoHeader=objectMapper.readValue(tmpEntity.toString(),ZSoHeader.class);
                        rtn.add(tmp_ZSoHeader);
                    }
              
                }
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } 
             

            
        return rtn;
    }
}

