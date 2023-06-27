import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;

public interface DB extends Remote {
    public abstract ArrayList<Student> getQueryResult(String query) throws RemoteException;
    public abstract void QueryBuild(String query)throws RemoteException;
}
