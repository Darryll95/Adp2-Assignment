
package za.ac.cput.assignment3project;
import java.io.BufferedWriter;
import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

/**
 *
 * @author 220253595_Darryll Merkeur ADP3 Assignment
 */
public class Result {
    String stakeholder = "stakeholder.ser";
    FileWriter fw;
    BufferedWriter buf;
    BufferedWriter noob;
    InputStream instream;
    ObjectInputStream objstream;
    
    public void myFile(String myFile)
    {
        try
        {
            fw = new FileWriter(new File(myFile));
            buf = new BufferedWriter(fw);
            System.out.println(" Has been created "+ myFile );
            
        } catch (IOException ioe)
        {
            System.exit(0);
        }
    }
    private ArrayList<Customer> customersList()
    {
     ArrayList<Customer> customers = new ArrayList<>();
     try
     {
        instream = new FileInputStream(stakeholder);
        objstream = new ObjectInputStream(instream);
            while (true)
            {
                Object obj = objstream .readObject();
                if (obj instanceof Customer)
                {
                    customers.add((Customer) obj);
                }
            }
            
        } catch (EOFException eofe)
        {
            
        } catch (IOException | ClassNotFoundException e)
        {
           System.exit(0);
            
        } finally
        {
            try
            {
               buf.close();
                objstream.close();
                
            } catch (IOException e)
            {
            }
        }
        if (!customers.isEmpty())
        {
            Collections.sort(customers,
                    (Customer sort, Customer sorted) ->
                     sort.getStHolderId().compareTo(sorted.getStHolderId())
            );
        }
        return customers;
    }
    private void customeroutput()
    {
        String formula = "%s\t%-10s\t%-10s\t%-10s\t%-10s\n";
        String line = "===========================================================\n";
        
        try
        {   
            System.out.print( "======================= CUSTOMERS =========================\n");
            
            System.out.printf(formula, "ID", "Name", "Surname", 
                    "Date Of Birth", "Age");
            
            System.out.print(line);
            
            for (int i = 0; i < customersList().size(); i++)
            {   
                System.out.printf(
                        formula,
                        customersList().get(i).getStHolderId(),
                        customersList().get(i).getFirstName(),
                        customersList().get(i).getSurName(),
                        formatDate(customersList().get(i).getDateOfBirth()),
                        Agecalculate(customersList().get(i).getDateOfBirth())
                );
            }
           System.out.println("\nNumber of customers who can rent:" + canRent());
           System.out.println("\nNumber of customers who cannot rent:"+ canNotRent());
        } catch (Exception e)
        {
        }
    }
    private String formatDate(String tome)
    {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(
                "dd MMM yyyy", 
                Locale.ENGLISH);

        LocalDate parTome = LocalDate.parse(tome);
        return parTome.format(formatter);
    }
    
    private int Agecalculate(String tome)
    {
        LocalDate parseDob = LocalDate.parse(tome); 
        int dobYear  = parseDob.getYear(); 
        ZonedDateTime todayDate = ZonedDateTime.now(); 
        int currentYear = todayDate.getYear();
        return currentYear - dobYear;
    }
    
    private int canRent()
    {
        int canRent = 0;
        for (int i = 0; i < customersList().size(); i++)
        {
            if (customersList().get(i).getCanRent())
            {
                canRent += 1;
            }
        } 
        return canRent;
    }
    
    private int canNotRent()
    {
        int canNotRent = 0;
        for (int i = 0; i < customersList().size(); i++)
        {
            if (!customersList().get(i).getCanRent())
            {
                canNotRent += 1;
            }
        }
        return canNotRent;
    }
    private ArrayList<Supplier> supList()
    {
        ArrayList<Supplier> suppliers = new ArrayList<>();
        
        try
        {
            instream = new FileInputStream(new File(stakeholder));
            objstream = new ObjectInputStream(instream);
            while (true)
            {
                Object obj = objstream.readObject();
                if (obj instanceof Supplier)
                {
                    suppliers.add((Supplier) obj);
                }
            }
            
        } catch (EOFException eofe)
        {
          
        } catch (IOException | ClassNotFoundException e)
        {
        } finally
        {
            try
            {
                instream.close();
                objstream.close();
                
            } catch (IOException e)
            {
            }
        }
        if (!suppliers.isEmpty())
        {
            Collections.sort(
            suppliers, 
            (Supplier su, Supplier sou) -> 
            su.getName().compareTo(sou.getName())
            );
        }
        return suppliers;
    }
    
    private void supplierFile()
    {
        try
        {
            System.out.print("======================= SUPPLIERS =========================\n");
            System.out.printf("%s\t%-20s\t%-10s\t%-10s\n", "ID", "Name", "Prod Type",
                "Description");
            System.out.print("===========================================================\n");
            for (int i = 0; i < supList().size(); i++)
            {
               System.out.printf(
                        "%s\t%-20s\t%-10s\t%-10s\n",
                        supList().get(i).getStHolderId(),
                        supList().get(i).getName(),
                        supList().get(i).getProductType(),
                        supList().get(i).getProductDescription()
                );
            }
            
        } catch (Exception e)
        {
        }
    }
    
    public void closethisFile(String myFile)
    {
        try
        {
            fw.close();
            buf.close();
            System.out.println(myFile + " Closed");

        } catch (IOException ex)
        {
        }
    }
    public static void main(String[] args)
    {
        Result sheetprint = new Result();
        sheetprint.myFile("customerOutput.txt");
        sheetprint.customeroutput();
        sheetprint.closethisFile("customerOutput.txt");
        System.out.println("---------------------------------------------------");
        sheetprint.myFile("supplierOutput.txt");
        sheetprint.supplierFile();
        sheetprint.closethisFile("supplierOutput.txt");
    }
}
