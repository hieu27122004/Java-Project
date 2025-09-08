package model;

import java.time.LocalDate;
import java.util.*;
import java.io.*;

public class StudentManagementSystem {
    private List<Student> students = new ArrayList<>();
    private List<Course> courses = new ArrayList<>();
    private Map<String, List<Course>> prerequisites = new HashMap<>();

    // Thêm/xóa sinh viên
    public void addStudent(Student s) { students.add(s); }
    public void removeStudent(String studentId) {
        students.removeIf(s -> s.getStudentId().equals(studentId));
    }
    public Student findStudent(String studentId) {
        for (Student s : students) if (s.getStudentId().equals(studentId)) return s;
        return null;
    }

    // Thêm/tìm/xóa khóa học
    public void addCourse(Course c) { courses.add(c); }
    public Course findCourse(String courseCode) {
        for (Course c : courses) if (c.getCourseCode().equals(courseCode)) return c;
        return null;
    }

    // Đăng ký môn học cho SV
    public boolean enrollCourse(String studentId, String courseCode) {
        Student s = findStudent(studentId);
        Course c = findCourse(courseCode);
        if (s == null || c == null) return false;
        if (!c.enrollStudent(s)) return false;
        Enrollment e = new Enrollment(s, c, LocalDate.now());
        s.addEnrollment(e);
        return true;
    }

    // Báo cáo
    public void report() {
        for (Student s : students) s.generateTranscript();
    }

    // Lưu dữ liệu
    public void saveToFile(String fileName) {
        try(PrintWriter pw = new PrintWriter(fileName)) {
            for (Student s : students) {
                Address a = s.getAddress();
                pw.println("S," + s.getStudentId() + "," + s.getFirstName() + "," + s.getLastName() + "," +
                        s.getBirthDate() + "," + s.getEmail() + "," + s.getPhoneNumber() + "," +
                        a.getStreet() + "," + a.getCity() + "," + a.getProvince() + "," + a.getCountry());
                for (Enrollment e : s.getEnrollments()) {
                    pw.println("E," + s.getStudentId() + "," + e.getCourse().getCourseCode() + "," +
                            e.getGrade() + "," + e.getAttendancePercentage() + "," + e.getStatus());
                }
            }
        } catch (Exception ex) {
            System.out.println("Lỗi khi lưu file: " + ex.getMessage());
        }
    }
    // Đọc dữ liệu
    public void loadFromFile(String fileName) {
        students.clear();
        try(Scanner f = new Scanner(new File(fileName))) {
            Map<String, Student> studentMap = new HashMap<>();
            while(f.hasNextLine()) {
                String line = f.nextLine();
                String[] p = line.split(",", -1);
                if (p[0].equals("S")) {
                    Address a = new Address(p[7], p[8], p[9], p[10]);
                    Student s = new Student(p[1], p[2], p[3], LocalDate.parse(p[4]), p[5], p[6], a);
                    students.add(s);
                    studentMap.put(p[1], s);
                } else if (p[0].equals("E")) {
                    Student s = studentMap.get(p[1]);
                    Course c = findCourse(p[2]);
                    if (s != null && c != null) {
                        Enrollment e = new Enrollment(s, c, LocalDate.now());
                        e.setGrade(Double.parseDouble(p[3]));
                        e.setAttendancePercentage(Integer.parseInt(p[4]));
                        e.setStatus(Enrollment.EnrollmentStatus.valueOf(p[5]));
                        s.addEnrollment(e);
                    }
                }
            }
        } catch (Exception ex) {
            System.out.println("Lỗi khi đọc file: " + ex.getMessage());
        }
    }

    // Getter
    public List<Student> getStudents() { return students; }
    public List<Course> getCourses() { return courses; }
}
