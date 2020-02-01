package integration;

import entities.Program;
import java.util.stream.Stream;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;

@RunWith(JUnitPlatform.class)
public class ProgramDAOImplTest {

    ProgramDAO DAO;

    @BeforeEach
    public void setUp() {
        this.DAO = new MockProgramDAO();
    }

    @Test
    public void testFindByNameOrID1() {
        String input = "ava";
        Program result = DAO.findByNameOrID(input).findAny().orElse(null);
        assertNotNull(result);
    }

    @Test
    public void testFindByNameOrID2() {
        String input = "1";
        Program result = DAO.findByNameOrID(input).findFirst().orElse(null);
        assertNotNull(result);
    }

    @Test
    public void testFindByNameOrID3() {
        String input = "it-projektledare";
        Program result = DAO.findByNameOrID(input).findAny().orElse(null);
        assertNull(result);
    }

    @Test
    public void testFindByNameOrID4() {
        String input = "9999999999999999999999999";
        Program result = DAO.findByNameOrID(input).findFirst().orElse(null);
        assertNull(result);
    }

    @Test
    public void testFindAll() {

        Stream<Program> result = DAO.findAll();
        long count = result.count();
        assertEquals(3L, count);
    }

    @Test
    public void testCreate() {
        Program p = new Program("Android-utvecklare", "2022-01-01", "2022-03-01");
        p.setId(4);
        DAO.create(p);
        Program p1=DAO.findByNameOrID("droid-utveck").findAny().orElse(null);
        assertNotNull(p1);
        
    }

    @Test
    public void testUpdate() {
        Program p = DAO.findByID(1).findFirst().orElse(null);
        String name=p.getName();
        p.setName("Javautvecklare 2");
        DAO.update(p);
        p=DAO.findByID(1).findFirst().orElse(null);
        assertNotEquals(name, p.getName());
    }

    @Test
    public void testDelete() {
        Program p = DAO.findByID(1).findFirst().orElse(null);
        assertNotNull(p);
        p.removeCoursesAndStudents();
        DAO.delete(p);
        p=DAO.findByID(1).findFirst().orElse(null);
        assertNull(p);
    }

}
