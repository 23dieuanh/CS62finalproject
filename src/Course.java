import java.util.ArrayList;

public class Course {
    String courseID;
    String name;
    String major;
    int startTime;
    int endTime;
    String day;
    String campus;
    ArrayList<String> GEs;
    ArrayList<String> prerequisites;
    
    public Course(String name, String courseID){
        this.name = name;
        this.courseID = courseID;
        this.GEs = new ArrayList<>();
        this.prerequisites = new ArrayList<>();
    }

    public String getID(){
        return this.courseID;
    }
    
    public String getMajor(){
        return this.major;
    }
    
    public void setMajor(String major){
        this.major = major;
    }
    
    public String getTime(){
        String output = "";
        output += this.day + ": " + this.startTime + "-" + this.endTime + "\n";
        return output;
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

    @Override
    public String toString(){
        String output = "";
        output = courseID + ": " + name + ";" + "Major: " + major + ";" + "Time: " + getTime() + ";" + "Campus :" + campus + ";" + "Areas fulfilled: " + GEs + ";" + "Prerequisites: " + prerequisites;
        return output;
    }
}