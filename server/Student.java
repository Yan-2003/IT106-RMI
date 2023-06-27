import java.io.Serializable;


public class Student implements Serializable {
    private int ID;
    private int Age;
    private String Name;
    private String Address;
    private String Contact;

    public void setName(String Name){
        this.Name = Name;
    }
    public void setAddress(String Address){
        this.Address = Address;
    }
    public void setContact(String Contact){
        this.Contact = Contact;
    }
    public void setAge(int Age){
        this.Age = Age;
    }
    public void setID(int ID){
        this.ID = ID;
    }

    public String getName(){
        return this.Name;
    }

    public String getAddress(){
       return this.Address;
    }

    public String getContact(){
        return this.Contact;
    }

    public int getAge(){
        return this.Age;
    }

    public int getID(){
        return this.ID;
    }

}
