# overlap2d for development experimental

In the moment script support only linux,

It is basically will pull the latest update from github,

<ol>
  <li>https://github.com/kotcrab/VisEditor.git</li>
  <li>https://github.com/UnderwaterApps/overlap2d.git</li>
  <li>https://github.com/azakhary/overlap2d-runtime-libgdx.git</li>
  <li>https://github.com/libgdx/ashley.git</li>
  <li>https://github.com/EsotericSoftware/spine-runtimes.git</li>
</ol>

sh ./patch-master.sh <br/>
mvn clean install && java -jar target/overlap2d-jar-with-dependencies.jar
