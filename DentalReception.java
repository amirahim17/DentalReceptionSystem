
public class DentalReception
{
    private String name;
    private String icNum;
    private String time;
    private String treatment;//Tooth Filling/Tooth Scaling/Braces/Dentures
    
    public DentalReception(){}
    
    public DentalReception(String n, String ic, String ti, String t){
        this.name=n;
        this.icNum=ic;
        this.time=ti;
        this.treatment=t;
    }
    
    public void setName(String n){this.name=n;}
    public void setICNum(String ic){this.icNum=ic;}
    public void setTime(String ti){this.time=ti;}
    public void setTreatment(String t){this.treatment=t;}
    
    public String getName(){return this.name;}
    public String getICNum(){return this.icNum;}
    public String getTime(){return this.time;}
    public String getTreatment(){return this.treatment;}
    
    public double calcPayment(){//filling scaling braces dentures
        double p=0;
        if(getTreatment().equalsIgnoreCase("Tooth Scaling")){
            p = 120.00;
        }
        else if(getTreatment().equalsIgnoreCase("Tooth Filling")){
            p = 150.00;
        }
        else if(getTreatment().equalsIgnoreCase("Braces")){
            p = 5500.00;
        }
        else{//dentures
            p =400.00;
        }
        return p;
    }
    
    public String toString(){
        return "Full Name: "+getName()+"\nIC Number: "+getICNum()+"\nTime for Reservation: "+getTime()+"\nTreatment: "+getTreatment()+"\n\n";
    }
    
    public String writeString(){ //to write file
        return this.getName()+ ";" +this.getICNum()+ ";" +this.getTime()+ ";" +this.getTreatment();
    }
    
    
}
