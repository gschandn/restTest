# Bench restTest for Gurpreet Chandna

## Technologies
- Java 11 using Spring Boot and Gradle



## Instructions to Build and Run if Java 11 & Gradle are installed (Recommended)
- If Java 11 is not installed please download and install from https://www.oracle.com/ca-en/java/technologies/javase-jdk11-downloads.html
- If Git is not installed please download and install from https://git-scm.com/downloads
- If Gradle is not installed please download and install from https://gradle.org/install/
In a terminal run the following commands:
```
git clone https://github.com/gschandn/restTest.git && cd restTest
./gradlew build
java -jar build/libs/restTest-1.jar 
```

## Instructions to run precompiled jar included in repo for convenience, if Java 11 is installed
- If Java 11 is not installed please download and install from https://www.oracle.com/ca-en/java/technologies/javase-jdk11-downloads.html
- If Git is not installed please download and install from https://git-scm.com/downloads

```
git clone https://github.com/gschandn/restTest.git && cd restTest
java -jar restTest-1.jar
```

## Instructions using Docker to run precompiled jar included in repo for convenience, if Docker is installed
- If Docker is not installed please download and install from https://www.docker.com/products/docker-desktop
- If Git is not installed please download and install from https://git-scm.com/downloads

In a terminal run the following commands:
```
git clone https://github.com/gschandn/restTest.git && cd restTest
docker build -t resttest .
docker run resttest
```

## Example Output  
2013-12-12 -227.35  
2013-12-13 -1456.93  
2013-12-15 -1462.32  
2013-12-16 -6037.85  
2013-12-17 4648.43  
2013-12-18 2807.14  
2013-12-19 22560.45  
2013-12-20 18505.85  
2013-12-21 18487.87  
2013-12-22 18377.16    

## Future Implementations
- Check if the # of transactions processed actually matches the # given (throw an exception if not)
- Do more in depth error handling; retry relevant HTTP errors such as timeouts
- Implement multithreading for API fetches 
- Align the output to improve readability
