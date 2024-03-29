IMP: Remove the "IMT2019043_" prefix from all filenames, because, in Java, class name needs to be same as 
file names. 

This is a Java implementation of the IAS machine. 

The IAS machine was the first electronic computer to be built at the Institute for Advanced Study (IAS)
in Princeton, New Jersey. It is sometimes called the von Neumann machine, since the paper describing
its design was edited by John von Neumann, a mathematics professor at both Princeton University and IAS. 

The IAS package has the Helper, Accumulator, IBR, IR, MAR, MBR, Memory, MQ, PC, Processor and Test class files.
The memory1.txt and memory2.txt mimic the memory structure of the computer. 
The program1.txt and program2.txt contain the two programs in a pseudo high level language. 
The output1.pdf and output2.pdf contain the output for the two programs, generated by IntelliJ IDEA, 
an integrated development environment for Java.

How to compile and execute?
0. Ensure that you have Java 11/OpenJDK 11 or higher. 
1. Rename memory1.txt or memory2.txt to memory.txt. 
2. Go to the IAS directory and run the command javac *.java. 
3. cd .. (come back to the root directory). 
4. Run the command java IAS.Test. 
5. The output is displayed on the Terminal window. 

