namespace z.sap.com.cap.odata;

    using { Currency, cuid, managed, sap.common.CodeList } from '@sap/cds/common';

    entity Products : cuid, managed {
        title    : String(111);
        descr    : String(1111);
        stock    : Integer;
        price    : Decimal(9,2);
        currency : Currency;
        category : Association to Categories;
    }

    entity Categories : CodeList {
        key ID   : Integer;
        parent   : Association to Categories;
        children : Composition of many Categories on children.parent = $self;
    }
    // -----------PO相關 Start ---------------
entity PO_HEADER : managed {
    @id key PO_ID : UUID;
    PO_NO         : String;
    FORM_TYPE     : String default 'PO';
    COMP_CD       : String;
    VENDOR_ID     : String;
    VENDOR_NAME   : String;
    PUR_GRP       : String;
    PUR_ORG       : String;
    CURRENCY      : String;
    REMARK        : String;

}

entity PO_ITEMS : managed {
    POITEM_ID     : UUID;
    PO_NO         : String;
    ITEM_NO       : Integer;
    MATERIAL_NO   : String;
    MATERIAL_DESP : String;
    QTY           : Decimal;
    UNIT          : String;
    UNIT_PRICE    : Decimal;
    DELIVERY_DATE : Timestamp;
    FLAG          : String;
}
    