package integration;

import entities.Student;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class MockStudentDAO implements StudentDAO {

    Student s1, s2, s3;
    List<Student> listStudents = new ArrayList<>();

    public MockStudentDAO() {
        s1 = new Student("Sebastian");
        s1.setId(1);
        s2 = new Student("Caspar");
        s2.setId(2);
        s3 = new Student("Joel");
        s3.setId(3);
        listStudents.add(s1);
        listStudents.add(s2);
        listStudents.add(s3);

    }

    @Override
    public Stream<Student> findByNameOrID(String input) {
        if (input.matches("^[0-9]\\d*$")) {
            try {
                int id = Integer.parseInt(input);
                Stream<Student> c = findByID(id);
                return c;
            } catch (NumberFormatException e) {
                return Stream.empty();
            }
        } else {
            Stream<Student> c = findByName(input);
            return c;
        }
    }

    @Override
    public Stream<Student> findByName(String name) {

        return listStudents.stream()
                .filter(e -> e.getName().toLowerCase().contains(name.toLowerCase()));

    }

    @Override
    public Stream<Student> findByID(int id) {
        return listStudents.stream()
                .filter(e -> e.getId()==id);
    }

    @Override
    public Stream<Student> findAll() {
        return listStudents.stream();
    }

    @Override
    public void create(Student c) {
        listStudents.add(c);
    }

    @Override
    public void update(Student c) {
        if (listStudents.removeIf(e -> e.getId() == c.getId())) {
            listStudents.add(c);
        }

    }

    @Override
    public void delete(Student c) {
        listStudents.remove(c);
    }

}
