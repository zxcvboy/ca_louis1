package customer.ca_louis2.handler;

import com.sap.cds.services.handler.annotations.ServiceName;
import org.springframework.stereotype.Component;
import cds.gen.testservice.Products;
import cds.gen.z.sap.com.cap.odata.Categories;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import com.sap.cds.ql.Select;
import com.sap.cds.ql.Update;
import com.sap.cds.ql.cqn.CqnSelect;
import com.sap.cds.ql.cqn.CqnUpdate;
import com.sap.cds.services.ErrorStatuses;
import com.sap.cds.services.ServiceException;
import com.sap.cds.services.cds.CdsService;
import com.sap.cds.services.handler.annotations.Before;
import com.sap.cds.services.persistence.PersistenceService;
import java.math.BigDecimal;

import com.sap.cds.services.handler.EventHandler;
import com.sap.cds.services.handler.annotations.After;

@Component
@ServiceName("TestService")
public class zHandler implements EventHandler {
//Replace this comment with the code of Step 2 of this tutorial
@Autowired
PersistenceService db;

@After(event = CdsService.EVENT_READ, entity = "TestService.Categories")
public void readProducts(List<Categories> categories) {
    for (Categories category : categories) {
       category.setDescr("Louis description");
    }
};

}