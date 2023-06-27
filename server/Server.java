import java.rmi.server.UnicastRemoteObject;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class Server{
	public static void main(String[] args){
		try{
			
			System.setProperty("java.rmi.server.hostname", "127.0.0.1");
			System.out.println("Server has been started...");

			
			StudentAPI std = new StudentAPI();

			DB stub1 = (DB) UnicastRemoteObject.exportObject(std, 0);

			Registry registry = LocateRegistry.createRegistry(9100);

			registry.rebind("d", stub1);

			System.out.println("Exporting and binding of Objects has been completed...");

		}catch(Exception e){
			System.out.println("Some server error..." + e);
		}
	}
}


// CLI Server - start rmiregistry 9100
// CLI Server - compile and run
// CLI Client - compile and run