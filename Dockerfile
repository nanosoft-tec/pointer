FROM openjdk:12-alpine

COPY target/pointer.jar /usr/nanosoft/app.jar

ENV JAVA_OPTS="-XX:+UseStringDeduplication -XX:+UseParallelGC -Djava.security.egd=file:/dev/./urandom -Dfile.encoding=UTF-8"
ENV MONGODB_HOST=""
ENV MONGODB_PORT=""
ENV MONGODB_USERNAME=""
ENV MONGODB_PASSWORD=""
ENV MONGODB_DATABASE=""
ENV SSL_PATH="classpath:keystore.p12"
ENV SSL_PASSWORD="password"
ENV SSL_ALIAS="nanosoft"
ENV GITHUB_CLIENT_ID=""
ENV GITHUB_CLIENT_SECRET=""

RUN apk upgrade --update && \
    apk add --no-cache curl && \
    rm -rf /tmp/* /var/cache/apk/*

EXPOSE 8443

ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS -jar /usr/nanosoft/app.jar --server.port=8443 --spring.data.mongodb.host=${MONGODB_HOST} --spring.data.mongodb.port=${MONGODB_PORT} --spring.data.mongodb.username=${MONGODB_USERNAME} --spring.data.mongodb.password=${MONGODB_PASSWORD} --spring.data.mongodb.database=${MONGODB_DATABASE} --server.ssl.key-store=${SSL_PATH} --server.ssl.key-store-password=${SSL_PASSWORD} --server.ssl.key-store-type=PKCS12 --server.ssl.key-alias=${SSL_ALIAS} --server.ssl.key-password=${SSL_PASSWORD} --spring.security.oauth2.client.registration.github.client-id=${GITHUB_CLIENT_ID} --spring.security.oauth2.client.registration.github.client-secret=${GITHUB_CLIENT_SECRET} --management.endpoints.web.exposure.include=beans,health,env,info,loggers,heapdump,threaddump,metrics,prometheus --management.metrics.tags.application=pointer --management.health.probes.enabled=true"]
