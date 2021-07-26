FROM openjdk:11
WORKDIR /
ADD ./ ./
CMD java -jar restTest-1.jar