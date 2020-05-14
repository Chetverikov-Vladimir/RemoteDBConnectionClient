import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class ClientMain {

    //Метод-прослойка необходим в связи со спецификой написания функций в Oracle
    //Уникальное имя удаленного объекта. Совпадает с именем на сервере
    private static final String UNIQUE_BINDING_NAME = "server.DBConnect";
    private static String result;

    public static String runConnect(String inputData,
                                    String inputPoints,
                                    String inputRollsPack,
                                    String hostName,
                                    String portNumber) throws RemoteException, NotBoundException {

        main(new String[]{inputData,inputPoints,inputRollsPack,hostName,portNumber});
        return result;
    }


    public static void main(String[] args) throws RemoteException, NotBoundException {

        //Проверяем на корректность, если нет - устанавливаем по умолчанию

        //дата в формате ГГГГММДД
        if(args[0].equals("00000000")){
            args[0]="replace(date_sub(current_date,1),\"-\",\"\")";
        }
        //Количество точек для преобразования Фурье
        if(args[1].equals("0")){
            args[1]="512";
        }
        //Объем пакета роликов
        if(args[2].equals(0)){
            args[2]="40";
        }
        //Адрес сервера
        if(args[3].equals("0")){
            args[3]="10.48.25.101";
        }
        //Порт подключения
        if(args[4].equals("0")){
            args[4]="1099";
        }


//Задаем хост и порт для подключения к удаленному серверу
        final Registry registry = LocateRegistry.getRegistry(args[3],Integer.parseInt(args[4]));
//Получаем объект-обертку
        DBConnection connect = (DBConnection) registry.lookup(UNIQUE_BINDING_NAME);
//Выполняем метод удаленного объекта
        result = connect.runConnect(args[0],args[1],args[2]);

    }
}
