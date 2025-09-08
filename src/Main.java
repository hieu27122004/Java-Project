import model.*;
import java.time.LocalDate;
import java.util.*;

public class Main {
    static StudentManagementSystem sms = new StudentManagementSystem();
    static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {
        initSampleCourses(); // Thêm vài môn học mẫu
        while (true) {
            System.out.println("\n======= MENU QUẢN LÝ SINH VIÊN =======");
            System.out.println("1. Thêm sinh viên");
            System.out.println("2. Xem danh sách sinh viên");
            System.out.println("3. Xóa sinh viên");
            System.out.println("4. Thêm môn học");
            System.out.println("5. Xem danh sách môn học");
            System.out.println("6. Đăng ký môn học cho sinh viên");
            System.out.println("7. Nhập điểm & điểm danh cho sinh viên");
            System.out.println("8. Xem bảng điểm (transcript) của sinh viên");
            System.out.println("9. Lưu dữ liệu ra file");
            System.out.println("10. Đọc dữ liệu từ file");
            System.out.println("0. Thoát");
            System.out.print("Chọn chức năng: ");
            int ch = Integer.parseInt(sc.nextLine());
            switch (ch) {
                case 1: addStudent(); break;
                case 2: listStudents(); break;
                case 3: removeStudent(); break;
                case 4: addCourse(); break;
                case 5: listCourses(); break;
                case 6: enrollCourse(); break;
                case 7: inputGradeAttendance(); break;
                case 8: transcript(); break;
                case 9: saveData(); break;
                case 10: loadData(); break;
                case 0: System.out.println("Tạm biệt!"); return;
                default: System.out.println("Chọn lại!"); break;
            }
        }
    }

    // Mẫu
    static void initSampleCourses() {
        sms.addCourse(new Course("C001", "Java", 3, "Mr. A", 50));
        sms.addCourse(new Course("C002", "OOP", 3, "Ms. B", 50));
        sms.addCourse(new Course("C003", "SQL", 2, "Mr. C", 30));
    }

    static void addStudent() {
        System.out.print("Nhập mã SV: "); String id = sc.nextLine().trim();
        System.out.print("Họ: "); String last = sc.nextLine().trim();
        System.out.print("Tên: "); String first = sc.nextLine().trim();
        System.out.print("Ngày sinh (yyyy-mm-dd): "); LocalDate dob = LocalDate.parse(sc.nextLine().trim());
        System.out.print("Email: "); String email = sc.nextLine().trim();
        System.out.print("SĐT: "); String phone = sc.nextLine().trim();
        System.out.print("Địa chỉ (street): "); String street = sc.nextLine().trim();
        System.out.print("Thành phố: "); String city = sc.nextLine().trim();
        System.out.print("Tỉnh: "); String prov = sc.nextLine().trim();
        System.out.print("Quốc gia: "); String country = sc.nextLine().trim();
        Address addr = new Address(street, city, prov, country);
        Student s = new Student(id, first, last, dob, email, phone, addr);
        sms.addStudent(s);
        System.out.println("Thêm sinh viên thành công!");
    }

    static void listStudents() {
        if (sms.getStudents().isEmpty()) { System.out.println("Chưa có sinh viên nào!"); return; }
        for (Student s : sms.getStudents()) {
            System.out.println(s.getStudentId() + " | " + s.getLastName() + " " + s.getFirstName() + " | " + s.getEmail());
        }
    }

    static void removeStudent() {
        System.out.print("Nhập mã SV cần xóa: "); String id = sc.nextLine().trim();
        sms.removeStudent(id);
        System.out.println("Đã xóa nếu tồn tại.");
    }

    static void addCourse() {
        System.out.print("Nhập mã môn: "); String code = sc.nextLine().trim();
        System.out.print("Tên môn: "); String name = sc.nextLine().trim();
        System.out.print("Số tín chỉ: "); int credits = Integer.parseInt(sc.nextLine().trim());
        System.out.print("Tên giảng viên: "); String instructor = sc.nextLine().trim();
        System.out.print("Sĩ số tối đa: "); int max = Integer.parseInt(sc.nextLine().trim());
        sms.addCourse(new Course(code, name, credits, instructor, max));
        System.out.println("Thêm môn học thành công!");
    }

    static void listCourses() {
        if (sms.getCourses().isEmpty()) { System.out.println("Chưa có môn học nào!"); return; }
        for (Course c : sms.getCourses()) {
            System.out.println(c.getCourseCode() + " | " + c.getCourseName() + " | " + c.getInstructor());
        }
    }

    static void enrollCourse() {
        System.out.print("Nhập mã SV: "); String sid = sc.nextLine().trim();
        Student s = sms.findStudent(sid);
        if (s == null) { System.out.println("Không tìm thấy SV!"); return; }
        listCourses();
        System.out.print("Nhập mã môn muốn đăng ký: "); String code = sc.nextLine().trim();
        boolean ok = sms.enrollCourse(sid, code);
        if (ok) System.out.println("Đăng ký thành công!"); else System.out.println("Thất bại!");
    }

    static void inputGradeAttendance() {
        System.out.print("Nhập mã SV: "); String sid = sc.nextLine().trim();
        Student s = sms.findStudent(sid);
        if (s == null) { System.out.println("Không tìm thấy SV!"); return; }
        for (Enrollment e : s.getEnrollments()) {
            System.out.println("Nhập điểm cho môn " + e.getCourse().getCourseCode() + ": ");
            double grade = Double.parseDouble(sc.nextLine().trim());
            e.setGrade(grade);
            System.out.println("Nhập % điểm danh: ");
            int att = Integer.parseInt(sc.nextLine().trim());
            e.setAttendancePercentage(att);
        }
        System.out.println("Đã nhập xong!");
    }

    static void transcript() {
        System.out.print("Nhập mã SV: "); String sid = sc.nextLine().trim();
        Student s = sms.findStudent(sid);
        if (s == null) { System.out.println("Không tìm thấy SV!"); return; }
        s.generateTranscript();
    }

    static void saveData() {
        sms.saveToFile("students.txt");
        System.out.println("Đã lưu dữ liệu ra students.txt!");
    }

    static void loadData() {
        sms.loadFromFile("students.txt");
        System.out.println("Đã nạp dữ liệu từ students.txt!");
    }
}
