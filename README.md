# Lazy Library [![Release](https://repo.srnyx.com/api/badge/latest/releases/xyz/srnyx/lazy-library?color=006d82&name=Release)](https://repo.srnyx.com/#/releases/xyz/srnyx/lazy-library) [![Snapshot](https://repo.srnyx.com/api/badge/latest/snapshots/xyz/srnyx/lazy-library?color=006d82&name=Snapshot)](https://repo.srnyx.com/#/snapshots/xyz/srnyx/lazy-library)

A Discord bot library for srnyx's JDA Discord bots ([Cobalt](https://github.com/Venox-Network/cobalt), [srnyx's Bot](https://github.com/srnyx/srnyx-bot), [Creator Laser Tag](https://github.com/Venox-Network/laser-tag-bot), etc...)

### Wiki / Javadocs

- **Wiki:** [github.com/srnyx/lazy-library/wiki](https://github.com/srnyx/lazy-library/wiki)
- **Javadocs:** [repo.srnyx.com/javadoc/releases/xyz/srnyx/lazy-library/latest](https://repo.srnyx.com/javadoc/releases/xyz/srnyx/lazy-library/latest)

## Importing

You can import the library using [Reposilite](https://repo.srnyx.com/#/releases/xyz/srnyx/lazy-library). Make sure to replace `VERSION` with the version you want. You **MUST** use `implementation`.

- **Gradle Kotlin** (`build.gradle.kts`)**:**
```kotlin
// Required plugins
plugins { 
  java
  id("com.gradleup.shadow") version "8.3.9" // https://github.com/GradleUp/shadow/releases/latest
}
// Reposilite repository
repositories { 
  maven("https://repo.srnyx.com/releases/")
}
// Lazy Library dependency declaration
dependencies {
  implementation("xyz.srnyx:lazy-library:VERSION")
}
```
- **Gradle Groovy** (`build.gradle`)**:**
```groovy
// Required plugins
plugins {
  id 'java'
  id 'com.gradleup.shadow' version '8.3.9' // https://github.com/GradleUp/shadow/releases/latest
}
// Reposilite repository
repositories {
  maven { url = 'https://repo.srnyx.com/releases/' }
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
    * Reposilite repository
  ```xml
   <repositories>
        <repository>
            <id>srnyx</id>
            <url>https://repo.srnyx.com/releases/</url>
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
