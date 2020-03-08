import edu.duke.*;
import org.apache.commons.csv.*;
import java.io.*;
/**
 * Write a description of test1 here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class test1 {
public int getTotalBirthsRankedHigher(int year, String name, String gender){
String fname = ("us_babynames/us_babynames_by_year/yob" + year + ".csv");
FileResource fr = new FileResource(fname);
int SumF = 0;
int SumM = 0;
int Sumoverall = 0;
String findName = null;
String findGender = null;
int findcount = 0;
for (CSVRecord rec : fr.getCSVParser(false)){
findName = rec.get(0);
findGender = rec.get(1);
findcount = Integer.parseInt(rec.get(2));
if(findGender.equals("F")){  
     if(findGender.equals(gender) && findName.equals(name)){
        Sumoverall = SumF;
        break;
        }
        SumF = SumF + findcount;
}
else if(findGender.equals("M")){
  if(findGender.equals(gender) && findName.equals(name)){
    Sumoverall = SumM;
    break;
    }
    SumM = SumM + findcount;
}
}
return Sumoverall;
}
        
public void testing(){
 int sumval = getTotalBirthsRankedHigher(2012, "Anthony", "M");
 System.out.println("Total births ranked higher is "+sumval);
}
}
