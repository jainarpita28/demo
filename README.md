Requirements:
Backend
 - Created 2 element 
    - 1st holds the information of files uploaded
    - 2nd holds the elemnts uploaded by txt file 
 - implemented ORM to store data
 - implemented asybc thread to avoid delay in response returned to user for file upload
    - parsing of the file happens in background
 - Accept only txt file, CSV not supported
 - Implemented Trnasactional Insertion of al the records at once sop that even if one record fails whole complete file is aborted

Frontend 
 - use https://www.npmjs.com/package/http-server 
    - having said npm is installed on system
    - install `http-server` module 
    - run `http-server /path/to/frontend/` 
    - url should look like http://localhost:8080/index.html
 - or else use live server extension in visual studio
    - install live server extension
    - open front end folder in visual studio
    - on index.html right click and click open wiht live serve 
    - this should start a live server
    - url should look like http://localhost:5500/elements

File to import
 - Sample File Commiited with the name element.txt 
 - placed next to README.md

Requirement
language/frameworks: java 8+, spring boot, maven, db - any

Description:

Implement REST interface that enables user to upload comma-separated text file.

Scenario details:

the REST interface should implement 4 endpoints

Endpoint that uploads a file
1. a user should be able to upload comma separated text file
2. file should have the below header:
PRIMARY_KEY,PROPERTY_NAME,DESCRIPTION,TIMESTAMP
3. other lines are comma separated properties

Endpoint that retrieves particular file entry by primary key
4. the user should be able to retrieve particular entry by primary key

Endpoint that removes particular file entry by primary key
5. the user should be able to remove particular entry by primary key

6. it should not be possible to upload corrupted file

7. simple UI interface is a plus

Project should be developed according to TDD.

The candidate can use any materials during development. 

The development process should be test driven. 








