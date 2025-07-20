package dal;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;

import java.lang.reflect.ParameterizedType;
import java.util.List;

public abstract class GenericDao<T> {
    private static final EntityManagerFactory ENTITY_MANAGER_FACTORY = Persistence.createEntityManagerFactory("bangiay");

    private final Class<T> entityClass;
    protected EntityManager entityManager;

    @SuppressWarnings("unchecked")
    public GenericDao() {
        this.entityClass = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
        this.entityManager = ENTITY_MANAGER_FACTORY.createEntityManager(); // Use existing factory
    }

    public List<T> getAll() {
        return entityManager.createQuery("SELECT e FROM " + entityClass.getSimpleName() + " e", entityClass).getResultList();
    }

    public T getById(Long id) {
        return entityManager.find(entityClass, id);
    }

    public void save(T entity) {
        EntityTransaction transaction = entityManager.getTransaction();
        try {
            transaction.begin();
            entityManager.persist(entity);
            transaction.commit();
        } catch (Exception e) {
            transaction.rollback();
            e.printStackTrace();
        }
    }

    public void saveAll(List<T> entities) {
        EntityTransaction transaction = entityManager.getTransaction();
        try {
            transaction.begin();
            for (T entity : entities) {
                entityManager.persist(entity);
            }
            transaction.commit();
        } catch (Exception e) {
            transaction.rollback();
            e.printStackTrace();
        }
    }


    public void update(T entity) {
        EntityTransaction transaction = entityManager.getTransaction();
        try {
            transaction.begin();
            entityManager.merge(entity);
            transaction.commit();
        } catch (Exception e) {
            transaction.rollback();
            e.printStackTrace();
        }
    }

    public void updateAll(List<T> entities) {
        EntityTransaction transaction = entityManager.getTransaction();
        try {
            transaction.begin();
            for (T entity : entities) {
                entityManager.merge(entity);
            }
            transaction.commit();
        } catch (Exception e) {
            transaction.rollback();
            e.printStackTrace();
        }
    }

    public void delete(T entity) {
        EntityTransaction transaction = entityManager.getTransaction();
        try {
            transaction.begin();
            if (entity != null && entityManager.contains(entity)) {
                entityManager.remove(entity);
            } else {
                // merge nếu entity bị detached
                entityManager.remove(entityManager.merge(entity));
            }
            transaction.commit();
        } catch (Exception e) {
            transaction.rollback();
            e.printStackTrace();
        }
    }

    public void deleteAll(List<T> entities) {
        if (entities == null || entities.isEmpty()) {
            System.out.println("No entities to delete.");
            return;
        }

        EntityTransaction transaction = entityManager.getTransaction();
        try {
            transaction.begin();
            System.out.println("Deleting " + entities.size() + " entities");

            for (T entity : entities) {
                if (entity == null) continue;

                // In log nếu muốn: class name + hashCode (tránh dùng reflection)
                System.out.println("Deleting entity: " + entity.getClass().getSimpleName() + "@" + Integer.toHexString(System.identityHashCode(entity)));

                if (entityManager.contains(entity)) {
                    entityManager.remove(entity);
                } else {
                    entityManager.remove(entityManager.merge(entity));
                }
            }

            transaction.commit();
            System.out.println("DeleteAll: Transaction committed");
        } catch (Exception e) {
            if (transaction.isActive()) transaction.rollback();
            System.out.println("DeleteAll: Transaction rolled back due to exception:");
            e.printStackTrace();
        }
    }



    public void close() {
        entityManager.close();
    }
}
