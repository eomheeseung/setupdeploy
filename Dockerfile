## OpenJDK 11 이미지를 사용하여 애플리케이션을 실행
#FROM openjdk:11-jre-slim
#
## 애플리케이션 JAR 파일을 컨테이너 내에 복사
#COPY target/security-login-test-0.0.1-SNAPSHOT.jar app/application.jar
#
## 애플리케이션 실행
#ENTRYPOINT ["java", "-jar", "/app/your-application.jar"]
