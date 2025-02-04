-- <DS_SCRIPT>
-- DESCRIPTION...: FEATURE/#2 - Created unique constraint to email
-- RESPONSIBLE...: LUIZ
-- DATE..........: 03/02/2025
-- APPLICATION...: API_PHARMA
-- </DS_SCRIPT>

-- ---------------------------------------- CREATION CONSTRAINT ----------------------------------------

ALTER TABLE tb_users ADD CONSTRAINT uc_user_email UNIQUE (email);



