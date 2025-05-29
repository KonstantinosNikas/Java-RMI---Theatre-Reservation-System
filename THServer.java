import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class THServer {
    public static void main(String[] args) {
        try {
            THImpl obj = new THImpl();
            Registry registry = LocateRegistry.getRegistry();
            registry.rebind("Theatre", obj);
            System.out.println("Server is ready.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
