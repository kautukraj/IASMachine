package IAS;
class AC
{
    String data; // contents of the AC

    AC() // default constructor
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