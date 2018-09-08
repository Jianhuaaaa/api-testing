# api-testing
This is a demo project for API testing

Version: 0.0.1
Date: Sep 8, 2018
Author: Jianhua Sun

Framework User Menu：
1. The framework support 2 mode:
   -Traditional TestNG mode.
   -BDD mode.
2. How to run test in traditional TestNG mode:
   -Option1: Specify maven goal: mvn test -DsuiteXmlFiles=traditional_testng.xml
   -Option2: Invoke run_test_in_traditional_mode.bat file.
3. How to run test in BDD mode:
   -Option1: Specify maven goal: mvn test -DsuiteXmlFiles=cucumber_testng.xml
   -Option2: Invoke run_test_in_cucumber_mode.bat file.
4. How to config maven project in Jenkins:
   -Step1: Click New Items on the left menu
           ->Enter project name;
           ->Select Maven project
           ->Click OK
   -Step2: Provide job description.
   -Step3: In Source Code Management, specify the local maven project path(I didn't put project on Github because I'm not sure if the project contains sensitive key works of your company).
   -Step4: In Build, specify the Root POM & Goals and options: mvn test -DsuiteXmlFiles=cucumber_testng.xml

Note. To run the project you need to install all the dependencies specified in pom.xml file and install Cucumber plugin.