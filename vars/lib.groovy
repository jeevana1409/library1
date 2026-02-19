def gitDownload(repo)
{
  git branch: 'main', url: "https://github.com/jeevana1409/${repo}.git"

}
def build()
{
  sh 'mvn package'
}
def dockerContext()
{
  sh '''
      mkdir -p docker 
      cp webapp/target/webapp.war docker/webapp.war
      cat <<EOF > docker/Dockerfile
      FROM tomcat:10
      COPY webapp.war /usr/local/tomcat/webapps
      EXPOSE 8080
      EOF
      ''' 
}
def dockerBuild(imageName)
{ 
  sh 'docker build -t ${imageName} docker' 
}
def dockerContainer(containerName, imageName)
{
  sh """
     docker rm -f ${containerName} || true
     docker run -d  -p 8081:8080 --name ${containerName} ${imageName} 
     """
}
