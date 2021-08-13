using { z.sap.com.cap.odata as db } from '../db/schema';

service TestService {
    entity Products   as projection on db.Products;
    entity Categories as projection on db.Categories;
    entity PO_HEADER as projection on db.PO_HEADER;
    entity PO_ITEMS as projection on db.PO_ITEMS;
}