package IAS;
class IBR
{
    String data; // contents of the IBR

    IBR() // default constructor
    {
        this.data = "";
    }

    void setData(String newData) // setter function
    {
        this.data = newData;
    }

    String getData() // getter function
    {
        return this.data;
    }

}