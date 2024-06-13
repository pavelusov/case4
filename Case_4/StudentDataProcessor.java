import java.io.*;
import java.nio.file.*;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.stream.Collectors;

class Student {
    private String name;
    private int age;
    private double grade;

    public Student(String name, int age, double grade) {
        this.name = name;
        this.age = age;
        this.grade = grade;
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    public double getGrade() {
        return grade;
    }

    @Override
    public String toString() {
        return name + "-" + age + "-" + grade;
    }
}

class StudentProcessor {
    private List<Student> students;

    public StudentProcessor(List<Student> students) {
        this.students = students;
    }

    public List<Student> sortByName() {
        return students.stream()
                .sorted(Comparator.comparing(Student::getName))
                .collect(Collectors.toList());
    }

    public List<Student> filterByGrade(double threshold) {
        return students.stream()
                .filter(student -> student.getGrade() >= threshold)
                .collect(Collectors.toList());
    }

    public double averageGrade() {
        return students.stream()
                .mapToDouble(Student::getGrade)
                .average()
                .orElse(0.0);
    }

    public List<Student> getStudents() {
        return students;
    }
}

public class StudentDataProcessor {
    public static void main(String[] args) {
        List<Student> students = readStudentsFromFile("students.txt");

        StudentProcessor processor = new StudentProcessor(students);

        System.out.println("All students:");
        students.forEach(System.out::println);

        System.out.println("\nSorted by name:");
        processor.sortByName().forEach(System.out::println);

        System.out.println("\nFiltered by grade (>= 75):");
        processor.filterByGrade(75).forEach(System.out::println);

        System.out.println("\nAverage grade:");
        System.out.println(processor.averageGrade());

        List<Student> newStudents = inputNewStudents();
        students.addAll(newStudents); // Добавляем новых студентов к старым

        System.out.println("\nAll students after adding new ones:");
        students.forEach(System.out::println);

        saveStudentsToFile("new_students.txt", students); // Сохраняем всех студентов в файл
    }

    private static List<Student> readStudentsFromFile(String fileName) {
        List<Student> students = new ArrayList<>();
        try {
            List<String> lines = Files.readAllLines(Paths.get(fileName), StandardCharsets.UTF_8);
            for (String line : lines) {
                String[] parts = line.split("-");
                if (parts.length == 3) {
                    String name = parts[0].trim();
                    int age = Integer.parseInt(parts[1].trim());
                    double grade = Double.parseDouble(parts[2].trim());
                    students.add(new Student(name, age, grade));
                }
            }
        } catch (IOException e) {
            System.err.println("Error reading file: " + e.getMessage());
        }
        return students;
    }

    private static List<Student> inputNewStudents() {
        List<Student> students = new ArrayList<>();
        Scanner scanner = new Scanner(System.in, "UTF-8"); // Обеспечиваем корректную обработку ввода в UTF-8
        System.out.println("Enter new student data (name-age-grade), type 'exit' to finish:");
        while (true) {
            String input = scanner.nextLine();
            if (input.equalsIgnoreCase("exit")) {
                break;
            }
            String[] parts = input.split("-");
            if (parts.length == 3) {
                try {
                    String name = parts[0].trim();
                    int age = Integer.parseInt(parts[1].trim());
                    double grade = Double.parseDouble(parts[2].trim());
                    students.add(new Student(name, age, grade));
                } catch (NumberFormatException e) {
                    System.out.println("Invalid input. Please enter data in the format: name-age-grade");
                }
            } else {
                System.out.println("Invalid input. Please enter data in the format: name-age-grade");
            }
        }
        return students;
    }

    private static void saveStudentsToFile(String fileName, List<Student> students) {
        try (BufferedWriter writer = Files.newBufferedWriter(Paths.get(fileName), StandardCharsets.UTF_8)) {
            for (Student student : students) {
                writer.write(student.toString());
                writer.newLine();
            }
        } catch (IOException e) {
            System.err.println("Error writing file: " + e.getMessage());
        }
    }
}
