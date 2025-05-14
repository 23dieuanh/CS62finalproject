# Introduction 
Course EasyStration is an interactive course planning assistant designed to help our fellow college students navigate through the process of course registration–a semesterly experience that makes every student scratch their head. complex degree requirements, general education (GE) paths, and course prerequisites with greater ease and clarity. The tool allows users to check their eligibility for courses, view all major-specific classes and their dependencies, receive personalized course recommendations based on academic interests and availability, and identify future courses they are eligible to take based on their current standing. In this way, EasyStration provides personalized features in the pre-registration course search process that help our peers make more informed and efficient registration decisions. 

# Running the code
When you run easyStration, it will prompt you for a filename that contains information pertinent to your registration. The format of the file must be as follows: 
	
	(Major 1)
	(Major 2)
	(Minor)
	(Course history)
	(GEs yet to fulfill)
	(Time preference: morning, afternoon, and/or evening)
	(Campus preference)
	(Whether you are seeking to fulfill more major or GE classes this semester)
	
We have placed multiple sample user files in the package, which you should use to run our code. If you decide to create your own file, keep the following instructions in mind:
Your major and minor must follow the standard major code (2-4 capital letters) for each major: e.g. ECON, CSCI, MUS
Your course history must be a list with no spaces, with courses separated by commas. Each course must follow the standard courseID format (major code+3-digit course number, e.g. MATH032). Sometimes there can be a letter after the course number, a.k.a CSCI005L for lab. 
List your unfulfilled GE areas as a list with no spaces separated by commas, by using the code designated for each GE area. For example, Pomona College Area 5 (Quantitative Reasoning) must be written as 1A5.
For time preference, list “morning”, “afternoon”, and “evening” in the order of preference as a list without spaces separated by commas. If you are not willing to consider one of those time slots, do not list it.
For campus preference, list the seven campus codes (e.g. PO or HM, not the campus names spelled out) in the order of preference as a list without spaces separated by commas. If you are not willing to consider one of the campuses, do not list it.
On the last line, write either “major”, “GEs” or “both”, depending on whether you are more desperate about advancing your major requirements or your GE requirements that semester. If no preference, put “both”.

easyStration will then process the data set containing the entire course catalog for Fall 2024. 
As a first feature, it will ask you for a course for which you would like to check your eligibility. Make sure to, once again, indicate the courseID in the standardized format, which are the two to four capital letters for the major code followed by the course number (e.g. CSCI054). Otherwise, the system will not find your course. If the course you inputted exists in your catalog, the system will tell you whether you are eligible to take the course based on the courses you have already taken and the course’s prerequisites. It will keep prompting you for courses to check until you enter “next”.
For example, here is my sample output:

```text
Please enter filename: 
jadeInput.txt
Which course would you like to check? 

ECON107
You are not eligible to take ECON107
Which other course would you like to check? (enter next to move on)

MATH137
You are eligible to take MATH137
Which other course would you like to check? (enter next to move on)

next
```

As a second feature, the system will ask you whether you would like to see all courses. This prints out every single course offered that semester. A fragment of the output looks like:

```text
CSCI070: DataStructures/PrgmDevelopment; Majors: [CSCI]; Time: -M-W---: 1445-1600
; Campus :HM; Areas fulfilled: [1A5]; Prerequisites: [[CSCI060, CSCI042], [MATH055]]

POLI110: IntrotoComparativePolitics; Majors: [AGPL, POLI]; Time: --T-R--: 935-1050
; Campus :SC; Areas fulfilled: [1A2, 2FYA, 2SS, 4HSS, 5SOC]; Prerequisites: []

POLI111: GreenPoliticalTheory; Majors: [AMST, LAST, POLI]; Time: --T-R--: 1315-1430
; Campus :PO; Areas fulfilled: [1A2, 2SS, 4HSS, 5SOC]; Prerequisites: [[POLI001A]]

POST020A: USPolitics:Resistance&Transfr; Majors: [AGPL, POLI, POST]; Time: --T-R--: 1445-1600
; Campus :PZ; Areas fulfilled: [1A2, 2SS, 4HSS, 5IUL, 5SJT, 5SOC]; Prerequisites: []

FREN101A: IntroToLiteraryAnalysis; Majors: [ALAN, FREN]; Time: --T-R--: 935-1050
; Campus :PO; Areas fulfilled: [1A1, 1FL, 1SIR, 4HSS, 5HUM]; Prerequisites: []

PHIL100E: SpecialTopics:HistOfPhilo; Majors: [PHIL]; Time: -M-W---: 1445-1600
; Campus :CM; Areas fulfilled: [1A3, 2LT, 4HSS, 5HUM]; Prerequisites: []
```

where each line contains information about a course including courseID, course name, major areas, day and time, campus, GE areas fulfilled, and prerequisites (blank list if none).

It will then ask you the major you are interested in investigating. For this part, you are not compelled to enter the major you are pursuing—this can be any major area whose course layout you would like to see. The system will then print out each individual course in the major and if the course is a prerequisite for further courses, it will show this by linking that course and those further courses with an arrow. For example, say I am interested to see the output for chemistry, then this is the output I would get:

Which major are you interested in? 

```text
CHEM
The courses for the CHEM major (and the following courses each allows you to take)
-------------------------------------
CHEM014L -> CHEM122
BIOL043L -> BIOL177, CHEM177
CHEM115
CHEM058
CHEM193M
CHEM056 -> CHEM171
CHEM199
CHEM177
CHEM051 -> CHEM053
CHEM053
MATH031 -> CHEM122
CHEM161
CHEM042R
CHEM140
CHEM158A
CHEM110A
CHEM023A -> CHEM056
CHEM001A
CHEM126L
CHEM116L -> BIOL177, CHEM177
BIOL177
CHEM024 -> CHEM058, CHEM056
CHEM187
CHEM122
CHEM042 -> CHEM056
CHEM164
CHEM194
CHEM171
CHEM191
```

Which is a list of all chemistry courses and the courses for which they are prerequisites, if any. Note that it is normal for courses from other areas (such as math and biology) to also appear if they help fulfill the chemistry major in some way. 

After you've seen your course eligibility and explored major-specific courses, our third feature helps you figure out what to actually take next semester. Based on the input file you initially provided (your majors, GEs left, preferred campuses and times, and whether you're prioritizing major or GE requirements), the system will ask:
```text
How many courses would you like us to recommend?
```
Let's say you're running the program with your Input.txt that looks like this:
```text
ECON
CSCI
MATH
ECON051
1A6
morning,afternoon
PO
major
```
After you input a number, the system will calculate the best options for you out of all eligible courses and print a ranked list using our course scoring system. Here's an example output:
```text
Top 3 Course Recommendations:
1, ECON143: EconomicsandFilm; --T-R--: 0810-0925
2, CSCI051: IntroductiontoComputerScience; -M-W-F-: 1300-1350
3, ECON123: InternationalEconomics; --T-R--: 1445-1600
```
If there are no courses you're eligible for, easyStration will return:
```test
Top 3 Course Recommendations:
No eligible courses found.
```
This means the system has:
- Filtered for courses you're eligible for based on having taken ECON051,
- Ranked courses that help you ECON/CSCI majors higher (since you selected "major")
- Prioritized classes at Pomona in the morning or afternoon,
- And only returned ones you haven't already completed.
This feature is perfect if you're overwhelmed by the number of classes out there and just want a clear, personalized list of what to take. And because the system filters out ineligible courses, you know that everything on the list is actually within reach.

The final feature helps you look ahead: it tells you which major and minor courses you still need to complete, and which of those you're currently eligible to take. This is especially useful when planning future semesters or tracking your graduation progress.
EasyStration will ask:
```text
Would you like to see your remaining major classes?
```
If you respond yes, it will return a breakdown by major and minor (if applicable). Here are two example outputs:
**Example 1 - ECON Major**
```text
Remaining courses for ECON:
- ECON101: MacroeconomicTheory
- ECON107: AppliedEconometrics
- ECON123: InternationalEconomics
- ECON132: EmpiricalMethodsofIO

Out of those, the courses you are currently eligible for are:
- ECON123: InternationalEconomics
```
**Example 2 - CSCI Major**
```text
Remaining courses for CSCI:
- CSCI054: DiscreteMathandFunc.Prog.
- CSCI062: DataStructuresAdvProgramming
- CSCI101: IntrotoLanguagesandTheory
- CSCI140: OperaingSystems

Out of those, the courses you are currently eligible for are:
- CSCI054: DiscreteMathandFunc.Prog.
```
If you've already completed all required courses for a major or minor, EasyStration will let you know for example:
```text
You've completed all required courses for MATH!
```
This final step provides a big-picture view so you not only know what to register for now, but also where you're headed.

# EasyStration Public API

This section documents the public methods and constructors for the main classes in our EasyStration project.

---

## Course

### Constructor
```java
Course(String name, String courseID)
```
Creates a course with its ID and name. Instance variables corresponding to all information about the course are:
    String courseID;
    String name;
    ArrayList<String> majors;
    int startTime;
    int endTime;
    String day;
    String campus;
    ArrayList<String> GEs;
    ArrayList<ArrayList<String>> prerequisites = new ArrayList<>();

When printing a course, the `toString()` method prints something like this:

```text
RUSS187: EverydayLifeintheUSSR; Majors: [ALAN, IR, RUSS]; Time: --T-R--: 1315–1430 ; Campus: PO; Areas fulfilled: [1A1, 1FL, 4HSS, 5HUM]; Prerequisites: [[RUSS044]]
```
---

## CourseGraph

### Constructors
```java
CourseGraph()
CourseGraph(String majorArea)
```
Initializes a course graph by associating it with a specific major area.

### Public Methods
```java
void addCourse(Course course)
```
Adds a course to the graph if not already present.

```java
void addDirectedEdge(Course course1, Course course2)
```
Creates a prerequisite edge from `course1` to `course2`.

```java
ArrayList<Course> getNextCourses(Course course)
```
Returns a list of courses that can be taken after the given course.
For example, 
if we create a course object using CSCI054 and call getNextCourses on CSCI054:
```java
                String testID = "CSCI054";
                Course testCourse = courseArray.get(testID);
                System.out.println(allMajorCourses.get("CSCI").getNextCourses(testCourse)); //use allMajorCourses to locate the CSCI courseGraph then call getNextCourses on CSCI054 
```
this would return:
```text
[CSCI101: IntrotoLanguagesandTheory; Majors: [CSCI]; Time: -M-W---: 1100-1215
; Campus :PO; Areas fulfilled: [1A5]; Prerequisites: [[CSCI054]]
, CSCI062: DataStructuresAdvProgramming; Majors: [CSCI]; Time: -M-W---: 1315-1430
; Campus :PO; Areas fulfilled: []; Prerequisites: [[CSCI054]]
]
```

```java
void showAllMajorCourses()
```
Prints all major courses in the graph along with the courses they unlock. This is the method called in the main() of easyStration as our second feature.

---

## courseRecommender

### Constructor
```java
courseRecommender(ArrayList<String> inputList)
```
Initializes user preferences from an 8-item input list (provided by analysis of the user-inputted file).

### Public Methods
```java
void assignScore(ArrayList<Course> eligibleCourses)
```
Assigns scores to eligible courses based on user preferences (majors, GEs, time, campus).

```java
int getScore(Course course)
```
Returns the score of a given course.

```java
static PriorityQueue<Course> rankCourses(ArrayList<Course> eligibleCourses, Comparator<Course> comp)
```
Returns a priority queue of eligible courses ranked by the provided comparator.

### Public Field
```java
Comparator<Course> newComparator
```
Sorts courses by descending score.

---

## 🔧 Other Features in easyStration class

Variables include:
    - Map<String, Course> courseArray (a hashmap mapping courseID to Course object)
    - Map<String, CourseGraph> allMajorCourses (a big hashmap mapping major to courseGraph of major)
    - ArrayList<String> userInput = new ArrayList<>() (a list of user information extracted from user file)


Public methods (helper functions) include:
```java
boolean checkEligibility(Course course)
```
Returns true if the user has met the prerequisites for the given course.

```java
void remainingCourses()
```
Prints a list of untaken but eligible courses in the user's declared major(s).

(output shown above in how to run the code)

---

