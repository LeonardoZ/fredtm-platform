


INSERT INTO account(id,uuid,email,name,password_hash,salt) VALUES(1,'23ca7484-9126-4eeb-91c7-262197aaef46','leo.zapparoli@gmail.com','leo','82B0369FBF21C6D1C1718B3B48A23FBA0AC78570','FAE4CE6E796026E7');
INSERT INTO account(id,uuid,email,name,password_hash,salt) VALUES(2,'48493871-920d-43b9-bdd0-5f804d1b9be4','beth.zapparoli@gmail.com','Beth','82B0369FBF21C6D1C1718B3B48A23FBA0AC78570','FAE4CE6E796026E7');
INSERT INTO account(id,uuid,email,name,password_hash,salt) VALUES(3,'2b7d6dc0-76ce-4d55-82d0-28ea515ed00a','ze.zapparoli@gmail.com','Zé','82B0369FBF21C6D1C1718B3B48A23FBA0AC78570','FAE4CE6E796026E7');
INSERT INTO account(id,uuid,email,name,password_hash,salt) VALUES(4,'4415aa4f-927f-4f3b-8607-83fa89a92529','henrique.zapparoli@gmail.com','Henrique','82B0369FBF21C6D1C1718B3B48A23FBA0AC78570','FAE4CE6E796026E7');
INSERT INTO account(id,uuid,email,name,password_hash,salt) VALUES(5,'a8bd8843-8341-485e-8648-1cc975cc1d07','rafael.zapparoli@gmail.com','Rafael','82B0369FBF21C6D1C1718B3B48A23FBA0AC78570','FAE4CE6E796026E7');
INSERT INTO account(id,uuid,email,name,password_hash,salt) VALUES(6,'4fd21d5c-52d8-412b-bd19-ac2959662a16','pedro.zapparoli@gmail.com','Pedro','82B0369FBF21C6D1C1718B3B48A23FBA0AC78570','FAE4CE6E796026E7');
INSERT INTO account(id,uuid,email,name,password_hash,salt) VALUES(7,'f662b8ec-8be1-4440-ac7c-118bf29d3bd0','hana.zapparoli@gmail.com','Hana','82B0369FBF21C6D1C1718B3B48A23FBA0AC78570','FAE4CE6E796026E7');

INSERT INTO account_roles (account_id,role) VALUES(1,'USER');
INSERT INTO account_roles (account_id,role) VALUES(2,'USER');
INSERT INTO account_roles (account_id,role) VALUES(3,'USER');
INSERT INTO account_roles (account_id,role) VALUES(4,'USER');
INSERT INTO account_roles (account_id,role) VALUES(5,'USER');
INSERT INTO account_roles (account_id,role) VALUES(6,'USER');
INSERT INTO account_roles (account_id,role) VALUES(7,'USER');

INSERT INTO operation(id,uuid,name,company,technical_characteristics,account_id,modified)
VALUES(1,'29c31a3d-97a7-439e-b201-ce80b716dfc6','Op dd teste','Farm SA','Operação de coleta de valores',1,'2015-04-10 20:07:48');

INSERT INTO activity(id,uuid,title,description,activity_type,quantitative,item_name,operation_id)
VALUES(1,'202d39c2-17c7-4949-a494-248dca56b833','A','Descripion of A',0,'0','',1);
INSERT INTO activity(id,uuid,title,description,activity_type,quantitative,item_name,operation_id)
VALUES(2,'202d39c2-17c7-4949-a494-248dca16b890','B','Descripion of B',2,'1','Trees',1);

INSERT INTO collect(id, uuid,operation_id) VALUES(1,'4d28f6f6-eeb3-4f79-848a-5926e40fba8c',1);

INSERT INTO time_activity(id,uuid,activity_id,collect_id,start_date,final_date,timed,collected_amount)
VALUES(1,'a456599f-102e-4b15-9978-c0731e2c451b',1,1,
123344,144444,100,0);

INSERT INTO sync(id,uuid,
created,
operation_id) values (1,'1be9a059-f92c-46b2-a269-4bf0bac79e99','2015-04-14 02:07:48',1);
