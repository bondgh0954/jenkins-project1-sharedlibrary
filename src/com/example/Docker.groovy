#!/user/bin/env groovy

package com.example

class Docker implements Serializable {
    def script
    Docker(script){
        this.script =script
    }

    def buildJar(){
        script.echo 'building application jar file'
        script.sh 'mvn package'
    }

    def buildImage(String imageName){
        script.echo "building docker image from the application"
        script.sh "docker build -t $imageName ."
    }

    def dockerLogin(){
        script.echo 'logging to docker private repository'
        script.withCredentials([script.usernamePassword(credentialsId:'dockerhub-credentials',usernameVariable:'USERNAME',passwordVariable:'PASSWORD')]){
            script.sh "echo '${script.PASSWORD}' |docker login -u '${script.USERNAME}' --password-stdin"
        }
    }
    def dockerPush(String imageName){
        script.echo 'pushing image into the private repository'
        script.sh "docker push $imageName"
    }
}