def TeleUrl = 'https://api.telegram.org/bot1647436660:AAF8OrMu-FqlwWxj2_M4k_allIGLNwjS83E/sendMessage'
def TeleChatId = -912839571
def ServiceName = '[Backend]'
def GIT_COMMIT_MSG
def GIT_AUTHOR


pipeline {
    agent any

    stages {
		stage ('Get git commit information') {
			steps {
				script {
					GIT_COMMIT_MSG = sh(script: 'git log -1 --pretty=%B | head -5', returnStdout: true).trim()
					GIT_AUTHOR = sh(script: 'git log -1 --pretty=format:"%Cgreen%an %Cblue%ae"', returnStdout: true).trim()
				}
			}
		}
		
		stage ('Message to telegram chat room when started to deploy') {
			steps {
				script {
					sh """
						curl -X GET \
						-H "Content-Type: application/json" \
						-d '{"chat_id": ${TeleChatId}, "text": "${ServiceName} Commit \\"${GIT_COMMIT_MSG}\\" performed by ${GIT_AUTHOR} started to deploy."}' \
						${TeleUrl}
					"""				
				}
			}

		}
	
		stage('Build and deploy') {
				steps {
					// Change directory to frontend source code
					dir('ladi_be') {
						//Build jar file
						sh 'mvn clean package -Dmaven.test.skip=true'
						
						//Build docker image
						sh 'docker build -t laditest:latest -f Dockerfile .'
						
						//Compress docker image
						sh 'docker save -o BE_image.tar laditest:latest'
						
						//Copy image to Staging host
						sh 'scp -i ~/.ssh/id_rsa BE_image.tar root@35.240.138.76:.'
						
						//Build image on Staging host
						sh 'ssh -i ~/.ssh/id_rsa root@35.240.138.76 "docker load -i BE_image.tar"'
						
						//Stop current container
						sh 'ssh -i ~/.ssh/id_rsa root@35.240.138.76 "docker stop laditest"'
						
						//Remove current container
						sh 'ssh -i ~/.ssh/id_rsa root@35.240.138.76 "docker rm laditest"'
						
						//Run container with new image
						sh 'ssh -i ~/.ssh/id_rsa root@35.240.138.76 "docker run -itdp 8081:8080 --name laditest laditest:latest"'
						
						//Remove compressed image
						sh 'ssh -i ~/.ssh/id_rsa root@35.240.138.76 "rm BE_image.tar"'
						
						//Remove files on local
						sh 'rm BE_image.tar'
						
						// Remove unused images on local
						sh '~/remove_none_images.sh'
					}
				}
			}
		}

    post {
		failure {
			script {
				echo 'The pipeline run error!'
				sh """
					curl -X GET \
					-H "Content-Type: application/json" \
					-d '{"chat_id": ${TeleChatId}, "text": "${ServiceName} Commit \\"${GIT_COMMIT_MSG}\\" performed by ${GIT_AUTHOR} has been deployed failed."}' \
					${TeleUrl}
				"""
			}
		}
  
		success {
			script {
				echo 'The pipeline run success!'
				sh """
					curl -X GET \
					-H "Content-Type: application/json" \
					-d '{"chat_id": ${TeleChatId}, "text": "${ServiceName} Commit \\"${GIT_COMMIT_MSG}\\" performed by ${GIT_AUTHOR} has been deployed successful."}' \
					${TeleUrl}
				"""
			}
		}
	}
}
