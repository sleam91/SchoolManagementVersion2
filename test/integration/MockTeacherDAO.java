package integration;

import entities.Teacher;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class MockTeacherDAO implements TeacherDAO {

    Teacher t1, t2, t3;
    List<Teacher> listTeachers = new ArrayList<>();

    public MockTeacherDAO() {
        t1 = new Teacher("Ulf");
        t1.setId(1);
        t2 = new Teacher("Bita");
        t2.setId(2);
        t3 = new Teacher("David");
        t3.setId(3);
        listTeachers.add(t1);
        listTeachers.add(t2);
        listTeachers.add(t3);

    }

    @Override
    public Stream<Teacher> findByNameOrID(String input) {
        if (input.matches("^[0-9]\\d*$")) {
            try {
                int id = Integer.parseInt(input);
                Stream<Teacher> c = findByID(id);
                return c;
            } catch (NumberFormatException e) {
                return Stream.empty();
            }
        } else {
            Stream<Teacher> t = findByName(input);
            return t;
        }
    }

    @Override
    public Stream<Teacher> findByName(String name) {

        return listTeachers.stream()
                .filter(e -> e.getName().toLowerCase().contains(name.toLowerCase()));

    }

    @Override
    public Stream<Teacher> findByID(int id) {
        return listTeachers.stream()
                .filter(e -> e.getId()==id);
    }

    @Override
    public Stream<Teacher> findAll() {
        return listTeachers.stream();
    }

    @Override
    public void create(Teacher t) {
        listTeachers.add(t);
    }

    @Override
    public void update(Teacher t){
        if (listTeachers.removeIf(e -> e.getId() == t.getId())) {
            listTeachers.add(t);
        }

    }

    @Override
    public void delete(Teacher t) {
        listTeachers.remove(t);
    }

}
