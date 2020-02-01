package integration;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class Factory {

    public static EntityManagerFactory emf = Persistence.createEntityManagerFactory("SchoolManagementPU");
}
