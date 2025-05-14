/**
 * The CourseGraph class represents a directed graph of courses within a major area.
 * Each node is an object of type Course, and directed edges point from a prerequisite course to the courses it enables.
 */

 import java.util.*;

 public class CourseGraph {
     public String majorArea;  // The major or department this graph represents
     public Map<Course, ArrayList<Course>> adjList;  // Adjacency list mapping a course to its dependents
 
     /**
      * Constructs a CourseGraph for the given major area.
      * @param majorArea The name of the major/discipline
      */
     public CourseGraph(String majorArea) {
         this.majorArea = majorArea;
         this.adjList = new HashMap<>();
     }
 
     /**
      * Adds a course to the graph as a node.
      * If the course already exists, this does nothing.
      * @param course The course to be added
      */
     public void addCourse(Course course) {
         if (!adjList.containsKey(course)) {
             adjList.put(course, new ArrayList<Course>());
         }
     }
 
     /**
      * Adds a directed edge from course1 (prerequisite) to course2
      * If course1 is not already present, it will be added.
      * @param course1 Prerequisite course
      * @param course2 Course that has course1 as prerequisite
      */
     public void addDirectedEdge(Course course1, Course course2) {
         if (adjList.containsKey(course1)) {
             adjList.get(course1).add(course2);
         } else {
             ArrayList<Course> course1Value = new ArrayList<>();
             course1Value.add(course2);
             adjList.put(course1, course1Value);
         }
     }
 
     /**
      * Returns a list of courses that are directly reachable from the given course.
      * a.k.a. the courses that depend on the given course as a prerequisite.
      * @param course The potential prerequisite course
      * @return List of courses that follow from the input course (empty if course is not a prerequisite for any other course in the major)
      */
     public ArrayList<Course> getNextCourses(Course course) {
         return adjList.getOrDefault(course, new ArrayList<>());
     }
 
     /**
      * Prints out all courses in the graph along with the courses that each enables.
      * Displays the structure of the prerequisite graph for the current major.
      */
     public void showAllMajorCourses() {
         System.out.println("The courses for the " + majorArea + " major (and the following courses each allows you to take)");
         System.out.println("-------------------------------------");
 
         for (Course course : adjList.keySet()) {
             ArrayList<Course> nextCourses = adjList.get(course);
             if (nextCourses.isEmpty()) {
                 System.out.println(course.getID());
             } else {
                 StringBuilder line = new StringBuilder(course.getID() + " -> ");
                 for (int i = 0; i < nextCourses.size(); i++) {
                     line.append(nextCourses.get(i).getID());
                     if (i < nextCourses.size() - 1) {
                         line.append(", ");
                     }
                 }
                 System.out.println(line);
             }
         }
     }
 }