package cinema;
import java.util.*;

public class Cinema {
    private static Scanner scanner;
    private char[][] cinema;
    private final int ROW_LEN;
    private final int COL_LEN;
    private final int maxSeating;
    private final int maxIncome;
    private int purchasedTickets;
    private int currentIncome;
    private int rowNum;
    private int seatNum;

    public Cinema() {
        this.scanner = new Scanner(System.in);
        System.out.println("Enter the number of rows:");
        this.rowNum = scanner.nextInt();
        System.out.println("Enter the number of seats in each row:");
        this.seatNum = scanner.nextInt();

        this.ROW_LEN = rowNum;
        this.COL_LEN = seatNum;
        this.maxSeating = this.ROW_LEN * this.COL_LEN;
        this.maxIncome = seatingCost();
    }
    public void run()   {
        initCinema();   // Initialize our 2D matrix(cinema)
        displayMenu();  // User input will drive program
    }
    /*
        1. This method will populate the 2D char array to mimic the seating area
        in the cinema.
        2. 1 row and 1 column were added for visual representation.
        ExAMPLE 3x3 = 9 seats:    1 2 3
                                1 S S S
                                2 S S S
                                3 S S S
     */
    private void initCinema() {
        char num = '0';
        cinema = new char[rowNum + 1][seatNum + 1];
        cinema[0][0] = ' ';

        for(int i = 1; i < seatNum + 1; i++)  {
            cinema[0][i] = ++num;
        }
        num = '0';
        for(int i = 1; i < rowNum + 1; i++)  {
            cinema[i][0] = ++num;
        }
        for (int i = 0; i < rowNum + 1; i++) {
            for (int j = 0; j < seatNum + 1; j++) {
                if(i != 0 && j != 0)    {
                    cinema[i][j] = 'S';
                }
            }
        }
    }
    /*
        Visual aid of the cinema and its seating.
     */
    public void printCinema() {
        System.out.println("Cinema:");
        for (char[] row : this.cinema) {
            for (char seat : row) {
                System.out.print(seat + " ");
            }
            System.out.println();
        }
    }
    /*
        Calculations are performed to keep track of sales.
     */
    public void statistics()    {
        System.out.printf("Number of purchased tickets: %d%n", this.purchasedTickets);
        System.out.printf("Percentage: %.2f%s%n", percentageSold(), "%");
        System.out.printf("Current Income: $%d%n", this.currentIncome);
        System.out.printf("Total income: $%d%n%n", maxIncome);
    }
    /*
        1. As we tally purchased tickets, the percentage will be calculated as such.
        Ex: for 6 x 6 cinema = 36 seats and 1 ticket sold --> (1/36) = (x/100) [We are looking for X]
        cross multiply: 36x = 100 --> Get x alone --> x = 100/36 = 2.78% for one ticket sold
     */
    private double percentageSold() {
        return (double)(purchasedTickets * 100) / (this.ROW_LEN * this.COL_LEN);
    }

    /*
        1. This method will calculate the cost of seating based on size of cinema.
        2. If the seating area has more than 60 seats, then the front half
        will cost $10 dollars per seat. The back half will cost $8 dollars per seat.
        3. If row count is odd, the smaller half will cost more.
        Ex: 9 rows --> First 4 rows will cost $10 per seat & last 5 rows will cost $8 dollars per seat.
     */
    private int seatingCost()  {
        if(ROW_LEN * COL_LEN <= 60)    {
            return ROW_LEN * COL_LEN * 10;
        }else   {
            int firstHalf = ROW_LEN / 2;
            int secondHalf = ROW_LEN - firstHalf;
            return firstHalf * COL_LEN * 10 + secondHalf * COL_LEN * 8;
        }
    }
    /*
        1. This method will check if seats are available for purchase.
        2. Edge cases: Out of bounds and seat already purchased have been guarded.
        3. Tickets purchased are tracked and tallied.
        4. Since the requirement was to request another seat from the user until
        an available seat was chosen, A repeat call to this method is made to satisfy the constraint.
        5. I added a check if all seats were filled, so that the program doesn't get stuck in an infinite loop
        of method calling: 'purchaseSeat()'.
     */
    private void purchaseSeat()   {
        if  (this.purchasedTickets == this.maxSeating)  {
            System.out.println("The cinema is sold out!");
            return;
        }
        System.out.println("Enter a row number:");
        rowNum = scanner.nextInt();
        System.out.println("Enter a seat number in that row:");
        seatNum = scanner.nextInt();

        if(rowNum < 1 || rowNum > cinema.length - 1 || seatNum < 1 || seatNum > cinema.length -1)  {
            System.out.println("Wrong input!\n");
            purchaseSeat();
            return;
        }
        if(cinema[rowNum][seatNum] == 'B')  {
            System.out.println("That ticket has already been purchased!\n");
            purchaseSeat();
            return;
        }
        int price = 0;
        if  (ROW_LEN * COL_LEN <= 60)   {
            System.out.println("Ticket price: $" + 10);
            price = 10;
        }   else    {
            if  (rowNum <= ROW_LEN/2) {
                System.out.println("Ticket price: $" + 10);
                price = 10;
            }   else    {
                System.out.println("Ticket price: $" + 8);
                price = 8;
            }
        }
        this.purchasedTickets++;
        this.currentIncome += price;
        this.cinema[rowNum][seatNum] = 'B';
    }
    /*
        This method serves as the main UI for interacting with the application.
     */
    private void displayMenu()   {
        boolean flag = true;

        while(flag) {
            System.out.println("1. Show the seats");
            System.out.println("2. Buy a ticket");
            System.out.println("3. Statistics");
            System.out.println("0. Exit");

            int option = scanner.nextInt();
            switch(option)   {
                case 0 :    {
                    flag = false;
                    break;
                }
                case 1 :    {
                    printCinema();
                    break;
                }
                case 2 :    {
                    purchaseSeat();
                    break;
                }
                case 3: {
                    statistics();
                    break;
                }
                default : break;
            }
        }
    }
    /*
        Driver method
     */
    public static void main(String[] args) {
        Cinema theater_1 = new Cinema();
        theater_1.run();
        scanner.close();
    }
}