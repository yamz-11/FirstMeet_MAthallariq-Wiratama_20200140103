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
public class TokoJpaController implements Serializable {

    public TokoJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = Persistence.createEntityManagerFactory("tugas_cemilanconnectdb_jar_0.0.1-SNAPSHOTPU");

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public TokoJpaController() {
    }

    public void create(Toko toko) throws PreexistingEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(toko);
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findToko(toko.getIdToko()) != null) {
                throw new PreexistingEntityException("Toko " + toko + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Toko toko) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            toko = em.merge(toko);
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                String id = toko.getIdToko();
                if (findToko(id) == null) {
                    throw new NonexistentEntityException("The toko with id " + id + " no longer exists.");
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
            Toko toko;
            try {
                toko = em.getReference(Toko.class, id);
                toko.getIdToko();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The toko with id " + id + " no longer exists.", enfe);
            }
            em.remove(toko);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Toko> findTokoEntities() {
        return findTokoEntities(true, -1, -1);
    }

    public List<Toko> findTokoEntities(int maxResults, int firstResult) {
        return findTokoEntities(false, maxResults, firstResult);
    }

    private List<Toko> findTokoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Toko.class));
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

    public Toko findToko(String id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Toko.class, id);
        } finally {
            em.close();
        }
    }

    public int getTokoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Toko> rt = cq.from(Toko.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
