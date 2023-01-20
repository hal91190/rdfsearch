plugins {
    application
}

application {
    mainClass.set("hal.rdfsearch.RDFSearch")
}

java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}

val jenaVersion = "3.1.1"
val luceneVersion = "6.4.0"

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.slf4j:slf4j-api:2.0.6")
    implementation("ch.qos.logback:logback-classic:1.4.5")
    implementation("ch.qos.logback:logback-core:1.4.5")

    testImplementation("junit:junit:4.12")

    implementation("org.apache.jena:apache-jena-libs:$jenaVersion") {
        exclude(module = "slf4j-log4j12")
        exclude(module = "log4j")
    }

    implementation("org.apache.lucene:lucene-core:$luceneVersion")
    implementation("org.apache.lucene:lucene-analyzers-common:$luceneVersion")
    implementation("org.apache.lucene:lucene-queryparser:$luceneVersion")
}
