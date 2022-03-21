import utilities.*

def call() {
    pipeline {
        agent any
//        parameters {
//            string  name: 'stages', description: 'Ingrese los stages para ejecutar', trim: true
//        }
        stages {
            stage('pipeline') {
                steps {
                    script {
                        sh "env"
                        env.STAGE = ""
                        env.PIPELINE = ""
                        env.WEBDRIVER = "/usr/bin/chromedriver"

                        env.GIT_REPO_NAME = env.GIT_URL.replaceFirst(/^.*\/([^\/]+?).git$/, '$1')

                        ci.call(params.stages)

                    }
                }
            }
        }
    }
}

return this;
