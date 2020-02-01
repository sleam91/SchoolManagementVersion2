package integration;

import entities.Student;
import java.util.stream.Stream;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;

@RunWith(JUnitPlatform.class)
public class StudentDAOImplTest {

    StudentDAO DAO;

    @BeforeEach
    public void setUp() {
        this.DAO = new MockStudentDAO();
    }

    @Test
    public void testFindByNameOrID1() {
        String input = "bas";
        Student result = DAO.findByNameOrID(input).findAny().orElse(null);
        assertNotNull(result);
    }

    @Test
    public void testFindByNameOrID2() {
        String input = "1";
        Student result = DAO.findByNameOrID(input).findFirst().orElse(null);
        assertNotNull(result);
    }

    @Test
    public void testFindByNameOrID3() {
        String input = "Ivan";
        Student result = DAO.findByNameOrID(input).findAny().orElse(null);
        assertNull(result);
    }

    @Test
    public void testFindByNameOrID4() {
        String input = "9999999999999999999999999";
        Student result = DAO.findByNameOrID(input).findFirst().orElse(null);
        assertNull(result);
    }

    @Test
    public void testFindAll() {

        Stream<Student> result = DAO.findAll();
        long count = result.count();
        assertEquals(3L, count);
    }

    @Test
    public void testCreate() {
        Student s = new Student("Nicolas");
        s.setId(4);
        DAO.create(s);
        Student s1=DAO.findByNameOrID("Nico").findAny().orElse(null);
        assertNotNull(s1);
        
    }

    @Test
    public void testUpdate() {
        Student s = DAO.findByID(1).findFirst().orElse(null);
        String name=s.getName();
        s.setName("Sebas");
        DAO.update(s);
        s=DAO.findByID(1).findFirst().orElse(null);
        assertNotEquals(name, s.getName());
    }

    @Test
    public void testDelete() {
        Student s = DAO.findByID(1).findFirst().orElse(null);
        assertNotNull(s);
        s.removeProgram();
        DAO.delete(s);
        s=DAO.findByID(1).findFirst().orElse(null);
        assertNull(s);
    }

}
