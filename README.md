# Overlap2d for Experiment  Development

In the moment script support only linux,

It is basically will pull the latest update from github,

1. https://github.com/kotcrab/VisEditor.git
2. https://github.com/azakhary/overlap2d.git
3. https://github.com/UnderwaterApps/overlap2d-runtime-libgdx.git
4. https://github.com/libgdx/ashley.git
5. https://github.com/EsotericSoftware/spine-runtimes.git

sh ./patch-master.sh
mvn clean install && java -jar target/overlap2d-jar-with-dependencies.jar
