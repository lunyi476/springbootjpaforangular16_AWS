# angular16_springboot_jpa_AWS

This project embedded angular16 app into springboot and we can deploy it into AWS Elastic Beanstalk.
It is like simplified Microservice and REST application.

For easy way to embedded angular into springboot. there are some points:  

(0) In order to use gulpfile.js as ES Module (import  instead of require), change package to type: module in root directory,
not chnage angular project package.json (still use reqire of CommonJS module).  


(1) On Visual Code (suppose you created angular app in Visual Code), build angular application as product into dist folder.
    Or you can build it from springboot application folder after copying.
    
(2) Add gulpfile into springboot root folder, and copy angular app root folder into springboot project src/maim/ folder.
    also, create static folder in src\main\resources of springboot project.
    
(3) Add some plugin and dependencyies in POM.xml, such as exec: gulp, npm, node.  Check my POM.xml file.

(4) In spring java com.springbootjpaangular2.controllers.IndexControl.java class (check this file), 
    pointing "/" to static/index.html. This will launch angular app.
    Spring app port points to 5000 (or others, 5000 is default in AWS. So pointing to 5000). Angular does not 
    has its own port (such as: 4200) because it is embedded in springboot.
    
(5) In package.json, you may need to add (built files direct under dist folder, this affects images search path):   
    "build-prod": "ng build --prod --deploy-url /" ( search "build": "ng build" and add it after this)

(6) Run maven command:  clean install -X  ( or manually clean and Run:  install -X)
    auto-created folder docker, root, node_modules

(7) After created jar file, run it at local command console:   java -jar  yourjarfile, and open http://localhost:5000/
    If you see error, just check/compare my application files or google it. It should work. 

(8) Finally, deploy it into AWS Elastic Beanstalk (create free account (it may be not free in 2023) and go to "Elastic Beanstalk Console", create new environment
    and add new instance by uploading jar file into it. AWS will take care of port and url by giving you a link)
