FROM gradle:8.5-jdk21 AS docs-build

WORKDIR /home/gradle/project
COPY --chown=gradle:gradle . .
RUN chown gradle:gradle /home/gradle/project
USER gradle
RUN ./gradlew --no-daemon dokkaJavadoc

FROM nginx:alpine

COPY --from=docs-build /home/gradle/project/build/dokka/javadoc/ /usr/share/nginx/html/
EXPOSE 80
