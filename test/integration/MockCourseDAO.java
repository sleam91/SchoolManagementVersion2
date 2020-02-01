package integration;

import entities.Course;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class MockCourseDAO implements CourseDAO {

    Course c1, c2, c3;
    List<Course> listCourses = new ArrayList<>();

    public MockCourseDAO() {
        c1 = new Course("Java SE", "2021-01-30", "2021-03-03");
        c1.setId(1);
        c2 = new Course("C#", "2021-01-30", "2021-03-03");
        c2.setId(2);
        c3 = new Course("Javascript", "2021-01-30", "2021-03-03");
        c3.setId(3);
        listCourses.add(c1);
        listCourses.add(c2);
        listCourses.add(c3);

    }

    @Override
    public Stream<Course> findByNameOrID(String input) {
        if (input.matches("^[0-9]\\d*$")) {
            try {
                int id = Integer.parseInt(input);
                Stream<Course> c = findByID(id);
                return c;
            } catch (NumberFormatException e) {
                return Stream.empty();
            }
        } else {
            Stream<Course> c = findByName(input);
            return c;
        }
    }

    @Override
    public Stream<Course> findByName(String name) {

        return listCourses.stream()
                .filter(e -> e.getName().toLowerCase().contains(name.toLowerCase()));

    }

    @Override
    public Stream<Course> findByID(int id) {
        return listCourses.stream()
                .filter(e -> e.getId()==id);
    }

    @Override
    public Stream<Course> findAll() {
        return listCourses.stream();
    }

    @Override
    public void create(Course c) {
        listCourses.add(c);
    }

    @Override
    public void update(Course c) {
        if (listCourses.removeIf(e -> e.getId() == c.getId())) {
            listCourses.add(c);
        }

    }

    @Override
    public void delete(Course c) {
        listCourses.remove(c);
    }

}
