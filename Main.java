import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Scanner;

class Food implements Serializable
{
    int itemno;
    int quantity;   
    float price;
    
    Food(int itemno,int quantity)
    {
        this.itemno=itemno;
        this.quantity=quantity;
        switch(itemno)
        {
            case 1:price=quantity*5;
                break;
            case 2:price=quantity*7;
                break;
            case 3:price=quantity*19;
                break;
            case 4:price=quantity*55;
                break;
        }
    }
}
class Singleroom implements Serializable
{
    String firstName;
    String lastName;
    String contact;
    ArrayList<Food> food =new ArrayList<>();
    Singleroom()
    {
        this.firstName = "";
        this.lastName = "";
    }
    Singleroom(String firstName, String lastName, String contact)
    {
        this.firstName = firstName;
        this.lastName = lastName;
        this.contact=contact;
    }
}
class NotAvailable extends Exception
{
    @Override
    public String toString()
    {
        return "Nera tokios galimybes !";
    }
}
class holder implements Serializable
{
    Singleroom deluxe_singleerrom[]=new Singleroom[5];
    Singleroom luxury_singleerrom[]=new Singleroom[0]; 
}
class Hotel
{
    static holder hotel_ob=new holder();
    static Scanner sc = new Scanner(System.in);
    static void customersDetails(int i,int rn)
    {
        String firstName, lastName, contact;
        System.out.print("\nIveskite kliento Varda: ");
        firstName = sc.next();
        System.out.print("\nIveskite kliento Pavarde: ");
        lastName = sc.next();
        System.out.print("\nIveskite kontaktini mob. nr.: ");
        contact=sc.next();
        
          switch (i) {
            case 1:hotel_ob.deluxe_singleerrom[rn]=new Singleroom(firstName, lastName,contact);
                break;
            case 2:hotel_ob.luxury_singleerrom[rn]=new Singleroom(firstName, lastName, contact);
                break;
            default:System.out.println("Netinkamas pasirinkimas");
                break;
        }
    }
    
    static void bookRoom(int i)
    {
        int j;
        int rn;
        boolean availableRoom = false;

        System.out.println("\nPasirinkite kambario numery is saraso : ");

        switch (i) {
            case 1:
                for(j=0;j<hotel_ob.deluxe_singleerrom.length;j++)
                {
                    if(hotel_ob.deluxe_singleerrom[j]==null)
                    {
                        System.out.print(j+1+",");
                        availableRoom = true; 
                    }
                }
                if (!availableRoom) {
                    System.out.println("Atsiprasome, Nera laisvu kambariu sio tipo.");
                    return;
                }
                System.out.print("\nIveskite kambario numery: ");
                try{
                rn=sc.nextInt();
                rn--;
                if(hotel_ob.deluxe_singleerrom[rn]!=null)
                    throw new NotAvailable();
                    customersDetails(i,rn);
                }
                catch(Exception e)
                {
                    System.out.println("Netinkamas pasirinkimas");
                    return;
                }
                break;
            case 2:
                 for(j=0;j<hotel_ob.luxury_singleerrom.length;j++)
                {

                    if(hotel_ob.luxury_singleerrom[j]==null)
                    {
                        System.out.print(j+5+",");
                        availableRoom = true;
                    }
                }
                if (!availableRoom) {
                    System.out.println("Atsiprasome, Nera laisvu kambariu sio tipo.");
                    return;
                }
                System.out.print("\nIveskite kambario numery: ");
                try{
                rn=sc.nextInt();
                rn=rn-6;
                if(hotel_ob.luxury_singleerrom[rn]!=null)
                    throw new NotAvailable();
                    customersDetails(i,rn);
                }
                catch(Exception e)
                {
                    System.out.println("Netinkamas pasirinkimas");
                    return;
                }
                break;
            default:
            System.out.println("Iveskite egzistuojanti pasirinkima");
                break;
        }
          System.out.println("Kambarys sekmingai rezervuotas");
    }

    static void bill(int rn,int rtype)
    {
        double amount=0;
        String list[]={"Sumustinis","Makaronai","Kepsnys","Vynas"};
        System.out.println("\n*******");
        System.out.println(" Nefiskaline Saskaita:-");
        System.out.println("*******");
               
        switch(rtype)
        {
            case 1:amount+=10;
            System.out.println("Kambario kaina  - "+10);
            System.out.println("\nMaisto ir Gerimu kaina: ");
            System.out.println("===============");
            System.out.println("Vienetas   Kiekis    Kaina");
            System.out.println("-------------------------");
            for(Food obb: hotel_ob.deluxe_singleerrom[rn].food)
            {
                amount+=obb.price;
                String format = "%-10s%-10s%-10s%n";
                System.out.printf(format,list[obb.itemno-1],obb.quantity,obb.price );
            }
            break;
            case 2:amount+=20;
                    System.out.println("Kambario kaina  - "+20);
                    System.out.println("\nMaisto ir Gerimu kaina: ");
                    System.out.println("===============");
                    System.out.println("Vienetas   Kiekis    Kaina");
                    System.out.println("-------------------------");
                    for(Food obb:hotel_ob.luxury_singleerrom[rn].food)
                    {
                        amount+=obb.price;
                        String format = "%-10s%-10s%-10s%n";
                        System.out.printf(format,list[obb.itemno-1],obb.quantity,obb.price );
                    }
                break;

            default:
                System.out.println("Negalimas");
        }
        System.out.println("\nSuma Viso: - "+amount);
    }
    
    static void moveOut(int rn,int rtype)
    {
        char w;
        switch (rtype) {
            case 1:               
            if(hotel_ob.deluxe_singleerrom[rn]!=null)
                System.out.println("Kambaryje gyvena  "+hotel_ob.deluxe_singleerrom[rn].firstName+ ' ' + hotel_ob.deluxe_singleerrom[rn].lastName);                
            else 
            {    
                System.out.println("Kambarys nebuvo uzimtas");
                    return;
            }
            System.out.println("Ar norite isvykti?(t/n)");
             w=sc.next().charAt(0);
            if(w=='t'||w=='T')
            {
                bill(rn,rtype);
                hotel_ob.deluxe_singleerrom[rn]=null;
                System.out.println("Kambarys atlaisvintas");
            }
            break;
             case 2:
                if(hotel_ob.luxury_singleerrom[rn]!=null)
                    System.out.println("Kambaryje gyvena "+hotel_ob.luxury_singleerrom[rn].firstName + ' ' + hotel_ob.luxury_singleerrom[rn].lastName);                
                else 
                 {    
                    System.out.println("Kambarys nebuvo uzimtas");
                        return;
                }
                System.out.println("Ar norite isvykti?(t/n)");
                w=sc.next().charAt(0);
                if(w=='t'||w=='T')
                {
                    bill(rn,rtype);
                    hotel_ob.luxury_singleerrom[rn]=null;
                    System.out.println("Kambarys atlaisvintas");
                }
                
                break;

            default:
                System.out.println("\nIveskite reikiama pasirinkima : ");
                break;
        }
    }
    
    static void foodOrder(int rn,int rtype)
    {
        int i,q;
        char wish;
         try{
             System.out.println("\n----------\n  Menu:  \n----------\n\n1. Sumustinis\t5 Eur\n2. Makaronai\t7 Eur\n3. Kepsnys\t19 Eur\n4. Vyno Butelis\t55 Eur\n");
        do
        {
            i = sc.nextInt();
            System.out.print("Kiekis- ");
            q=sc.nextInt();
            switch(rtype){
            case 1: hotel_ob.deluxe_singleerrom[rn].food.add(new Food(i,q));
                break;   
            case 2: hotel_ob.luxury_singleerrom[rn].food.add(new Food(i,q));
                break;
                                         
        }
              System.out.println("Ar noresite uzsakyti daugiau ? (t/n)");
              wish=sc.next().charAt(0); 
        }while(wish=='t'||wish=='T');  
        }
         catch(NullPointerException e)
            {
                System.out.println("\nNeuzsakyta");
            }
         catch(Exception e)
         {
             System.out.println("Negalimas veiksmas");
         }
    }

    static void listOccupiedRooms() {
    boolean occupiedRoomsExist = false; 
    System.out.println("\nSarasas uzimtu kambariu:");
    System.out.println("-----------------");

    for (int i = 0; i < hotel_ob.deluxe_singleerrom.length; i++) {
        if (hotel_ob.deluxe_singleerrom[i] != null) {
            System.out.println("Deluxe Vienvietis " + (i + 1) + ": " + hotel_ob.deluxe_singleerrom[i].firstName + ' '+ hotel_ob.deluxe_singleerrom[i].lastName);
            occupiedRoomsExist = true;
        }
    }
    for (int i = 0; i < hotel_ob.luxury_singleerrom.length; i++) {
        if (hotel_ob.luxury_singleerrom[i] != null) {
            System.out.println("Luxury Vienvietis " + (i + 6 ) + ": " + hotel_ob.luxury_singleerrom[i].firstName+ ' '+ hotel_ob.luxury_singleerrom[i].lastName);
            occupiedRoomsExist = true;
        }
    }
    if (!occupiedRoomsExist) {
        System.out.println("Visi kambariai laisvi.");
    }

}

    static void checkStatus() {
    int j;
    boolean availableRoom = false;

            for (j = 0; j < hotel_ob.deluxe_singleerrom.length; j++) {
                if (hotel_ob.deluxe_singleerrom[j] == null) {
                    System.out.println("Kambarys " + (j + 1) + " - Laisvas");
                } else {
                    System.out.println("Kambarys " + (j + 1) + " - Uzimtas");
                    Singleroom room = hotel_ob.deluxe_singleerrom[j];
                    System.out.print(" Vardas: " + room.firstName);
                    System.out.print(" Pavarde: " + room.lastName);
                    System.out.print(" Kontaktinis mob. nr.: " + room.contact);
                    System.out.println("");
                }
                availableRoom = true;
            }
            if (!availableRoom) {
                System.out.println("Nera laisvu kambariu sio tipo.");
            }
        
}

}

class write implements Runnable
{
    holder hotel_ob;
    write(holder hotel_ob)
    {
        this.hotel_ob=hotel_ob;
    }
    @Override
    public void run() {
          try{
        FileOutputStream fout=new FileOutputStream("hotel_data.txt");
        ObjectOutputStream oos=new ObjectOutputStream(fout);
        oos.writeObject(hotel_ob);
        oos.close();
        fout.close();
        }
        catch (Exception e) {
            System.out.println("Klaida irasant duomenis i faila");
        }     
    }
}

public class Main {
    public static void main(String[] args){
        
        try
        {           
        File f = new File("hotel_data.txt");
        if(f.exists())
        {
            FileInputStream fin=new FileInputStream(f);
            ObjectInputStream ois=new ObjectInputStream(fin);
            Hotel.hotel_ob=(holder)ois.readObject();
            ois.close();
            fin.close();
        }
        Scanner sc = new Scanner(System.in);
        int ch,ch2;
        char wish;
        x:
        do{

        System.out.println("\n************* Viesbutis *************");
        System.out.println("\nPasirinkite norima veiksma:\n1.Svecio registracija \n2.Svecio isregistravimas \n3.Kambariu uzimtumo perziura \n4.Kambario istorija ir statusas \n5.Maisto ir Gerimu uzsakymas \n9.Iseiti is programos\n");
        ch = sc.nextInt();
        switch(ch){
                case 1:System.out.println("\nPasirinkti kambario tipa :\n1.Standartinis vienvietis kambarys \n2.Prabangus vienvietis kambarys\n");
                     ch2 = sc.nextInt();
                     Hotel.bookRoom(ch2);                     
                break;
                case 2:System.out.print("Kambario numeris -");
                     ch2 = sc.nextInt();
                     if(ch2>11)
                         System.out.println("Tokio kambario nėra");
                     else if(ch2>5)
                         Hotel.moveOut(ch2-6,2);
                     else if(ch2>0)
                         Hotel.moveOut(ch2-1,1);
                     else
                         System.out.println("Tokio kambario nera");
                     break;
                case 3:                   
                    Hotel.checkStatus();
                    break;
                case 4: 
                    Hotel.listOccupiedRooms();
                   break;
                case 5: System.out.print("Kambario numeris -");
                     ch2 = sc.nextInt();
                     if(ch2>11)
                         System.out.println("Tokio kambario nėra");
                     else if(ch2>5)
                         Hotel.foodOrder(ch2-6,1);
                     else if(ch2>0)
                         Hotel.foodOrder(ch2-1,2);
                     else
                         System.out.println("Tokio kambario nėra");
                     break;


            case 9:break x;
                
        }
            System.out.println("\nTesti ?: (t/n)");
            wish=sc.next().charAt(0); 
            if(!(wish=='t'||wish=='T'||wish=='n'||wish=='N'))
            {
                System.out.println("Netinkamas Pasirinkimas");
                System.out.println("\nTesti : (t/n)");
                wish=sc.next().charAt(0); 
            }
            
        }while(wish=='t'||wish=='T');    
        
        Thread t=new Thread(new write(Hotel.hotel_ob));
        t.start();
        }        
            catch(Exception e)
            {
                System.out.println("Netinkama ivestis");
            }
    }
}