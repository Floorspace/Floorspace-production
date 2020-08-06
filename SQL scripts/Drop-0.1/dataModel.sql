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


CREATE TABLE visible_user_details(
   user_id   VARCHAR(250)          NOT NULL,
   caste   VARCHAR(30)          NOT NULL,
   religion   VARCHAR(30)          NOT NULL,
   gender   VARCHAR(30)          NOT NULL,
   age      INT                    NOT NULL,
   known_languages   VARCHAR(250)          NOT NULL,
   native_state   VARCHAR(50)          NOT NULL,
   PRIMARY KEY (user_id),
   FOREIGN KEY (user_id) REFERENCES pii_user_details(user_id)
);

CREATE TABLE common_ad_info(
   pool_id VARCHAR(250) NOT NULL,
   lender_id   VARCHAR(250) NOT NULL,
   property_type VARCHAR (50) NOT NULL,
   construction_type VARCHAR (50) NOT NULL,
   city VARCHAR (50) NOT NULL,
   latitude DOUBLE NOT NULL, 
   longitude DOUBLE NOT NULL, 
   address TEXT NOT NULL,
   description TEXT NOT NULL,
   property_cost  INT NOT NULL,
   num_of_pool_members  INT DEFAULT 1 NOT NULL,
   PRIMARY KEY (pool_id)
);


CREATE TABLE house_construction_ad_info(
   pool_id VARCHAR(250) NOT NULL,
   existing_num_of_floors INT NOT NULL, 
   floorspace_area DOUBLE NOT NULL, 
   floorspace_length DOUBLE NOT NULL, 
   floorspace_breadth DOUBLE NOT NULL, 
   num_Of_allowed_floors INT NOT NULL,
   PRIMARY KEY (pool_id),
   FOREIGN KEY (pool_id) REFERENCES common_ad_info(pool_id)
);

CREATE TABLE standalone_building_ad_info(
   pool_id VARCHAR(250) NOT NULL,
   num_of_building_floors INT NOT NULL, 
   building_area DOUBLE NOT NULL, 
   building_length DOUBLE NOT NULL, 
   building_breadth DOUBLE NOT NULL, 
   num_Of_allowed_building_floors INT NOT NULL,
   PRIMARY KEY (pool_id),
   FOREIGN KEY (pool_id) REFERENCES common_ad_info(pool_id)
);

CREATE TABLE apartment_ad_info(
   pool_id VARCHAR(250) NOT NULL,
   num_of_apartment_floors INT NOT NULL, 
   num_of_flats_per_floor INT NOT NULL,
   apartment_area DOUBLE NOT NULL, 
   apartment_length DOUBLE NOT NULL, 
   apartment_breadth DOUBLE NOT NULL, 
   num_Of_allowed_apartment_floors INT NOT NULL,
   PRIMARY KEY (pool_id),
   FOREIGN KEY (pool_id) REFERENCES common_ad_info(pool_id)
);


CREATE TABLE lender_buyer_pool_mapping(
   pool_id VARCHAR(250) NOT NULL,
   lender_id VARCHAR(250) NOT NULL,
   buyer_id VARCHAR(250) NOT NULL,
   requested TINYINT(1) NOT NULL,
   accepted TINYINT(1) NOT NULL,
   PRIMARY KEY (pool_id, lender_id, buyer_id)
);






