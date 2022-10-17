/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tugas.cemilanconnectdb;

import java.io.Serializable;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.Persistence;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import tugas.cemilanconnectdb.exceptions.NonexistentEntityException;
import tugas.cemilanconnectdb.exceptions.PreexistingEntityException;

/**
 *
 * @author Wiratama
 */
public class DaftarmenuJpaController implements Serializable {

    public DaftarmenuJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = Persistence.createEntityManagerFactory("tugas_cemilanconnectdb_jar_0.0.1-SNAPSHOTPU");

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public DaftarmenuJpaController() {
    }

    public void create(Daftarmenu daftarmenu) throws PreexistingEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(daftarmenu);
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findDaftarmenu(daftarmenu.getIdMenu()) != null) {
                throw new PreexistingEntityException("Daftarmenu " + daftarmenu + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Daftarmenu daftarmenu) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            daftarmenu = em.merge(daftarmenu);
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                String id = daftarmenu.getIdMenu();
                if (findDaftarmenu(id) == null) {
                    throw new NonexistentEntityException("The daftarmenu with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(String id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Daftarmenu daftarmenu;
            try {
                daftarmenu = em.getReference(Daftarmenu.class, id);
                daftarmenu.getIdMenu();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The daftarmenu with id " + id + " no longer exists.", enfe);
            }
            em.remove(daftarmenu);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Daftarmenu> findDaftarmenuEntities() {
        return findDaftarmenuEntities(true, -1, -1);
    }

    public List<Daftarmenu> findDaftarmenuEntities(int maxResults, int firstResult) {
        return findDaftarmenuEntities(false, maxResults, firstResult);
    }

    private List<Daftarmenu> findDaftarmenuEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Daftarmenu.class));
            Query q = em.createQuery(cq);
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public Daftarmenu findDaftarmenu(String id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Daftarmenu.class, id);
        } finally {
            em.close();
        }
    }

    public int getDaftarmenuCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Daftarmenu> rt = cq.from(Daftarmenu.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
