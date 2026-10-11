plugins {
    id("java")
    id("application")
    id("me.champeau.jmh") version "0.7.2"
}

group = "org.example"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

// Newton's Tree.java contains box-drawing characters in its source,
// so the compiler must read files as UTF-8.
tasks.withType<JavaCompile>().configureEach {
    options.encoding = "UTF-8"
}

application {
    // package + class name of Tree.java (see note below)
    mainClass.set("tree.Tree")
    applicationDefaultJvmArgs = listOf(
        "-Dfile.encoding=UTF-8",
        "-Dsun.stdout.encoding=UTF-8",
        "-Dstdout.encoding=UTF-8"
    )
}

dependencies {
    testImplementation(platform("org.junit:junit-bom:6.0.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}

tasks.test {
    useJUnitPlatform()
}

jmh {
    warmupIterations.set(2)
    iterations.set(4)
    fork.set(1)
    resultFormat.set("CSV")
    resultsFile.set(project.layout.buildDirectory.file("results/jmh/results.csv"))
}