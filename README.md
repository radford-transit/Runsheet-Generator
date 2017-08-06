# Runsheet Generator :oncoming_bus:
Generates a Radford Transit runsheet, in the XSLS file format, from a CSV file exported by WhenToWork scheduling software.

## Required software
- [Java 8 SDK](http://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html)
- [Gradle](https://gradle.org/install/) is recommened but not required

## Build
If you have Gradle installed, run `gradle build`. To wrap the build into a Windows executable, run `gradle createExe`.

Otherwise,
- On **Linux** or **macOS**, in the project's root directory, run `./gradlew build`
- On **Windows**, in the project's root directory, run `gradlew.bat build`. To wrap the build into a Windows executable, run `gradlew.bat createExe`.

## Run
If you have Gradle installed, run `gradle run`

Otherwise,
- On **Linux** or **macOS**, in the project's root directory, run `./gradlew run`
- On **Windows**, in the project's root directory, run `gradlew.bat run`

## Dependencies
#### Included as JARs
- [ZBUtils](https://github.com/zbeach/ZBUtils)
- [SwingExtensions](https://github.com/zbeach/SwingExtensions)

#### Automatically downloaded with Gradle
- [Apache Commons CSV 1.4](https://commons.apache.org/proper/commons-csv/)
- [Apache POI 3.15](https://poi.apache.org)

## Gradle plugins (automatically downloaded)
- [Shadow](https://github.com/johnrengelman/shadow) combines dependency classes and resources with a project’s into a single output JAR.
- [gradle-launch4j](https://github.com/TheBoegl/gradle-launch4j) uses [Launch4J](http://launch4j.sourceforge.net) to wrap JARs into Windows executables.
