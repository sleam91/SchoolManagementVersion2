package integration;

import entities.Teacher;
import java.util.stream.Stream;

public interface TeacherDAO {

    Stream<Teacher> findByNameOrID(String input);

    Stream<Teacher> findByName(String name);

    Stream<Teacher> findByID(int id);

    Stream<Teacher> findAll();

    void create(Teacher t);

    void update(Teacher t);

    void delete(Teacher t);

}
