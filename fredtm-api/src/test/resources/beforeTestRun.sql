INSERT INTO account(id,email,name,password_hash) VALUES('A','leo.zapparoli@gmail.com','leo','a665a45920422f9d417e4867efdc4fb8a04a1f3fff1fa07e998e86f7f7a27ae3');
INSERT INTO account(id,email,name,password_hash) VALUES('B','beth.zapparoli@gmail.com','Beth','a665a45920422f9d417e4867efdc4fb8a04a1f3fff1fa07e998e86f7f7a27ae3');
INSERT INTO account(id,email,name,password_hash) VALUES('C','ze.zapparoli@gmail.com','Zé','a665a45920422f9d417e4867efdc4fb8a04a1f3fff1fa07e998e86f7f7a27ae3');
INSERT INTO account(id,email,name,password_hash) VALUES('D','henrique.zapparoli@gmail.com','Henrique','a665a45920422f9d417e4867efdc4fb8a04a1f3fff1fa07e998e86f7f7a27ae3');
INSERT INTO account(id,email,name,password_hash) VALUES('E','rafael.zapparoli@gmail.com','Rafael','a665a45920422f9d417e4867efdc4fb8a04a1f3fff1fa07e998e86f7f7a27ae3');
INSERT INTO account(id,email,name,password_hash) VALUES('F','pedro.zapparoli@gmail.com','Pedro','a665a45920422f9d417e4867efdc4fb8a04a1f3fff1fa07e998e86f7f7a27ae3');
INSERT INTO account(id,email,name,password_hash) VALUES('G','hana.zapparoli@gmail.com','Hana','a665a45920422f9d417e4867efdc4fb8a04a1f3fff1fa07e998e86f7f7a27ae3');


INSERT INTO operation(id,name,company,technical_characteristics,account_id,modified)
VALUES('fd806586-35d4-4e8c-a119-935d4bea0773','Op dd teste','Farm SA','Operação de coleta de valores','A','2015-05-12 15:20:02.071');

INSERT INTO activity(id,title,description,activity_type,quantitative,item_name,operation_id)
VALUES('eC211186-35d4-4e8c-a1d9-935d4beaaa73','A','Descripion of A','IMPRODUCTIVE','0','','fd806586-35d4-4e8c-a119-935d4bea0773');
INSERT INTO activity(id,title,description,activity_type,quantitative,item_name,operation_id)
VALUES('935d4beaaa73-4e8c-4e8c-a1d9-eC211186','B','Descripion of B','PRODUCTIVE','1','Trees','fd806586-35d4-4e8c-a119-935d4bea0773');

INSERT INTO collect(id, operation_id) VALUES('01f0997f-7330-4dd8-b186-87b6bc344f90','fd806586-35d4-4e8c-a119-935d4bea0773');

INSERT INTO time_activity(id,activity_id,collect_id,start_date,final_date,timed,collected_amount)
VALUES('eeaaca35-728b-477c-bb8c-edc76767d457','eC211186-35d4-4e8c-a1d9-935d4beaaa73','01f0997f-7330-4dd8-b186-87b6bc344f90',
123344,144444,100,0);
