package IAS;
import java.io.FileNotFoundException;

public class Test
{
    public static void main (String [] args) throws FileNotFoundException
    {
        AC ac = new AC();
        MQ mq = new MQ();
        MBR mbr = new MBR();
        IBR ibr = new IBR();
        Memory memory = new Memory();
        PC pc = new PC();
        IR ir = new IR();
        MAR mar = new MAR(pc);
        Processor processor = new Processor(memory, ac, mq, mbr, ibr, pc, ir, mar);
        processor.fetch();
    }
}











