import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Map;
import java.util.Scanner;

public class THClient {
    public static void main(String[] args) {
        try {
            if (args.length < 1) {
                System.out.println("Χρήση:");
                System.out.println("java THClient list <hostname>");
                System.out.println("java THClient book <hostname> <type> <number> <name>");
                System.out.println("java THClient guests <hostname>");
                System.out.println("java THClient cancel <hostname> <type> <number> <name>");
                return;
            }

            String command = args[0];

            if (command.equals("list") && args.length == 2) {
                Registry registry = LocateRegistry.getRegistry(args[1]);
                THInterface stub = (THInterface) registry.lookup("Theatre");
                Map<String, String> list = stub.listAvailableSeats();
                list.forEach((k, v) -> System.out.println(v));
            } else if (command.equals("book") && args.length == 5) {
                Registry registry = LocateRegistry.getRegistry(args[1]);
                THInterface stub = (THInterface) registry.lookup("Theatre");
                String response = stub.bookSeats(args[2], Integer.parseInt(args[3]), args[4]);
                System.out.println(response);
                if (response.contains("θέλετε να τις κλείσετε")) {
                    Scanner sc = new Scanner(System.in);
                    if (sc.nextLine().equalsIgnoreCase("Ναι")) {
                        System.out.println(stub.bookSeats(args[2], seatsLeft(stub, args[2]), args[4]));
                    } else {
                        System.out.println("Δεν ολοκληρώθηκε κράτηση.");
                        System.out.println("Θέλετε ειδοποίηση αν ακυρωθούν; (Ναι/Όχι)");
                        if (sc.nextLine().equalsIgnoreCase("Ναι")) {
                            stub.registerNotification(args[2], args[4]);
                            System.out.println("Εγγραφή στη λίστα ειδοποιήσεων.");
                        }
                    }
                }
            } else if (command.equals("guests") && args.length == 2) {
                Registry registry = LocateRegistry.getRegistry(args[1]);
                THInterface stub = (THInterface) registry.lookup("Theatre");
                System.out.println(stub.guests());
            } else if (command.equals("cancel") && args.length == 5) {
                Registry registry = LocateRegistry.getRegistry(args[1]);
                THInterface stub = (THInterface) registry.lookup("Theatre");
                System.out.println(stub.cancelSeats(args[2], Integer.parseInt(args[3]), args[4]));
            } else {
                System.out.println("Μη έγκυρη εντολή.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static int seatsLeft(THInterface stub, String type) throws Exception {
        Map<String, String> list = stub.listAvailableSeats();
        String s = list.get(type);
        return Integer.parseInt(s.split(" ")[0]);
    }
}
