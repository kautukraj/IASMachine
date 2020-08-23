package IAS;
class MQ // memory quotient
{
    String data; // contents of the MQ

    MQ() // default constructor
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