    CREATE TABLE HashPassword (  
      userId INT AUTO_INCREMENT,  
      userName VARCHAR(128) NOT NULL,  
      password VARCHAR(128) NOT NULL,
      saltValue VARCHAR(128) NULL,
      PRIMARY KEY (userId)
      );  
      
     
     DROP TABLE HashPassword;
     
     select * from HashPassword;
     select * from users where name='';
     select * from HashPassword where userName='';
     delete from HashPassword where userName='';
     
     
     ALTER TABLE users CHANGE email emails; 
     
   	 INSERT INTO `users`   
     (`name`,  `password`  )  
     VALUES  ('phyoe',  '12345'  ); 
     
     