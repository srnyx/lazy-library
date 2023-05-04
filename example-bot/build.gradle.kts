application.mainClass.set("xyz.srnyx.lazyexample.LazyExample")

dependencies {
    implementation(project(path = ":LazyLibrary", configuration = "shadow"))
}
