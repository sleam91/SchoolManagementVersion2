package integration;

import entities.Course;
import java.util.stream.Stream;

public interface CourseDAO {

    Stream<Course> findByNameOrID(String input);

    Stream<Course> findByName(String name);

    Stream<Course> findByID(int id);

    Stream<Course> findAll();

    void create(Course t);

    void update(Course t);

    void delete(Course t);

}
