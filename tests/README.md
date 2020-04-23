# How to run JUnit in this directory:

1. Install JUnit by making sure that both the hamcrest.jar and junit.jar
    are in the folder

2. Compile classes from nfa folder

3. Compile test classes from this folder with this command:

```.``` signifies the current folder

```./..``` signifies the folder where the nfa packages are

To compile, run on terminal console:

```
javac -cp .:junit-4.13.jar:hamcrest-core-1.3.jar:./.. FILE_NAME.java
```
4. Run the test with this command on terminal console:
(notice that this is run with java, not javac)
(notice that this is run on the class, which is just the name and without the .java extension)
```
java -cp .:junit-4.13.jar:hamcrest-core-1.3.jar:./.. org.junit.runner.JUnitCore FILE_NAME
```