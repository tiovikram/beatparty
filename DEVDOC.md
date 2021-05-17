## Developer Documentation

### Table of Contents
1. [How to obtain the source code](#how-to-obtain-the-source-code)  
2. [Directory structure layout](#directory-structure-layout)    
   2.1 [Source Code](#source-code)  
   2.2 [Test Files](#test-files)  
   2.3 [Application Resources](#application-resources)  
3. [How to build the software](#how-to-build-the-software)  
   3.1 [Building the frontend](#building-the-frontend)  
   3.2 [Building the backend](#building-the-backend)  
4. [How to test the software](#how-to-test-the-software)  
5. [How to add new tests](#how-to-add-new-tests)  
6. [How to build a release of the software](#how-to-build-a-release-of-the-software)  
  
### How to obtain the source code
The front-end and back-end source code are both contained within the same repository: `https://github.com/v-subbiah/beatparty`  
  
To clone a copy of the repository using git:  
1. Check that you have the latest version of `git` running.  
  `git --version`  
2. Clone the repository to your local machine.  
   - Using HTTPS:  
      `git clone https://github.com/v-subbiah/beatparty.git`  
   - Using SSH:  
       ⚠️  Note: SSH keys for your local machine need to be setup on GitHub prior to cloning  
      `git clone git@github.com:v-subbiah/beatparty.git`  
  
### Directory structure layout
#### Source Code
The source code is within the `/src/main` folder which is divided into subfolders for the front-end, back-end and back-end resources.
  
- **Front End source code:**  
Under `src/main/`, the `interface/src` folder contains the Typescript front-end components with the CSS files for each component.  
The naming convention used for `.tsx` files and `.css` files is `<component-name>.tsx` and the corresponding CSS file as `<component-name.css>`  
  
- **Back End source code:**  
Under `src/main`, the `java/com/beatparty/beatpartyapp` folder contains the source code for the back-end server.
  
It is further subdivided into three sub-folders.  
1. The `controller` folder contains API definitions/logic
2. The `dao` folder contains classes to interact with database
3. The `entity` folder contains Java classes representing the POJOs, such as `Song.java`
  
The folder also contains a `BeatpartyappApplication.java` class which serves as the Spring Framework target for running the back-end application.  
  
#### Test Files
The test code is contained within `/src/test` folder
The `src/test/java` folder mirrors the structure of the `src/main/java` folder
  
It contains unit tests for the back-end source code under `src/main/java/com/beatparty/beatpartyapp`  
  
#### Application Resources
The resources folder contains configuration files, such as the application.properties used by SpringBoot to store initialization properties of the backend server.  
  
### How to build the software.
#### Building the frontend
1. Install npm
[Instructions to install npm](https://www.npmjs.com/get-npm)
2. Ensure that your working directory is the root directory of the repository.
```
   $ pwd
   <local_filepath>/beatparty/
```
3. Change your working directory to the `src/main/interface` directory
```
   $ cd src/main/interface
```
4. Install the dependencies for the front-end application
```
   $ npm install
```
5. Run the npm development server on your local machine
```
   $ npm start
```

#### Building the backend
1. Install Java 11 or greater using either the Oracle package or OpenJDK package  
[Instructions to install Oracle Java 11](https://www.oracle.com/java/technologies/javase-jdk11-downloads.html) or [Instructions to install OpenJDK 11](https://openjdk.java.net/projects/jdk/11/)  
2. Install Maven  
[Instructions to install Maven](https://maven.apache.org/install.html)  
3. Ensure that your working directory is the root directory of the repository.
```
   $ pwd
   <local_filepath>/beatparty/
```
4. Run the maven wrapper file to build and run the backend
```
   $ ./mvnw spring-boot:run
```
  
⚠️  Note: For the front-end client to communicate with the back-end server, the back-end server must be run before the front-end client.  
  
### How to test the software.
#### Testing the backend
1. Install Java 11 or greater using either the Oracle package or OpenJDK package  
[Instructions to install Oracle Java 11](https://www.oracle.com/java/technologies/javase-jdk11-downloads.html) or [Instructions to install OpenJDK 11](https://openjdk.java.net/projects/jdk/11/)  
2. Install Maven  
[Instructions to install Maven](https://maven.apache.org/install.html)  
3. Ensure that your working directory is the root directory of the repository.
```
   $ pwd
   <local_filepath>/beatparty/
```
4. Run the maven test script  
```
   $ mvn test
```
   
⚠️  Note: The front-end UI cannot be tested using automated testing since it is difficult to mock user behaviour.
  
### How to add new tests.
1. Ensure that your working directory is the root directory of the repository.
```
   $ pwd
   <local_filepath>/beatparty/
```
2. Change your working directory to the directory with the tests.  
```
   $ cd src/test/java/com/beatparty/beatpartyapp
```
3. Create a new Java test class titled `<test_name>Test.java`  
  
   **Example:**  
```
  $ touch NewApplicationTest.java
```
4. Import the appropriate packages  
```java
   import org.junit.jupiter.api.Test;
   import org.junit.jupiter.api.Assertions;
   import org.springframework.boot.test.context.SpringBootTest;
```
5. Mark the class decleration with `@SpringBootTest` and the test methods with `@Test`  
   Use assertions to compare expected output with actual output  
  
   **Example:**  
```java
   @SpringBootTest
   public class NewApplicationTest {

   ...

       @Test 
       public void nonNullReturnTest() {
           Assertions.assertNonNull(testReturnValue);
       }

       @Test
       public void exampleTest() {
           Asssertions.assertEquals(expectedValue, actualValue);
       }
```
  
### How to build a release of the software.
🛑 TODO: To be done once Ansh and Yasin deploy the application.
