package IAS;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

class Memory
{
    ArrayList<String> lines = new ArrayList<>(); // store the contents of the memory in an ArrayList

    Memory() throws FileNotFoundException // default constructor
    {
        getLines();
    }

    void getLines() throws FileNotFoundException
    {
        Scanner scanner = new Scanner(new File("memory.txt"));
        while (scanner.hasNextLine())
        {
            String line = scanner.nextLine().substring(0, 40);
            lines.add(line);
        }
    }

    String readLine(String address) // getter function
    {
        return lines.get(Helper.binToDec(address));
    }

    void writeLine(String address, String data) // setter function
    {
        lines.set(Helper.binToDec(address), data);
    }

}