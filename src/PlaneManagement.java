import java.io.File;
import java.util.Scanner;
import java.util.InputMismatchException;

public class PlaneManagement {

    private static int[] rowA = {0,0,0,0,0,0,0,0,0,0,0,0,0,0};
    private static int[] rowB = {0,0,0,0,0,0,0,0,0,0,0,0};
    private static  int[] rowC = {0,0,0,0,0,0,0,0,0,0,0,0};
    private static int[] rowD = {0,0,0,0,0,0,0,0,0,0,0,0,0,0};

    private static Ticket[] tickets = new Ticket[52];

    private static int tickets_Count = 0;

    public static void main(String[] args){
        Scanner scanner = new Scanner(System.in);
        System.out.println("Welcome to Plane Management application");
        int option;
        do {
            DisplayMenu();
            try {
                option = scanner.nextInt();

                switch (option) {
                    case 1:
                        buySeat(scanner);
                        break;
                    case 2:
                        cancelSeat(scanner);
                        break;
                    case 3:
                        findFirstAvailable();
                        break;
                    case 4:
                        showSeatingPlan();
                        break;
                    case 5:
                        print_tickets_info();
                        break;
                    case 6:
                        search_ticket(scanner);
                    case 0:
                        System.out.println("Quit");
                        break;
                    default:
                        System.out.println("Invalid option");
                }
            }catch (InputMismatchException e){
                System.out.println("Invalid input. Please enter a valid integer.");
                scanner.nextLine();   // clear the invalid input
                option = -1;     // To continue the loop
            }

        }while (option != 0);
        scanner.close();

    }

    private static void DisplayMenu() {
        for (int i = 0; i<51; i++){
            System.out.print("*");
        }
        System.out.println("\n*                MENU OPTIONS                     *");
        for (int n = 0; n<51; n++){
            System.out.print("*");
        }
        System.out.println("\n      1) Buy a seat");
        System.out.println("      2) Cancel a seat");
        System.out.println("      3) Find first available seat");
        System.out.println("      4) Show seating plan ");
        System.out.println("      5) Print tickets information and total sales ");
        System.out.println("      6) Search ticket");
        System.out.println("      0) Quit");

        for (int m = 0; m<51; m++){
            System.out.print("*");
        }
        System.out.println("\nPlease select an option: ");
    }
    // Buy seat method collects Row letter and Seat number & Personal information
    public static void buySeat(Scanner scanner) {
        try {

            System.out.print("Enter row letter (A/B/C/D): ");
            char row_letter = scanner.next().toUpperCase().charAt(0);

            if (row_letter < 'A' || row_letter > 'D') {
                System.out.println("Invalid row letter. Please try again");
                return;
            }
            System.out.print("Enter seat number: ");
            int seat_number = scanner.nextInt();

            if ((row_letter == 'A' && (seat_number > 14 || seat_number<=0)) || (row_letter == 'B' && (seat_number > 12 || seat_number<=0)) || (row_letter == 'C' && (seat_number > 12 || seat_number<=0)) || (row_letter== 'D' && (seat_number >14 || seat_number<=0))) {
                System.out.println("Invalid Input!. Out of range! ");
                return;
            }
            int[] selectedRow;
            switch (row_letter) {
                case 'A':
                    selectedRow = rowA;
                    break;
                case 'B':
                    selectedRow = rowB;
                    break;
                case 'C':
                    selectedRow = rowC;
                    break;
                case 'D':
                    selectedRow = rowD;
                    break;
                default:
                    System.out.println("Invalid row. Please try again");
                    return;

            }
            if (selectedRow[seat_number - 1] == 0) {
                selectedRow[seat_number - 1] = 1;

                // Getting Personal information

                System.out.println("Enter person's name: ");
                String name = scanner.next();

                System.out.println("Enter person's Surname: ");
                String surname = scanner.next();

                System.out.println("Enter Person's email: ");
                String email = scanner.next();

                Person person = new Person(name, surname, email);
                double price = calculate_price(row_letter, seat_number);
                Ticket ticket = new Ticket(row_letter, seat_number,price, person);
                if (tickets_Count >= 52) {
                    System.out.println("Sorry , the maximum number of tickets has been reached.");
                    return;
                }
                tickets[tickets_Count] = ticket;
                tickets_Count++;
                ticket.save();


                System.out.println("Seat " + row_letter + seat_number +" "+ "booked");

            } else {
                System.out.println("Seat " + row_letter + seat_number +" "+ "is already booked");

            }

        } catch (InputMismatchException e) {
            System.out.println("Invalid input. Please enter valid data.");
            scanner.nextLine();
        }
    }
    private static double calculate_price(char row_letter , int seat_number){
        double price;
        switch (row_letter){
            case 'A':
                if (seat_number >=1 && seat_number<=14 ){
                    if (seat_number>=1 && seat_number<=5){
                        price =   200.0;
                    }else if (seat_number>=6 && seat_number<=9) {
                        price = 150.0;
                    } else {
                        price = 180.0;
                    }
                }else {
                    price = 0.0;   // Invalid seat number for row A
                }
                break;
            case 'B':
            case 'C':
            case 'D':
                if (seat_number>= 1 && seat_number <= 14){
                    if (seat_number >=1 && seat_number<=5){
                        price = 200.0;
                    }else if (seat_number >= 6 && seat_number<=9){
                        price = 150.0;
                    } else {
                        price = 180.0;
                    }
                }else {
                    price = 0.0; // Invalid seat
                }
                break;
            default:
                price = 0.0;  // Invalid row letter
        }
        return price;
    }

    private static void cancelSeat(Scanner scanner){
        try {
            System.out.println("Enter row letter(A/B/C/D) : ");
            char row_letter = scanner.next().toUpperCase().charAt(0);

            if (row_letter < 'A' || row_letter >'D') {
                System.out.println("Invalid row letter. Please try again.");
                return;
            }
            System.out.println("Enter seat number: ");
            int seat_number = scanner.nextInt();

            int[] selectedRow;
            switch (row_letter) {
                case 'A':
                    selectedRow = rowA;
                    break;
                case 'B':
                    selectedRow = rowB;
                    break;
                case 'C':
                    selectedRow = rowC;
                    break;
                case 'D':
                    selectedRow =rowD;
                    break;
                default:
                    System.out.println("Invalid row. Please try again");
                    return;

            }
            if (seat_number< 1 || seat_number> selectedRow.length){
                System.out.println("Invalid seat number for this row. Try again.");
                return;
            }
            if (selectedRow[seat_number - 1] == 1) {
                selectedRow[seat_number - 1] = 0;

                System.out.println("Seat " + row_letter + seat_number +" "+ "canceled");

                // remove the corresponding ticket
                for (int i = 0; i<tickets_Count; i++){
                    if (tickets[i].getRow() == row_letter && tickets[i].getSeat() == seat_number){

                        String file_name = row_letter + Integer.toString(seat_number)+ ".txt";
                        File file = new File(file_name);
                        if (file.exists()) {
                            if (file.delete()) {
                                System.out.println("File deleted: " + file_name);
                            }else {
                                System.out.println("Fail to delete:" + file_name);
                            }
                        }else{
                            System.out.println("File not found: " + file_name);
                        }

                        for (int j = i; j< tickets_Count - 1; j++){
                            tickets[j] = tickets[j + 1];
                        }
                        tickets[tickets_Count - 1] = null;
                        tickets_Count--;
                        break;
                    }
                }

            } else {
                System.out.println("Seat " + row_letter + seat_number + "is not booked.");
            }
        }catch (InputMismatchException e){
            System.out.println("Invalid input. Please enter a valid data.");
            scanner.nextLine();
        }

    }

    private static void findFirstAvailable(){
        for (int[] row : new int[][]{rowA,rowB,rowC,rowD}){
            for (int i = 0; i < row.length; i++){
                if (row[i] == 0){
                    char row_letter = getRow_Letter(row);
                    int seat_number = i + 1;
                    System.out.println("First available seat: " +row_letter + seat_number);
                    return;

                }
            }
        }
        System.out.println("No available seats.");
    }

    private static char getRow_Letter(int[] row){
        if (row == rowA){
            return 'A';
        } else if (row == rowB) {
            return 'B';
        } else if (row == rowC) {
            return 'C';
        }else {
            return 'D';
        }
    }

    private static void showSeatingPlan(){
        System.out.println("Seating Plan: ");
        for (int[] row: new int[][]{rowA,rowB,rowC,rowD}){
            for (int seat: row){
                if (seat == 0){
                    System.out.print("O");
                }else {
                    System.out.print("X");
                }
            }
            System.out.println();
        }
    }
// prints the total price of tickets sold during the session & details of all those tickets
    private static void print_tickets_info(){
        double total_sales = 0.0;
        System.out.println("Tickets information: ");
        for (int i = 0; i< tickets_Count; i++){
            Ticket ticket = tickets[i];
            double ticket_price = ticket.getPrice();
            total_sales += ticket_price;

            System.out.println("Name: " + ticket.getPerson().getName() +"\n"+"Surname: " + ticket.getPerson().getSurname()+"\n" +"Email: " + ticket.getPerson().getEmail()+"\n "+"Row: " + ticket.getRow() +"\n "+"Seat: " +ticket.getSeat() + " \n"+" price £"+ ticket_price);
        }
        System.out.println("Total sales: £" + total_sales);

    }
    private static void search_ticket(Scanner scanner){
        try {
            System.out.println("Enter row letter(A/B/C/D): ");
            char row_letter = scanner.next().toUpperCase().charAt(0);

            if (row_letter < 'A' || row_letter > 'D') {
                System.out.println("Invalid row letter. Please try again");
                return;
            }
            System.out.println("Enter seat number: ");
            int seat_number = scanner.nextInt();

            int[] selectedRow;
            switch (row_letter) {
                case 'A':
                    selectedRow = rowA;
                    break;
                case 'B':
                    selectedRow = rowB;
                    break;
                case 'C':
                    selectedRow = rowC;
                    break;
                case 'D':
                    selectedRow = rowD;
                    break;
                default:
                    System.out.println("Invalid row. Please try again");
                    return;
            }
            if (seat_number< 1 || seat_number> selectedRow.length) {
                System.out.println("Invalid seat number for this row. Try again.");
                return;
            }
            if (selectedRow[seat_number - 1] == 1) {
                System.out.println("This seat is booked.");
                for (Ticket ticket : tickets) {
                    if (ticket != null && ticket.getRow() == row_letter && ticket.getSeat() == seat_number) {
                        ticket.printTicketInformation();
                        return;
                    }
                }
            } else {
                System.out.println("This seat is available.");
            }
        } catch (InputMismatchException e) {
            System.out.println("Invalid input. ");
            scanner.nextLine();
        }
    }

}
