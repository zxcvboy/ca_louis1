package customer.ca_louis1;

import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
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

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
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
import customer.ca_louis1.zstructure.ZPoForm;
import customer.ca_louis1.zstructure.ZPoHeader;
import customer.ca_louis1.zstructure.ZPoItem;

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

    @PreAuthorize("permitAll()")
    @GetMapping("/test")
    @ResponseBody
    public String SayHello() {

        return "Hello Rest";
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
}

