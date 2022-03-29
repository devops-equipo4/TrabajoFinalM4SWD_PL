import utilities.*

def call(stages){


    def listStagesOrder = [
            'compile'         : 'sCompile',
            'unitTest'        : 'sUnitTest',
            'jar'             : 'sJar',
            'testCasePostman': 'sTestCasePostman',
            'testCaseSelenium': 'sTestCaseSelenium',
    ]

    allStages()
}

def allStages() {
    sCompile()
    sJar()
    sRun()
    sUnitTest()
    sTestCasePostman()
    sTestCaseSelenium()
    sTestCaseJmeter()
}

def sCompile() {
    env.STAGE = "Stage Compile"
    stage("$env.STAGE") {
        sh "mvn clean compile -e"
        }
    }

    def sJar() {
    env.STAGE = "Stage Jar"
    stage("$env.STAGE") {
        //sh "mvn clean package -e -DskipTests"
        sh "mvn install -DskipTests"
    }
}
def sRun() {
    env.STAGE = "Stage Run"
    stage("$env.STAGE") {
        sh "nohup mvn -f backend/pom.xml spring-boot:run & >/dev/null"
        sh "sleep 60"
    }
}

def sUnitTest() {
    env.STAGE = "Stage Unit Test"
    stage("$env.STAGE") {
        sh "mvn clean test -Dtest=com.devops.dxc.devops.unit.*Test -DfailIfNoTests=false\n"
    }
}

def sTestCasePostman() {
    env.STAGE = "Stage Test Case Postman (Newman)"
    stage("$env.STAGE") {
        sh "newman run 'Postman/Lab 4.postman_collection.json'\n"
    }
}

def sTestCaseSelenium() {
    env.STAGE = "Stage Test Case Selenium Web Driver"
    stage("$env.STAGE") {
        sh "echo '$env.WEBDRIVER'"
        sh "mvn clean test -Dtest=com.devops.dxc.devops.selenium.*Test -DfailIfNoTests=false\n"
    }
}

def sTestCaseJmeter(){
    env.STAGE = "Stage Test Case Jmeter"
    stage("$env.STAGE") {
        sh "cd "
        sh "mvn verify -Pperformance -DskipTests"
    }
}
return this;
