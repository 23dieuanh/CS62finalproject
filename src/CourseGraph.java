import java.util.*;

public class CourseGraph{
    private Map<Course, ArrayList<Course>> adjList;

    public CourseGraph(){
        adjList = new HashMap<>();
    }

    public void addCourse(Course course){
        if (!adjList.keySet().contains(course)){
            adjList.put(course, new ArrayList<Course>());
        }
    }

    public void addDirectedEdge(Course course1, Course course2){
        if (adjList.keySet().contains(course1)){
             adjList.get(course1).add(course2);
        }
        else{
            ArrayList<Course> course1Value = new ArrayList<Course>();
            course1Value.add(course2);
            adjList.put(course1, course1Value);
        }
    }
    
    public ArrayList<Course> getNextCourses(Course course){
        if (adjList.keySet().contains(course)){
            return adjList.get(course);
        }
        else {
            return new ArrayList<>();
        }
    }
    
    // public String showAllMajorCourses(){
    //     int V = 0;
    //     for (String course: adjList.keySet()){
    //         V++;
    //     }
    //    Map<String, Boolean> marked = new HashMap<>();
    //     int[] distTo = new int[V];
    //     Queue<Integer> graphQueue = new LinkedList<>();
        
    //     marked[0] = true;
    //     distTo[0] = 0;
    //     graphQueue.add(adjList.keySet().get(0));
        
    // }

    public String showAllMajorCourses(){
        Map<Course, Integer> inDegree = new HashMap<>();
        
        // set every indegree to 0
        for (Course course : adjList.keySet()) {
            inDegree.put(course, 0);
        }

        for (Course course : adjList.keySet()) {
            ArrayList<Course> neighbors = adjList.get(course); // courses that this course unlocks
            for (Course neighbor : neighbors) {
                if (inDegree.containsKey(neighbor)) {
                    int current = inDegree.get(neighbor);
                    inDegree.put(neighbor, current + 1);
                } else {
                    inDegree.put(neighbor, 1);
                }
            } 
        }
        // add all courses w indegree 0 to queue (no prereq)
        Queue<Course> queue = new LinkedList<>();
        for (Course course : inDegree.keySet()) {
            if (inDegree.get(course) == 0) {
                queue.add(course);
            }
        }

        StringBuilder output = new StringBuilder();

        while (!queue.isEmpty()) {
            Course current = queue.poll();
            output.append(current.courseID + ": " + current.name + "\n");

            // get all courses that require this as a prerequisite
            ArrayList<Course> neighbors = adjList.get(current);

            // for each of those neighbor courses, subtract 1 from their indegree
            for (Course neighbor : neighbors) {
                int newInDegree = inDegree.get(neighbor) - 1;
                inDegree.put(neighbor, newInDegree);

                // if a course now has in-degree 0, all prereqs are done so add to queue
                if (newInDegree == 0) {
                    queue.add(neighbor);
                }
            }
        }
        return output.toString();
    }

    public static void main(String[] args) {
        CourseGraph graph = new CourseGraph();

        // sample courses
        // graph.addCourse("CS051");
        // graph.addCourse("CS054");
        // graph.addCourse("CS062");
        // graph.addCourse("CS0140");

        // // add prereqs
        // graph.addDirectedEdge("CS051", "CS054");
        // graph.addDirectedEdge("CS054", "CS062");
        // graph.addDirectedEdge("CS062", "CS0140");

        // System.out.println("Adjacency List:");
        // for (String course : graph.adjList.keySet()) {
        //     ArrayList<String> nextCourses = graph.getNextCourses(course);
        //     System.out.println(course + "->" + nextCourses);
        // }

        // System.out.println(graph.showAllMajorCourses());

    }
}
