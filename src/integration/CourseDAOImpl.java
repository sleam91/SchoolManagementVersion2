package integration;

import entities.Course;
import java.util.stream.Stream;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

public class CourseDAOImpl implements CourseDAO {

    private EntityManagerFactory emf;

    public CourseDAOImpl(EntityManagerFactory emf) {
        this.emf = emf;
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
        EntityManager em = emf.createEntityManager();
        Stream<Course> c = null;
        try {
            c = em.createQuery("FROM Course c where c.name like :name", Course.class)
                    .setParameter("name", "%" + name + "%")
                    .getResultStream();

        } catch (Exception e) {
        }
        em.close();
        return c;
    }

    @Override
    public Stream<Course> findByID(int id) {
        EntityManager em = emf.createEntityManager();
        Course c = em.find(Course.class, id);
        em.close();
        return c != null ? Stream.of(c) : Stream.empty();

    }

    @Override
    public Stream<Course> findAll() {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        Stream<Course> listCourses = em.createQuery("from Course c", Course.class).getResultStream();
        em.getTransaction().commit();
        em.close();
        return listCourses;
    }

    @Override
    public void create(Course c) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        em.persist(c);
        em.getTransaction().commit();
        em.close();
    }

    @Override
    public void update(Course c) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        em.merge(c);
        em.getTransaction().commit();
        em.close();
    }

    @Override
    public void delete(Course c) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        em.remove(em.merge(c));
        em.getTransaction().commit();
        em.close();
    }

}
