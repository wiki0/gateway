FROM registry.ap-southeast-1.aliyuncs.com/wiki0/java:8
VOLUME /tmp
ADD ./target/docker-weixin-0.0.1-SNAPSHOT.jar app.jar
#RUN bash -c 'touch /app.jar'ls
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/app.jar"]

EXPOSE 8080


#FROM  registry.ap-southeast-1.aliyuncs.com/wiki0/tomcat8:latest
## add war in webapps
#ADD ./target/docker-weixin-0.0.1-SNAPSHOT.war /tmp
#
#RUN cd /usr/local/tomcat8/webapps/ \
#        #拷贝编译结果到指定目录
#        && rm -rf ./* \
#        && mv /tmp/*.war ./ROOT.war
#
#VOLUME /tmp
## container listener port
#EXPOSE 8080
#
## startup wev application services by self
#CMD ["catalina.sh", "run"]

#FROM maven:3.3.3
#
#ENV CATALINA_HOME /usr/local/tomcat
#ENV PATH $CATALINA_HOME/bin:$PATH
#RUN mkdir -p "$CATALINA_HOME"
#WORKDIR $CATALINA_HOME
#
#ENV TOMCAT_VERSION 8.0.47
#ENV TOMCAT_TGZ_URL https://www.apache.org/dist/tomcat/tomcat-8/v$TOMCAT_VERSION/bin/apache-tomcat-$TOMCAT_VERSION.tar.gz
#
#RUN set -x \
#        && curl -fSL "$TOMCAT_TGZ_URL" -o tomcat.tar.gz \
#        && tar -xf tomcat.tar.gz --strip-components=1 \
#        && rm bin/*.bat \
#        && rm -rf webapps/* \
#        && rm tomcat.tar.gz*
#
#ADD pom.xml /tmp/build/
#
#ADD src /tmp/build/src
#
#RUN cd /tmp/build && mvn -q package \
#        #拷贝编译结果到指定目录
#        && rm -rf $CATALINA_HOME/webapps/* \
#        && mv target/*.war $CATALINA_HOME/webapps/ROOT.war \
#        #清理编译痕迹
#        && cd / && rm -rf /tmp/build
#
#EXPOSE 8080
#CMD ["catalina.sh","run"]