import java.util.ArrayList;
import java.util.Optional;
import java.util.Scanner;
import java.util.Arrays;


//Collection of classes contributing to signing a user in
class Interface{
    String email, PW;

    String Login(){
        Scanner takeIn = new Scanner(System.in);

        System.out.println("Please enter the Email associated with your account: ");
        this.email = takeIn.nextLine();
        System.out.println("Please enter the Password associated with your account: ");
        this.PW = takeIn.nextLine();

        InputCoordination inputCoordination = new InputCoordination(email, PW);
        return inputCoordination.passCreds();
    }
}
class InputCoordination{
    String email, PW;

    public InputCoordination(String Email, String password){
        this.email = Email;
        this.PW = password;
    }

    String passCreds(){
        InputVerification user = new InputVerification(email, PW);
        return user.verify(RegisteredUsers.users);
    }
}
class InputVerification{
    String email, PW;
    public InputVerification(String Email, String PW){
        this.email = Email;
        this.PW = PW;
    }
    public String verify(RegisteredUsers[] users){
        for (RegisteredUsers user : users) {
            if (this.email.equals(user.email) && this.PW.equals(user.PW)) {
                System.out.println("Verification successful. User is " + user.status);
                return user.status;
            }
        }
        System.out.println("Verification failed. User not found.");
        return null;
    }
    }
class RegisteredUsers{
    String email, PW, status;

    public RegisteredUsers(String email, String PW, String status){
        this.email = email;
        this.PW = PW;
        this.status = status;
    }

    static RegisteredUsers[] users = {
            new RegisteredUsers("boschlive@outlook.com", "Eggplant1", "CEO"),
            new RegisteredUsers("thejuice@gmail.com", "Ballin", "Customer"),
            new RegisteredUsers("anon@yahoo.com", "Password1", "Customer"),
            new RegisteredUsers("mallcustomer@outlook.com", "ABC123", "Customer"),
            new RegisteredUsers("bscruggs@troy.edu", "JakeDog", "Staff"),
            new RegisteredUsers("alken@outlook.com", "Rush2112", "Staff"),
            new RegisteredUsers("mallworker@gmail.com", "staffpassword", "Staff")
    };
}



//Classes for sales reports
class SalesData{

    public static void Report(){
        Scanner takeIn = new Scanner(System.in);
        System.out.println("Welcome, CEO. Would you like to view the monthly or daily report?");
        System.out.println("1. Daily Sales Report");
        System.out.println("2. Montly Sales Report");
        int chooseCEO = takeIn.nextInt();

        switch(chooseCEO){
            case 1:
                DailyReport(Item.items);
                break;
            case 2:
                MonthlyReport(Item.items);
                break;
            default:
                System.out.println("invalid input");
        }
    }
    public static void DailyReport(Item[] items) {
        System.out.println("Daily Profit Report:");
        double totalProfit = 0;

        for (Item item : items) {
            double itemProfit = item.qtySold * item.price;
            totalProfit += itemProfit;

            System.out.println("Item: " + item.itemName +
                    ", Quantity Sold: " + item.qtySold +
                    ", Profit: $" + itemProfit);
        }

        System.out.println("Total Profit for the Day: $" + totalProfit);
        System.out.println("----------------------------");
    }

    public static void MonthlyReport(Item[] items) {
        System.out.println("Monthly Profit Report:");

        double totalSales = 0;
        double totalProfit = 0;

        for (Item item : items) {
            double itemSales = item.qtySold * item.price;
            totalSales += itemSales;

            double itemProfit = item.qtySold * item.price;
            totalProfit += itemProfit;
        }

        System.out.println("Total Sales for the Month: $" + totalSales);
        System.out.println("Total Profit for the Month: $" + totalProfit);
        System.out.println("----------------------------");
    }
}


//Collection of classes contributing to Displaying and Interacting with Item Information
class Item{
    int itemID, itemQty, qtySold;
    String itemName, description;
    double price;

    public Item(int id, String name, String desc, double price, int itemQty, int qtySold){
        this.itemID = id;
        this.itemName = name;
        this.description = desc;
        this.price = price;
        this.itemQty = itemQty;
        this.qtySold = qtySold;
    }

    public Item(int id, String name, String desc, double price, int itemQty) {
        this.itemID = id;
        this.itemName = name;
        this.description = desc;
        this.price = price;
        this.itemQty = itemQty;
    }

    public static void addToWishlist(int itemID) {
        if (CheckStock.checkStock(itemID)) {
            // If in stock, find the item and add it to the wishlist
            for (Item item : items) {
                if (item.itemID == itemID) {
                    wishlist.add(item);
                    System.out.println(item.itemName + " added to the wishlist.");
                }
            }
        }

        else {
            System.out.println("Item with ID " + itemID + " is out of stock. Cannot be added to the wishlist.");
        }
        CustomerFrontend frontend = new CustomerFrontend();
        frontend.Frontend(items);
    }

    public static void addToLikedList(int itemID) {
        for (Item item : items) {
            if (item.itemID == itemID) {
                if (!likesList.contains(item)) {
                    likesList.add(item);
                    System.out.println(item.itemName + " is in your liked items.");
                } else {
                    System.out.println(item.itemName + " is already in your liked items.");
                }
            }
        }
        CustomerFrontend frontend = new CustomerFrontend();
        frontend.Frontend(items);
    }

    static ArrayList<Item> wishlist = new ArrayList<>();
    static ArrayList<Item> likesList = new ArrayList<>();

    static Item[] items = {
            new Item(1, "chair", "A chair", 55.00, 5, 20),
            new Item(2, "couch", "A couch", 105.00, 0, 30),
            new Item(3, "table", "A table", 80.00, 3, 10),
            new Item(4, "lamp", "A lamp", 20.00, 0, 25),
            new Item(5, "desk", "A desk", 80.00, 10, 15),
            new Item(6, "shelf", "A shelf", 30.00, 20, 5)
    };
}
class AddRemoveItem{

    void AddItem(Item[] items, int ID, String name, String desc,double price, int Qty){
        Item newItem = new Item(ID, name, desc, price, Qty);

        items = Arrays.copyOf(items, items.length + 1);
        items[items.length - 1] = newItem;
        System.out.println("New item Added!");
        StaffBackend backend = new StaffBackend();
        backend.Backend(Item.items);
    }
    void RemoveItem(Item[] items, int ID){
        for (int i = 0; i < items.length; i++) {
            if (items[i].itemID == ID) {
                // Remove the item by shifting elements
                for (int j = i; j < items.length - 1; j++) {
                    items[j] = items[j + 1];
                }
                // Resize the array
                items = Arrays.copyOf(items, items.length - 1);
                break; // Exit the loop once the item is removed
            }
        }
        //
        StaffBackend backend = new StaffBackend();
        backend.Backend(Item.items);
    }
}
class EditItem{
    static void editItemInfo(Item[] items, int itemId, String newName, String newDesc, double newPrice) {
        for (Item item : items) {
            if (item.itemID == itemId) {
                item.itemName = newName;
                item.description = newDesc;
                item.price = newPrice;
                System.out.println("Item information updated successfully.");
                return; // Once the item is found and updated, exit the loop
            }
        }
        System.out.println("Item with ID " + itemId + " not found.");
    }
}
class RefillStock{
    static void refillItemQuantity(Item[] items, int itemId, int additionalQty) {
        for (Item item : items) {
            if (item.itemID == itemId) {
                item.itemQty += additionalQty;
                System.out.println("Item quantity refilled successfully.");
                System.out.println("Updated Item QTY: " + item.itemQty);
                return; // Once the item is found and quantity refilled, exit the loop
            }
        }
        System.out.println("Item with ID " + itemId + " not found.");
    }
}
class ItemInteraction{
    void InteractionRequest(Item[] items, int ID, int request){
        switch (request) {
            case 1:
                // Implement buy item logic (you can add a method for this)
                if(CheckStock.checkStock(ID)){
                    Purchase transaction = new Purchase();
                    transaction.PurchaseItem(Item.items, ID);
                }
                else{
                    System.out.println("Item not in stock. ");
                }
                break;
            case 2:
                Item.addToWishlist(ID);
                break;
            case 3:
                // Implement like item logic (you can add a method for this)
                Item.addToLikedList(ID);
                break;
            case 4:
                // User wants to return to the item list
                CustomerFrontend frontend = new CustomerFrontend();
                frontend.Frontend(items);
                break;
            default:
                System.out.println("Invalid choice");
        }
    }
}
class Purchase{
    void PurchaseItem(Item[] items, int ID) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Select payment method:");
        System.out.println("1. Credit card");
        System.out.println("2. Debit card");

        int paymentMethod = scanner.nextInt();

        if (paymentMethod == 1 || paymentMethod == 2) {
            System.out.println("Enter card number:");
            long cardNumber = scanner.nextLong();

            // Pretend to verify the card number (you may implement a real verification logic here)
            boolean isCardVerified = verifyCardNumber(cardNumber);

            if (isCardVerified) {
                System.out.println("Purchase successful!");

                // Decrease the item quantity by 1
            } else {
                System.out.println("Card verification failed. Purchase unsuccessful.");
            }
        } else {
            System.out.println("Invalid payment method selected.");
        }
    }

    private boolean verifyCardNumber(long cardNumber) {
        // Pretend verification logic
        // You may implement a real verification logic based on your requirements
        return cardNumber > 0;  // Just a simple example
    }
}
class CheckStock{
    public static boolean checkStock(int itemID) {
        for (Item item : Item.items) {
            if (item.itemID == itemID) {
                return item.itemQty > 0;
            }
        }
        System.out.println("Item with ID " + itemID + " not found in the items list.");
        return false;
    }
}



//Collection of Display classes
class CustomerFrontend {
    void Frontend(Item[] items) {
        Scanner takeIn = new Scanner(System.in);


        for (Item item : items) {
            System.out.println("Item ID: " + item.itemID);
            System.out.println("Item Name: " + item.itemName);
            System.out.println();
        }

            System.out.println("For more information or to purchase or wishlist an item, please enter the items ID number ");
            System.out.println("For more options, press 0 \n");
            int switchSelect = takeIn.nextInt();

            if(switchSelect != 0) {
                ProductView(Item.items, switchSelect);
            }
            else {
                System.out.println("To chat with support staff, press 1");
                System.out.println("To view your wishlist, press 2");
                System.out.println("To view your liked items, press 3");
                int nextSelect = takeIn.nextInt();

                switch (nextSelect){
                    case 1:
                        Chatbox message = new Chatbox();
                        message.openChat();
                        break;
                    case 2:
                        for (Item item : Item.wishlist) {
                            System.out.println("Item ID: " + item.itemID);
                            System.out.println("Item Name: " + item.itemName);
                            System.out.println("Stock: " + item.itemQty);
                            System.out.println();
                        }
                        break;
                    case 3:
                        for (Item item : Item.likesList) {
                            System.out.println("Item ID: " + item.itemID);
                            System.out.println("Item Name: " + item.itemName);
                            System.out.println("Stock: " + item.itemQty);
                            System.out.println();
                        }
                    default:
                        System.out.println("Not a valid input");
                        break;
                }
            }

    }

    void ProductView(Item[] items, int ID){
        Optional<Item> selectedItem = Arrays.stream(items)
                .filter(item -> item.itemID == ID)
                .findFirst();

        if (selectedItem.isPresent()) {
            // Item found, display details
            System.out.println("Name: " + selectedItem.get().itemName);
            System.out.println("Description: " + selectedItem.get().description);
            System.out.println("Price: " + selectedItem.get().price);
            System.out.println("Stock: " + selectedItem.get().itemQty);

            System.out.println("\nOptions:");
            System.out.println("1. Buy the item");
            System.out.println("2. Add to wishlist");
            System.out.println("3. Like the item");
            System.out.println("4. Return to item list");

            Scanner scanner = new Scanner(System.in);
            int userChoice = scanner.nextInt();
            ItemInteraction request = new ItemInteraction();
            request.InteractionRequest(Item.items, ID, userChoice);


        } else {
            // Item not found
            System.out.println("Item with ID " + ID + " not found.");
        }

    }
}
class StaffBackend{
    void Backend(Item[] items){
        Scanner takeIn = new Scanner(System.in);

        System.out.println("Select an option: ");
        System.out.println("1. Add an Item to inventory");
        System.out.println("2. Delete an Item from inventory");
        System.out.println("3. Edit an Item's info");
        System.out.println("4. Refill Item inventory");
        System.out.println("5. Respond to customer support request");
        System.out.println("6. View customer data");
        int staffSelect = takeIn.nextInt();

        switch (staffSelect){
            case 1:
            case 2:
                Add_Remove(staffSelect);
                break;
            case 3:
                Edit_Item_Info();
                break;
            case 4:
                Refill_Stock();
                break;
            case 5:
                Chatbox message = new Chatbox();
                message.replyTo();
            case 6:

            default:
                System.out.println("Not a valid input");
                break;
        };
    }
    void Add_Remove(int select){
        Scanner takeIn = new Scanner(System.in);

        if(select == 1){
            System.out.println("--Enter info for new Item-- ");
            System.out.println("Enter Item ID: ");
            int ID = takeIn.nextInt();
            takeIn.nextLine();
            System.out.println("Enter Item Name: ");
            String name = takeIn.nextLine();
            System.out.println("Enter Item Description: ");
            String desc = takeIn.nextLine();
            System.out.println("Enter Item Price: ");
            double price = takeIn.nextDouble();
            System.out.println("Enter Item Quantity: ");
            int Qty = takeIn.nextInt();

            AddRemoveItem add = new AddRemoveItem();
            add.AddItem(Item.items, ID, name, desc, price, Qty);
        }
        else if(select == 2){
            System.out.println("Enter the ID of the item to be removed: ");
            int ID = takeIn.nextInt();
            AddRemoveItem remove = new AddRemoveItem();
            remove.RemoveItem(Item.items, ID);
            System.out.println("Item removed succesfully");
        }
    }
    void Edit_Item_Info(){
        Scanner takeIn = new Scanner(System.in);

        System.out.println("Enter the ID of the item to be edited: ");
        int ID = takeIn.nextInt();
        takeIn.nextLine(); // Consume the newline character
        System.out.println("Enter new Item Name: ");
        String newName = takeIn.nextLine();
        System.out.println("Enter new Item Description: ");
        String newDesc = takeIn.nextLine();
        System.out.println("Enter new Item Price: ");
        double newPrice = takeIn.nextDouble();

        EditItem.editItemInfo(Item.items, ID, newName, newDesc, newPrice);
        StaffBackend backend = new StaffBackend();
        backend.Backend(Item.items);
    }
    void Refill_Stock(){
        Scanner takeIn = new Scanner(System.in);

        System.out.println("Enter the ID of the item to refill: ");
        int ID = takeIn.nextInt();
        System.out.println("Enter the quantity to add: ");
        int additionalQty = takeIn.nextInt();

        RefillStock.refillItemQuantity(Item.items, ID, additionalQty);
        StaffBackend backend = new StaffBackend();
        backend.Backend(Item.items);
    }

    void viewCustomerInfo(){
        for(RegisteredUsers user : RegisteredUsers.users){
            if(user.status.equals("Customer")){
                System.out.println("Email: " + user.email);
                System.out.println("Password: " + user.PW);
                System.out.println();
            }
        }
        Scanner takeIn = new Scanner(System.in);
        System.out.println("Press any key to return to options");
        takeIn.nextLine();
        StaffBackend backend = new StaffBackend();
        backend.Backend(Item.items);
    }
}



//Collection of classes for sending messages
class Chatbox{
    String message;

    void openChat(){
        Scanner takeIn = new Scanner(System.in);
        System.out.println("Staff: Hello! I am a representative. How can I help you today?");
        System.out.println("Please enter your message: ");
        message = takeIn.nextLine();
        MessageCoordinator send = new MessageCoordinator(message);
    }

    void replyTo(){
        Scanner takeIn = new Scanner(System.in);
        System.out.println("User: Hello! I need help. How can I wishlist items?");
        System.out.println("Please enter your reply: ");
        message = takeIn.nextLine();
        MessageCoordinator send = new MessageCoordinator(message);
    }
}
class MessageCoordinator{
    String message;

    public MessageCoordinator(String message){
        this.message = message;
        SendMessage(message);
    }
    void SendMessage(String message){
        System.out.println("User: " + message);
        System.out.println("Staff: Let me look into that for you, I can assist you shortly.");
    }
}



//Main Class
public class Main {
    public static void main(String[] args) {


        Interface login = new Interface();
        String userStatus = login.Login();

        if(userStatus.equals("Customer")){
            CustomerFrontend frontend = new CustomerFrontend();
            frontend.Frontend(Item.items);
        }
        else if(userStatus.equals("Staff")){
            StaffBackend backend = new StaffBackend();
            backend.Backend(Item.items);
        }
        else if(userStatus.equals("CEO")){
            SalesData.Report();
        }

    }
}