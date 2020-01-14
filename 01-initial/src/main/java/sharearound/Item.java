package sharearound;

import java.io.Serializable;
import java.math.BigInteger;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * The item entity.
 */
@Entity
@Table(name = "item")
public class Item implements Serializable {

    /**
     * Stores the serial version UID.
     */
    private static final long serialVersionUID = 1L;

    /**
     * Stores the id.
     */
    @Column(name = "id")
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator="item_id_seq")
    private BigInteger id;

    /**
     * Stores the short description.
     */
    @Column(name = "short_description")
    private String shortDescription;
    
    /**
     * Stores the title.
     */
    @Column(name = "title")
    private String title;

    /**
     * Get the id.
     *
     * @return the id.
     */
    public BigInteger getId() {
        return id;
    }

    /**
     * Get the short description.
     * 
     * @return the short description.
     */
    public String getShortDescription() {
        return shortDescription;
    }

    /**
     * Get the title.
     *
     * @return the title.
     */
    public String getTitle() {
        return title;
    }

    /**
     * Get the hash code.
     *
     * @return the hash code.
     */
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    /**
     * Equals.
     *
     * @param object the object to compare against.
     * @return true when equal, false otherwise.
     */
    @Override
    public boolean equals(Object object) {
        boolean result = false;
        if (object instanceof Item) {
            Item item = (Item) object;
            if (this.id != null) {
                result = this.id.equals(item.id);
            }
        }
        return result;
    }

    /**
     * Set the id.
     *
     * @param id the id.
     */
    public void setId(BigInteger id) {
        this.id = id;
    }

    /**
     * Set the short description.
     * 
     * @param shortDescription the short description.
     */
    public void setShortDescription(String shortDescription) {
        this.shortDescription = shortDescription;
    }
    
    /**
     * Set the title.
     *
     * @param title the title.
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Get the string representation.
     *
     * @return the string representation.
     */
    @Override
    public String toString() {
        return "sharearound.Item[ id=" + id + " ]";
    }
}
