-- <DS_SCRIPT>
-- DESCRIPTION...: FEATURE/#10 - Flyway migration script for authentication module
-- RESPONSIBLE...: LUIZ
-- DATE..........: 02/07/2025
-- APPLICATION...: API_PHARMA
-- </DS_SCRIPT>

-- Implements UUID as primary key and follows new architecture requirements

-- ---------------------------------------- CREATION OF TABLES ----------------------------------------

-- CREATE TABLE TB_USERS
CREATE TABLE tb_users (
    id BINARY(16) NOT NULL COMMENT 'UUID identifier for the user (Primary Key)',
    user_name VARCHAR(60) NOT NULL COMMENT 'Name of the user',
    email VARCHAR(100) NOT NULL COMMENT 'Email of the user',
    user_password VARCHAR(255) NOT NULL COMMENT 'Hashed password of the user',
    user_role ENUM('ATTENDANT','COMPOUNDER','CUSTOMER','MANAGER','PHARMACIST') NOT NULL COMMENT 'Role of the user',
    status ENUM('ACTIVE','INACTIVE','BLOCKED') NOT NULL COMMENT 'Status of the user',
    created_at DATETIME(6) NOT NULL COMMENT 'Creation timestamp',
    updated_at DATETIME(6) COMMENT 'Last update timestamp',
    created_by BINARY(16) COMMENT 'UUID of user who created this record',
    updated_by BINARY(16) COMMENT 'UUID of user who updated this record',
    PRIMARY KEY (id),
    CONSTRAINT uc_user_email UNIQUE (email)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='Table to store users';

-- CREATE TABLE TB_TOKENS
CREATE TABLE tb_tokens (
    id BINARY(16) NOT NULL COMMENT 'UUID identifier for the token (Primary Key)',
    token VARCHAR(255) NOT NULL COMMENT 'JWT token value',
    token_type ENUM('BEARER') NOT NULL COMMENT 'Type of token',
    revoked BOOLEAN NOT NULL DEFAULT FALSE COMMENT 'Indicates if token was revoked',
    expired BOOLEAN NOT NULL DEFAULT FALSE COMMENT 'Indicates if token is expired',
    user_id BINARY(16) NOT NULL COMMENT 'Reference to user who owns this token',
    created_at DATETIME(6) NOT NULL COMMENT 'Creation timestamp',
    PRIMARY KEY (id),
    CONSTRAINT uc_token_value UNIQUE (token),
    CONSTRAINT fk_token_user FOREIGN KEY (user_id) REFERENCES tb_users (id) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='Table to store authentication tokens';

-- Create indexes for better performance
CREATE INDEX idx_user_email ON tb_users (email);
CREATE INDEX idx_token_user ON tb_tokens (user_id);