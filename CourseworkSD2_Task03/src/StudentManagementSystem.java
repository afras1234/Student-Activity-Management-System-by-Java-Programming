import java.util.*;
import java.io.*;

public class StudentManagementSystem {
    static final int MAX_STUDENTS = 100;
    static Student[] students = new Student[MAX_STUDENTS];
    static int studentCount = 0;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String choice;
        do {
            displayMenu();
            choice = scanner.next();
            switch (choice) {
                case "1":
                    checkAvailableSeats();
                    break;
                case "2":
                    registerStudent(scanner);
                    break;
                case "3":
                    deleteStudent(scanner);
                    break;
                case "4":
                    findStudent(scanner);
                    break;
                case "5":
                    storeStudentDetails();
                    break;
                case "6":
                    loadStudentDetails();
                    break;
                case "7":
                    viewStudentsByName();
                    break;
                case "8":
                    additionalControls(scanner);
                    break;
                case "C":
                    generateSummary();
                    break;
                case "D":
                    generateCompleteReport();
                    break;
                case "0":
                    System.out.println("Exiting...");
                    break;
                default:
                    System.out.println("Invalid option. Try again.");
            }
        } while (!choice.equals("0"));
        scanner.close();
    }

    static void displayMenu() {
        System.out.println("1. Check available seats");
        System.out.println("2. Register student (with ID)");
        System.out.println("3. Delete student");
        System.out.println("4. Find student (with student ID)");
        System.out.println("5. Store student details into a file");
        System.out.println("6. Load student details from the file to the system");
        System.out.println("7. View the list of students based on their names");
        System.out.println("8. Additional Controls");
        System.out.println("C. Generate Summary");
        System.out.println("D. Generate Complete Report");
        System.out.println("0. Exit");
        System.out.print("Enter your choice: ");
    }

    static void checkAvailableSeats() {
        System.out.println("Available seats: " + (MAX_STUDENTS - studentCount));
    }

    static void registerStudent(Scanner scanner) {
        if (studentCount >= MAX_STUDENTS) {
            System.out.println("No available seats.");
            return;
        }
        System.out.print("Enter student ID: ");
        String id = scanner.next();
        System.out.print("Enter student name: ");
        String name = scanner.next();
        students[studentCount++] = new Student(id, name);
        System.out.println("Student registered successfully.");
    }

    static void deleteStudent(Scanner scanner) {
        System.out.print("Enter student ID to delete: ");
        String id = scanner.next();
        for (int i = 0; i < studentCount; i++) {
            if (students[i].getId().equals(id)) {
                students[i] = students[--studentCount];
                students[studentCount] = null;
                System.out.println("Student deleted successfully.");
                return;
            }
        }
        System.out.println("Student not found.");
    }

    static void findStudent(Scanner scanner) {
        System.out.print("Enter student ID to find: ");
        String id = scanner.next();
        for (int i = 0; i < studentCount; i++) {
            if (students[i].getId().equals(id)) {
                System.out.println("Student found: " + students[i].getName());
                return;
            }
        }
        System.out.println("Student not found.");
    }

    static void storeStudentDetails() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("students.dat"))) {
            oos.writeInt(studentCount);
            for (int i = 0; i < studentCount; i++) {
                oos.writeObject(students[i]);
            }
            System.out.println("Student details stored successfully.");
        } catch (IOException e) {
            System.out.println("Error storing student details.");
        }
    }

    static void loadStudentDetails() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream("students.dat"))) {
            studentCount = ois.readInt();
            for (int i = 0; i < studentCount; i++) {
                students[i] = (Student) ois.readObject();
            }
            System.out.println("Student details loaded successfully.");
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Error loading student details.");
        }
    }

    static void viewStudentsByName() {
        Student[] sortedStudents = Arrays.copyOf(students, studentCount);
        Arrays.sort(sortedStudents, Comparator.comparing(Student::getName));
        for (Student student : sortedStudents) {
            System.out.println(student.getName());
        }
    }

    static void additionalControls(Scanner scanner) {
        System.out.println("a. Add student name");
        System.out.println("b. Add module marks 1, 2, and 3");
        System.out.print("Enter your choice: ");
        char choice = scanner.next().charAt(0);
        switch (choice) {
            case 'a':
                System.out.print("Enter student ID: ");
                String id = scanner.next();
                for (int i = 0; i < studentCount; i++) {
                    if (students[i].getId().equals(id)) {
                        System.out.print("Enter student name: ");
                        students[i].setName(scanner.next());
                        System.out.println("Student name updated successfully.");
                        return;
                    }
                }
                System.out.println("Student not found.");
                break;
            case 'b':
                System.out.print("Enter student ID: ");
                id = scanner.next();
                for (int i = 0; i < studentCount; i++) {
                    if (students[i].getId().equals(id)) {
                        System.out.print("Enter Module 1 marks: ");
                        int module1 = scanner.nextInt();
                        System.out.print("Enter Module 2 marks: ");
                        int module2 = scanner.nextInt();
                        System.out.print("Enter Module 3 marks: ");
                        int module3 = scanner.nextInt();
                        students[i].setModule(module1, module2, module3);
                        System.out.println("Module marks updated successfully.");
                        return;
                    }
                }
                System.out.println("Student not found.");
                break;
            default:
                System.out.println("Invalid option. Try again.");
        }
    }

    static void generateSummary() {
        System.out.println("Total student registrations: " + studentCount);
        int passCount = 0;
        for (int i = 0; i < studentCount; i++) {
            Module module = students[i].getModule();
            if (module != null && module.getModule1() > 40 && module.getModule2() > 40 && module.getModule3() > 40) {
                passCount++;
            }
        }
        System.out.println("Total number of students who scored more than 40 marks in Module 1, 2, and 3: " + passCount);
    }

    static void generateCompleteReport() {
        Student[] sortedStudents = Arrays.copyOf(students, studentCount);
        bubbleSortByAverage(sortedStudents);

        System.out.println("ID\tName\tModule1\tModule2\tModule3\tTotal\tAverage\tGrade");
        for (Student student : sortedStudents) {
            Module module = student.getModule();
            if (module != null) {
                int total = module.getModule1() + module.getModule2() + module.getModule3();
                double average = total / 3.0;
                System.out.printf("%s\t%s\t%d\t%d\t%d\t%d\t%.2f\t%s\n",
                        student.getId(), student.getName(), module.getModule1(), module.getModule2(), module.getModule3(),
                        total, average, module.getGrade());
            }
        }
    }

    static void bubbleSortByAverage(Student[] array) {
        int n = array.length;
        boolean swapped;
        for (int i = 0; i < n - 1; i++) {
            swapped = false;
            for (int j = 0; j < n - i - 1; j++) {
                if (calculateAverage(array[j]) < calculateAverage(array[j + 1])) {
                    // Swap array[j] and array[j+1]
                    Student temp = array[j];
                    array[j] = array[j + 1];
                    array[j + 1] = temp;
                    swapped = true;
                }
            }
            // If no two elements were swapped by inner loop, then break
            if (!swapped) break;
        }
    }

    static double calculateAverage(Student student) {
        Module module = student.getModule();
        if (module == null) return 0;
        int total = module.getModule1() + module.getModule2() + module.getModule3();
        return total / 3.0;
    }
}

