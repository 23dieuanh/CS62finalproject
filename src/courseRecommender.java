/**
 * The courseRecommender class is responsible for generating personalized course recommendations
 * based on the user's preferences and background.
 * It gives a score to each eligible course and ranks them using a custom comparator via a priority queue.
 * Preferences include major/minor focus, general education requirements, preferred class time, and campus.
 */

 import java.util.*;

 public class courseRecommender {
 
     // Stores user input parsed from a file or terminal prompt
     String[] userInput = new String[8];
 
     // Maps each course to its computed score
     Map<Course, Integer> courseScores = new HashMap<>();
 
     /**
      * Constructs a courseRecommender with a list of user preferences.
      * @param inputList List containing user data (majors, course history, GE needs, preferences)
      */
     public courseRecommender(ArrayList<String> inputList) {
         for (int i = 0; i < inputList.size() && i < 8; i++) {
             userInput[i] = inputList.get(i);
         }
     }
 
     /**
      * Creates a priority queue of eligible courses ranked by score using a comparator built from comparing the scores of 2 courses.
      * @param eligibleCourses List of eligible courses for the user
      * @param comp Comparator to prioritize the courses
      * @return PriorityQueue sorted by highest score first
      */
     public static PriorityQueue<Course> rankCourses(ArrayList<Course> eligibleCourses, Comparator<Course> comp) {
         PriorityQueue<Course> pQ = new PriorityQueue<>(comp);
         for (Course course : eligibleCourses) {
             pQ.add(course);
         }
         return pQ;
     }
 
     /**
      * Assigns a numerical score to each eligible course based on how well it matches user preferences.
      * Score factors include:
      * - Whether user would like more emphasis on major courses or GE courses
      * - Preferred class times
      * - Preferred campus locations
      * Stores results in the courseScores map.
      * @param eligibleCourses List of courses the user is eligible to take
      */
     public void assignScore(ArrayList<Course> eligibleCourses) {
         for (Course c : eligibleCourses) {
             int score = 0;
 
             // Preference: Major or GE focus
             if (userInput[7].equals("major")) {
                 if (c.getMajors().contains(userInput[0]) || c.getMajors().contains(userInput[1])) {
                     score += 10;
                 } else if (c.getMajors().contains(userInput[2])) {
                     score += 9;
                 }
 
                 String[] userGE = userInput[4].split(",");
                 for (String ge : userGE) {
                     if (c.getGEs().contains(ge)) {
                         score += 5;
                     }
                 }
             } else if (userInput[7].equals("GEs")) {
                 if (c.getMajors().contains(userInput[0]) || c.getMajors().contains(userInput[1])) {
                     score += 9;
                 } else if (c.getMajors().contains(userInput[2])) {
                     score += 8;
                 }
 
                 String[] userGE = userInput[4].split(",");
                 for (String ge : userGE) {
                     if (c.getGEs().contains(ge)) {
                         score += 10;
                     }
                 }
             } else { // Neutral user
                 if (c.getMajors().contains(userInput[0]) || c.getMajors().contains(userInput[1])) {
                     score += 10;
                 } else if (c.getMajors().contains(userInput[2])) {
                     score += 9;
                 }
 
                 String[] userGE = userInput[4].split(",");
                 for (String ge : userGE) {
                     if (c.getGEs().contains(ge)) {
                         score += 7;
                     }
                 }
             }
 
             // Time preference scoring
             String time;
             if (c.getEndTime() <= 1230) {
                 time = "morning";
             } else if (c.getEndTime() >= 1300 && c.getEndTime() <= 1800) {
                 time = "afternoon";
             } else {
                 time = "evening";
             }
 
             String[] userTime = userInput[5].split(",");
             for (int i = 0; i < userTime.length; i++) {
                 if (time.equals(userTime[i])) {
                     score += 7 - i;  // Earlier preferences get higher weight
                 }
             }
 
             // Campus preference scoring
             String[] userCampus = userInput[6].split(",");
             for (int i = 0; i < userCampus.length; i++) {
                 if (c.getCampus().equals(userCampus[i])) {
                     score += 5 - i;
                 }
             }
 
             courseScores.put(c, score);
         }
     }
 
     /**
      * Retrieves the score assigned to a course.
      * @param course Course to look up
      * @return Score if assigned; otherwise 0
      */
     public int getScore(Course course) {
         return courseScores.getOrDefault(course, 0);
     }
 
     /**
      * A comparator used for ranking courses in descending order of their score.
      */
     public Comparator<Course> newComparator = new Comparator<Course>() {
         public int compare(Course course1, Course course2) {
             return Integer.compare(getScore(course2), getScore(course1));
         }
     };
 }