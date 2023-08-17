FROM openjdk:8-jdk-buster

WORKDIR /opt/deployments
COPY ./downtime-web/target/*.jar /opt/deployments/dt-api.jar
RUN unzip -q dt-api.jar && \
    rm -rf dt-api.jar
ENV JAVA_OPTS="-Xms1363148K -XX:MetaspaceSize=209715K -Xss699K -Xmx1363148K -XX:MaxMetaspaceSize=209715K"

ENV TZ=PRC
RUN ln -snf /usr/share/zoneinfo/$TZ /etc/localtime && echo $TZ > /etc/timezone

EXPOSE 8080
CMD java ${JAVA_OPTS} -cp /opt/deployments/. org.springframework.boot.loader.JarLauncher
