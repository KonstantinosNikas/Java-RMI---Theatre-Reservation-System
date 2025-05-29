import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.*;

public class THImpl extends UnicastRemoteObject implements THInterface {

    private final Map<String, Integer> seats = new HashMap<>();
    private final Map<String, Integer> prices = new HashMap<>();
    private final Map<String, List<String>> notifications = new HashMap<>();
    private final Map<String, Map<String, Integer>> bookings = new HashMap<>();

    public THImpl() throws RemoteException {
        seats.put("ΠΑ", 100);
        seats.put("ΠΒ", 200);
        seats.put("ΠΓ", 300);
        seats.put("ΚΕ", 250);
        seats.put("ΠΘ", 50);

        prices.put("ΠΑ", 50);
        prices.put("ΠΒ", 40);
        prices.put("ΠΓ", 30);
        prices.put("ΚΕ", 35);
        prices.put("ΠΘ", 25);

        notifications.put("ΠΑ", new ArrayList<>());
        notifications.put("ΠΒ", new ArrayList<>());
        notifications.put("ΠΓ", new ArrayList<>());
        notifications.put("ΚΕ", new ArrayList<>());
        notifications.put("ΠΘ", new ArrayList<>());

        System.out.println("Ο Server ξεκίνησε.");
    }

    @Override
    public synchronized Map<String, String> listAvailableSeats() throws RemoteException {
        Map<String, String> output = new LinkedHashMap<>();
        for (String type : seats.keySet()) {
            output.put(type, seats.get(type) + " θέσεις τύπου " + type + " - τιμή: " + prices.get(type) + " Ευρώ");
        }
        return output;
    }

    @Override
    public synchronized String bookSeats(String type, int number, String name) throws RemoteException {
        if (!seats.containsKey(type)) return "Μη έγκυρος τύπος θέσης.";

        int available = seats.get(type);
        if (available < number) {
            if (available > 0)
                return "Μόνο " + available + " διαθέσιμες θέσεις.\nΘέλετε να τις κλείσετε (Ναι/Όχι);";
            else
                return "Καμία διαθέσιμη θέση για τον τύπο: " + type;
        }

        seats.put(type, available - number);
        bookings.putIfAbsent(name, new HashMap<>());
        Map<String, Integer> userBookings = bookings.get(name);
        userBookings.put(type, userBookings.getOrDefault(type, 0) + number);
        return "Κράτηση επιτυχής. Συνολικό κόστος: " + (number * prices.get(type)) + " Ευρώ.";
    }

    @Override
    public synchronized String guests() throws RemoteException {
        StringBuilder sb = new StringBuilder();
        for (String guest : bookings.keySet()) {
            sb.append("Ο ").append(guest).append(" έχει:\n");
            int total = 0;
            for (Map.Entry<String, Integer> entry : bookings.get(guest).entrySet()) {
                int cost = entry.getValue() * prices.get(entry.getKey());
                sb.append("- ").append(entry.getValue()).append(" θέσεις τύπου ").append(entry.getKey())
                        .append(" (").append(cost).append("€)\n");
                total += cost;
            }
            sb.append("Συνολικό κόστος: ").append(total).append("€\n\n");
        }
        return sb.toString();
    }

    @Override
    public synchronized String cancelSeats(String type, int number, String name) throws RemoteException {
        if (!bookings.containsKey(name)) return "Δεν υπάρχει κράτηση για το όνομα " + name;
        Map<String, Integer> userBookings = bookings.get(name);
        if (!userBookings.containsKey(type) || userBookings.get(type) < number)
            return "Δεν υπάρχουν τόσες θέσεις για ακύρωση.";

        userBookings.put(type, userBookings.get(type) - number);
        seats.put(type, seats.get(type) + number);
        if (userBookings.get(type) == 0) userBookings.remove(type);

        // Ειδοποίηση πελατών που περιμένουν
        List<String> waiting = notifications.get(type);
        for (String client : waiting) {
            System.out.println(">> Υπάρχουν διαθέσιμες θέσεις για " + client + " στον τύπο: " + type);
        }
        notifications.get(type).clear();

        return "Ακύρωση επιτυχής. Υπόλοιπες κρατήσεις:\n" + guests();
    }

    @Override
    public synchronized boolean registerNotification(String type, String name) throws RemoteException {
        if (!notifications.containsKey(type)) return false;
        notifications.get(type).add(name);
        return true;
    }
}
