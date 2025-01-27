plugins {
    application
    `jvm-test-suite`
}

application {
    mainClass.set("hal.rdfsearch.RDFSearch")
}

testing {
    suites {
        val test by getting(JvmTestSuite::class) {
            useJUnitJupiter()
        }
    }
}

java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}

val jenaVersion = "5.3.0"
val luceneVersion = "10.1.0"

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.slf4j:slf4j-api:2.0.16")
    implementation("ch.qos.logback:logback-classic:1.5.16")
    implementation("ch.qos.logback:logback-core:1.5.16")

    implementation("org.apache.jena:apache-jena-libs:$jenaVersion") {
        exclude(module = "slf4j-log4j12")
        exclude(module = "log4j")
    }

    implementation("org.apache.lucene:lucene-core:$luceneVersion")
    implementation("org.apache.lucene:lucene-analysis-common:$luceneVersion")
    implementation("org.apache.lucene:lucene-queryparser:$luceneVersion")
}
