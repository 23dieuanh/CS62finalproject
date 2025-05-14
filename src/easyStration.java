/**
 * Class EasyStration uses an interactive user input scanner, takes in a user input and a course dataset file, builds a course dependency graph for each major and
 * and and implements the features of:
 * - Checking eligibility for courses based on prerequisites and their course history
 * - Listing all available courses
 * - Displaying course layout in a structured manner
 * - Giving course recommendations
 * - Displayinig remaining courses to complete a major
 * 
 */

 import java.io.*;
 import java.util.*;
 import java.util.regex.*;
 
 public class easyStration {
     static Map<String, Course> courseArray = new HashMap<>();
     static Map<String, CourseGraph> allMajorCourses = new HashMap<>();
     static ArrayList<String> userInput = new ArrayList<>();
 
     /**
      * Main method that manages user input parsing, dataset processing, and user interactions.
      */
     public static void main(String[] args) {
 
         // Read user input file
         try {
             Scanner inputFile = new Scanner(System.in);
             System.out.println("Please enter filename: ");
             String userFile = inputFile.nextLine().trim();
 
             Scanner scan = new Scanner(new File(userFile));
             while (scan.hasNextLine()) {
                 userInput.add(scan.nextLine().trim());
             }
             scan.close();
         } catch (FileNotFoundException e) {
             System.out.println("User input file not found");
             return;
         }
 
         // Read course dataset and populate course objects and graphs
         try {
             Scanner scan = new Scanner(new File("finalproject_dataset.txt"));
             while (scan.hasNextLine()) {
                 String line = scan.nextLine();
                 String[] parts = line.split(" ");
 
                 Course c = new Course(parts[1], parts[0]);
                 c.setStartTime(Integer.parseInt(parts[4]));
                 c.setEndTime(Integer.parseInt(parts[5]));
                 c.day = parts[6];
                 c.setCampus(parts[7]);
 
                 String[] tags = parts[10].split(",");
                 ArrayList<String> GEs = new ArrayList<>();
                 ArrayList<String> majorAreas = new ArrayList<>();
 
                 for (String tag : tags) {
                     if (Character.isDigit(tag.charAt(0))) {
                         GEs.add(tag);
                     } else {
                         majorAreas.add(tag);
                     }
                 }
                 c.setGEs(GEs);
                 c.setMajors(majorAreas);
 
                 // Parse prerequisites
                 String prereqField = parts[11];
                 ArrayList<ArrayList<String>> prereqs = new ArrayList<>();
                 if (!prereqField.equals("None")) {
                     Pattern groupPattern = Pattern.compile("\\(([^)]+)\\)|\\b[A-Z]{2,4}\\d{2,3}[A-Z]*\\b");
                     Matcher matcher = groupPattern.matcher(prereqField);
 
                     while (matcher.find()) {
                         String group = matcher.group();
                         if (group.startsWith("(")) {
                             group = group.substring(1, group.length() - 1);
                             prereqs.add(new ArrayList<>(Arrays.asList(group.split(","))));
                         } else {
                             prereqs.add(new ArrayList<>(Arrays.asList(group)));
                         }
                     }
                 }
                 c.setPreReqs(prereqs);
                 courseArray.put(c.courseID, c);
             }
         } catch (FileNotFoundException e) {
             System.out.println("finalproject_dataset.txt not found.");
         }
 
         // Organize courses into graphs per major
         for (String courseID : courseArray.keySet()) {
             Course course = courseArray.get(courseID);
             for (String major : course.getMajors()) {
                 allMajorCourses.putIfAbsent(major, new CourseGraph(major));
                 allMajorCourses.get(major).addCourse(course);
                 for (ArrayList<String> prereqGroup : course.getPreReqs()) {
                     for (String prereqID : prereqGroup) {
                         Course prereq = courseArray.get(prereqID);
                         if (prereq != null) {
                             allMajorCourses.get(major).addCourse(prereq);
                             allMajorCourses.get(major).addDirectedEdge(prereq, course);
                         }
                     }
                 }
             }
         }
 
         // Feature 1: Check course eligibility
         Scanner inputScanner = new Scanner(System.in);
         System.out.println("Which course would you like to check? \n");
         String userCourse = inputScanner.nextLine().trim();
 
         while (!userCourse.equals("next")) {
             if (!courseArray.containsKey(userCourse)) {
                 System.out.println("Course not found");
             } else {
                 Course course = courseArray.get(userCourse);
                 if (checkEligibility(course)) {
                     System.out.println("You are eligible to take " + userCourse);
                 } else {
                     System.out.println("You are not eligible to take " + userCourse);
                 }
             }
             System.out.println("Which other course would you like to check? (enter next to move on)\n");
             userCourse = inputScanner.nextLine().trim();
         }
 
         // Feature 2.1: See all courses
         System.out.println("Would you like to see all courses available?");
         String response = inputScanner.nextLine().trim();
         if (response.equals("yes")) {
             seeAllCourses();
         }
 
         // Feature 2.2: See all courses in a major
         System.out.println("Which major are you interested in? \n");
         String userMajor = inputScanner.nextLine().trim();
 
         while (!userMajor.equals("next")) {
             if (!allMajorCourses.containsKey(userMajor)) {
                 System.out.println("Major not offered");
             } else {
                 allMajorCourses.get(userMajor).showAllMajorCourses();
             }
             System.out.println("Which other major are you interested in? (enter next to move on) \n");
             userMajor = inputScanner.nextLine().trim();
         }
 
         // Feature 3: Course recommendations
         System.out.println("How many courses would you like us to recommend? \n");
         String userNum = inputScanner.nextLine().trim();
         int recNum = Integer.parseInt(userNum);
 
         if (recNum < 0 || recNum > courseArray.size()) {
             System.out.println("Invalid number");
         } else {
             recommendCourses(recNum);
         }
 
         // Feature 4: Remaining classes in user's majors
         System.out.println("Would you like to see the remaining classes you can take in your indicated major(s)? \n");
         String response2 = inputScanner.nextLine().trim();
         if (response2.equals("yes")) {
             remainingCourses();
             System.out.println("========================");
             System.out.println("Good luck with registration!");
         } else {
             System.out.println("Ok, good luck with registration!");
         }
 
         inputScanner.close();
     }
 
     /**
      * Checks if the user meets the prerequisite conditions for a given course.
      * @param course The course to check
      * @return true if eligible, false otherwise
      */
     public static boolean checkEligibility(Course course) {
         ArrayList<ArrayList<String>> prereqs = course.getPreReqs();
         String courseHistory = userInput.get(3);
         ArrayList<String> courseHistoryList = new ArrayList<>(Arrays.asList(courseHistory.split(",")));
 
         for (ArrayList<String> prereqGroup : prereqs) {
             boolean satisfied = false;
             for (String prereq : prereqGroup) {
                 if (courseHistoryList.contains(prereq)) {
                     satisfied = true;
                     break;
                 }
             }
             if (!satisfied) return false;
         }
         return true;
     }
 
     /**
      * Prints all courses stored in the course array.
      */
     public static void seeAllCourses() {
         for (String courseID : courseArray.keySet()) {
             System.out.println(courseArray.get(courseID));
         }
     }
 
     /**
      * Recommends a list of eligible courses based on user preferences and priority score.
      * @param recNum Number of course recommendations to return
      */
     public static void recommendCourses(int recNum) {
         ArrayList<Course> eligibleCourses = new ArrayList<>();
         for (Course c : courseArray.values()) {
             if (checkEligibility(c) && !userInput.get(3).contains(c.getID())) {
                 eligibleCourses.add(c);
             }
         }
 
         courseRecommender recommender = new courseRecommender(userInput);
         recommender.assignScore(eligibleCourses);
         PriorityQueue<Course> ranked = courseRecommender.rankCourses(eligibleCourses, recommender.newComparator);
 
         System.out.println("\nTop " + recNum + " Course Recommendations:");
         int count = 0;
         while (!ranked.isEmpty() && count < recNum) {
             Course recommended = ranked.poll();
             System.out.println((count + 1) + ", " + recommended.getID() + ": " + recommended.getName() + "; " + recommended.getTime());
             count++;
         }
 
         if (count == 0) {
             System.out.println("No eligible courses found.");
         }
     }
 
     /**
      * Displays the list of remaining and currently eligible courses in each major area user plans to pursue
      */
     public static void remainingCourses() {
         String major1 = userInput.get(0).trim();
         String major2 = userInput.get(1).trim();
         String minor = userInput.get(2).trim();
 
         String[] takenArray = userInput.get(3).split(",");
         ArrayList<String> takenCourses = new ArrayList<>();
         for (String t : takenArray) {
             takenCourses.add(t.trim());
         }
 
         for (String area : new String[]{major1, major2, minor}) {
             if (!allMajorCourses.containsKey(area)) continue;
 
             CourseGraph graph = allMajorCourses.get(area);
             ArrayList<Course> remaining = new ArrayList<>();
             ArrayList<Course> eligibleNextCourses = new ArrayList<>();
 
             for (Course course : graph.adjList.keySet()) {
                 if (takenCourses.contains(course.getID())) continue;
                 remaining.add(course);
                 if (checkEligibility(course)) {
                     eligibleNextCourses.add(course);
                 }
             }
 
             System.out.println("Remaining courses for " + area + ":");
             if (remaining.isEmpty()) {
                 System.out.println("You've completed all required courses for " + area + "!");
             } else {
                 for (Course c : remaining) {
                     System.out.println("- " + c.getID() + ": " + c.name);
                 }
                 System.out.println("\n Out of those, the courses you are currently eligible for are: ");
                 for (Course c : eligibleNextCourses) {
                     System.out.println("- " + c.getID() + ": " + c.name);
                 }
             }
             System.out.println("----------------- \n");
         }
     }
 }