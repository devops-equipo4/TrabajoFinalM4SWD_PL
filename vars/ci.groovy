import utilities.*

def call(stages){


    def listStagesOrder = [
            'compile'         : 'sCompile',
            'unitTest'        : 'sUnitTest',
            'jar'             : 'sJar',
            'gitCreateRelease': 'sGitCreateRelease',
    ]

    def stagesArray = []
    stagesArray = validations.searchKeyInArray(stages, ";", listStagesOrder)

    if (stages.isEmpty()){
        allStages()
    }else{
        stagesArray.each{ stageFunction ->//variable as param
            echo 'Ejecutando ' + stageFunction
            "${stageFunction}"()
        }
    }
}

def allStages() {
    sCompile()
    sUnitTest()
    sJar()
    sGitCreateRelease()
}

def sCompile() {
    env.STAGE = "Stage Compile"
    stage("$env.STAGE") {
        sh "mvn clean compile -e"
    }
}

def sUnitTest() {
    env.STAGE = "Stage Unit Test"
    stage("$env.STAGE") {
        sh "mvn clean test -e"
    }
}

def sJar() {
    env.STAGE = "Stage Jar"
    stage("$env.STAGE") {
        sh "mvn clean package -e"
    }
}

def sGitCreateRelease() {
    env.STAGE = "Stage Git Create Release"
    stage("Stage Git Create Release") {
        sh "git checkout feat_jenkins"
        sh "git checkout -b release-v1-0-0"
        sh "git push --set-upstream origin release-v1-0-0"
    }
}
return this;
