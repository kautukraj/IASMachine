package IAS;
class PC
{
    String address; // address to which the PC points to

    PC() // default constructor
    {
        this.address = "0".repeat(12);
    }

    String getAddress() // getter function
    {
        return this.address;
    }

    void setAddress(String newAddress) // setter function
    {
        this.address = newAddress;
    }
}
