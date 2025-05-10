import java.util.*;
import java.io.*;
import java.util.Scanner;
import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.regex.*;

public class easyStration{
    
    public static void main(String[] args) {
        CourseGraph courseGraph = new CourseGraph();
        ArrayList<String> userInput = new ArrayList<>();
        
        try {
            Scanner scan = new Scanner(new File("UserInputFile.txt"));
            while (scan.hasNextLine()) {
                userInput.add(scan.nextLine().trim());
            }
            scan.close();
            } catch (FileNotFoundException e) {
                System.out.println("User input file not found");
                return;
        }
        
        try {
            Scanner scan = new Scanner(new File("CoursesDataset.txt"));
            while(scan.hasNextLine()) {
                String line = scan.nextLine();
                String[] parts = line.split(" ");

                Course c = new Course(parts[1], parts[0]);
                c.setStartTime(Integer.parseInt(parts[3]));
                c.setEndTime(Integer.parseInt(parts[4]));
                c.day = parts[5];
                c.setCampus(parts[6]);

                String[] tags = parts[9].split(",");
                ArrayList<String> GEs = new ArrayList<>();
                ArrayList<String> majorAreas = new ArrayList<>();

                for (int i = 0; i < tags.length; i++){
                    String tag = tags[i];
                    char firstChar = tags[i].charAt(0);
                    if (firstChar >= '1' && firstChar <= '9') {
                        GEs.add(tags[i]);
                    } else {
                        majorAreas.add(tags[i]);
                    }
                }
            c.setGEs(GEs);
            c.setMajors(majorAreas);

            String prereqField = parts[10];
            
            ArrayList<String> prereqs = new ArrayList<>();
            if (!prereqField.equals("None")) {
                String[] words = prereqField.split(" ");
                for (String word : words) {
                    if (word.matches("[A-Z]{2,4}\\d{2,3}[A-Z]*")){
                    prereqs.add(word);
                    }
                }
            }
            c.setPreReqs(prereqs);

            courseGraph.addCourse(c);

}
        }
}}
