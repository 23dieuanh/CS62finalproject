import java.util.*;

public class courseRecommender {

    // Stores user input as an array of strings
    String[] userInput = new String[8];
    Map<Course, Integer> courseScores = new HashMap<>();

    // Constructor that initializes userInput
    public courseRecommender(ArrayList<String> inputList){
        for (int i = 0; i < inputList.size() && i < 8; i++){
            userInput[i] = inputList.get(i);
        }
    }

    // Priority queue builder
    public static PriorityQueue<Course> rankCourses(ArrayList<Course> eligibleCourses, Comparator<Course> comp) {
        // return a new PriorityQueue object that uses our given Comparator to prioritize people from the provided Scenario object
        PriorityQueue<Course> pQ = new PriorityQueue<>(comp);
        for (Course course : eligibleCourses){
            pQ.add(course);
        }
        return pQ;
    }

    // Assign scores to eligible scores based on user preferences
    public void assignScore(ArrayList<Course> eligibleCourses) {
        for (Course c : eligibleCourses) {
            int score = 0;
     
            //Case 1: user values majors over GEs
            if (userInput[7].equals("major")){

            //major score
                if ((c.getMajors().contains(userInput[0])) || (c.getMajors().contains(userInput[0]))){
                    score += 10;
                }
                else if (c.getMajors().contains(userInput[2])){
                    score += 9;
                }
                
                //GE score
                String[] userGE = userInput[4].split(",");
                for (int i = 0; i < userGE.length; i++){
                    if (c.getGEs().contains(userGE[i])){
                        score += 5;
                    }
                }
            }

            else if (userInput[7].equals("GEs")){ //Case 2: user values GE completion over major
                //major score
                if (c.getMajors().contains(userInput[0]) || c.getMajors().contains(userInput[1])){
                    score += 9;
                }
                else if (c.getMajors().contains(userInput[2])){
                    score += 8;
                }
                
                //GE score
                String[] userGE = userInput[4].split(",");
                for (int i = 0; i < userGE.length; i++){
                    for (String courseGE : c.getGEs()) {
                        if (userGE[i].equals(courseGE)) {
                        score += 10;
                        }
                    }
                }
            }

            else { // user indifferent
                //major score
                if (c.getMajors().contains(userInput[0]) || c.getMajors().contains(userInput[1])){
                    score += 10;
                }
                else if (c.getMajors().contains(userInput[2])){
                    score += 9;
                }
                
                //GE score
                String[] userGE = userInput[4].split(",");
                for (int i = 0; i < userGE.length; i++){
                    for (String courseGE : c.getGEs()) {
                        if (userGE[i].equals(courseGE)){
                            score += 7;
                        }
                    }
                }
            }

            //Time
            String time = "";
            if (c.getEndTime() <= 1230) {
                time = "morning";
            }
            else if (c.getEndTime() >= 1300 && c.getEndTime() <= 1800){
                time = "afternoon";
            }
            else{
                time = "evening";
            }

            String[] userTime = userInput[5].split(",");
            for (int i = 0; i < userTime.length; i++){
                if (time.equals(userTime[i])){
                    score += 7 - i;
                }
            }

            //Campus
            String[] userCampus = userInput[6].split(",");
            for (int i = 0; i < userCampus.length; i++){
                if (c.getCampus().equals(userCampus[i])){
                    score += 5 - i;
                }
            }
            courseScores.put(c, score);
        }
    }

    public int getScore(Course course){
        return courseScores.getOrDefault(course, 0);
    }

    public Comparator<Course> newComparator = new Comparator<Course>() {
        public int compare(Course course1, Course course2) {
            return Integer.compare(getScore(course2), getScore(course1));
    }

    };   
}
