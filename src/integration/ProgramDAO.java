package integration;

import entities.Program;
import java.util.stream.Stream;

public interface ProgramDAO {

    Stream<Program> findByNameOrID(String input);

    Stream<Program> findByName(String name);

    Stream<Program> findByID(int id);

    Stream<Program> findAll();

    void create(Program t);

    void update(Program t);

    void delete(Program t);

}
