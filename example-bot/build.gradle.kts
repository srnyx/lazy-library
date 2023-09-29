application.mainClass.set("xyz.srnyx.lazyexample.LazyExample")
dependencies{
    implementation(project(":LazyLibrary", "shadow")) // Lazy Library
    implementation("net.dv8tion", "JDA", "5.0.0-alpha.21") // JDA
}
