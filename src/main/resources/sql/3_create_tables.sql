CREATE TABLE producers
(
    id            BIGINT                                                   NOT NULL AUTO_INCREMENT,
    company_name  VARCHAR(255)                                             NOT NULL,
    country_code  ENUM ('AU','AT','BE','CA','HR','JP','US','PL','KR','SE') NOT NULL,
    creation_date DATE DEFAULT NULL,
    CONSTRAINT PK_producers PRIMARY KEY (id)
);

CREATE TABLE medicines
(
    id                  BIGINT       NOT NULL AUTO_INCREMENT,
    title               VARCHAR(255) NOT NULL,
    is_nonprescription  BOOLEAN      NOT NULL,
    producer_id         BIGINT       NOT NULL,
    approval_date       DATE         DEFAULT NULL,
    medicine_image_path VARCHAR(255) DEFAULT NULL,
    CONSTRAINT PK_medicines PRIMARY KEY (id),
    CONSTRAINT UC_medicines UNIQUE (title, is_nonprescription, producer_id),
    CONSTRAINT FK_medicines_producers FOREIGN KEY (producer_id)
        REFERENCES producers (id)
        ON UPDATE CASCADE
        ON DELETE RESTRICT
);

CREATE TABLE medicine_products
(
    id          BIGINT        NOT NULL AUTO_INCREMENT,
    medicine_id BIGINT        NOT NULL,
    dosage      SMALLINT      NOT NULL,
    form        VARCHAR(10)   NOT NULL,
    price       DECIMAL(6, 2) NOT NULL,
    amount      INTEGER       NOT NULL,
    CONSTRAINT PK_medicine_products PRIMARY KEY (id),
    CONSTRAINT FK_medicine_products_medicines FOREIGN KEY (medicine_id)
        REFERENCES medicines (id)
        ON UPDATE CASCADE
        ON DELETE RESTRICT
);

CREATE TABLE addresses
(
    health_care_card_number BIGINT       NOT NULL,
    postcode                INTEGER      NOT NULL,
    city                    VARCHAR(255) NOT NULL,
    street                  VARCHAR(255) NOT NULL,
    house                   INTEGER      NOT NULL,
    apartment               INTEGER DEFAULT NULL,
    CONSTRAINT PK_addresses PRIMARY KEY (health_care_card_number)
);

CREATE TABLE personal_info
(
    health_care_card_number BIGINT       NOT NULL,
    surname                 VARCHAR(255) NOT NULL,
    name                    VARCHAR(255) NOT NULL,
    date_of_birth           DATE         NOT NULL,
    phone                   VARCHAR(20)  NOT NULL,
    email                   VARCHAR(255)   DEFAULT NULL,
    position                VARCHAR(255)   DEFAULT NULL,
    personal_account        DECIMAL(10, 2) DEFAULT NULL,
    payment_card_number     BIGINT         DEFAULT NULL,
    CONSTRAINT PK_personal_info PRIMARY KEY (health_care_card_number),
    CONSTRAINT FK_personal_info_addresses FOREIGN KEY (health_care_card_number)
        REFERENCES addresses (health_care_card_number)
        ON UPDATE CASCADE
        ON DELETE RESTRICT
);

CREATE TABLE users
(
    health_care_card_number BIGINT       NOT NULL,
    login                   VARCHAR(255) NOT NULL UNIQUE,
    password                CHAR(64)     NOT NULL,
    /*
     0 - administrator (Role.ADMINISTRATOR)
     1 - pharmacist (Role.PHARMACIST)
     2 - client (Role.CLIENT)
     */
    role                    TINYINT      NOT NULL CHECK (role IN (0, 1, 2)),
    date_joined             DATE         NOT NULL,
    avatar_image_path       VARCHAR(255) DEFAULT NULL,
    CONSTRAINT PK_users PRIMARY KEY (health_care_card_number),
    CONSTRAINT FK_users_personal_info FOREIGN KEY (health_care_card_number)
        REFERENCES personal_info (health_care_card_number)
        ON UPDATE CASCADE
        ON DELETE RESTRICT
);

CREATE TABLE prescription_requests
(
    id                          BIGINT       NOT NULL AUTO_INCREMENT,
    prescription_scan_path      VARCHAR(255) NOT NULL,
    upload_date_time            TIMESTAMP    NOT NULL,
    prescription_request_status VARCHAR(11)  NOT NULL,
    health_care_card_number     BIGINT       NOT NULL,
    CONSTRAINT PK_prescription_requests PRIMARY KEY (id),
    CONSTRAINT FK_prescription_requests_users FOREIGN KEY (health_care_card_number)
        REFERENCES users (health_care_card_number)
        ON UPDATE CASCADE
        ON DELETE RESTRICT
);

CREATE TABLE prescriptions
(
    id                      BIGINT  NOT NULL AUTO_INCREMENT,
    health_care_card_number BIGINT  NOT NULL,
    medicine_product_id     BIGINT  NOT NULL,
    amount                  INTEGER NOT NULL,
    date                    DATE    NOT NULL,
    CONSTRAINT PK_prescriptions PRIMARY KEY (id),
    CONSTRAINT FK_prescriptions_medicine_products FOREIGN KEY (medicine_product_id)
        REFERENCES medicine_products (id)
        ON UPDATE CASCADE
        ON DELETE RESTRICT,
    CONSTRAINT FK_prescriptions_users FOREIGN KEY (health_care_card_number)
        REFERENCES users (health_care_card_number)
        ON UPDATE CASCADE
        ON DELETE RESTRICT
);

CREATE TABLE orders
(
    id                      BIGINT        NOT NULL AUTO_INCREMENT,
    date_time               TIMESTAMP     NOT NULL,
    health_care_card_number BIGINT        NOT NULL,
    medicine_product_id     BIGINT        NOT NULL,
    amount                  INTEGER       NOT NULL,
    price                   DECIMAL(6, 2) NOT NULL,


    payment_card_number     BIGINT        NOT NULL,
    contact_phone           VARCHAR(20)   NOT NULL,
    delivery_address        VARCHAR(255)  NOT NULL,


    CONSTRAINT PK_orders PRIMARY KEY (id),
    CONSTRAINT FK_orders_users FOREIGN KEY (health_care_card_number)
        REFERENCES users (health_care_card_number)
        ON UPDATE CASCADE
        ON DELETE RESTRICT,
    CONSTRAINT FK_orders_medicine_products FOREIGN KEY (medicine_product_id)
        REFERENCES medicine_products (id)
        ON UPDATE CASCADE
        ON DELETE RESTRICT
);

CREATE TABLE carts
(
    id                      BIGINT NOT NULL AUTO_INCREMENT,
    health_care_card_number BIGINT NOT NULL,
    medicine_product_id     BIGINT NOT NULL,
    cart_order              BIGINT NOT NULL,
    CONSTRAINT PK_carts PRIMARY KEY (id),
    CONSTRAINT FK_carts_users FOREIGN KEY (health_care_card_number)
        REFERENCES users (health_care_card_number)
        ON UPDATE CASCADE
        ON DELETE RESTRICT,
    CONSTRAINT FK_carts_medicine_products FOREIGN KEY (medicine_product_id)
        REFERENCES medicine_products (id)
        ON UPDATE CASCADE
        ON DELETE RESTRICT,
    CONSTRAINT UC_users_storages UNIQUE (health_care_card_number, medicine_product_id)
);
