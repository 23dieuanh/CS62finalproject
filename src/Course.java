import java.util.ArrayList;
import java.util.Objects;

public class Course {
    String courseID;
    String name;
    ArrayList<String> majors;
    int startTime;
    int endTime;
    String day;
    String campus;
    ArrayList<String> GEs;
    ArrayList<String> prerequisites;
    
    public Course(String name, String courseID){
        this.name = name;
        this.courseID = courseID;
        this.majors = new ArrayList<>();
        this.GEs = new ArrayList<>();
        this.prerequisites = new ArrayList<>();
    }

    public String getID(){
        return this.courseID;
    }
    
    public ArrayList<String> getMajors(){
        return this.majors;
    }
    
    public void setMajors(ArrayList<String> majors){
        for (String major: majors){
            this.majors.add(major);
        } 
    }
    
    public String getTime(){
        String output = "";
        output += this.day + ": " + this.startTime + "-" + this.endTime + "\n";
        return output;
    }

    public int getStartTime(){
        return this.startTime;
    }

    public int getEndTime(){
        return this.endTime;
    }

    public void setStartTime(int startTime){
        this.startTime = startTime;
    }

    public void setEndTime(int endTime){
        this.endTime = endTime;
    }

    public void setDay(String day){
        this.day = day;
    }

    public String getCampus(){
        return campus;
    }
    
    public void setCampus(String campus){
        this.campus = campus;
    }

    public ArrayList<String> getGEs(){
        return this.GEs;
    }
    
    public void setGEs(ArrayList<String> GEs){
        for (String ge: GEs){
            this.GEs.add(ge);
        }
    }
    

    public ArrayList<String> getPreReqs(){
        return this.prerequisites;
    }

    public void setPreReqs(ArrayList<String> prerequisites){
        for (String prereq: prerequisites){
            this.prerequisites.add(prereq);
        }
    }
    
    public int hashCode(){
        return Objects.hash(this.courseID); //shortcut
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Course other = (Course) obj;
        return this.courseID.equals(other.courseID);
    }

    @Override
    public String toString(){
        String output = "";
        output = courseID + ": " + name + ";" + "Majors: " + majors + ";" + "Time: " + getTime() + ";" + "Campus :" + campus + ";" + "Areas fulfilled: " + GEs + ";" + "Prerequisites: " + prerequisites;
        return output;
    }
}