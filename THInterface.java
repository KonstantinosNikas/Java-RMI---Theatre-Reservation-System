import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.Map;
import java.util.List;

public interface THInterface extends Remote {
    Map<String, String> listAvailableSeats() throws RemoteException;
    String bookSeats(String type, int number, String name) throws RemoteException;
    String guests() throws RemoteException;
    String cancelSeats(String type, int number, String name) throws RemoteException;
    boolean registerNotification(String type, String name) throws RemoteException;
}
