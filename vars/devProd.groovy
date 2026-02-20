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
  
    

    
