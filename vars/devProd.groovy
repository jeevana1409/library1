def gitDownload()
{
  stage('clone')
  {
    git branch: "main", url: "https://github.com/jeevana1409/FormFillApp.git" 
  }
}
def build()
{
  stage('build')
  {
    sh 'mvn package'
  }
}
def dockercontext(String imageName)
{
  stage('build Docker Image')
  {
    sh """
cat <<EOF > Dockerfile
FROM tomcat:9
COPY webapp/target/webapp.war /usr/local/tomcat/webapps/
EXPOSE 8080
EOF

docker build -t ${imageName}:${env.BUILD_NUMBER} .
docker tag ${imageName}:${env.BUILD_NUMBER} ${imageName}:latest
"""
    }
}   
def dockerPush(imageName)
{
  sh """
docker push ${imageName}:${env.BUILD_NUMBER}
docker push ${imageName}:latest
"""
}
def call(imageName, devServer) {
    stage('Deploy to DEV') {
        sh """
ssh -o StrictHostKeyChecking=no ${devServer} '
docker pull ${imageName}:latest
docker stop dev-container || true
docker rm dev-container || true
docker run -d -p 8081:8080 --name dev-container ${imageName}:latest
'
"""
    }
}
    

    
