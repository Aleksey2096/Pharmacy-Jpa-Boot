package by.academy.pharmacy2.service.database;

import static by.academy.pharmacy2.entity.Constant.AMOUNT;
import static by.academy.pharmacy2.entity.Constant.APPROVAL_DATE;
import static by.academy.pharmacy2.entity.Constant.COMPANY_NAME;
import static by.academy.pharmacy2.entity.Constant.CONTACT_PHONE;
import static by.academy.pharmacy2.entity.Constant.COUNTRY;
import static by.academy.pharmacy2.entity.Constant.CREATION_DATE;
import static by.academy.pharmacy2.entity.Constant.DATE;
import static by.academy.pharmacy2.entity.Constant.DATE_JOINED;
import static by.academy.pharmacy2.entity.Constant.DELIVERY_ADDRESS;
import static by.academy.pharmacy2.entity.Constant.DOSAGE;
import static by.academy.pharmacy2.entity.Constant.FORM;
import static by.academy.pharmacy2.entity.Constant.HEALTH_CARE_CARD_NUMBER;
import static by.academy.pharmacy2.entity.Constant.ID;
import static by.academy.pharmacy2.entity.Constant.IS_NONPRESCRIPTION;
import static by.academy.pharmacy2.entity.Constant.LOCAL_DATE_TIME;
import static by.academy.pharmacy2.entity.Constant.LOGIN;
import static by.academy.pharmacy2.entity.Constant.PAYMENT_CARD_NUMBER;
import static by.academy.pharmacy2.entity.Constant.PRESCRIPTION_REQUEST_STATUS;
import static by.academy.pharmacy2.entity.Constant.PRICE;
import static by.academy.pharmacy2.entity.Constant.ROLE;
import static by.academy.pharmacy2.entity.Constant.TITLE;
import static by.academy.pharmacy2.entity.Constant.UPLOAD_DATE_TIME;

public enum SpecificationFields {
    MEDICINE(new String[]{ID, TITLE, IS_NONPRESCRIPTION, APPROVAL_DATE}),
    MEDICINE_PRODUCT(new String[]{ID, DOSAGE, FORM, PRICE, AMOUNT}),
    ORDER(new String[]{ID, LOCAL_DATE_TIME, AMOUNT, PRICE, PAYMENT_CARD_NUMBER, CONTACT_PHONE,
            DELIVERY_ADDRESS}),
    PRESCRIPTION(new String[]{ID, AMOUNT, DATE}),
    PRESCRIPTION_REQUEST(new String[]{ID, UPLOAD_DATE_TIME, PRESCRIPTION_REQUEST_STATUS}),
    PRODUCER(new String[]{ID, COMPANY_NAME, COUNTRY, CREATION_DATE}),
    USER(new String[]{HEALTH_CARE_CARD_NUMBER, LOGIN, ROLE, DATE_JOINED});

    private final String[] fields;

    SpecificationFields(final String[] fields) {
        this.fields = fields;
    }

    public String[] getFields() {
        return fields;
    }
}
