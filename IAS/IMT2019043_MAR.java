package IAS;
class MAR
{
    String address; // address stored in the MAR
    PC pc;

    MAR(PC PC)
    {
        this.pc = PC;
        this.address = this.pc.getAddress(); // fetch the address from the PC
    }


    void setAddress(String address) // setter function
    {
        this.address = address;
    }

    String getAddress() // getter function
    {
        return this.address;
    }
}