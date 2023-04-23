# Lazy Library

A Discord bot library for srnyx's JDA Discord bots ([Cobalt](https://github.com/Venox-Network/cobalt), [srnyx's Bot](https://github.com/srnyx/srnyx-bot), [Creator Laser Tag](https://github.com/Venox-Network/laser-tag-bot), etc...)

### Wiki / Javadocs

- **Wiki:** [github.com/srnyx/lazy-library/wiki](https://github.com/srnyx/lazy-library/wiki)
- **Javadocs:** [javadoc.jitpack.io/xyz/srnyx/lazy-library/latest/javadoc/index.html](https://javadoc.jitpack.io/xyz/srnyx/lazy-library/latest/javadoc/index.html)

## Importing

You can import the library using [Jitpack](https://jitpack.io/#xyz.srnyx/lazy-library). Make sure to replace `VERSION` with the version you want. You **MUST** use `implementation`.

- **Gradle Kotlin** (`build.gradle.kts`)**:**
```kotlin
// Required plugins
plugins {
  java
  id("com.github.johnrengelman.shadow") version "7.1.2" // https://github.com/johnrengelman/shadow/releases/latest
}
// Jitpack repository
repositories {
  maven("https://jitpack.io")
}
// Lazy Library dependency declaration
dependencies {
    implementation("xyz.srnyx", "lazy-library", "VERSION")
}
```
- **Gradle Groovy** (`build.gradle`)**:**
```groovy
// Required plugins
plugins {
  id 'java'
  id 'com.github.johnrengelman.shadow' version '7.1.2' // https://github.com/johnrengelman/shadow/releases/latest
}
// Jitpack repository
repositories {
    maven { url = 'https://jitpack.io' }
}
// Lazy Library dependency declaration
dependencies {
    implementation 'xyz.srnyx:lazy-library:VERSION'
}
```
* **Maven** (`pom.xml`)**:**
    * Shade plugin
  ```xml
  <build>
    <plugins>
      <plugin>
        <groupId>org.apache.maven.plugins</groupId>
        <artifactId>maven-shade-plugin</artifactId>
        <version>3.4.1</version>
        <executions>
          <execution>
            <phase>package</phase>
            <goals>
              <goal>shade</goal>
            </goals>
          </execution>
        </executions>
        <!-- Exclude META-INF to avoid conflicts (not sure if this is needed) -->
        <configuration>
          <filters>
            <filter>
              <artifact>xyz.srnyx:*</artifact>
              <excludes>
                <exclude>META-INF/*.MF</exclude>
              </excludes>
            </filter>
          </filters>
        </configuration>
      </plugin>
    </plugins>
  </build>
  ```
    * Jitpack repository
  ```xml
   <repositories>
        <repository>
            <id>jitpack</id>
            <url>https://jitpack.io</url>
        </repository>
    </repositories>
  ```
    * Lazy Library dependency declaration
  ```xml
    <dependencies>
        <dependency>
            <groupId>xyz.srnyx</groupId>
            <artifactId>lazy-library</artifactId>
            <version>VERSION</version>
        </dependency>
    </dependencies>
  ```
