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
    //sCompile()
    //sUnitTest()
    //sJar()

    sTestCasePostman()
    sTestCaseSelenium()
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
        sh "mvn clean test -Dtest=com.devops.dxc.devops.unit.*Test -DfailIfNoTests=false\n"
    }
}

def sJar() {
    env.STAGE = "Stage Jar"
    stage("$env.STAGE") {
        sh "mvn clean package -e"
    }
}

def sTestCasePostman() {
    env.STAGE = "Stage Test Case Postman (Newman)"
    stage("$env.STAGE") {
        sh "mvn install"
        sh "nohup mvn -f backend/pom.xml spring-boot:run & >/dev/null"
        sh 'pwd'
        sh "sleep 60"
        sh "newman run '../Postman/Lab 4.postman_collection.json'\n"
    }
}

def sTestCaseSelenium() {
    env.STAGE = "Stage Test Case Selenium Web Driver"
    stage("$env.STAGE") {
        sh "echo '$env.WEBDRIVER'"
        sh "mvn clean test -Dtest=com.devops.dxc.devops.selenium.*Test -DfailIfNoTests=false\n"
    }
}
return this;
