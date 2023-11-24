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
            new RegisteredUsers("bscruggs@troy.edu", "JakeDog", "Staff"),
            new RegisteredUsers("alken@outlook.com", "Rush2112", "Staff")
    };
}

//Collection of classes contributing to Displaying and Interacting with Item Information
class Item{
    int itemID, itemQty;
    String itemName, description;
    double price;

    public Item(int id, String name, String desc, double price, int itemQty){
        this.itemID = id;
        this.itemName = name;
        this.description = desc;
        this.price = price;
        this.itemQty = itemQty;
    }

    static Item[] items = {
            new Item(1, "chair", "A chair", 55.00, 5),
            new Item(2, "couch", "A couch", 105.00, 8),
            new Item(3, "table", "A table", 80.00, 3)
    };
}
class AddRemoveItem{
    int ItemID;

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
    }
}
class EditItem{
    int itemID;
    String itemName, description;
    double itemPrice;
}

class RefillStock{
    int itemID, itemQty;
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
            System.out.println("To contact a staff member, press 0 \n");
            int switchSelect = takeIn.nextInt();

            if(switchSelect != 0) {
                ProductView(Item.items, switchSelect);
            }
            else {
                Chatbox message = new Chatbox();
                message.messageBody();
            }

    }

    void ProductView(Item[] items, int ID){
        System.out.println("Name: " + items[ID].itemName);
        System.out.println("Description: " + items[ID].description);
        System.out.println("Name: " + items[ID].price);

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
        int staffSelect = takeIn.nextInt();

        switch (staffSelect){
            case 1:
            case 2:
                Add_Remove(Item.items, staffSelect);
        };
    }
    void Add_Remove(Item[] items, int select){
        if(select == 1){
            System.out.println("Enter info for new Item: ");
        }
        else if(select == 2){
            System.out.println("Which item to remove?");
        }
    }
}

//Collection of classes for sending messages
class Chatbox{
    String message;

    void messageBody(){
        Scanner takeIn = new Scanner(System.in);
        System.out.println("Staff: Hello! I am a representative. How can I help you today?");
        System.out.println("Please enter your message: ");
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

    }
}