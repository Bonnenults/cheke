@Library('Autozi') _

// 初始化配置
dockerImage.nameSpace = 'hub.autozi.net/cheke'
dockerImage.releaseNameSpace = 'hub.autozi.com/cheke'
dockerImage.release = params.Release
dockerImage.repoName = 'none'
dockerImage.lastTag = dockerTagGenerate.FormBranch(env.BRANCH_NAME)
dockerImage.versionTag = dockerTagGenerate.DateTimeTag()
// 构建的Pipeline
pipeline {
    // 任意节点
    agent any
    // 构建空间优化
    options {
        buildDiscarder(logRotator(artifactDaysToKeepStr: '100', artifactNumToKeepStr: '100', daysToKeepStr: '100', numToKeepStr: '100'))
    }
    // 构建参数化
    parameters {
        booleanParam(name: 'Release', defaultValue: false, description: '释放镜像到IDC中心')
		booleanParam(name: 'Build_Common', defaultValue: false, description: '构建Common项目')
	    booleanParam(name: 'Build_Dubbo', defaultValue: false, description: '构建Dubbo项目')
        booleanParam(name: 'Build_Api', defaultValue: false, description: '构建Api项目')
		booleanParam(name: 'Build_Task', defaultValue: false, description: '构建Task项目')
		booleanParam(name: 'Build_Web', defaultValue: false, description: '构建Web项目')
    }
    // 工具支持
    tools {
        maven 'Maven3'
        jdk 'Jdk7'
    }
    // 构建步骤
    stages {
        stage('初始化构建环境') {
            steps {
                // 初始化脚本
                autoziInit()
                //打印提示信息
                echo "完成构建环境初始化，开始构建 $env.BRANCH_NAME 分支,Docker Image Name Space:$dockerImage.nameSpace, DcokerImage标志:$dockerImage.lastTag,DockerTag:$dockerImage.versionTag"
            }
        }
		stage('构建Common项目') {
             when {
                expression {
                    return params.Build_Common
                }
            }
            steps {
                autoziAutoMvn artifacts: 'autozi-cheke-common-parent/*/target/*.jar', sh: 'cd autozi-cheke-common-parent && mvn clean install -Dmaven.test.skip=true -P "Autozi Public"'
            }
        }
        stage('构建Dubbo项目') {
            when {
                expression {
                    return params.Build_Dubbo
                }
            }
            steps {
                script {
                    dockerImage.repoName = 'dubbo'
                }
                autoziAutoMvn artifacts: 'autozi-cheke-service-web/target/*.war', sh: 'cd autozi-cheke-service-web-parent && mvn clean package -Dmaven.test.skip=true -P "Autozi Public"'
                dockerStash targetDir: 'autozi-cheke-service-web/target', target: '*.war', dockerDir: 'docker/dubbo', docker: '**'
                dir('./docker-image') {
                    //清空工作区
                    deleteDir()
                    dockerUnStash()
                    sh '''
                    cd target
                    jar -vxf *.war
                    rm -rfv *.war
                    ls -lR
                    cd ..
                    '''
                    autoziDockerJob img: dockerImage, build: [
                            imageName: dockerImage.fullVersionImageName()
                    ]
                }
                script {
                    //修改构建说明
                    currentBuild.description = "${currentBuild.description == null ? '' : currentBuild.description + '\r\n'}${dockerImage.fullLastImageName()}\r\n${dockerImage.fullVersionImageName()}"
                    if (params.Release) {
                        //修改构建说明
                        currentBuild.description = "${currentBuild.description == null ? '' : currentBuild.description + '\r\n'}Release:\r\n${dockerImage.fullReleaseLastImageName()}\r\n${dockerImage.fullReleaseVersionImageName()}"
                    }
                }
            }
        }
        stage('构建Api项目') {
            when {
                expression {
                    return params.Build_Api
                }
            }
            steps {
                script {
                    dockerImage.repoName = 'api'
                }
                autoziAutoMvn artifacts: 'autozi-cheke-api/target/*.war', sh: 'cd autozi-cheke-api-parent && mvn clean package -Dmaven.test.skip=true -P "Autozi Public"'
                dockerStash targetDir: 'autozi-cheke-api/target', target: '*.war', dockerDir: 'docker/api', docker: '**'
                dir('./docker-image') {
                    //清空工作区
                    deleteDir()
                    dockerUnStash()
                    sh '''
                    cd target
                    jar -vxf *.war
                    rm -rfv *.war
                    ls -lR
                    cd ..
                    '''
                    autoziDockerJob img: dockerImage, build: [
                            imageName: dockerImage.fullVersionImageName()
                    ]
                }
                script {
                    //修改构建说明
                    currentBuild.description = "${currentBuild.description == null ? '' : currentBuild.description + '\r\n'}${dockerImage.fullLastImageName()}\r\n${dockerImage.fullVersionImageName()}"
                    if (params.Release) {
                        //修改构建说明
                        currentBuild.description = "${currentBuild.description == null ? '' : currentBuild.description + '\r\n'}Release:\r\n${dockerImage.fullReleaseLastImageName()}\r\n${dockerImage.fullReleaseVersionImageName()}"
                    }
                }
            }
        }
        stage('构建Task项目') {
            when {
                expression {
                    return params.Build_Task
                }
            }
            steps {
                script {
                    dockerImage.repoName = 'task'
                }
                autoziAutoMvn artifacts: 'autozi-cheke-task/target/*.war', sh: 'cd autozi-cheke-task-parent && mvn clean package -Dmaven.test.skip=true -P "Autozi Public"'
                dockerStash targetDir: 'autozi-cheke-task/target', target: '*.war', dockerDir: 'docker/task', docker: '**'
                dir('./docker-image') {
                    //清空工作区
                    deleteDir()
                    dockerUnStash()
                    sh '''
                    cd target
                    jar -vxf *.war
                    rm -rfv *.war
                    ls -lR
                    cd ..
                    '''
                    autoziDockerJob img: dockerImage, build: [
                            imageName: dockerImage.fullVersionImageName()
                    ]
                }
                script {
                    //修改构建说明
                    currentBuild.description = "${currentBuild.description == null ? '' : currentBuild.description + '\r\n'}${dockerImage.fullLastImageName()}\r\n${dockerImage.fullVersionImageName()}"
                    if (params.Release) {
                        //修改构建说明
                        currentBuild.description = "${currentBuild.description == null ? '' : currentBuild.description + '\r\n'}Release:\r\n${dockerImage.fullReleaseLastImageName()}\r\n${dockerImage.fullReleaseVersionImageName()}"
                    }
                }
            }
        }
        stage('构建Web项目') {
            when {
                expression {
                    return params.Build_Web
                }
            }
            steps {
                script {
                    dockerImage.repoName = 'web'
                }
                autoziAutoMvn artifacts: 'autozi-cheke-web/target/*.war', sh: 'cd autozi-cheke-web-parent &&mvn clean package -Dmaven.test.skip=true -P "Autozi Public"'
                dockerStash targetDir: 'autozi-cheke-web/target', target: '*.war', dockerDir: 'docker/web', docker: '**'
                dir('./docker-image') {
                    //清空工作区
                    deleteDir()
                    dockerUnStash()
                    sh '''
                    cd target
                    jar -vxf *.war
                    rm -rfv *.war
                    ls -lR
                    cd ..
                    '''
                    autoziDockerJob img: dockerImage, build: [
                            imageName: dockerImage.fullVersionImageName()
                    ]
                }
                script {
                    //修改构建说明
                    currentBuild.description = "${currentBuild.description == null ? '' : currentBuild.description + '\r\n'}${dockerImage.fullLastImageName()}\r\n${dockerImage.fullVersionImageName()}"
                    if (params.Release) {
                        //修改构建说明
                        currentBuild.description = "${currentBuild.description == null ? '' : currentBuild.description + '\r\n'}Release:\r\n${dockerImage.fullReleaseLastImageName()}\r\n${dockerImage.fullReleaseVersionImageName()}"
                    }
                }
            }
        }  
    }
}