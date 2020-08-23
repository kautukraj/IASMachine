package IAS;
class IR
{
    String data; // contents of the IBR

    IR() // default constructor
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