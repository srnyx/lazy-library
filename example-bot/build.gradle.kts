application.mainClass.set("xyz.srnyx.lazyexample.LazyExample")
dependencies.implementation(dependencies.project(path = ":LazyLibrary", configuration = "shadow"))
