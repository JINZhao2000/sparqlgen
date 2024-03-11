# SPARQL Generator
## About this project
This project aims to create a SPARQL generator. It can analyze an RDF graph, and generate a SPARQL. It is seperated in 4 following parts:
- SELECT   
  The select query can help us to browse the data. This part requires an RDF file, and optional a configuration file. The format of configuration is as `conf.xml` in this project.
- CONSTRUCT   
  The construct query can help us to construct an RDF graph. This part requires an RDF file.
- ASK     
  The ask query can check if a triple pattern exists. This part requires an RDF file, and then, you can choose a triple to query.
- DESCRIBE    
  The describe query can give an RDF graph of corresponding uri. This part requires a URI.
## Usage
You can compile a jar file in command line as following:
```bash
./gradlew bootJar
```
And then the file will be generated in `./build/libs/`
Check your java version is 17, after that run:
```bash
java -jar sparqlg-1.0.jar
```
The service will be available at [localhost:8080](http://localhost:8080)