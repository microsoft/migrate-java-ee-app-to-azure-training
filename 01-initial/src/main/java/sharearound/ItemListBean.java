package sharearound;

import java.util.List;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

@Named(value = "itemListBean")
@RequestScoped
public class ItemListBean {

    /**
     * Stores the entity manager.
     */
    @PersistenceContext(unitName = "sharearound")
    private EntityManager em;
    
    /**
     * Get all the items.
     * 
     * @return the items.
     */
    public List<Item> getItems() {
        Query query = em.createQuery("SELECT object(o) FROM Item AS o");
        return query.getResultList();
    }
}
