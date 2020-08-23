package IAS;
class Processor
{
    Memory memory;
    PC pc;
    MBR mbr;
    MAR mar;
    IBR ibr;
    IR ir;
    MQ mq;
    AC ac;
    boolean halt; // flag to halt the cycle

    Processor(Memory MEMORY, AC AC, MQ MQ, MBR MBR, IBR IBR, PC PC, IR IR, MAR MAR) // parametrized constructor
    {
        this.memory = MEMORY;
        this.pc = PC;
        this.mbr = MBR;
        this.mar = MAR;
        this.ibr = IBR;
        this.ir = IR;
        this.mq = MQ;
        this.ac = AC;
        halt = false;
    }

    void fetch() // fetch cycle
    {
        // printing the memory contents before operations
        System.out.println("\nMemory before operations...");
        for (int i = 0; i < memory.lines.size(); i++)
        {
            System.out.println("Line " + i + ": " + memory.lines.get(i));
        }
        System.out.println();
        while (!halt)
        {
            if (ibr.getData().equals("")) // IBR is empty
            {
                System.out.println("IBR is empty");
                mar.setAddress(pc.getAddress()); // MAR <-- PC
                System.out.println("Address of PC = " + pc.getAddress());
                System.out.println("Address of MAR = " + mar.getAddress());
                mbr.setData(memory.readLine(mar.getAddress()));  // MBR <-- M[MAR]
                System.out.println("Address of MBR = " + mbr.getData());
                ibr.setData(mbr.getData().substring(20)); // IBR <-- MBR[20:39]
                System.out.println("Address of IBR = " + ibr.getData());
                ir.setData(mbr.getData().substring(0, 8));  // IR <-- MBR[0:7]
                System.out.println("Address of IR = " + ir.getData());
                mar.setAddress(mbr.getData().substring(8, 20));  // MAR <-- MBR[8:19]
                System.out.println("Address of MAR = " + mar.getAddress());
                pc.setAddress(Helper.decToBin(Helper.binToDec(pc.getAddress()) + 1));  // PC <-- PC + 1
                System.out.println("PC incremented to " + pc.getAddress() + " (" + Helper.sign_binToDec(pc.getAddress()) + ")");
            }
            else // IBR is not empty
            {
                System.out.println("IBR is not empty");
                ir.setData(ibr.getData().substring(0, 8));  // IR <-- IBR[0:7]
                System.out.println("Address of IR = " + ir.getData());
                mar.setAddress(ibr.getData().substring(8));  // MAR <-- IBR[8:19]
                System.out.println("Address of MAR = " + mar.getAddress());
                ibr.setData(""); // empty the IBR
            }
            execute(); // call the execute cycle
        }
    }

    void execute() // execute cycle
    {
        String instruction = ir.getData();
        System.out.println("The instruction is " + instruction);

        switch (instruction)
        {
            case "00001010":
                LOAD_MQ();
                break;

            case "00001001":
                LOAD_MQ_MX();
                break;

            case "00100001":
                STOR_MX();
                break;

            case "00000001":
                LOAD_MX();
                break;

            case "00000010":
                LOAD_negMX();
                break;

            case "00000011":
                LOAD_MODMX();
                break;

            case "00000100":
                LOAD_NEGMODMX();
                break;

            case "00001101":
                JUMP_MX_LEFT();
                break;

            case "00001110":
                JUMP_MX_RIGHT();
                break;

            case "00001111":
                JUMP_posMX_LEFT();
                break;

            case "00010000":
                JUMP_posMX_RIGHT();
                break;

            case "00000101":
                ADD_MX();
                break;

            case "00000111":
                ADD_MODMX();
                break;

            case "00000110":
                SUB_MX();
                break;

            case "00001000":
                SUB_MODMX();
                break;

            case "00001011":
                //MUL_MX(); not implemented
                break;

            case "00001100":
                DIV_MX();
                break;

            case "00010100":
                LSH();
                break;

            case "00010101":
                RSH();
                break;

            case "00010010":
                // STOR_LEFTMX(); not implemented
                break;

            case "00010011":
                // STOR_RIGHTMX(); not implemented
                break;

            case "11111111":
                HALT();
                break;

        }

    }

    void LOAD_MQ()  // LOAD MQ: transfer the contents of register MQ to the accumulator AC
    {
        System.out.println("LOAD MQ: AC <-- MQ");
        if (mq.getData().length() == 0)
            System.out.println("Contents of MQ = empty");
        else
            System.out.println("Contents of MQ = " + mq.getData() + " (" + Helper.sign_binToDec(ac.getData()) + ")");

        if (ac.getData().length() == 0)
            System.out.println("Contents of AC before op = empty");
        else
            System.out.println("Contents of AC before op = " + ac.getData() + " (" + Helper.sign_binToDec(ac.getData()) + ")");
        ac.setData(mq.getData());
        System.out.println("Contents of AC after op = " + ac.getData() + " (" + Helper.sign_binToDec(ac.getData()) + ")");
    }

    void LOAD_MQ_MX() // LOAD MQ,M(X): transfer the contents of memory location X to MQ
    {
        System.out.println("LOAD MQ, MX: MQ <-- M[X]");
        System.out.println("Contents of M[X] = " + memory.readLine(mar.getAddress()) + " (" + Helper.sign_binToDec(memory.readLine(mar.getAddress())) + ")");
        if (mq.getData().length() == 0)
            System.out.println("Contents of MQ before op = empty");
        else
            System.out.println("Contents of MQ before op = " + mq.getData() + " (" + Helper.sign_binToDec(mq.getData()) + ")");
        mq.setData(memory.readLine(mar.getAddress()));
        System.out.println("Contents of MQ after op = " + mq.getData() + " (" + Helper.sign_binToDec(mq.getData()) + ")");
    }

    void STOR_MX()  // STOR M(X): transfer the contents of AC to M[X]
    {
        System.out.println("STOR MX: M[X] <-- AC");
        if (ac.getData().length() == 0)
            System.out.println("Contents of AC = empty");
        else
            System.out.println("Contents of AC = " + ac.getData() + " (" + Helper.sign_binToDec(ac.getData()) + ")");
        System.out.println("Contents of M[X] before op = " + memory.readLine(mar.getAddress()) + " (" + Helper.sign_binToDec(memory.readLine(mar.getAddress())) + ")");
        memory.writeLine(mar.getAddress(), ac.getData());
        System.out.println("Contents of M[X] after op = " + memory.readLine(mar.getAddress()) + " (" + Helper.sign_binToDec(memory.readLine(mar.getAddress())) + ")");
    }

    void LOAD_MX()  // LOAD M(X): transfer the contents of M[X] to the AC
    {
        System.out.println("LOAD MX: AC <-- M[X]");
        if (ac.getData().length() == 0)
            System.out.println("Contents of AC before op = empty");
        else
            System.out.println("Contents of AC before op = " + ac.getData() + " (" + Helper.sign_binToDec(ac.getData()) + ")");
        ac.setData(memory.readLine(mar.getAddress()));
        System.out.println("Contents of AC after op = " + ac.getData() + " (" + Helper.sign_binToDec(ac.getData()) + ")");
    }

    void LOAD_negMX() // LOAD -M(X): transfer the contents of -M[X] to the AC
    {
        System.out.println("LOAD -MX: AC <-- -M[X]");
        if (ac.getData().length() == 0)
            System.out.println("Contents of AC before op = empty");
        else
            System.out.println("Contents of AC before op = " + ac.getData() + " (" + Helper.sign_binToDec(ac.getData()) + ")");
        String data = memory.readLine(mar.getAddress());
        int dataDecimal = Helper.sign_binToDec(data);
        String negBinary = Helper.decToSign_bin(-dataDecimal);
        ac.setData(negBinary);
        System.out.println("Contents of AC after op = " + ac.getData() + " (" + Helper.sign_binToDec(ac.getData()) + ")");

    }

    void LOAD_MODMX() // LOAD |M(X)|: transfer the contents of |M[X]| to the AC
    {
        System.out.println("LOAD |MX|: AC <-- |M[X]|");
        System.out.println("Contents of AC before op = " + ac.getData() + " (" + Helper.sign_binToDec(ac.getData()) + ")");
        String data = memory.readLine(mar.getAddress());
        int dataDecimal = Helper.sign_binToDec(data);
        if (dataDecimal > 0)
            ac.setData(data);
        else
        {
            String negBinary = Helper.decToSign_bin(-dataDecimal);
            ac.setData(negBinary);
        }
        System.out.println("Contents of AC after op = " + ac.getData() + " (" + Helper.sign_binToDec(ac.getData()) + ")");
    }

    void LOAD_NEGMODMX() // LOAD -|M(X)|: transfer the contents of -|M[X]| to the AC
    {
        System.out.println("LOAD -|MX|: AC <-- -|M[X]|");
        System.out.println("Contents of AC before op = " + ac.getData() + " (" + Helper.sign_binToDec(ac.getData()) + ")");
        String data = memory.readLine(mar.getAddress());
        int dataDecimal = Helper.sign_binToDec(data);
        dataDecimal = Math.abs(dataDecimal);
        ac.setData(Helper.decToSign_bin(-dataDecimal));
        System.out.println("Contents of AC after op = " + ac.getData() + " (" + Helper.sign_binToDec(ac.getData()) + ")");
    }

    void JUMP_MX_LEFT()  // JUMP M(X,0:19) - take next instruction from LHS of M[X]
    {
        System.out.println("JUMP M(X, 0:19)");
        ibr.setData(""); // empty the IBR
        pc.setAddress(mar.getAddress()); // jump to the new location
        System.out.println("PC changed to " + pc.getAddress() + " (" + Helper.sign_binToDec(pc.getAddress()) + ")");
    }

    void JUMP_MX_RIGHT() // JUMP M(X,20:39) - take next instruction from RHS of M[X]
    {
        System.out.println("JUMP M(X, 20:39)");
        ibr.setData(memory.readLine(mar.getAddress()).substring(20, 40)); // RHS instruction
        pc.setAddress(Helper.decToBin(Helper.binToDec(mar.getAddress()) + 1)); // go to (M[X]+1)th location
        System.out.println("PC changed to " + pc.getAddress() + " (" + Helper.sign_binToDec(pc.getAddress()) + ")");
    }

    void JUMP_posMX_LEFT() // JUMP+ M(X,0:19); if the number in AC is non negative then take instruction from LHS of M[X]
    {
        System.out.println("JUMP +M(X, 0:19)");
        int accumulatorData = Helper.sign_binToDec(ac.getData());
        System.out.println("Contents of AC = " + ac.getData() + " (" + Helper.sign_binToDec(ac.getData()) + ")");
        if (accumulatorData > 0) // positive
        {
            ibr.setData("");
            pc.setAddress(mar.getAddress());
        }
        System.out.println("PC changed to " + pc.getAddress() + " (" + Helper.sign_binToDec(pc.getAddress()) + ")");
    }

    void JUMP_posMX_RIGHT() // JUMP+ M(X,20:39); if the number in AC is non negative then take instruction from RHS of M[X]
    {
        System.out.println("JUMP +M(X, 20:39)");
        int accumulatorData = Helper.sign_binToDec(ac.getData());
        System.out.println("Contents of AC = " + ac.getData() + " (" + Helper.sign_binToDec(ac.getData()) + ")");
        if (accumulatorData > 0) // positive
        {
            ibr.setData(memory.readLine(mar.getAddress()).substring(20, 40)); // RHS instruction
            pc.setAddress(Helper.decToBin(Helper.binToDec(mar.getAddress()) + 1)); // go to (M[X]+1)th location
        }
        System.out.println("PC changed to " + pc.getAddress() + " (" + Helper.sign_binToDec(pc.getAddress()) + ")");
    }

    void ADD_MX() // ADD MX : add MX to AC; put the result in AC
    {
        System.out.println("ADD M[X]");
        System.out.println("Contents of M[X] = " + memory.readLine(mar.getAddress()) + " (" + Helper.sign_binToDec(memory.readLine(mar.getAddress())) + ")");
        if (ac.getData().length() == 0)
            System.out.println("Contents of AC before op = empty");
        else
            System.out.println("Contents of AC before op = " + ac.getData() + " (" + Helper.sign_binToDec(ac.getData()) + ")");
        ac.setData(Helper.decToSign_bin(Helper.sign_binToDec(ac.getData()) + Helper.sign_binToDec(memory.readLine(mar.getAddress()))));
        System.out.println("Contents of AC after op = " + ac.getData() + " (" + Helper.sign_binToDec(ac.getData()) + ")");
    }

    void ADD_MODMX() // ADD |MX| : add |MX| to AC; put the result in AC
    {
        System.out.println("ADD |M[X]|");
        System.out.println("Contents of M[X] = " + memory.readLine(mar.getAddress()) + " (" + Helper.sign_binToDec(memory.readLine(mar.getAddress())) + ")");
        if (ac.getData().length() == 0)
            System.out.println("Contents of AC before op = empty");
        else
            System.out.println("Contents of AC before op = " + ac.getData() + " (" + Helper.sign_binToDec(ac.getData()) + ")");
        int MXData = Helper.sign_binToDec(memory.readLine(mar.getAddress()));
        if (MXData > 0)
            ac.setData(Helper.decToSign_bin(Helper.sign_binToDec(ac.getData()) + Helper.sign_binToDec(memory.readLine(mar.getAddress()))));
        else
            ac.setData(Helper.decToSign_bin(Helper.sign_binToDec(ac.getData()) - Helper.sign_binToDec(memory.readLine(mar.getAddress()))));
        System.out.println("Contents of AC after op = " + ac.getData() + " (" + Helper.sign_binToDec(ac.getData()) + ")");
    }

    void SUB_MX() // SUB MX : subtract MX from AC; put the result in AC
    {
        System.out.println("SUB M[X]");
        System.out.println("Contents of M[X] = " + memory.readLine(mar.getAddress()) + " (" + Helper.sign_binToDec(memory.readLine(mar.getAddress())) + ")");
        if (ac.getData().length() == 0)
            System.out.println("Contents of AC before op = empty");
        else
            System.out.println("Contents of AC before op = " + ac.getData() + " (" + Helper.sign_binToDec(ac.getData()) + ")");
        ac.setData(Helper.decToSign_bin(Helper.sign_binToDec(ac.getData()) - Helper.sign_binToDec(memory.readLine(mar.getAddress()))));
        System.out.println("Contents of AC after op = " + ac.getData() + " (" + Helper.sign_binToDec(ac.getData()) + ")");
    }

    void SUB_MODMX() // SUB |MX| : subtract |MX| from AC; put the result in AC
    {
        System.out.println("SUB |M[X]|");
        System.out.println("Contents of M[X] = " + memory.readLine(mar.getAddress()) + " (" + Helper.sign_binToDec(memory.readLine(mar.getAddress())) + ")");
        if (ac.getData().length() == 0)
            System.out.println("Contents of AC before op = empty");
        else
            System.out.println("Contents of AC before op = " + ac.getData() + " (" + Helper.sign_binToDec(ac.getData()) + ")");
        int MXData = Helper.sign_binToDec(memory.readLine(mar.getAddress()));
        if (MXData > 0)
            ac.setData(Helper.decToSign_bin(Helper.sign_binToDec(ac.getData()) - Helper.sign_binToDec(memory.readLine(mar.getAddress()))));
        else
            ac.setData(Helper.decToSign_bin(Helper.sign_binToDec(ac.getData()) + Helper.sign_binToDec(memory.readLine(mar.getAddress()))));
        System.out.println("Contents of AC after op = " + ac.getData() + " (" + Helper.sign_binToDec(ac.getData()) + ")");
    }

    // MUL MX not implemented

    void DIV_MX() // DIV M(X): divide AC by M[X]; put the quotient in MQ and the remainder in AC
    {
        System.out.println("DIV MX: AC / MX");
        System.out.println("Put quotient in MQ and remainder in AC");
        System.out.println("Contents of AC before op = " + ac.getData() + " (" + Helper.sign_binToDec(ac.getData()) + ")");
        System.out.println("Contents of MX = " + memory.readLine(mar.getAddress()) + " (" + Helper.sign_binToDec(memory.readLine(mar.getAddress()))+ ")");
        mq.setData(Helper.decToSign_bin(Helper.sign_binToDec(ac.getData()) / Helper.sign_binToDec(memory.readLine(mar.getAddress()))));
        ac.setData(Helper.decToSign_bin(Helper.sign_binToDec(ac.getData()) % Helper.sign_binToDec(memory.readLine(mar.getAddress()))));
        System.out.println("Contents of MQ after op = " + mq.getData() + " (" + Helper.sign_binToDec(mq.getData()) + ")");
        System.out.println("Contents of AC after op = " + ac.getData() + " (" + Helper.sign_binToDec(ac.getData()) + ")");
    }

    void LSH() // LSH : multiply accumulator by 2
    {
        System.out.println("LSH: multiply accumulator by 2");
        System.out.println("Contents of AC before op = " + ac.getData() + " (" + Helper.sign_binToDec(ac.getData()) + ")");
        int ACData = Helper.sign_binToDec(ac.getData());
        ACData = ACData << 1;
        ac.setData(Helper.decToSign_bin(ACData));
        System.out.println("Contents of AC after op = " + ac.getData() + " (" + Helper.sign_binToDec(ac.getData()) + ")");
    }

    void RSH() // RSH : divide accumulator by 2
    {
        System.out.println("RSH: divide accumulator by 2");
        System.out.println("Contents of AC before op = " + ac.getData() + " (" + Helper.sign_binToDec(ac.getData()) + ")");
        int ACData = Helper.sign_binToDec(ac.getData());
        ACData = ACData >> 1;
        ac.setData(Helper.decToSign_bin(ACData));
        System.out.println("Contents of AC after op = " + ac.getData() + " (" + Helper.sign_binToDec(ac.getData()) + ")");
    }

    // STOR M(X, 8:19) not implemented
    // STOR M(X, 28:39) not implemented

    void HALT()
    {
        System.out.println("Halting now, bye!");
        System.out.println();
        System.out.println("Memory after operations...");
        halt = true; // setting the halt flag to true
        // printing the memory contents after operation
        for (int i = 0; i < memory.lines.size(); i++)
        {
            System.out.println("Line " + i + ": " + memory.lines.get(i));
        }
    }
}