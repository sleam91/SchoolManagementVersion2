package integration;

import entities.Student;
import java.util.stream.Stream;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

public class StudentDAOImpl implements StudentDAO {

    private EntityManagerFactory emf;

    public StudentDAOImpl(EntityManagerFactory emf) {
        this.emf=emf;
    }

    @Override
    public Stream<Student> findByNameOrID(String input) {
        if (input.matches("^[0-9]\\d*$")) {
            try {
                int id = Integer.parseInt(input);
                Stream<Student> s = findByID(id);
                return s;
            } catch (NumberFormatException e) {
                return Stream.empty();
            }
        } else {
            Stream<Student> s = findByName(input);
            return s;
        }
    }

    @Override
    public Stream<Student> findByName(String name) {
        EntityManager em = emf.createEntityManager();
        Stream<Student> s = null;
        try {
            s = em.createQuery("FROM Student s where s.name like :name", Student.class)
                    .setParameter("name", "%" + name+"%")
                    .getResultStream();
        } catch (Exception e) {
        }
        em.close();
        return s;
    }

    @Override
    public Stream<Student> findByID(int id) {
        EntityManager em = emf.createEntityManager();
        Student s = em.find(Student.class, id);
        em.close();
        return s != null ? Stream.of(s) : Stream.empty();
    }

    @Override
    public Stream<Student> findAll() {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        Stream<Student> listStudent = em.createQuery("from Student s", Student.class).getResultStream();
        em.getTransaction().commit();
        em.close();
        return listStudent;
    }

    @Override
    public void create(Student s) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        em.persist(s);
        em.getTransaction().commit();
        em.close();
    }

    @Override
    public void update(Student s) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        em.merge(s);
        em.getTransaction().commit();
        em.close();
    }

    @Override
    public void delete(Student s) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        em.remove(em.merge(s));
        em.getTransaction().commit();
        em.close();
    }

}
