FROM registry.cn-shanghai.aliyuncs.com/c7n/javabase:0.9.0
RUN mkdir /apps
RUN mkdir /apps/tmp
RUN mkdir /apps/files
WORKDIR /apps
COPY target/fregata-single.jar /apps
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/apps/fregata-single.jar"]