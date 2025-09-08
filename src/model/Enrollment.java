package model;

import java.time.LocalDate;

public class Enrollment {
    public enum EnrollmentStatus { ENROLLED, DROPPED, COMPLETED }
    private final Student student;
    private final Course course;
    private final LocalDate enrollmentDate;
    private double grade = -1; // -1 = chưa nhập điểm
    private int attendancePercentage = 0;
    private EnrollmentStatus status = EnrollmentStatus.ENROLLED;

    public Enrollment(Student student, Course course, LocalDate date) {
        this.student = student;
        this.course = course;
        this.enrollmentDate = date;
    }

    public Student getStudent() { return student; }
    public Course getCourse() { return course; }
    public LocalDate getEnrollmentDate() { return enrollmentDate; }
    public double getGrade() { return grade; }
    public void setGrade(double grade) { this.grade = grade; }
    public int getAttendancePercentage() { return attendancePercentage; }
    public void setAttendancePercentage(int p) { this.attendancePercentage = p; }
    public EnrollmentStatus getStatus() { return status; }
    public void setStatus(EnrollmentStatus status) { this.status = status; }

}
