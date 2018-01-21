package ticketBooking;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Scanner;

/**
 * The Main Class of ReservationSystem.
 */
public class ReservationSystem extends SeatService{
    
    /**
     * Instantiates a new reservation system.
     *
     * @param filePath the data file path
     * @param isNewFile Whether create a empty new data file
     */
    public ReservationSystem(String filePath, boolean isNewFile) {
        super(filePath, isNewFile);
    }

    private static SeatService seatService = null;
    
    private final static String prompt = "Welcome to Air Tickest booking system\n" + "[P] Add Passenger\n"
            + "[G] Add Group\n" + "[C] Cancel Reservations\n" + "[A] Print Seating Availability Chart\n"
            + "[M] Print Manifest\n" + "[Q] Quit\n";


    /**
     * The main method.
     *
     * @param args
     * @throws IOException
     */
    public static void main(String[] args) throws IOException {
        int argsNum = args.length;
        if (argsNum == 0) {
            System.out.println("No data file detected, system will create a new data file.");
            System.out.print("Please input new data file path: ");
            InputStreamReader is_reader = new InputStreamReader(System.in);
            String dataPath = new BufferedReader(is_reader).readLine();
            seatService = new SeatService(dataPath, true);
        } else if (argsNum == 1) {
            SeatService.fromFile = true;
            System.out.println("The data file is: " + args[0]);
            seatService = new SeatService(args[0], false);
        } else {
            System.out.println("Incorrect parameters, the system will exit");
            return;
        }

        String userInput;
        System.out.print(prompt);
        boolean is_exit = false;

        while (!is_exit) {
            System.out.print("\nPlease input an opeartion: ");
            userInput = getInput(SeatService.fromFile);
            if (userInput.matches("P|G|C|A|M|Q")) {
                switch (userInput) {
                case ("Q"):
                    is_exit = true;
                    System.out.println("System exited");
                    break;
                case ("P"):
                    seatService.addSeat();
                    break;
                case ("G"):
                    seatService.groupBooking();
                    break;
                case ("A"):
                    seatService.printAvailSeats();
                    break;
                case ("M"):
                    seatService.printManifest();
                    break;
                case ("C"):
                    seatService.cancelSeat();
                    break;
                default:
                    break;
                }
            } else {
                System.out.println("Unrecognized operation, please check your input.");
            }
        }
    }
}
