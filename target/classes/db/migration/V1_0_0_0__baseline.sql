CREATE TABLE `user` (                    
           `id` bigint(20) NOT NULL AUTO_INCREMENT,         
           `version` bigint(20) NOT NULL,                   
           `created_date` datetime DEFAULT NULL,         
           `created_by` bigint(20) DEFAULT NULL,        
           `last_modified_date` datetime DEFAULT NULL,   
           `last_modified_by` bigint(20) DEFAULT NULL,     
           `status` varchar(100) NOT NULL, 
           `first_name` varchar(255) DEFAULT NULL,
           `last_name` varchar(255) DEFAULT NULL,
            PRIMARY KEY (`id`)
   ) ENGINE=InnoDB DEFAULT CHARSET=utf8; 
 