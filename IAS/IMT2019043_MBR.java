package IAS;
class MBR
{
    String data; // contents of the MBR

    MBR () // default constructor
    {
        this.data = "";
    }

    String getData() // getter function
    {
        return this.data;
    }

    void setData(String newData) // setter function
    {
        this.data = newData;
    }
}