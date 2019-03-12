package sam.pillpal.models;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Medication {
    private long id = 0;
    private String name = null;
    private String dosage = null;
    private String instructions = null;
    private Date refillDate = null;

    public Medication(long id, String name, String dosage, String instructions, Date refillDate) {
        this.id = id;
        this.name = name;
        this.dosage = dosage;
        this.instructions = instructions;
        this.refillDate = refillDate;
    }
     public String getName(){
        return this.name;
     }
     public String getDosage(){
        return this.dosage;
     }
     public String getRefillDate(){
        // TODO localization
         DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy hh:mm");
         String strDate = dateFormat.format(this.refillDate);
        return strDate;
     }
}


