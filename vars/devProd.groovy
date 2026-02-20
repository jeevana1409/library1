def gitDownload()
{
  stage('clone')
  {
    git "https://github.com/jeevana1409/FormFillApp.git"
  }
}
    
