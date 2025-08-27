-- DEPRECATED: Esta migration foi substituída por V1__Create_auth_schema.sql
-- Mantida apenas para referência histórica

-- <DS_SCRIPT>
-- DESCRIPTION...: FEATURE/#2 - Created unique constraint to email
-- RESPONSIBLE...: LUIZ
-- DATE..........: 03/02/2025
-- APPLICATION...: API_PHARMA
-- </DS_SCRIPT>

-- ---------------------------------------- CREATION CONSTRAINT ----------------------------------------

ALTER TABLE tb_users ADD CONSTRAINT uc_user_email UNIQUE (email);



