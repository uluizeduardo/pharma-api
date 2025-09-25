-- DEPRECATED: Esta migration foi substituída por V1__Create_auth_schema.sql
-- Mantida apenas para referência histórica

-- <DS_SCRIPT>
-- DESCRIPTION...: FEATURE/#1 - INITIAL CREATION OF TABLES
-- RESPONSIBLE...: LUIZ
-- DATE..........: 20/01/2025
-- APPLICATION...: API_PHARMA
-- </DS_SCRIPT>

-- ---------------------------------------- CREATION OF TABLES ----------------------------------------

-- CREATE TABLE TB_ADDRESSES
CREATE TABLE tb_addresses (
    id               BIGINT NOT NULL AUTO_INCREMENT COMMENT 'Unique identifier for the address (Primary Key)',
    address_number   VARCHAR(10) NOT NULL COMMENT 'Number of the address',
    city             VARCHAR(50) NOT NULL COMMENT 'City of the address',
    complement       VARCHAR(50) COMMENT 'Additional details of the address',
    country          VARCHAR(50) NOT NULL COMMENT 'Country of the address',
    neighborhood     VARCHAR(50) NOT NULL COMMENT 'Neighborhood of the address',
    state            VARCHAR(30) NOT NULL COMMENT 'State of the address',
    street           VARCHAR(60) NOT NULL COMMENT 'Street name of the address',
    zip_code         VARCHAR(8) NOT NULL COMMENT 'Postal code (ZIP) of the address',
    PRIMARY KEY (id)
) ENGINE=InnoDB COMMENT 'Table to store address data';

-- CREATE TABLE TB_ATTENDANTS
CREATE TABLE tb_attendants (
    id         BIGINT NOT NULL AUTO_INCREMENT COMMENT 'Unique identifier for the attendant (Primary Key)',
    user_id    BIGINT NOT NULL COMMENT 'Reference to the users table',
    PRIMARY KEY (id)
) ENGINE=InnoDB COMMENT 'Table to store attendants';

-- CREATE TABLE TB_ATTENDANTS_PHARMACIES
CREATE TABLE tb_attendants_pharmacies (
    attendant_id BIGINT NOT NULL COMMENT 'Reference to the attendant',
    pharmacy_id  BIGINT NOT NULL COMMENT 'Reference to the pharmacy'
) ENGINE=InnoDB COMMENT 'Table to associate attendants with pharmacies';

-- CREATE TABLE TB_COMPOUNDERS
CREATE TABLE tb_compounders (
    id         BIGINT NOT NULL AUTO_INCREMENT COMMENT 'Unique identifier for the compounder (Primary Key)',
    user_id    BIGINT NOT NULL COMMENT 'Reference to the users table',
    PRIMARY KEY (id)
) ENGINE=InnoDB COMMENT 'Table to store compounders';

-- CREATE TABLE TB_COMPOUNDERS_PHARMACIES
CREATE TABLE tb_compounders_pharmacies (
    compounder_id BIGINT NOT NULL COMMENT 'Reference to the compounder',
    pharmacy_id   BIGINT NOT NULL COMMENT 'Reference to the pharmacy'
) ENGINE=InnoDB COMMENT 'Table to associate compounders with pharmacies';

-- CREATE TABLE TB_CUSTOMERS
CREATE TABLE tb_customers (
    id            BIGINT NOT NULL AUTO_INCREMENT COMMENT 'Unique identifier for the customer (Primary Key)',
    phone_number  VARCHAR(11) NOT NULL COMMENT 'Phone number of the customer (up to 11 digits)',
    address_id    BIGINT NOT NULL COMMENT 'Reference to the address table',
    user_id       BIGINT NOT NULL COMMENT 'Reference to the users table',
    PRIMARY KEY (id)
) ENGINE=InnoDB COMMENT 'Table to store customers';

-- CREATE TABLE TB_PHARMACIES
CREATE TABLE tb_pharmacies (
    id             BIGINT NOT NULL AUTO_INCREMENT COMMENT 'Unique identifier for the pharmacy (Primary Key)',
    pharmacy_name  VARCHAR(100) NOT NULL COMMENT 'Name of the pharmacy',
    unit_name      VARCHAR(50) NOT NULL COMMENT 'Unit name of the pharmacy',
    address_id     BIGINT NOT NULL COMMENT 'Reference to the address table',
    PRIMARY KEY (id)
) ENGINE=InnoDB COMMENT 'Table to store pharmacies';

-- CREATE TABLE TB_PHARMACISTS
CREATE TABLE tb_pharmacists (
    id          BIGINT NOT NULL AUTO_INCREMENT COMMENT 'Unique identifier for the pharmacist (Primary Key)',
    crm_number  VARCHAR(20) NOT NULL COMMENT 'CRM number of the pharmacist',
    specialty   VARCHAR(50) NOT NULL COMMENT 'Specialty of the pharmacist',
    user_id     BIGINT NOT NULL COMMENT 'Reference to the users table',
    PRIMARY KEY (id)
) ENGINE=InnoDB COMMENT 'Table to store pharmacists';

-- CREATE TABLE TB_PHARMACISTS_PHARMACIES
CREATE TABLE tb_pharmacists_pharmacies (
    pharmacist_id BIGINT NOT NULL COMMENT 'Reference to the pharmacist',
    pharmacy_id   BIGINT NOT NULL COMMENT 'Reference to the pharmacy'
) ENGINE=InnoDB COMMENT 'Table to associate pharmacists with pharmacies';

-- CREATE TABLE TB_TOKENS
CREATE TABLE tb_tokens (
    id         BIGINT NOT NULL AUTO_INCREMENT COMMENT 'Unique identifier for the token (Primary Key)',
    expired    BIT NOT NULL COMMENT 'Indicates if the token has expired (0 = false, 1 = true)',
    revoked    BIT NOT NULL COMMENT 'Indicates if the token has been revoked (0 = false, 1 = true)',
    token      VARCHAR(255) COMMENT 'Token value',
    tokenType  ENUM ('BEARER') COMMENT 'Type of the token',
    user_id    BIGINT NOT NULL COMMENT 'Reference to the users table',
    PRIMARY KEY (id)
) ENGINE=InnoDB COMMENT 'Table to store authentication tokens';

-- CREATE TABLE TB_USERS
CREATE TABLE tb_users (
    id           BIGINT NOT NULL AUTO_INCREMENT COMMENT 'Unique identifier for the user (Primary Key)',
    created_at   DATETIME(6) COMMENT 'Creation date of the user record',
    email        VARCHAR(100) NOT NULL COMMENT 'Email of the user',
    is_active    BIT COMMENT 'Indicates if the user is active (0 = false, 1 = true)',
    user_password VARCHAR(100) NOT NULL COMMENT 'Password of the user',
    user_role    ENUM ('ATTENDANT','COMPOUNDER','CUSTOMER','MANAGER','PHARMACIST') COMMENT 'Role of the user',
    updated_at   DATETIME(6) COMMENT 'Last update date of the user record',
    user_name    VARCHAR(60) NOT NULL COMMENT 'Name of the user',
    PRIMARY KEY (id)
) ENGINE=InnoDB COMMENT 'Table to store users';

-- ---------------------------------------- CREATION OF CONSTRAINTS ----------------------------------------
-- Drop e criação de índices únicos (UK)
ALTER TABLE tb_attendants
ADD CONSTRAINT UK_user_id_attendants UNIQUE (user_id);

ALTER TABLE tb_compounders
ADD CONSTRAINT UK_user_id_compounders UNIQUE (user_id);

ALTER TABLE tb_customers
ADD CONSTRAINT UK_address_id_customers UNIQUE (address_id);

ALTER TABLE tb_customers
ADD CONSTRAINT UK_user_id_customers UNIQUE (user_id);

ALTER TABLE tb_pharmacies
ADD CONSTRAINT UK_address_id_pharmacies UNIQUE (address_id);

ALTER TABLE tb_pharmacists
ADD CONSTRAINT UK_user_id_pharmacists UNIQUE (user_id);

ALTER TABLE tb_tokens
ADD CONSTRAINT UK_token_tokens UNIQUE (token);

-- Criação de chaves estrangeiras (FK)
ALTER TABLE tb_attendants
ADD CONSTRAINT FK_user_id_attendants FOREIGN KEY (user_id) REFERENCES tb_users (id);

ALTER TABLE tb_attendants_pharmacies
ADD CONSTRAINT FK_pharmacy_id_attendants_pharmacies FOREIGN KEY (pharmacy_id) REFERENCES tb_pharmacies (id);

ALTER TABLE tb_attendants_pharmacies
ADD CONSTRAINT FK_attendant_id_attendants_pharmacies FOREIGN KEY (attendant_id) REFERENCES tb_attendants (id);

ALTER TABLE tb_compounders
ADD CONSTRAINT FK_user_id_compounders FOREIGN KEY (user_id) REFERENCES tb_users (id);

ALTER TABLE tb_compounders_pharmacies
ADD CONSTRAINT FK_pharmacy_id_compounders_pharmacies FOREIGN KEY (pharmacy_id) REFERENCES tb_pharmacies (id);

ALTER TABLE tb_compounders_pharmacies
ADD CONSTRAINT FK_compounder_id_compounders_pharmacies FOREIGN KEY (compounder_id) REFERENCES tb_compounders (id);

ALTER TABLE tb_customers
ADD CONSTRAINT FK_address_id_customers FOREIGN KEY (address_id) REFERENCES tb_addresses (id);

ALTER TABLE tb_customers
ADD CONSTRAINT FK_user_id_customers FOREIGN KEY (user_id) REFERENCES tb_users (id);

ALTER TABLE tb_pharmacies
ADD CONSTRAINT FK_address_id_pharmacies FOREIGN KEY (address_id) REFERENCES tb_addresses (id);

ALTER TABLE tb_pharmacists
ADD CONSTRAINT FK_user_id_pharmacists FOREIGN KEY (user_id) REFERENCES tb_users (id);

ALTER TABLE tb_pharmacists_pharmacies
ADD CONSTRAINT FK_pharmacy_id_pharmacists_pharmacies FOREIGN KEY (pharmacy_id) REFERENCES tb_pharmacies (id);

ALTER TABLE tb_pharmacists_pharmacies
ADD CONSTRAINT FK_pharmacist_id_pharmacists_pharmacies FOREIGN KEY (pharmacist_id) REFERENCES tb_pharmacists (id);

ALTER TABLE tb_tokens
ADD CONSTRAINT FK_user_id_tokens FOREIGN KEY (user_id) REFERENCES tb_users (id);




