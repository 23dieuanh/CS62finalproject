/**
 * The Course class represents a single college course offered in the semester.
 * It contains information such as course ID, name, schedule, campus,
 * GE requirements, majors it fulfills, and prerequisites.
 */

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
     ArrayList<ArrayList<String>> prerequisites = new ArrayList<>();
 
     /**
      * Constructs a Course with a given name and course ID.
      * @param name Name of the course
      * @param courseID Unique course identifier (e.g., MATH030)
      */
     public Course(String name, String courseID){
         this.name = name;
         this.courseID = courseID;
         this.majors = new ArrayList<>();
         this.GEs = new ArrayList<>();
         this.prerequisites = new ArrayList<>();
     }
 
     /**
      * @return the course ID
      */
     public String getID(){ return this.courseID; }
 
     /**
      * @return The name of the course
      */
     public String getName(){ return this.name; }
 
     /**
      * @return A list of majors this course fulfills
      */
     public ArrayList<String> getMajors(){ return this.majors; }
 
     /**
      * Sets the majors this course belongs to.
      * @param majors List of major codes
      */
     public void setMajors(ArrayList<String> majors){
         for (String major : majors){
             this.majors.add(major);
         }
     }
 
     /**
      * @return A formatted string with the course day and time
      */
     public String getTime(){
         return this.day + ": " + this.startTime + "-" + this.endTime + "\n";
     }
 
     public int getStartTime(){ return this.startTime; }
     public int getEndTime(){ return this.endTime; }
 
     /**
      * Sets the course start time.
      * @param startTime Time in HHMM format
      */
     public void setStartTime(int startTime){ this.startTime = startTime; }
 
     /**
      * Sets the course end time.
      * @param endTime Time in HHMM format
      */
     public void setEndTime(int endTime){ this.endTime = endTime; }
 
     /**
      * Sets the days of the week the course meets.
      * @param day A string like "-M-W-F-"
      */
     public void setDay(String day){ this.day = day; }
 
     /**
      * @return The campus where the course is offered
      */
     public String getCampus(){ return campus; }
 
     /**
      * Sets the campus where the course is held.
      * @param campus Campus code
      */
     public void setCampus(String campus){ this.campus = campus; }
 
     /**
      * @return A list of general education requirements this course fulfills
      */
     public ArrayList<String> getGEs(){ return this.GEs; }
 
     /**
      * Sets the GE requirements this course fulfills.
      * @param GEs List of GE area codes
      */
     public void setGEs(ArrayList<String> GEs){
         for (String ge : GEs){
             this.GEs.add(ge);
         }
     }
 
     /**
      * @return A nested list representing prerequisites.
      *         Each inner list is an OR group, outer list is AND.
      */
     public ArrayList<ArrayList<String>> getPreReqs(){ return this.prerequisites; }
 
     /**
      * Sets the prerequisites for this course.
      * @param prerequisites Nested list of prerequisites
      */
     public void setPreReqs(ArrayList<ArrayList<String>> prerequisites){
         for (ArrayList<String> prereqGroup : prerequisites){
             this.prerequisites.add(prereqGroup);
         }
     }
 
     /**
      * Generates a hash code based on the course ID.
      * @return Hash code of the course
      */
     public int hashCode(){ return Objects.hash(this.courseID); }
 
     /**
      * Checks equality based on course ID.
      * @param obj Object to compare
      * @return true if both objects are courses with the same ID
      */
     @Override
     public boolean equals(Object obj) {
         if (this == obj) return true;
         if (obj == null || getClass() != obj.getClass()) return false;
         Course other = (Course) obj;
         return this.courseID.equals(other.courseID);
     }
 
     /**
      * Provides a string representation of the course object.
      * @return String summarizing course attributes
      */
     @Override
     public String toString(){
         return courseID + ": " + name + "; " +
                "Majors: " + majors + "; " +
                "Time: " + getTime() + "; " +
                "Campus :" + campus + "; " +
                "Areas fulfilled: " + GEs + "; " +
                "Prerequisites: " + prerequisites + "\n";
     }
 }