CREATE TABLE pii_user_details(
   user_id   VARCHAR(250)          NOT NULL,
   name VARCHAR (20)     	   NOT NULL,
   registration_time  INT          NOT NULL,
   mobile_number  VARCHAR (11) 	   NOT NULL,
   email_id   VARCHAR (50) 	   NOT NULL, 
   aadhar_number  CHAR (12) 	   NOT NULL,  
   pan_number   CHAR (10) 	   NOT NULL,        
   PRIMARY KEY (user_id),
   CONSTRAINT  mobile_number UNIQUE (mobile_number),
   CONSTRAINT  email_id UNIQUE (email_id),
   CONSTRAINT  aadhar_number UNIQUE (aadhar_number),
   CONSTRAINT  pan_number UNIQUE (pan_number)
);

