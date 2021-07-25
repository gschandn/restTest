FROM openjdk:11
WORKDIR /
ADD ./ ./
RUN ./gradlew build
CMD java -jar ./build/libs/restTest-1.jar