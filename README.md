# TestingAndCi

To tun it in jenkins:
* First you create a new item
* Then name it and create a feeestyle project
* Select git under "Source Code Managment"
* Then enter this githubRepo link https://github.com/JuliusJak/TestingAndCi.git in the Repository URL
* Specify to use the */test branch
* Then go to build step and add 2 commands
* ./mvnw compile
* ./mvnw package
* In post-build actions add Archive the artifacts
* Then target/*.jar
* Add it's done you can now run your project

To run with Docker
* First you create a new item
* Then name it and create a feeestyle project
* Select git under "Source Code Managment"
* Then enter this githubRepo link https://github.com/JuliusJak/TestingAndCi.git in the Repository URL
* Specify to use the */main branch
* Then go to build step and add 3 commands
* cd TestingAndCi
* docker build -t testing-and-ci:latest .
* docker run -d testing-and-ci:latest
* Add it's done you can now run your project

(if you get docker command not found you need to add docker to you PATH)
to do this go to manage jenkins -> nodes -> configure the node that's being used -> add enviormental varable
name it PATH and set teh value to dockers path, it should look something like this "/usr/local/bin:$PATH"
