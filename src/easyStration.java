import java.io.*;
import java.util.*;
import java.util.regex.*;

public class easyStration{
    static Map<String, Course> courseArray = new HashMap<>();
    static Map<String, CourseGraph> allMajorCourses = new HashMap<>();
    static ArrayList<String> userInput = new ArrayList<>();
    
    public static void main(String[] args) {
        
        try {
            Scanner inputFile = new Scanner(System.in);  // Create a Scanner object
            System.out.println("Please enter filename: ");
            String userFile = inputFile.nextLine().trim();  // Read user input

            Scanner scan = new Scanner(new File(userFile));
            while (scan.hasNextLine()) {
                userInput.add(scan.nextLine().trim());
            }
            scan.close();
            } catch (FileNotFoundException e) {
                System.out.println("User input file not found");
                return;
        }
        
        try {
            Scanner scan = new Scanner(new File("finalproject_dataset.txt"));
            while(scan.hasNextLine()) {
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
                    tag = tag.trim(); // Clean whitespace

                    // General Education tags start with a digit like '1A2', '2FYA', etc.
                    if (!tag.isEmpty() && Character.isDigit(tag.charAt(0))) {
                        GEs.add(tag);
                    } else {
                        majorAreas.add(tag); // Assume all non-digit-starting tags are majors
                    }
                }
            c.setGEs(GEs);
            c.setMajors(majorAreas);
            System.out.println("Parsed majors for " + c.getID() + ": " + majorAreas);


            String prereqField = parts[11];
            System.out.println(prereqField);
            
            ArrayList<ArrayList<String>> prereqs = new ArrayList<>();
            if (!prereqField.equals("None")) {
                // Regex pattern to match either groups like (A,B) or single codes like ECON051
                Pattern groupPattern = Pattern.compile("\\(([^)]+)\\)|\\b[A-Z]{2,4}\\d{2,3}[A-Z]*\\b"); //cite ths from ChatGPT
                Matcher matcher = groupPattern.matcher(prereqField);

                while (matcher.find()) {
                    String group = matcher.group();
                    
                    if (group.startsWith("(")) {
                        // Remove parentheses from OR group and split into individual course codes
                        group = group.substring(1, group.length() - 1);
                        prereqs.add(new ArrayList<>(Arrays.asList(group.split(","))));
                    } else {
                        // Single course code = AND condition
                        prereqs.add(new ArrayList<>(Arrays.asList(group)));
                    }
                }
            }
            c.setPreReqs(prereqs);

            // ArrayList<String> prereqs = new ArrayList<>();
            // if (!prereqField.equals("None")) {
            //     String[] words = prereqField.split(",");
            //     for (String word : words) {
            //         if (word.matches("[A-Z]{2,4}\\d{2,3}[A-Z]*")){
            //         prereqs.add(word);
            //         }
            //     }
            // System.out.print(prereqs);
            // }
            // c.setPreReqs(prereqs);
            courseArray.put(c.courseID, c);
            // System.out.println(courseArray);
            
             } 
             }catch (FileNotFoundException e) {
                System.out.println("CoursesDataset.txt not found.");}

            //place each course into a graph corresponding to major
            for (String courseID : courseArray.keySet()){
                Course course = courseArray.get(courseID);
                ArrayList<String> majors = course.getMajors();
                for (int i = 0; i < majors.size(); i++){
                    String major = course.getMajors().get(i);
                    if (!allMajorCourses.containsKey(major)) {
                        allMajorCourses.put(major, new CourseGraph(major));
                    }
                    allMajorCourses.get(major).addCourse(course);



                    for (ArrayList<String> prereqGroup : course.getPreReqs()){
                        for (String prereqID: prereqGroup){
                            Course prereq = courseArray.get(prereqID);
                            if (prereq != null) {
                                allMajorCourses.get(major).addCourse(prereq); // ← ensures the node exists
                                allMajorCourses.get(major).addDirectedEdge(prereq, course);
                                allMajorCourses.get(major).addDirectedEdge(prereq, course);
                            }
                    }
                }
            }
            System.out.println("The majors are:" + allMajorCourses.keySet());

    //Feature 1: Check if user is eligible for a particular class based on course history
    
    //Ask user for course
    Scanner inputScanner = new Scanner(System.in);  // Create a Scanner object
    System.out.println("Which course would you like to check? \n");
    String userCourse = inputScanner.nextLine().trim();  // Read user input
    
    if (!courseArray.containsKey(userCourse)){
        System.out.println("Course not found");
    } else {
        Course checkedCourse = courseArray.get(userCourse);
        if (checkEligibility(checkedCourse)) {
            System.out.println("You are eligible to take " + userCourse);
        } else {
            System.out.println("You are not eligible to take " + userCourse);
        }
    } 

    //Feature 2.1: Display all courses
    System.out.println("Would you like to see all courses available?");
    String response = inputScanner.nextLine().trim();
    if (response.equals("yes")){
        seeAllCourses();
    }

    //Feature 2: Display all courses in major
    System.out.println("Which major are you interested in? \n");
    String userMajor = inputScanner.nextLine().trim();

    if (!allMajorCourses.keySet().contains(userMajor)){
        System.out.println("Major not offered");
    }
    else{
        allMajorCourses.get(userMajor).showAllMajorCourses();
    }

    //Feature 3: Course recommendation
    System.out.println("How many courses would you like us to recommend? \n");
    String userNum = inputScanner.nextLine().trim();
    int recNum = Integer.parseInt(userNum);
    
    if(recNum < 0 || recNum > courseArray.size()){
        System.out.println("Invalid number");
    }
    else{
        recommendCourses(recNum);
    }
    



    inputScanner.close();}


    
    }

    public static boolean checkEligibility(Course course){
        //  ArrayList<String> prereqs = course.getPreReqs();
    //      String courseHistory = userInput.get(3);
    //      ArrayList<String> courseHistoryList = new ArrayList<>(Arrays.asList(courseHistory.split(",")));
         
    //      for (String prereq :prereqs){
    //         if (!courseHistoryList.contains(prereq)){
    //             return false;
    //         }
    //      }
    //      return true;

        ArrayList<ArrayList<String>> prereqs = course.getPreReqs();
        String courseHistory = userInput.get(3);
        ArrayList<String> courseHistoryList = new ArrayList<>(Arrays.asList(courseHistory.split(",")));
        for (ArrayList<String> prereqGroup: prereqs){
            int count = 0;
            for (String prereq: prereqGroup){
                if (courseHistoryList.contains(prereq)){
                    continue;
                }   
                count += 1;       
            }
                
            if (count == prereqGroup.size()){
                return false;
            }
        }
        return true;
        }

    // }

    public static void seeAllCourses(){
        for (String courseID: courseArray.keySet()){
            System.out.println(courseArray.get(courseID));
        }
    }

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
            System.out.println((count + 1) + ", " + ranked.poll());
            count++;
        }

        if (count == 0) {
            System.out.println("No eligible courses found.");
        }
    }
    // Feature 4: what courses are left to take in the major
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
            if (!allMajorCourses.containsKey(area)) {
                continue;
            }

            CourseGraph graph = allMajorCourses.get(area);
            ArrayList<Course> remaining = new ArrayList<>();
            ArrayList<Course> eligibleNextCourses = new ArrayList<>();

            for (Course course : graph.adjList.keySet()) {
                if (takenCourses.contains(course.getID())) {
                    continue;
                }
                boolean isPrereqForTakenCourse = false;
                ArrayList<Course> nextCourses = graph.adjList.get(course);
                for (Course next : nextCourses) {
                    if (takenCourses.contains(next.getID())) {
                        isPrereqForTakenCourse = true;
                        break;
                    }
                }
                if (!isPrereqForTakenCourse) {
                    remaining.add(course);
                    if (checkEligibility(course)){
                        eligibleNextCourses.add(course);
                    }
                }
            }
            System.out.println("Remaining courses for " + area + ":");
            if (remaining.size() == 0) {
                System.out.println("You've completed all required courses for " + area + "!");
            } else {
                for (Course c : remaining) {
                    System.out.println("- " + c.getID() + ": " + c.name);
                }
                System.out.println("Out of those, the courses you are currently eligible for are: ");
                for (Course c: eligibleNextCourses){
                    System.out.println("- " + c.getID() + ": " + c.name);
                }
            }
        }
    }
}
