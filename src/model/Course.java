package model;

import java.util.*;

public class Course {
    private final String courseCode;
    private String courseName;
    private int credits;
    private String instructor;
    private int maxCapacity;
    private List<Student> enrolledStudents = new ArrayList<>();
    private Map<String, Integer> schedule = new HashMap<>(); // Ngày -> Giờ

    public Course(String code, String name, int credits, String instructor, int maxCapacity) {
        if (code == null || code.isEmpty()) throw new IllegalArgumentException("courseCode required");
        this.courseCode = code;
        this.courseName = name;
        this.credits = credits;
        this.instructor = instructor;
        this.maxCapacity = maxCapacity;
    }
    public String getCourseCode() { return courseCode; }
    public String getCourseName() { return courseName; }
    public int getCredits() { return credits; }
    public String getInstructor() { return instructor; }
    public int getMaxCapacity() { return maxCapacity; }
    public List<Student> getEnrolledStudents() { return enrolledStudents; }
    public Map<String, Integer> getSchedule() { return schedule; }

    // Đăng ký môn học
    public boolean enrollStudent(Student student) {
        if (enrolledStudents.size() >= maxCapacity) {
            System.out.println("Khóa học đã đầy!");
            return false;
        }
        enrolledStudents.add(student);
        return true;
    }

}
