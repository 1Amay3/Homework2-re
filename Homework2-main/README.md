# ECE/CS-5510 Homework 2 Part II Template (Fall 2024)

## Contact

Please use the Homework2 discussion forum on Canvas for any questions.

## Overview

You must use this template to submit your solution to Part II of Homework 2. 
Your assignment may not be graded otherwise. 
**Submit your solution as a zip file on Canvas.** 

This template comes with its own set of unit test that you must satisfy before submission to be considered for full credit. 
We will check your source files only for accepted tests to ensure that the respective solutions meets specifications and grade accordingly.

**DO NOT MODIFY THE PROVIDED UNIT TESTS (SubmissionTest1.java/SubmissionTest2.java). YOUR SUBMISSION MAY NOT BE GRADED OTHERWISE.** 

You are free to modify any other part of the source code.

## Downloading this template

If you know `git`, you can clone this repository and work on it.

Otherwise, click the `Clone or download` button on the repository page and then click `Download zip` to download a zip file.   

## Submission

You must zip this folder and submit to Canvas

## Building and Running the template

The project uses _Gradle_ build system to compile, package and to run junit tests. In case you are unable to run the below `gradle` commands, please check compatibility of the installed _JDK_ against that of _Gradle (8.8)_. You may have to lower your _JDK_ version to be compatible to that of _Gradle_ ([compatibility matrix](https://docs.gradle.org/current/userguide/compatibility.html)).

Before executing the `gradlew` script, please make the script excutable:

Linux: `chmod +x gradlew`

### Building the project and running unit test

Run the following command to build the project and run the juint tests:

Linux: `./gradlew build`

Windows: `./gradlew.bat build`

### Running Unit Test

Run the following command to run the junit test, without re-building the project:

Linux: `./gradlew test`

Windows: `./gradlew.bat test`

The unit tests are expected to fail (or even run forever) as is, due to missing implementations. 
**One of your objective is to ensure that the corresponding tests for your implementations pass**
Again, **Do not modify the provided unit tests..** 

### Building Test and Test2 main programs:

Linux: ` ./gradlew build -x test`

Windows: `./gradlew.bat build -x test`

### Running Test and Test2 main programs:

__Test__:
`java -cp build/libs/homework2.jar edu.vt.ece.Test <YOUR_ARGS>`

__Test2__:
`java -cp build/libs/homework2.jar edu.vt.ece.Test2 <YOUR_ARGS>`

Replace `<YOUR_ARGS>` with the arguments you would like to pass to the respective programs. Please refer to the _Test.java_ and _Test2.java_ to identify the arguments.

## Intellij

This gradle project can be imported into Intellj by going to `File -> Open` and choosing this directory.

For more instructions, go to https://www.jetbrains.com/help/idea/gradle.html#gradle_import_project_start

## Disclaimer

The provided unit tests by no means verify the correctness of your implementations in its entirety. 
It is the responsibility of the students to ensure that their implementations meet specifications to receive full credit.
