import java.rmi.RemoteException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

public class StudentAPI implements DB{
    
    MySql MyStudent = new MySql();

    StudentAPI(){
        MyStudent.setUser("root");
        MyStudent.setHost("localhost:3308");
        MyStudent.setDBname("my-student");
        MyStudent.setPassword("");
    }

    @Override
    public ArrayList<Student> getQueryResult(String query) throws RemoteException {
        ArrayList<Student> list = new ArrayList<>();
        try {
            Class.forName(MyStudent.getDriver());
            Connection con = DriverManager.getConnection(MyStudent.getConnectionDB(),MyStudent.getUser(),MyStudent.getPassword());
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            while(rs.next()){
                Student object = new Student();
                object.setAddress(rs.getString(4));
                object.setAge(rs.getInt(3));
                object.setName(rs.getString(2));
                object.setID(rs.getInt(1));
                object.setContact(rs.getString(5));
                System.out.println("Query Successfull....");
                list.add(object);
            }
            con.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public void QueryBuild(String query) {
         try {
            Class.forName(MyStudent.getDriver());
            Connection con = DriverManager.getConnection(MyStudent.getConnectionDB(),MyStudent.getUser(),MyStudent.getPassword());
            PreparedStatement statement = con.prepareStatement(query);
            statement.executeUpdate();
            statement.close();
            con.close();
            System.out.println("Query sucssesfully execute....");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    
}
