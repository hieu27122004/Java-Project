package model;

import java.time.LocalDate;
import java.util.*;

public class Student {
    private final String studentId;
    private String firstName;
    private String lastName;
    private LocalDate birthDate;
    private String email;
    private String phoneNumber;
    private Address address;
    private List<Enrollment> enrollments = new ArrayList<>();
    private Map<String, Double> grades = new HashMap<>();

    // Constructor đầy đủ
    public Student(String studentId, String firstName, String lastName, LocalDate birthDate,
                   String email, String phoneNumber, Address address) {
        if (studentId == null || studentId.isEmpty()) throw new IllegalArgumentException("studentId required");
        this.studentId = studentId;
        setFirstName(firstName);
        setLastName(lastName);
        setBirthDate(birthDate);
        setEmail(email);
        setPhoneNumber(phoneNumber);
        setAddress(address);
    }
    // Các setter với kiểm tra hợp lệ
    public void setFirstName(String firstName) {
        if (firstName == null || firstName.isEmpty()) throw new IllegalArgumentException("firstName required");
        this.firstName = firstName;
    }
    public void setLastName(String lastName) {
        if (lastName == null || lastName.isEmpty()) throw new IllegalArgumentException("lastName required");
        this.lastName = lastName;
    }
    public void setBirthDate(LocalDate birthDate) {
        if (birthDate == null) throw new IllegalArgumentException("birthDate required");
        this.birthDate = birthDate;
    }
    public void setEmail(String email) {
        if (email == null || !email.contains("@")) throw new IllegalArgumentException("Invalid email");
        this.email = email;
    }
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
    public void setAddress(Address address) {
        if (address == null) throw new IllegalArgumentException("address required");
        this.address = address;
    }

    // Getter
    public String getStudentId() { return studentId; }
    public String getFirstName() { return firstName; }
    public String getLastName() { return lastName; }
    public LocalDate getBirthDate() { return birthDate; }
    public String getEmail() { return email; }
    public String getPhoneNumber() { return phoneNumber; }
    public Address getAddress() { return address; }
    public List<Enrollment> getEnrollments() { return enrollments; }
    public Map<String, Double> getGrades() { return grades; }

    // Đăng ký/hủy môn học
    public void addEnrollment(Enrollment e) { enrollments.add(e); }
    public void removeEnrollment(String courseCode) {
        enrollments.removeIf(e -> e.getCourse().getCourseCode().equals(courseCode));
    }

    // Tính GPA có trọng số
    public double calculateGPA() {
        double total = 0, creditSum = 0;
        for (Enrollment e : enrollments) {
            if (e.getGrade() >= 0) {
                total += e.getGrade() * e.getCourse().getCredits();
                creditSum += e.getCourse().getCredits();
            }
        }
        return creditSum > 0 ? total / creditSum : 0;
    }

    // Sinh bảng điểm
    public void generateTranscript() {
        System.out.println("Transcript for: " + studentId + " - " + lastName + " " + firstName);
        for (Enrollment e : enrollments) {
            System.out.printf("Course: %s | Grade: %.2f | Attendance: %d%% | Status: %s\n",
                    e.getCourse().getCourseCode(), e.getGrade(), e.getAttendancePercentage(), e.getStatus());
        }
        System.out.printf("GPA: %.2f\n", calculateGPA());
    }

    // Kiểm tra đủ điều kiện tốt nghiệp ( >= 130 tín chỉ đã qua)
    public boolean checkGraduation(int requiredCredits) {
        int totalCredits = 0;
        for (Enrollment e : enrollments)
            if (e.getGrade() >= 5.0)
                totalCredits += e.getCourse().getCredits();
        return totalCredits >= requiredCredits;
    }
}
