# Java based website
This website is meant to be something that I use to teach myself full stack technologies. This uses a couple of technologies

* HTML / CSS / Javascript for frontend.
* Java http server for backend.
* AWS DynamoDB for database.
* AWS S3 for storing static website resources like images.
* Maven for building.

# Build
## Commands
Use the following commands to buid:
```
mvn clean compile assembly:single
```

To start the server, we will execute the generated jar
```
java -jar target/infosite-0.0.1-jar-with-dependencies.jar
```

# References
## Maven
## HTML
* https://www.w3schools.com/tags/att_script_src.asp
* https://www.w3schools.com/tags/tag_script.asp
* https://stackoverflow.com/questions/247483/http-get-request-in-javascript

## AWS
## Java
* https://www.mkyong.com/logging/log4j-hello-world-example/

## Cryptography
* https://stackoverflow.com/questions/39355241/compute-hmac-sha512-with-secret-key-in-java
* https://github.com/Caligatio/jsSHA