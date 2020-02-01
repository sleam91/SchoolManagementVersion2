package integration;

import entities.Student;
import java.util.stream.Stream;

public interface StudentDAO {

    Stream<Student> findByNameOrID(String input);

    Stream<Student> findByName(String name);

    Stream<Student> findByID(int id);

    Stream<Student> findAll();

    void create(Student t);

    void update(Student t);

    void delete(Student t);

}
