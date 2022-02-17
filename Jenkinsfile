node {
    stage("Git Clone"){
        sh 'rm -rf *'
        git credentialsId: 'GIT_HUB_CREDENTIALS',
        url: 'https://github.com/goorm-k8s/sulmoon-api.git',
        branch: 'develop/all'
    }
    
    stage("api-gateway-svc Build") {
        
        dir('api-gateway-service'){
            sh 'pwd'
            sh """sed -i -e \'s/localhostgate/ab7844a5eb2164cbd82b034fb540a5a1-1653959158.ap-northeast-2.elb.amazonaws.com/g\' ./user-service/src/main/resources/application.yml"""
            sh """sed -i -e \'s/localhostdis/a4e018cbeca2e4a11bacdcd6cc8e1372-1380515803.ap-northeast-2.elb.amazonaws.com/g\' ./user-service/src/main/resources/application.yml"""
            sh 'cat ./user-service/src/main/resources/application.yml'
            sh 'sh ./gradlew clean build'
            sh 'docker version'
            sh 'ls -al'
            sh 'docker build -t api-gateway-svc .'
            sh 'docker image list'
            sh 'docker tag api-gateway-svc sulmoon/api-gateway-svc:$BUILD_NUMBER'
        }
        
    }
    stage("discovery-svc Build") {
        
        dir('discovery-service'){
            sh 'pwd'
            sh 'cat ./user-service/src/main/resources/application.yml'
            sh 'sh ./gradlew clean build'
            sh 'ls -al user-service/build/libs/'
            sh 'docker version'
            sh 'docker build -t discovery-svc .'
            sh 'docker image list'
            sh 'docker tag discovery-svc sulmoon/discovery-svc:$BUILD_NUMBER'
        }
        
    }
    stage("auth-svc Build") {
        
        dir('auth-service'){
            sh 'pwd'
            sh """sed -i -e \'s/localhostgate/ab7844a5eb2164cbd82b034fb540a5a1-1653959158.ap-northeast-2.elb.amazonaws.com/g\' ./user-service/src/main/resources/application.yml"""
            sh """sed -i -e \'s/localhostdis/a4e018cbeca2e4a11bacdcd6cc8e1372-1380515803.ap-northeast-2.elb.amazonaws.com/g\' ./user-service/src/main/resources/application.yml"""
            sh 'cat ./user-service/src/main/resources/application.yml'
            sh 'sh ./gradlew clean build'
            sh 'ls -al user-service/build/libs/'
            sh 'docker version'
            sh 'docker build -t auth-svc .'
            sh 'docker image list'
            sh 'docker tag auth-svc sulmoon/auth-svc:$BUILD_NUMBER'
        }
        
    }
    stage("survey-svc Build") {
        
        dir('survey-service'){
            sh 'pwd'
            sh """sed -i -e \'s/localhostgate/ab7844a5eb2164cbd82b034fb540a5a1-1653959158.ap-northeast-2.elb.amazonaws.com/g\' ./user-service/src/main/resources/application.yml"""
            sh """sed -i -e \'s/localhostdis/a4e018cbeca2e4a11bacdcd6cc8e1372-1380515803.ap-northeast-2.elb.amazonaws.com/g\' ./user-service/src/main/resources/application.yml"""
            sh 'cat ./user-service/src/main/resources/application.yml'
            sh 'sh ./gradlew clean bootjar'
            sh 'ls -al user-service/build/libs/'
            sh 'docker version'
            sh 'docker build -t survey-svc .'
            sh 'docker image list'
            sh 'docker tag survey-svc sulmoon/survey-svc:$BUILD_NUMBER'
        }
        
    }
    stage("user-svc Build") {
        
        dir('user-service'){
            sh 'pwd'
            sh """sed -i -e \'s/localhostgate/ab7844a5eb2164cbd82b034fb540a5a1-1653959158.ap-northeast-2.elb.amazonaws.com/g\' ./user-service/src/main/resources/application.yml"""
            sh """sed -i -e \'s/localhostdis/a4e018cbeca2e4a11bacdcd6cc8e1372-1380515803.ap-northeast-2.elb.amazonaws.com/g\' ./user-service/src/main/resources/application.yml"""
            sh 'cat ./user-service/src/main/resources/application.yml'
            sh 'sh ./gradlew clean bootjar'
            sh 'ls -al user-service/build/libs/'
            sh 'docker version'
            sh 'docker build -t user-svc .'
            sh 'docker image list'
            sh 'docker tag user-svc sulmoon/user-svc:$BUILD_NUMBER'
        }
            
    }
    
    withCredentials([string(credentialsId: 'DOCKER_HUB_PASSWORD', variable: 'PASSWORD')]) {
        sh 'docker login -u sulmoon --password $PASSWORD'
    }
    
        stage("Push Image to Docker Hub"){
        sh 'docker push  sulmoon/api-gateway-svc:$BUILD_NUMBER'
        sh 'docker push  sulmoon/auth-svc:$BUILD_NUMBER'
        sh 'docker push  sulmoon/discovery-svc:$BUILD_NUMBER'
        sh 'docker push  sulmoon/survey-svc:$BUILD_NUMBER'
        sh 'docker push  sulmoon/user-svc:$BUILD_NUMBER'
        sh 'docker image prune -f'
    }
    
    stage("K8s manifest edit"){
        
        dir('manifest'){
            
            git credentialsId: 'd4db7d58-5dc4-43a5-9d72-373c03d92786',
            url: 'git@github.com:bojongK/k8s-manifest.git',
            branch: 'main'
            sh 'ls -a'
            sh "cat ./deployment.yaml | grep image"
            sh """
                sed -i -e \'s/svc:.*/svc:$BUILD_NUMBER/g\' ./deployment.yaml
            """
            sh """
                sed -i -e \'s/svc:.*/svc:$BUILD_NUMBER/g\' ./deployment-gate.yaml
            """
            sh "cat ./deployment.yaml | grep image"
            sh "cat ./deployment-gate.yaml | grep image"
            sh "git add -A"
            sh "git status"
            sh """git commit -m \'Update Deployment\'"""
            sh 'git push origin main'
        }
    }
}
