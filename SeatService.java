package ticketBooking;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * SeatService class contains the core logic of system.
 */
public class SeatService {

    /** Whether read from existing data file. */
    protected static boolean fromFile = false;
    
    /** The first class seats. */
    private static List<Seat> firstClassSeats = new ArrayList<>();
    
    /** The economy class seats. */
    private static List<Seat> economyClassSeats = new ArrayList<>();
    
    /** The operation list. */
    private static List<String> operationList = new ArrayList<>();
    
    /** The operation counter. */
    private static int operationCounter = 0;
    
    /** The number of first class row. */
    private final int numberOfFirstRow = 2;
    
    /** The number of first class column. */
    private final int numberOfFirstCol = 4;
    
    /** The number of economy class row start. */
    private final int numberOfEconomyRowStart = 10;
    
    /** The number of economy class row. */
    private final int numberOfEconomyRow = 20;
    
    /** The number of economy class column. */
    private final int numberOfEconomyCol = 6;

    /** The new customer name. */
    private String newName;
    
    /** The new seat class. */
    private String newSeatClass;
    
    /** The new seat preference. */
    private String newSeatPreference;
    
    /** The data file path. */
    private static String dataFilePath = null;

    /**
     * Instantiates a new seat service.
     *
     * @param filePath the data file path
     * @param isNewFile whether read from file
     */
    public SeatService(String filePath, boolean isNewFile) {
        initSeat(filePath, isNewFile);
    }

    /**
     * Inits the seat array.
     *
     * @param filePath the data file path
     * @param isNewFile whether read from file
     */
    private void initSeat(String filePath, boolean isNewFile) {

        if (isNewFile) {
            File file = new File(filePath);
            Seat seat;
            if (!file.exists()) {
                // init all seats
                // init first class
                for (int i = 1; i < 1 + numberOfFirstRow; i++) {
                    for (int j = 0; j < numberOfFirstCol; j++) {
                        Character seatCol = (char) ('A' + j);
                        if (seatCol.equals('A') || seatCol.equals('D')) {
                            seat = new Seat(i, seatCol, SeatClass.FIRST, SeatPreference.Window);
                        } else {
                            seat = new Seat(i, seatCol, SeatClass.FIRST, SeatPreference.Aisle);
                        }
                        firstClassSeats.add(seat);
                    }
                }
                // init economy class
                for (int i = numberOfEconomyRowStart; i < numberOfEconomyRowStart + numberOfEconomyRow; i++) {
                    for (int j = 0; j < numberOfEconomyCol; j++) {
                        Character seatCol = (char) ('A' + j);
                        if (seatCol.equals('A') || seatCol.equals('F')) {
                            seat = new Seat(i, seatCol, SeatClass.ECONOMY, SeatPreference.Window);
                        } else if (seatCol.equals('B') || seatCol.equals('E')) {
                            seat = new Seat(i, seatCol, SeatClass.ECONOMY, SeatPreference.Center);
                        } else {
                            seat = new Seat(i, seatCol, SeatClass.ECONOMY, SeatPreference.Aisle);
                        }
                        economyClassSeats.add(seat);
                    }
                }
                try {
                    file.createNewFile();
                    ObjectOutputStream os = new ObjectOutputStream(new FileOutputStream(filePath));
                    os.writeObject(firstClassSeats);
                    os.writeObject(economyClassSeats);
                    os.writeObject(operationList);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                    System.exit(-1);
                } catch (IOException e) {
                    e.printStackTrace();
                    System.exit(-1);
                }
                // save data path
                this.dataFilePath = filePath;
            } else {
                System.out.println("File is alreay existing, system will exit.");
                System.exit(-1);
            }
        } else {
            try {
                File file = new File(filePath);
                if (file.exists()) {
                    ObjectInputStream is = new ObjectInputStream(new FileInputStream(file));
                    firstClassSeats = (List<Seat>) is.readObject();
                    economyClassSeats = (List<Seat>) is.readObject();
                    operationList = (List<String>) is.readObject();
                    // save data path
                    this.dataFilePath = filePath;
                } else {
                    file.createNewFile();
                    this.dataFilePath = filePath;
                    ObjectInputStream is = new ObjectInputStream(System.in);
                    firstClassSeats = (List<Seat>) is.readObject();
                    economyClassSeats = (List<Seat>) is.readObject();
                    operationList = (List<String>) is.readObject();  
                    System.out.println(operationList.size());
                }
            } catch (IOException e) {
                e.printStackTrace();
                System.exit(-1);
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
                System.exit(-1);
            }
        }
    }

    /**
     * Gets the user input.
     *
     * @param fromFile whether read operation from file
     * @return the input
     */
    // get input
    public static String getInput(boolean fromFile) {
        if (!fromFile) {
            InputStreamReader is_reader = new InputStreamReader(System.in);
            String retStr = null;
            try {
                retStr = new BufferedReader(is_reader).readLine();
                operationList.add(retStr);
                ObjectOutputStream os = new ObjectOutputStream(new FileOutputStream(dataFilePath));
                os.writeObject(firstClassSeats);
                os.writeObject(economyClassSeats);
                os.writeObject(operationList);
            } catch (IOException e) {
                e.printStackTrace();

            }
            return retStr;
        } else {
            String operation = operationList.get(operationCounter);
            System.out.println(operation);
            operationCounter++;
            return operation;
        }
    }

    /**
     * Book single seat.
     */
    public void addSeat() {
        printBookingForm();
        Seat seat = findAEmptySeat(SeatClass.fromValue(newSeatClass), SeatPreference.fromValue(newSeatPreference));
        if (seat == null) {
            seat = findOtherEmptySeat(SeatClass.fromValue(newSeatClass), SeatPreference.fromValue(newSeatPreference));
            if (seat == null) {
                System.out.println("Sorry, system cannot find proper seat for you");
            } else {
                Customer customer = new Customer(newName, null, seat);
                seat.setCustomer(customer);
                seat.setBooked(true);
                System.out.println(
                        "Name: " + customer.getName() + " " + "Class: " + customer.getSeat().getSeatType().toString()
                                + " Preference: " + customer.getSeat().getSeatPreference().toString());
            }
        } else {
            Customer customer = new Customer(newName, null, seat);
            seat.setCustomer(customer);
            seat.setBooked(true);
            System.out.println("Name: " + customer.getName() + " " + " Service Class: "
                    + customer.getSeat().getSeatType().toString() + " Seat Preference: "
                    + customer.getSeat().getSeatPreference().toString());
        }
        // write to data file
        try {
            // File file = new File(dataFilePath);
            ObjectOutputStream os = new ObjectOutputStream(new FileOutputStream(dataFilePath));
            os.writeObject(firstClassSeats);
            os.writeObject(economyClassSeats);
            os.writeObject(operationList);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Prints the single booking form.
     */
    private void printBookingForm() {
        // print booking form
        System.out.println("--- Ticket booking form ----");

        System.out.print("Please input passenger name: ");
        newName = getInput(SeatService.fromFile);
        newSeatClass = null;
        newSeatPreference = null;

        while (true) {
            System.out.print("Please input seat class: ");
            newSeatClass = getInput(SeatService.fromFile);
            if (!newSeatClass.matches("First|Economy")) {
                System.out.println("Not a valid seat class, valid value is \"First\" or \"Economy\"");
            } else {
                break;
            }
        }
        while (true) {
            System.out.print("Please input preference: ");
            newSeatPreference = getInput(SeatService.fromFile);
            if (!newSeatPreference.matches("Window|Center|Aisle")) {
                System.out.println("Not a valid preference, valid value is \"Window\" or \"Center\" or \"Aisle\"");
            } else if (newSeatClass.equals("First") && newSeatPreference.equals("Center")) {
                System.out.println("Sorry, first class doesn't has \"Center\" seat");
            } else {
                break;
            }
        }
    }

    /**
     * Find A empty seat.
     *
     * @param seatClass the seat class
     * @param preference the seat preference
     * @return the seat
     */
    private Seat findAEmptySeat(SeatClass seatClass, SeatPreference preference) {
        if (seatClass.equals(SeatClass.FIRST)) {
            for (Seat seat : firstClassSeats) {
                // book a seat
                if (seat.getSeatPreference().equals(preference) && !seat.isBooked()) {
                    return seat;
                } else {
                    continue;
                }
            }
        } else { // search economy class seats
            for (Seat seat : economyClassSeats) {
                // book a seat
                if (seat.getSeatPreference().equals(preference) && !seat.isBooked()) {
                    return seat;
                } else {
                    continue;
                }
            }
        }
        return null;
    }

    /**
     * Find other empty seat if there is no user preferred choice.
     *
     * @param seatClass the seat class
     * @param preference the seat preference
     * @return the seat
     */
    private Seat findOtherEmptySeat(SeatClass seatClass, SeatPreference preference) {

        System.out.println("Sorry, No seat for class " + seatClass.getValue() + " and preference " + preference);
        System.out.print("Do you want to try other preference? <Y/N> ");
        String check = getInput(SeatService.fromFile);
        if (check.equals("Y")) {
            Integer otherPreference = preference.getValue();
            if (seatClass.equals(SeatClass.FIRST)) {
                otherPreference = otherPreference - 1;
                Seat seat = findAEmptySeat(seatClass, SeatPreference.fromValue(Math.abs(otherPreference)));
                return seat;
            } else {
                // preference = 0 or 2
                if (otherPreference == 0 || otherPreference == 2) {
                    for (int i = 0; i < 2; i++) {
                        otherPreference = otherPreference - 1;
                        Seat seat = findAEmptySeat(seatClass, SeatPreference.fromValue(Math.abs(otherPreference)));
                        if (seat != null) {
                            return seat;
                        }
                    }
                } else {
                    // preference = 1
                    Seat seat = findAEmptySeat(seatClass, SeatPreference.fromValue(Math.abs(otherPreference - 1)));
                    if (seat != null) {
                        return seat;
                    } else {
                        seat = findAEmptySeat(seatClass, SeatPreference.fromValue(Math.abs(otherPreference + 1)));
                        if (seat != null) {
                            return seat;
                        }
                    }
                }
            }
        }

        return null;
    }

    /**
     * Prints all available seats.
     */
    public void printAvailSeats() {
        StringBuilder firstString = new StringBuilder();
        int currRow = 0;
        int numberOfFirstAvail = 0;
        int numberOfEconomyAvail = 0;
        for (int index = 0; index < firstClassSeats.size(); index++) {
            Seat seat = firstClassSeats.get(index);
            if (!seat.isBooked()) {
                numberOfFirstAvail++;
            }
        }
        for (int index = 0; index < economyClassSeats.size(); index++) {
            Seat seat = economyClassSeats.get(index);
            if (!seat.isBooked()) {
                numberOfEconomyAvail++;
            }
        }
        firstString.append("First ");
        if (numberOfFirstAvail > 0) {
            for (int index = 0; index < firstClassSeats.size(); index++) {
                int tempRow = index / numberOfFirstCol + 1;
                if (currRow != tempRow) {
                    currRow = tempRow;
                    firstString.deleteCharAt(firstString.length() - 1);
                    firstString.append("  ").append(String.valueOf(currRow) + ": ");
                }
                Seat seat = firstClassSeats.get(index);
                if (!seat.isBooked()) {
                    firstString.append(seat.getCol()).append(",");
                }
            }
            firstString.deleteCharAt(firstString.length() - 1);
        } else {
            firstString.append(" : no available first class seat ");
        }
        System.out.println(firstString.toString());

        StringBuilder economyString = new StringBuilder();
        currRow = 0;
        economyString.append("Economy ");
        if (numberOfEconomyAvail > 0) {
            for (int index = 0; index < economyClassSeats.size(); index++) {
                int tempRow = index / numberOfEconomyCol + 1;
                if (currRow != tempRow) {
                    currRow = tempRow;
                    economyString.deleteCharAt(economyString.length() - 1);
                    if (tempRow % 6 == 0) {
                        economyString.append("\n");
                    }
                    economyString.append("  ").append(String.valueOf(currRow) + ":");
                }
                Seat seat = economyClassSeats.get(index);
                if (!seat.isBooked()) {
                    economyString.append(seat.getCol()).append(",");
                }
            }
            economyString.deleteCharAt(economyString.length() - 1);
        } else {
            economyString.append(" : no available economy class seat ");
        }
        System.out.println(economyString.toString());
    }

    /**
     * Prints the manifest list.
     */
    public void printManifest() {
        int numberOfFirstUser = 0;
        int numberOfEconomyUser = 0;
        for (int index = 0; index < firstClassSeats.size(); index++) {
            Seat seat = firstClassSeats.get(index);
            if (seat.isBooked()) {
                numberOfFirstUser++;
            }
        }
        for (int index = 0; index < economyClassSeats.size(); index++) {
            Seat seat = economyClassSeats.get(index);
            if (seat.isBooked()) {
                numberOfEconomyUser++;
            }
        }

        StringBuilder firstString = new StringBuilder();
        int currRow = 0;
        firstString.append("First ");
        if (numberOfFirstUser > 0) {
            for (int index = 0; index < firstClassSeats.size(); index++) {
                int tempRow = index / numberOfFirstCol + 1;
                if (currRow != tempRow) {
                    currRow = tempRow;
                    firstString.deleteCharAt(firstString.length() - 1);
                    firstString.append("  ").append(String.valueOf(currRow) + ": ");
                }
                Seat seat = firstClassSeats.get(index);
                if (seat.isBooked()) {
                    firstString.append(String.valueOf(tempRow)).append(seat.getCol()).append(",");
                }
            }
            firstString.deleteCharAt(firstString.length() - 1);
        } else {
            firstString.append(" : no passenger booked first class seats ");
        }
        System.out.println(firstString.toString());

        StringBuilder economyString = new StringBuilder();
        currRow = 0;
        economyString.append("Economy ");
        if (numberOfEconomyUser > 0) {
            for (int index = 0; index < economyClassSeats.size(); index++) {
                int tempRow = index / numberOfEconomyCol + 1;
                if (currRow != tempRow) {
                    currRow = tempRow;
                    economyString.deleteCharAt(economyString.length() - 1);
                    if (tempRow % 6 == 0) {
                        economyString.append("\n");
                    }
                    economyString.append("  ").append(String.valueOf(currRow) + ": ");
                }
                Seat seat = economyClassSeats.get(index);
                if (seat.isBooked()) {
                    economyString.append(String.valueOf(tempRow)).append(seat.getCol()).append(",");
                }
            }
            economyString.deleteCharAt(economyString.length() - 1);
        } else {
            economyString.append(" : no passenger booked economy class seats ");
        }
        System.out.println(economyString.toString());
    }

    /**
     * Cancel seat.
     */
    public void cancelSeat() {

        Map<String, String> ret = printCancelForm();
        cancelPassenger(ret);
        // write to data file
        try {
            File file = new File(dataFilePath);
            file.createNewFile();
            ObjectOutputStream os = new ObjectOutputStream(new FileOutputStream(dataFilePath));
            os.writeObject(firstClassSeats);
            os.writeObject(economyClassSeats);
            os.writeObject(operationList);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Cancel a passenger booking.
     *
     * @param Map<String, String> input
     */
    private void cancelPassenger(Map<String, String> input) {
        String operation = input.get("operation");
        if (operation.equals("single")) {
            String username = input.get("name");
            for (int i = 0; i < firstClassSeats.size(); i++) {
                if (firstClassSeats.get(i).isBooked()) {
                    if (firstClassSeats.get(i).getCustomer().getName().equals(username)) {
                        firstClassSeats.get(i).setBooked(false);
                        firstClassSeats.get(i).setCustomer(null);
                        System.out.println("Passenger has been cancelled successfully");
                        return;
                    }
                }
            }
            for (int i = 0; i < economyClassSeats.size(); i++) {
                if (economyClassSeats.get(i).isBooked()) {
                    if (economyClassSeats.get(i).getCustomer().getName().equals(username)) {
                        economyClassSeats.get(i).setBooked(false);
                        economyClassSeats.get(i).setCustomer(null);
                        System.out.println("Passenger has been cancelled successfully");
                        return;
                    }
                }
            }
            System.out.println("System doesn't find this passenger");
        } else {
            String group = input.get("name");
            int groupCount = 0;
            for (int i = 0; i < firstClassSeats.size(); i++) {
                if (firstClassSeats.get(i).isBooked()) {
                    if (firstClassSeats.get(i).getCustomer().getGroup() == null) {
                        continue;
                    }
                    if (firstClassSeats.get(i).getCustomer().getGroup().equals(group)) {
                        firstClassSeats.get(i).setBooked(false);
                        firstClassSeats.get(i).setCustomer(null);
                        groupCount++;
                    }
                }
            }
            for (int i = 0; i < economyClassSeats.size(); i++) {
                if (economyClassSeats.get(i).isBooked()) {
                    if (economyClassSeats.get(i).getCustomer().getGroup() == null) {
                        continue;
                    }
                    if (economyClassSeats.get(i).getCustomer().getName().equals(group)) {
                        economyClassSeats.get(i).setBooked(false);
                        economyClassSeats.get(i).setCustomer(null);
                        groupCount++;
                    }
                }
            }
            if (groupCount == 0) {
                System.out.println("System doesn't find this group");
            } else {
                System.out.println("Group has been cancelled successfully");
            }
        }

    }

    /**
     * Prints the cancel form.
     *
     * @return Map<String, String>
     */
    private Map<String, String> printCancelForm() {
        String cond;
        while (true) {
            System.out.print("Do you want to cancel a group? <Y/N>");
            cond = getInput(SeatService.fromFile);
            if (cond.matches("Y|N")) {
                break;
            } else {
                System.out.println("Invalid input, please re-input");
            }
        }
        if (cond.equals("N")) {
            // cancel a person
            System.out.print("Please input passenger name: ");
            String name = getInput(SeatService.fromFile);
            Map<String, String> retMap = new HashMap<>();
            retMap.put("name", name);
            retMap.put("operation", "single");
            return retMap;
        } else {
            // cancel a group
            System.out.print("Please input group name: ");
            String group = getInput(SeatService.fromFile);
            Map<String, String> retMap = new HashMap<>();
            retMap.put("name", group);
            retMap.put("operation", "group");
            return retMap;
        }
    }

    /**
     * Group booking entry function.
     */
    public void groupBooking() {
        List<Customer> userList = processGroupBookingForm();
        List<SeatCapacity> seatCapacityList = null;
        if (userList.size() > 0) {
            // book first
            if (userList.get(0).getUserClass().equals(SeatClass.FIRST)) {
                seatCapacityList = getGroupCapacity(SeatClass.FIRST);
                if (!checkGroupCapacity(userList, seatCapacityList)) {
                    System.out.println("Sorry, the plane doesn't has enough space for the group");
                } else {
                    bookAGroup(userList, seatCapacityList);
                }
            } else { // book economy
                seatCapacityList = getGroupCapacity(SeatClass.ECONOMY);
                if (!checkGroupCapacity(userList, seatCapacityList)) {
                    System.out.println("Sorry, the plane doesn't has enough space for the group");
                } else {
                    bookAGroup(userList, seatCapacityList);
                }
            }
        }
        // write to data file
        try {
            File file = new File(dataFilePath);
            file.createNewFile();
            ObjectOutputStream os = new ObjectOutputStream(new FileOutputStream(dataFilePath));
            os.writeObject(firstClassSeats);
            os.writeObject(economyClassSeats);
            os.writeObject(operationList);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Group booking logic.
     *
     * @param List<Customer>userList the passenger list
     * @param List<SeatCapacity>seatCapacityList the seat capacity list
     */
    private void bookAGroup(List<Customer> userList, List<SeatCapacity> seatCapacityList) {
        SeatClass seatClass = userList.get(0).getUserClass();
        int userPos = 0;

        for (SeatCapacity seatCapacity : seatCapacityList) {
            int rowNumber = seatCapacity.getRowNumber();
            for (int i = 0; i < seatCapacity.getCapacity(); i++) {
                // calc start row
                if (seatClass.equals(SeatClass.FIRST)) {
                    int startInArray = (rowNumber - 1) * numberOfFirstCol;
                    for (int j = startInArray; j < startInArray + numberOfFirstCol; j++) {
                        if (!firstClassSeats.get(j).isBooked) {
                            firstClassSeats.get(j).isBooked = true;
                            firstClassSeats.get(j).setGroup(userList.get(userPos).getGroup());
                            firstClassSeats.get(j).setCustomer(userList.get(userPos));
                            userPos++;
                            if (userPos == userList.size()) {
                                return;
                            }
                        }
                    }
                } else {
                    int startInArray = (rowNumber - 1) * numberOfFirstCol;
                    for (int j = startInArray; j < startInArray + numberOfFirstCol; j++) {
                        if (!economyClassSeats.get(j).isBooked) {
                            economyClassSeats.get(j).isBooked = true;
                            economyClassSeats.get(j).setGroup(userList.get(userPos).getGroup());
                            economyClassSeats.get(j).setCustomer(userList.get(userPos));
                            userPos++;
                            if (userPos == userList.size()) {
                                return;
                            }
                        }
                    }
                }
            }
        }
    }

    /**
     * Check group capacity.
     *
     * @param List<Customer>userList the user list
     * @param List<SeatCapacity>seatCapacityList the seat capacity list
     * @return true, if successful
     */
    private boolean checkGroupCapacity(List<Customer> userList, List<SeatCapacity> seatCapacityList) {
        Integer totalCapacity = 0;
        for (SeatCapacity seatCapacity : seatCapacityList) {
            totalCapacity = totalCapacity + seatCapacity.getCapacity();
        }

        return (totalCapacity < userList.size()) ? false : true;
    }

    /**
     * Gets the group capacity.
     *
     * @param seatClass the seat class
     * @return List<SeatCapacity> the group capacity
     */
    private List<SeatCapacity> getGroupCapacity(SeatClass seatClass) {
        Integer rowCapacity = 0;
        Integer index = 0;
        if (seatClass.equals(SeatClass.FIRST)) {
            List<SeatCapacity> firstCapacity = new ArrayList<>();
            for (int i = 1; i < 1 + numberOfFirstRow; i++) {
                for (int j = 0; j < numberOfFirstCol; j++) {
                    if (!firstClassSeats.get(index).isBooked()) {
                        rowCapacity++;
                    }
                    index++;
                }
                firstCapacity.add(new SeatCapacity(i, rowCapacity));
                rowCapacity = 0;
            }
            Collections.sort(firstCapacity);
            return firstCapacity;
        } else {
            List<SeatCapacity> economyCapacity = new ArrayList<>();
            for (int i = 1; i < 1 + numberOfEconomyRow; i++) {
                for (int j = 0; j < numberOfEconomyCol; j++) {
                    if (!economyClassSeats.get(index).isBooked()) {
                        rowCapacity++;
                    }
                    index++;
                }
                economyCapacity.add(new SeatCapacity(i, rowCapacity));
                rowCapacity = 0;
            }
            Collections.sort(economyCapacity);
            return economyCapacity;
        }
    }

    /**
     * Process group booking form.
     *
     * @return List<Customer> the list of customer
     */
    private List<Customer> processGroupBookingForm() {
        // print booking form
        System.out.println("--- Group Ticket booking form ----");

        System.out.print("Please input group name: ");
        String groupName = getInput(SeatService.fromFile);
        System.out.print("Please input passenger name list: ");
        String nameList = getInput(SeatService.fromFile);
        String userClass;

        while (true) {
            System.out.print("Please input seat class: ");
            userClass = getInput(SeatService.fromFile);
            if (!userClass.matches("First|Economy")) {
                System.out.println("Not a valid seat class, valid value is \"First\" or \"Economy\"");
            } else {
                break;
            }
        }

        List<Customer> userList = new ArrayList<>();
        String[] users = nameList.split(",");
        for (int index = 0; index < users.length; index++) {
            String name = users[index];
            Customer customer = new Customer(name, groupName, SeatClass.fromValue(userClass));
            userList.add(customer);
        }

        return userList;
    }
}
