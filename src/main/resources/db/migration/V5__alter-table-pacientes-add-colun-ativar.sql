alter table pacientes add ativo tinyint;
update pacientes set ativo = 1;
ALTER TABLE pacientes MODIFY ativo tinyint NOT NULL ;
