import java.rmi.Remote;
import java.rmi.RemoteException;

public interface DBConnection extends Remote {
    String runConnect(String inputData, String inputPoints, String inputRollsPack) throws RemoteException;
}
