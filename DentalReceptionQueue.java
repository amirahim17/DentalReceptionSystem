import java.util.*;
import java.io.*;
public class DentalReceptionQueue {
   public static int count = 0;
   public static void main(String[] args) throws Exception {
      Scanner in = new Scanner(System.in);
      Queue recordsQ = new Queue();
      DentalReception pat = null;
      readFile(recordsQ);
      System.out.println("Welcome to DentalReception Program\n\n");

      while(true) {
         while(true) {
            menu();
            System.out.print("Choose option: ");
            char o = in.next().charAt(0);
            if (o == 'A') {
               insertRecord(recordsQ);
            } else if (o == 'B') {
               displayRecord(recordsQ);
            } else if (o == 'C') {
               updateRecord(recordsQ);
            } else if (o == 'D') {
               totalDentalReception(recordsQ);
            } else if (o == 'E') {
               popularTreatment(recordsQ);
            } else if (o == 'F') {
               double totalRev = totalRevenue(recordsQ);
               System.out.println("Total Revenue: RM" + totalRev);
            } else {
               if (o == 'Z') {
                  System.out.println("END OF PROGRAM");
                  return;
               }

               System.out.println("\n\nInvalid Input!!");
            }
         }
      }
   }

   public static void writeFile(DentalReception p) throws Exception {
      try {
         FileWriter fw = new FileWriter("patients.txt", true);
         BufferedWriter bw = new BufferedWriter(fw);
         PrintWriter pw = new PrintWriter(bw);
         
         pw.println(p.writeString());
         
         System.out.println("\nQueue elements written to the file successfully.");
         pw.close();
         bw.close();
         fw.close();
      } 
      catch (Exception ex) {}
   }

   public static void readFile(Queue q) {
      try {
         FileReader fr = new FileReader("patients.txt");
         BufferedReader br = new BufferedReader(fr);
         String data = br.readLine();
         while(data != null) {
                StringTokenizer parse = new StringTokenizer(data, ";");
                if(parse.countTokens()>=4){
                    String pn = parse.nextToken();
                    String ic = parse.nextToken();
                    String ti = parse.nextToken();
                    String t = parse.nextToken();
                    
                    DentalReception pat = new DentalReception(pn, ic, ti, t);
                    q.addLast(pat);
                }
                data = br.readLine();
        }
        br.close();

      } 
      catch (Exception e) {
         e.printStackTrace();
      }

   }

   public static void displayRecord(Queue q) throws Exception {
       try {
          DentalReception pat = null;
          Queue tempQ = new Queue();
          System.out.println("Display all record(s)...");

          while(!q.isEmpty()) {
             pat = (DentalReception)q.dequeue();
             tempQ.enqueue(pat);
             System.out.println(pat.toString());
          }
          insertBacktoQ(q, tempQ);
       } 
       catch (Exception x) {}
    }

   public static void insertBacktoQ(Queue q, Queue tempQ) {
      DentalReception pat = null;
      while(!tempQ.isEmpty()) {
         pat = (DentalReception)tempQ.dequeue();
         q.enqueue(pat);
      }
   }

   public static void insertRecord(Queue q) throws Exception {
      Scanner in = new Scanner(System.in);
      System.out.println("[Insert Data]");
      System.out.print("Full Name: ");
      String n = in.nextLine();
      System.out.print("IC Number: ");
      String ic = in.next();
      System.out.print("Time [HHMM] :");
      String ti = in.next();
      in.nextLine();
      System.out.println("1-Tooth Filling|2-Tooth Scaling|3-Braces|4-Dentures");
      System.out.print("Treatment: ");
      int choice = in.nextInt();
      String t;
      if (choice == 1) {
         t = "Tooth Filling";
      } 
      else if (choice == 2) {
         t = "Tooth Scaling";
      } 
      else if (choice == 3) {
         t = "Braces";
      } 
      else {
         t = "Dentures";
      }

      DentalReception pat = new DentalReception(n, ic, ti, t);

      try {
         writeFile(pat);
         q.enqueue(pat);
      } 
      catch (Exception ex) {}
   }


   public static void menu() {
      System.out.println("Option              Menu        ");
      System.out.println("A                   Add New Appointments");
      System.out.println("B                   View All Appointments");
      System.out.println("C                   Update Appointments");
      System.out.println("D                   Find Total Appointments");
      System.out.println("E                   Find The Most Popular Treatment");
      System.out.println("F                   Total Revenue");
      System.out.println("Z                   Exit Program");
   }

   public static void totalDentalReception(Queue q) throws Exception {
      Queue tempQ = new Queue();
      System.out.println(q);
      int count = 0;

      for(DentalReception pat = null; !q.isEmpty(); ++count) {
         pat = (DentalReception)q.dequeue();
         tempQ.enqueue(pat);
      }
      System.out.println("Total DentalReceptions: " + count);
      insertBacktoQ(q, tempQ);
   }

   public static void updateRecord(Queue q) throws Exception {
      Scanner in = new Scanner(System.in);
      System.out.print("Enter IC number to update: ");
      String icnum = in.next();
      DentalReception pat = null;
      Queue tempQ = new Queue();
      boolean found = false;

      while(!q.isEmpty()) {
         pat = (DentalReception)q.dequeue();
         tempQ.enqueue(pat);
         if (pat.getICNum().equals(icnum)) {
            found = true;
            System.out.print("Enter number to update (1-Type of dental treatment, 2-Time)");
            int upd = in.nextInt();
            in.nextLine();
            if (upd == 1) {
               System.out.println("1-Tooth Filling|2-Tooth Scaling|3-Braces|4-Dentures");
               System.out.print("Enter type of dental treatment: ");
               int choice = in.nextInt();
               String t;
               if (choice == 1) {
                  t = "Tooth Filling";
               } else if (choice == 2) {
                  t = "Tooth Scaling";
               } else if (choice == 3) {
                  t = "Braces";
               } else {
                  t = "Dentures";
               }

               pat.setTreatment(t);
            } else if (upd == 2) {
               System.out.print("Enter time for appointment: ");
               String appointmentT = in.next();
               pat.setTime(appointmentT);
            }
            break;
         }
      }
      if (!found) {
         System.out.println("DentalReception with IC number " + icnum + " is not found");
      }
      insertBacktoQ(q, tempQ);
   }

   public static double totalRevenue(Queue q) throws Exception {
      double total = 0.0;
      DentalReception pat = null;

      Queue tempQ = new Queue();
      while(!q.isEmpty()){
        pat = (DentalReception) q.dequeue();
        total += pat.calcPayment();
        tempQ.enqueue(pat);
        }
        
      insertBacktoQ(q, tempQ);
      return total;
   }

   public static void popularTreatment(Queue q) {
      Scanner in = new Scanner(System.in);
      
      Queue toothScalingQ = new Queue();
      Queue toothFillingQ = new Queue();
      Queue bracesQ = new Queue();
      Queue denturesQ = new Queue();
      
      Queue tempQ = new Queue();
      
      DentalReception pat = null;
      while(!q.isEmpty()) {
         pat = (DentalReception)q.dequeue();
         if (pat.getTreatment().equalsIgnoreCase("Tooth Scaling")) {
            toothScalingQ.enqueue(pat);
         } else if (pat.getTreatment().equalsIgnoreCase("Tooth Filling")) {
            toothFillingQ.enqueue(pat);
         } else if (pat.getTreatment().equalsIgnoreCase("Braces")) {
            bracesQ.enqueue(pat);
         } else {
            denturesQ.enqueue(pat);
         }
      }

      int scalingCount = 0;
      int fillingCount = 0;
      int bracesCount = 0;
      int denturesCount = 0;
      DentalReception patD;
      
      while(!toothScalingQ.isEmpty()){
          patD = (DentalReception)toothScalingQ.dequeue();
          tempQ.enqueue(patD);
          scalingCount++;
      }
      
      while(!toothFillingQ.isEmpty()) {
         patD = (DentalReception)toothFillingQ.dequeue();
         tempQ.enqueue(patD);
         fillingCount++;
      }

      while(!bracesQ.isEmpty()) {
         patD = (DentalReception)bracesQ.dequeue();
         tempQ.enqueue(patD);
         bracesCount++;
      }

      while(!denturesQ.isEmpty()) {
         patD = (DentalReception)denturesQ.dequeue();
         tempQ.enqueue(patD);
         denturesCount++;
      }

      System.out.println("\nTooth Scaling count : " + scalingCount);
      System.out.println("Tooth Filling count : " + fillingCount);
      System.out.println("Braces count : " + bracesCount);
      System.out.println("Dentures count : " + denturesCount);
      
      if (scalingCount > fillingCount && scalingCount > bracesCount && scalingCount > denturesCount) {
         System.out.println("The most popular treatment: Tooth Scaling\n");
      } else if (fillingCount > scalingCount && fillingCount > bracesCount && fillingCount > denturesCount) {
         System.out.println("The most popular treatment: Tooth Filling\n");
      } else if (bracesCount > scalingCount && bracesCount > fillingCount && bracesCount > denturesCount) {
         System.out.println("The most popular treatment: Braces\n");
      } else if (denturesCount > scalingCount && denturesCount > fillingCount && denturesCount > bracesCount) {
         System.out.println("The most popular treatment: Dentures\n");
      }
      insertBacktoQ(q, tempQ);
   }
}