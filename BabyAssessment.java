import edu.duke.*;
import org.apache.commons.csv.*;
import java.io.*;
/**
 * Write a description of BabyAssessment here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class BabyAssessment {
    
public void printNames(FileResource fr){
   for(CSVRecord rec : fr.getCSVParser(false)){
    int numBorn = Integer.parseInt(rec.get(2));
    if(numBorn<=100){
      System.out.println("Name "+rec.get(0)+
                  " Gender "+rec.get(1)+
                  " Num Born "+rec.get(2));
    }
   }  
}

public void totalBirths(FileResource fr){
  int totalBirth = 0;
  int totalBoys = 0;
  int totalGirls = 0;
  for(CSVRecord rec : fr.getCSVParser(false)){
    totalBirth +=1;
      if(rec.get(1).equals("M")){
        totalBoys +=1;
      }
      else{
        totalGirls += 1;
      }
  }
  System.out.println("Total Birth = " +totalBirth);
  System.out.println("Total Boys = " +totalBoys);
  System.out.println("Total Girls = " +totalGirls);
}

public int getRank(int year, String name, String gender){
   String filename = ("us_babynames/us_babynames_by_year/yob" + year + ".csv");
   FileResource fr = new FileResource(filename);
   String findName = null;
   String findGender = null;
   int rankF = 0;
   int rankM = 0;
   int Rankoverall = 0;
     for (CSVRecord rec : fr.getCSVParser(false)) {
        findName = rec.get(0);
        findGender = rec.get(1);
        if(findGender.equals("F")){
           rankF++;
           if(findName.equals(name) && findGender.equals(gender)){
           Rankoverall = rankF;
           break;
           }            
        } 
        else if (findGender.equals("M")){
           rankM++;
           if(findName.equals(name) && findGender.equals(gender)){
              Rankoverall = rankM;
              break;
             }
             else Rankoverall = -1;
           }  
          } 
        return Rankoverall;
}

public String getName(int year, int rank, String gender){
  String filename = ("us_babynames/us_babynames_by_year/yob"+year+".csv");
  FileResource fr = new FileResource(filename);
  int countRankM = 0;
  int countRankF = 0;
  String Babyname = null;
  for(CSVRecord rec : fr.getCSVParser(false)){
    if(rec.get(1).equals("F")){
      countRankF = countRankF + 1;
      if(countRankF == rank && rec.get(1).equals(gender)){
        Babyname = rec.get(0);
        }
      }
      else if(rec.get(1).equals("M")){
      countRankM = countRankM + 1;
      if(countRankM == rank && rec.get(1).equals(gender)){
        Babyname = rec.get(0);
        }
      }
      else{
        Babyname = "No Name";
        }
    }
    return Babyname;
}

public void whatIsNameInYear(String name, int year, int newyear, String gender){
  int oldRank = getRank(year, name, gender);
  String newname = getName(newyear, oldRank, gender);
  System.out.println(name+" born in "+year+" would be "+newname+" in "+newyear);
}

public int yearOfHighestRank(String name, String gender){
  int fileyear = 0;
  int RankGirls = 0;
  int RankBoys = 0;
  int Rankoverall = 0;
  String filename = "";
  int fileyr = 0;
  int currRank = 0;
  int highestRank = 1000000000;
  DirectoryResource dr = new DirectoryResource();
  for( File f : dr.selectedFiles()){
    FileResource fr = new FileResource(f);
    filename = f.getPath();
    fileyr = Integer.parseInt(filename.substring(99,103));
    currRank = getRank(fileyr, name, gender);
    if(currRank < highestRank){
      highestRank = currRank;
      fileyear = fileyr;
     }
    }
  return fileyear; 
}

public double getAverageRank(String name, String gender){
DirectoryResource dr = new DirectoryResource();
String filename = "";
int currRank = 0;
int fileyr = 0;
int totalval = 0;
int count = 0;
double avg = 0;
for( File f : dr.selectedFiles()){
    FileResource fr = new FileResource(f);
    filename = f.getPath();
    fileyr = Integer.parseInt(filename.substring(99,103));
    currRank = getRank(fileyr, name, gender);
    count = count + 1;
    if(currRank != -1){
    totalval = totalval + currRank;
    }
}
avg = (double) totalval/count;
return avg;
}

public int getTotalBirthsRankedHigher(int year, String name, String gender){
String fname = ("us_babynames/us_babynames_by_year/yob" + year + ".csv");
FileResource fr = new FileResource(fname);
int SumF = 0;
int SumM = 0;
int Sumoverall = 0;
String findName = null;
String findGender = null;
int findcount = 0;6
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
FileResource fr = new FileResource();
printNames(fr);
totalBirths(fr);
  
int NameRank = getRank(1971,"Frank","M"); 
System.out.println("Rank is "+NameRank); 

String RankName = getName(1980, 350, "F");
System.out.println("Name is "+RankName);

whatIsNameInYear("Owen", 1974, 2014, "M");

int highestyear = yearOfHighestRank("Mich","M");
System.out.println("Highest Ranking is in "+highestyear);

double averageval = getAverageRank("Robert","M");
System.out.println("Average Rank is "+averageval);

int sumval = getTotalBirthsRankedHigher(1990, "Drew", "M");
System.out.println("Total births ranked higher is "+sumval);
}
}
