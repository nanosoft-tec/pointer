# POINTER

# Application.properties
`
spring.data.mongodb.host=
spring.data.mongodb.username=
spring.data.mongodb.password=
spring.data.mongodb.port=
spring.data.mongodb.database=

server.port=8443

server.ssl.key-store=classpath:keystore.p12
server.ssl.key-store-password=password
server.ssl.key-store-type=PKCS12
server.ssl.key-alias=tomcat
server.ssl.key-password=password
`
# 1. Generate a self-signed SSL certificate
First of all, we need to generate a pair of cryptographic keys, use them to produce an SSL certificate and store it in a keystore. The keytool documentation defines a keystore as a database of "cryptographic keys, X.509 certificate chains, and trusted certificates".

The two most common formats used for keystores are JKS, a proprietary format specific for Java, and PKCS12, an industry-standard format. JKS used to be the default choice, but now Oracle recommends to adopt the PKCS12 format. We're going to see how to use both.

**Generate an SSL certificate in a keystore**
>keytool -genkeypair -alias tomcat -keyalg RSA -keysize 2048 -storetype PKCS12 -keystore keystore.p12 -validity 3650 -storepass password

- genkeypair: generates a key pair;
- alias: the alias name for the item we are generating;
- keyalg: the cryptographic algorithm to generate the key pair;
- keysize: the size of the key. We have used 2048 bits, but 4096 would be a better choice for production;
- storetype: the type of keystore;
- keystore: the name of the keystore;
- validity: validity number of days;
- storepass: a password for the keystore.


# 2. Enable HTTPS in Spring Boot
Whether our keystore contains a self-signed certificate or one issued by a trusted Certificate Authority, we can now set up Spring Boot to accept requests over HTTPS instead of HTTP by using that certificate.

The first thing to do is placing the keystore file inside the Spring Boot project. We want to put it in the resources folder or the root folder.

Then, we configure the server to use our brand new keystore and enable https.

server.ssl.key-store=classpath:keystore.p12
server.ssl.key-store-password=password
server.ssl.key-store-type=PKCS12
server.ssl.key-alias=tomcat
server.ssl.key-password=password

# 3. Distribute the SSL certificate to clients
When using a self-signed SSL certificate, our browser won't trust our application and will warn the user that it's not secure. And that'll be the same with any other client.

It's possible to make a client trust our application by providing it with our certificate.

**Extract an SSL certificate from a keystore**

We have stored our certificate inside a keystore, so we need to extract it. Again, keytool supports us very well:

>keytool -export -keystore keystore.jks -alias tomcat -file myCertificate.crt




