//Program:      AirportSimulator
//Course:       COSC210: Data Structures and Algorithms
//Description:  This program simulates the time behavior of airplanes arriving 
//              at an airport and waiting to land, where airplanes arrive at 
//              random intervals with a limited quantity of fuel which 
//              determines their position in the holding pattern. Airlines with 
//              less fuel are given landing priority over airlines with more 
//              fuel and the airport can accept a landing every two minutes.
//Author:       Michael Megginson
//Revised:      5/2/17
//Language:     Java
//IDE:          NetBeans 8.1
//******************************************************************************
//******************************************************************************
//Class:        AirportSimulator
//Description:  This is the main class for the program. It contains the main  
//              method and other methods with operations.

public class AirportSimulator {

    static int clock = 0;
    static QueueInterface planeQueue = new PriorityQueue();
    static int timeSinceLanding = 2;
//******************************************************************************
//******************************************************************************
    //Method:       main
    //Description:  This is the main method, which gets inputs from the user and
    //              calls all other methods. It loops until the user ends it.
    //Parameters:   None
    //Returns:      Nothing
    //Calls:        timePasses()
    //              priorityEnqueue()
    //              display()
    //              enqueue()
    //              dequeue()
    //              getCharacter()
    //Globals:      clock               keeps track of the time since the start 
    //                                  of the program
    //              planeQueue          the priority queue containing the planes
    //              timeSinceLanding    regulates planes landing every 2 min

    public static void main(String[] args) {
        KeyboardInputClass keyboardInput = new KeyboardInputClass();
        System.out.println("Airport Simulation Program: Michael Megginson\n"
                + "---------------------------------------------\n"
                + "ENTER = increment the simulation clock only (default)\n"
                + " P    = generate a new arrival (and increment the clock)\n"
                + " S    = show priority queue contents (clock does not change)\n"
                + " X    = exit simulation program");

        while (true) {
            char input = keyboardInput.getCharacter(true, 'D', "PSX", 1,
                    "\nNext Action:");
            switch (input) {
                case 'D':
                    timePasses();
                    clock++;
                    break;
                case 'P':
                    Airplane newPlane = new Airplane();
                    planeQueue.priorityEnqueue(newPlane);
                    System.out.println(newPlane.airline + " flight "
                            + newPlane.flightNum + " arrives at "
                            + newPlane.arrival + " minutes with "
                            + newPlane.fuelLeft + " minutes of fuel left");
                    timePasses();
                    clock++;
                    break;
                case 'S':
                    QueueInterface holdingQueue = new PriorityQueue();
                    while (!planeQueue.isEmpty()) {
                        Airplane p = (Airplane) planeQueue.dequeue();
                        p.display();
                        holdingQueue.enqueue(p);
                    }
                    while (!holdingQueue.isEmpty()) {
                        planeQueue.priorityEnqueue((Comparable) holdingQueue.dequeue());
                    }
                    break;
                case 'X':
                    System.exit(0);
            }
        }
    }
 //**************************************************************************

    //Method:       timePasses
    //Description:  This method simulates all the things that happens while one
    //              minute passes, including incrementing the clock, decrementing
    //              each planes fuel remaining, and planes landing and crashing.
    //Parameters:   none
    //Returns:      nothing
    //Calls:        PriorityQueue()
    //              isEmpty()
    //              dequeue()
    //              priorityEnqueue()
    //              enqueue()
    //Globals:      none
    public static void timePasses() {
        QueueInterface holdingQueue = new PriorityQueue();
        timeSinceLanding++;
        if (timeSinceLanding > 1 && !planeQueue.isEmpty()) {
            Airplane p = (Airplane) planeQueue.dequeue();
            System.out.println(p.airline + " flight "
                    + p.flightNum + " lands with "
                    + p.fuelLeft + " minutes of fuel left after waiting "
                    + (clock - p.arrival) + " min(s)");
            timeSinceLanding = 0;
        }
        while (!planeQueue.isEmpty()) {
            Airplane p = (Airplane) planeQueue.dequeue();
            p.fuelLeft--;
            holdingQueue.enqueue(p);
            if (p.fuelLeft == -1) {
                System.out.println("\n" + p.airline + " flight "
                        + p.flightNum + " CRASHES after waiting "
                        + (clock - p.arrival) + " min(s)");
                holdingQueue.dequeue();
            }
        }
        while (!holdingQueue.isEmpty()) {
            planeQueue.priorityEnqueue((Comparable) holdingQueue.dequeue());
        }
    }
}
//******************************************************************************

//Class:        Airplane
//Description:  This class defines the object Airplane as containing an airline,
//              a flight number, an amount of fuel left, and an arrival time. It
//              has a display method and a compareTo method.
class Airplane implements Comparable<Object> {

    String airline;
    int flightNum;
    int fuelLeft;
    int arrival;

    public Airplane() {
        switch ((int) (Math.random() * 8)) {
            case 0:
                airline = "United";
                break;
            case 1:
                airline = "Southwest";
                break;
            case 2:
                airline = "Northwest";
                break;
            case 3:
                airline = "Continental";
                break;
            case 4:
                airline = "American";
                break;
            case 5:
                airline = "Frontier";
                break;
            case 6:
                airline = "Alaska";
                break;
            case 7:
                airline = "Lufthansa";
        }
        flightNum = ((int) (Math.random() * 9000) + 1000);
        fuelLeft = (((int) (Math.random() * 20)) + 1);
        arrival = AirportSimulator.clock;
    }
//******************************************************************************
//******************************************************************************

    //Method:       display
    //Description:  This method displays each element of an Airplane object.
    //Parameters:   none
    //Returns:      nothing
    //Calls:        nothing
    //Globals:      none
    public void display() {
        System.out.println(airline + " flight " + flightNum + ": Fuel left="
                + fuelLeft + "; Arrival time=" + arrival);
    }
//******************************************************************************

    //Method:       compareTo
    //Description:  This method compares the fuel remaining in different 
    //              Airplane objects and overrides the general compareTo method.
    //Parameters:   Object o
    //Returns:      int
    //Calls:        nothing
    //Globals:      none
    @Override
    public int compareTo(Object o) {
        Airplane plane = (Airplane) o;
        int firstInt = this.fuelLeft;
        int secondInt = plane.fuelLeft;
        if (firstInt == secondInt) {
            return 0;
        } else if (firstInt < secondInt) {
            return -1;
        } else {
            return 1;
        }
    }
}
//******************************************************************************
