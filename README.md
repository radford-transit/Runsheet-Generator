# Runsheet Generator :oncoming_bus:
Generates a Radford Transit runsheet, in the XSLS file format, from a CSV file exported by WhenToWork scheduling software.

## Required software
- [Java 15 SDK](https://www.oracle.com/java/technologies/javase-jdk15-downloads.html)
- [Gradle 6.6.1](https://github.com/gradle/gradle/releases/tag/v6.6.1)

## Build
If you have Gradle installed, run `gradle build`. To wrap the build into a Windows executable, run `gradle createExe`

Otherwise,
- On **Linux** or **macOS**, in the project's root directory, run `./gradlew build`
- On **Windows**, in the project's root directory, run `gradlew.bat build`. To wrap the build into a Windows executable, run `gradlew.bat createExe`.

## Run
If you have Gradle installed, run `gradle run`

Otherwise,
- On **Linux** or **macOS**, in the project's root directory, run `./gradlew run`
- On **Windows**, in the project's root directory, run `gradlew.bat run`

## Style requirement
Runsheet Generator's source code complies with [Google's Java Style standard](https://google.github.io/styleguide/javaguide.html).

If you have Gradle installed, to easily format the source code, run `gradle spotlessApply`. To verify that all `*.java` files are formatted properly, run `gradle spotlessJavaCheck`.

Otherwise,
- On **Linux** or **macOS**, in the project's root directory, run `./gradlew goJF`. To verify that all `*.java` files are formatted properly, run `./gradlew spotlessJavaCheck`.
- On **Windows**, in the project's root directory, run `gradlew.bat goJF`. To verify that all `*.java` files are formatted properly, run `gradlew.bat spotlessJavaCheck`.

## Generate Javadoc
If you have Gradle installed, run `gradle makeJavadocs`

Otherwise,
- On **Linux** or **macOS**, in the project's root directory, run `./gradlew makeJavadocs`
- On **Windows**, in the project's root directory, run `gradlew.bat  makeJavadocs`

## Dependencies
#### Automatically downloaded with Gradle
- [ZB Utils 1.1](https://github.com/zbeach/zb-utils)
- [Swing Extensions 1.0](https://github.com/zbeach/swing-extensions)
- [Apache Commons CSV 1.8](https://commons.apache.org/proper/commons-csv/)
- [Apache POI 4.1.2](https://poi.apache.org)

## Gradle plugins (automatically downloaded) and dependencies
- [JitPack](https://jitpack.io) adds the ability to use Git repos as dependencies.
- [Shadow](https://github.com/johnrengelman/shadow) combines dependency classes and resources with a project’s into a single output JAR.
- [gradle-launch4j](https://github.com/TheBoegl/gradle-launch4j) uses [Launch4J](http://launch4j.sourceforge.net) to wrap JARs into Windows executables.
- [Spotless plugin for Gradle](https://github.com/diffplug/spotless/tree/main/plugin-gradle) uses [google-java-format](https://github.com/google/google-java-format) to reformat Java source code to comply with Google Java Style.
