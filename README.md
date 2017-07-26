# Runsheet Generator :oncoming_bus:
Generates a Radford Transit runsheet, in the XSLS file format, from a CSV file exported by WhenToWork scheduling software.

## Build
If you have Gradle installed, run `gradle build`

Otherwise,
- On **Linux** or **macOS**, in the project's root directory, run `./gradlew build`
- On **Windows**, in the project's root directory, run `gradlew.bat build`

## Run
If you have Gradle installed, run `gradle run`

Otherwise,
- On **Unix-like OSs**, in the project's root directory, run `./gradlew run`
- On **Windows**, in the project's root directory, run `gradlew.bat run`

## Dependencies
#### Included as JARs
- [ZBUtils](https://github.com/zbeach/ZBUtils)
- [SwingExtensions](https://github.com/zbeach/SwingExtensions)

#### Downloaded with Gradle
- [Apache Commons CSV 1.4](https://commons.apache.org/proper/commons-csv/)
- [Apache POI 3.15](https://poi.apache.org)
