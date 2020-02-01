package integration;

import entities.Program;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class MockProgramDAO implements ProgramDAO {

    Program p1, p2, p3;
    List<Program> listPrograms = new ArrayList<>();

    public MockProgramDAO() {
        p1 = new Program("Javautvecklare", "2021-01-30", "2021-03-03");
        p1.setId(1);
        p2 = new Program(".NET-utvecklare", "2021-01-30", "2021-03-03");
        p2.setId(2);
        p3 = new Program("Frontendutvecklare", "2021-01-30", "2021-03-03");
        p3.setId(3);
        listPrograms.add(p1);
        listPrograms.add(p2);
        listPrograms.add(p3);

    }

    @Override
    public Stream<Program> findByNameOrID(String input) {
        if (input.matches("^[0-9]\\d*$")) {
            try {
                int id = Integer.parseInt(input);
                Stream<Program> p = findByID(id);
                return p;
            } catch (NumberFormatException e) {
                return Stream.empty();
            }
        } else {
            Stream<Program> p = findByName(input);
            return p;
        }
    }

    @Override
    public Stream<Program> findByName(String name) {

        return listPrograms.stream()
                .filter(e -> e.getName().toLowerCase().contains(name.toLowerCase()));

    }

    @Override
    public Stream<Program> findByID(int id) {
        return listPrograms.stream()
                .filter(e -> e.getId()==id);
    }

    @Override
    public Stream<Program> findAll() {
        return listPrograms.stream();
    }

    @Override
    public void create(Program p) {
        listPrograms.add(p);
    }

    @Override
    public void update(Program p) {
        if (listPrograms.removeIf(e -> e.getId() == p.getId())) {
            listPrograms.add(p);
        }

    }

    @Override
    public void delete(Program p) {
        listPrograms.remove(p);
    }

}
