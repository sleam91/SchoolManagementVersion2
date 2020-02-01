package integration;

import entities.Teacher;
import java.util.stream.Stream;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;

@RunWith(JUnitPlatform.class)
public class TeacherDAOImplTest {

    TeacherDAO DAO;

    @BeforeEach
    public void setUp() {
        this.DAO = new MockTeacherDAO();
    }

    @Test
    public void testFindByNameOrID1() {
        String input = "lf";
        Teacher result = DAO.findByNameOrID(input).findAny().orElse(null);
        assertNotNull(result);
    }

    @Test
    public void testFindByNameOrID2() {
        String input = "1";
        Teacher result = DAO.findByNameOrID(input).findFirst().orElse(null);
        assertNotNull(result);
    }

    @Test
    public void testFindByNameOrID3() {
        String input = "Christer";
        Teacher result = DAO.findByNameOrID(input).findAny().orElse(null);
        assertNull(result);
    }

    @Test
    public void testFindByNameOrID4() {
        String input = "9999999999999999999999999";
        Teacher result = DAO.findByNameOrID(input).findFirst().orElse(null);
        assertNull(result);
    }

    @Test
    public void testFindAll() {

        Stream<Teacher> result = DAO.findAll();
        long count = result.count();
        assertEquals(3L, count);
    }

    @Test
    public void testCreate() {
        Teacher t = new Teacher("Mats");
        t.setId(4);
        DAO.create(t);
        Teacher c1=DAO.findByNameOrID("at").findAny().orElse(null);
        assertNotNull(c1);
        
    }

    @Test
    public void testUpdate() {
        Teacher t = DAO.findByID(1).findFirst().orElse(null);
        String name=t.getName();
        t.setName("Mats 2");
        DAO.update(t);
        t=DAO.findByID(1).findFirst().orElse(null);
        assertNotEquals(name, t.getName());
    }

    @Test
    public void testDelete() {
        Teacher t = DAO.findByID(1).findFirst().orElse(null);
        assertNotNull(t);
        t.removeCourses();
        DAO.delete(t);
        t=DAO.findByID(1).findFirst().orElse(null);
        assertNull(t);
    }

}
