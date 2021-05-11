package customer.ca_louis2;

import java.util.ArrayList;
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

import cds.gen.z.sap.com.cap.odata.Products;
import cds.gen.z.sap.com.cap.odata.Products_;

import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@RestController

@RequestMapping(value = "/rest")
public class myController {
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
}

