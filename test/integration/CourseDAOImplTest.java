package integration;

import entities.Course;
import java.util.stream.Stream;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;

@RunWith(JUnitPlatform.class)
public class CourseDAOImplTest {

    CourseDAO DAO;

    @BeforeEach
    public void setUp() {
        this.DAO = new MockCourseDAO();
    }

    @Test
    public void testFindByNameOrID1() {
        String input = "av";
        Course result = DAO.findByNameOrID(input).findAny().orElse(null);
        assertNotNull(result);
    }

    @Test
    public void testFindByNameOrID2() {
        String input = "1";
        Course result = DAO.findByNameOrID(input).findFirst().orElse(null);
        assertNotNull(result);
    }

    @Test
    public void testFindByNameOrID3() {
        String input = "database";
        Course result = DAO.findByNameOrID(input).findAny().orElse(null);
        assertNull(result);
    }

    @Test
    public void testFindByNameOrID4() {
        String input = "9999999999999999999999999";
        Course result = DAO.findByNameOrID(input).findFirst().orElse(null);
        assertNull(result);
    }

    @Test
    public void testFindAll() {

        Stream<Course> result = DAO.findAll();
        long count = result.count();
        assertEquals(3L, count);
    }

    @Test
    public void testCreate() {
        Course c = new Course("Java EE", "2022-01-01", "2022-03-01");
        c.setId(4);
        DAO.create(c);
        Course c1=DAO.findByNameOrID("va e").findAny().orElse(null);
        assertNotNull(c1);
        
    }

    @Test
    public void testUpdate() {
        Course c = DAO.findByID(1).findFirst().orElse(null);
        String name=c.getName();
        c.setName("Java 2");
        DAO.update(c);
        c=DAO.findByID(1).findFirst().orElse(null);
        assertNotEquals(name, c.getName());
    }

    @Test
    public void testDelete() {
        Course c = DAO.findByID(1).findFirst().orElse(null);
        assertNotNull(c);
        c.removeTeachersAndProgram();
        DAO.delete(c);
        c=DAO.findByID(1).findFirst().orElse(null);
        assertNull(c);
    }

}
