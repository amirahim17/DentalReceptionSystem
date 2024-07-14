import java.util.*;
import java.io.*;
public class DentalReceptionLinkedList
{
    public static int count = 0;
    public static void main(String args[])throws Exception{
        Scanner in = new Scanner(System.in);
        LinkedList recordsLL = new LinkedList();
        readFile(recordsLL);
        DentalReception pat = null;
        
        System.out.println("Welcome to DentalReception Program\n\n");
            while(true){
            menu();
            System.out.print("Choose option: ");
            char o = in.next().charAt(0); // option variable
            if(o=='A'){
                insertRecord(recordsLL);
            }
            else if(o=='B'){
                displayRecord(recordsLL);
            }
            else if(o=='C'){ //update record
                updateRecord(recordsLL);
            }
            else if(o=='D'){ //total DentalReception
                totalDentalReception(recordsLL);
            }
            else if(o=='E'){ //max DentalReception - from separated linkedlist
                popularTreatment(recordsLL); // move/copy
            }
            else if(o=='F'){
                double totalRev = totalRevenue(recordsLL);
                System.out.println("Total Revenue: RM" + totalRev);
                continue;
            }
            else if(o=='Z'){
                System.out.println("END OF PROGRAM");
                break;
            }
            else{
                System.out.println("\n\nInvalid Input!!");
            }
        }
    }
    
    public static void writeFile(DentalReception p) throws Exception {
        try{
            FileWriter fw = new FileWriter("patients.txt", true); // Set the second parameter to true for append mode
            BufferedWriter bw = new BufferedWriter(fw);
            PrintWriter pw = new PrintWriter(bw);
            
            pw.println(p.writeString());
            
            System.out.println("\nLinked list elements written to the file successfully.");
            pw.close();
            bw.close();
            fw.close();
        }
        catch(Exception ex) {}
    }
    
    public static void readFile(LinkedList ll){
        try{
            FileReader fr = new FileReader("patients.txt");
            BufferedReader br = new BufferedReader(fr); // read file
            String data = br.readLine();
            while(data != null) {
                StringTokenizer parse = new StringTokenizer(data, ";");
                if(parse.countTokens()>=4){
                    String pn = parse.nextToken();
                    String ic = parse.nextToken();
                    String ti = parse.nextToken();
                    String t = parse.nextToken();
                    
                    DentalReception pat = new DentalReception(pn, ic, ti, t);
                    ll.addLast(pat);
                }
                data = br.readLine();
            }
            br.close();
        }
        catch(Exception e) {
            e.printStackTrace();
        } 
    }
    
    public static void displayRecord(LinkedList ll) throws Exception {
        try {
            DentalReception pat = (DentalReception) ll.getFirst();
            System.out.println("Display all record(s)...");
            while(!ll.isEmpty()){
                System.out.println(pat.toString());
                pat = (DentalReception) ll.getNext();
            }
        }
        catch(Exception x) {}
    }
    
    public static void insertRecord(LinkedList ll) throws Exception {
        Scanner in = new Scanner(System.in);
        System.out.println("[Insert Data]");
        System.out.print("Full Name: ");
        String n = in.nextLine();
        System.out.print("IC Number: ");
        String ic = in.next();
        System.out.print("Time [HHMM] :");
        String ti = in.next();
        in.nextLine(); // Consume the newline character left by next()
        System.out.println("1-Tooth Filling|2-Tooth Scaling|3-Braces|4-Dentures");
        System.out.print("Treatment: ");
        int choice = in.nextInt();
        String t;
        if(choice == 1){
            t = "Tooth Filling";
        }
        else if(choice == 2){
            t = "Tooth Scaling";
        }
        else if(choice==3){
            t = "Braces";
        }
        else{
            t = "Dentures";
        }
        
        DentalReception pat = new DentalReception(n,ic,ti,t);
        
        try {
            writeFile(pat);
            ll.addLast(pat);
        }
        catch(Exception ex) {}
    }
    
    public static void menu(){
        System.out.println("Option              Menu        ");
        System.out.println("A                   Add New Appointment"); //must have
        System.out.println("B                   View All Appointments"); //display all items
        System.out.println("C                   Update Appointments"); //searching - update
        System.out.println("D                   Find Total Appointments"); //searching - count
        System.out.println("E                   Find The Most Popular Treatment"); //max
        System.out.println("F                   Total Revenue");//total
        System.out.println("Z                   Exit Program"); 
    }
    
    public static void totalDentalReception(LinkedList ll){
        Scanner in = new Scanner(System.in);
        
        System.out.println(ll);
        
        int count = 0;
        DentalReception pat = null;
        
        pat = (DentalReception) ll.getFirst();
        while(pat!=null){
            count++;
            pat = (DentalReception) ll.getNext();
        }
        System.out.println("Total DentalReceptions: "+count);
    }
    
    public static void updateRecord(LinkedList ll){
        Scanner in = new Scanner(System.in);
        System.out.print("Enter IC number to update: ");
        String icnum = in.next();
        DentalReception pat = null;
        
        boolean found = false;
        pat = (DentalReception) ll.getFirst();
        while(pat!=null){
            if(pat.getICNum().equals(icnum)){
                found = true;
                System.out.print("Enter number to update (1-Type of dental treatment, 2-Time): ");
                int upd = in.nextInt();
                in.nextLine(); // consume additional thing
                if(upd==1){
                    System.out.println("1-Tooth Filling|2-Tooth Scaling|3-Braces|4-Dentures");
                    System.out.print("Enter type of dental treatment: ");
                    int choice = in.nextInt();
                    String t;
                    if(choice==1){
                        t="Tooth Filling";
                    }
                    else if(choice==2){
                        t="Tooth Scaling";
                    }
                    else if(choice==3){
                        t="Braces";
                    }
                    else{
                        t="Dentures";
                    }
                    pat.setTreatment(t);
                }
                else if(upd==2){
                    System.out.print("Enter time for appointment: ");
                    String appointmentT = in.next();
                    pat.setTime(appointmentT);
                }
                break;
            }
            pat = (DentalReception) ll.getNext();
        }
        if(!found){
            System.out.println("DentalReception with IC number "+icnum+" is not found");
        }
    }
    
    public static double totalRevenue(LinkedList ll){
        double total = 0.00;
        DentalReception pat = null;
        
        pat = (DentalReception) ll.getFirst();
        while(pat!=null){
            total += pat.calcPayment();
            pat = (DentalReception) ll.getNext();
        }
        
        return total;
    }
    
    public static void popularTreatment(LinkedList ll){
        Scanner in = new Scanner(System.in);
        
        LinkedList toothScalingLL = new LinkedList();
        LinkedList toothFillingLL = new LinkedList();
        LinkedList bracesLL = new LinkedList();
        LinkedList denturesLL = new LinkedList();
        
        DentalReception pat = null;
        
        pat = (DentalReception) ll.getFirst();
        while(pat!=null){
            //System.out.println("Debug: Original LinkedList - " + pat.toString());
            if(pat.getTreatment().equalsIgnoreCase("Tooth Scaling")){
                toothScalingLL.addLast(pat);
            }
            else if(pat.getTreatment().equalsIgnoreCase("Tooth Filling")){
                toothFillingLL.addLast(pat);
            }
            else if(pat.getTreatment().equalsIgnoreCase("Braces")){
                bracesLL.addLast(pat);
            }
            else{//dentures
                denturesLL.addLast(pat);
            }
            pat = (DentalReception) ll.getNext();
        }
        //System.out.println("Debug: toothScalingLL - " + toothScalingLL.toString());
        //System.out.println("Debug: toothFillingLL - " + toothFillingLL.toString());
        //System.out.println("Debug: bracesLL - " + bracesLL.toString());
        //System.out.println("Debug: denturesLL - " + denturesLL.toString());

        int scalingCount = 0;
        int fillingCount = 0;
        int bracesCount = 0;
        int denturesCount = 0;
        
        DentalReception patS = (DentalReception) toothScalingLL.removeFirst();
        while(patS!=null){
            scalingCount++;
            patS = (DentalReception) toothScalingLL.removeFirst();
        }
        
        DentalReception patF = (DentalReception) toothFillingLL.removeFirst();
        while(patF!=null){
            fillingCount++;
            patF = (DentalReception) toothFillingLL.removeFirst();
        }
        
        DentalReception patB = (DentalReception) bracesLL.removeFirst();
        while(patB!=null){
            bracesCount++;
            patB = (DentalReception) bracesLL.removeFirst();
        }
        
        DentalReception patD = (DentalReception) denturesLL.removeFirst();
        while(patD!=null){
            denturesCount++;
            patD = (DentalReception) denturesLL.removeFirst();
        }
        
        System.out.println("\nTooth Scaling count : "+scalingCount);
        System.out.println("Tooth Filling count : "+fillingCount);
        System.out.println("Braces count : "+bracesCount);
        System.out.println("Dentures count : "+denturesCount);
        
        if(scalingCount>fillingCount && scalingCount>bracesCount && scalingCount>denturesCount){
            System.out.println("The most popular treatment: Tooth Scaling\n");
        }
        else if(fillingCount>scalingCount && fillingCount>bracesCount && fillingCount>denturesCount){
            System.out.println("The most popular treatment: Tooth Filling\n");
        }
        else if(bracesCount>scalingCount && bracesCount>fillingCount && bracesCount>denturesCount){
            System.out.println("The most popular treatment: Braces\n");
        }
        else if(denturesCount>scalingCount && denturesCount>fillingCount && denturesCount>bracesCount){
            System.out.println("The most popular treatment: Dentures\n");
        }
    }
}

