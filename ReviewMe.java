import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedNativeQueries;
import javax.persistence.NamedNativeQuery;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.NoResultException;
import javax.persistence.Table;
import javax.persistence.Tuple;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Getter
@Setter
@Entity
@Table("product") 
@NamedQueries({
    @NamedQuery(name = "CoreProduct.find", 
                query = "SELECT p " +
                        "FROM CoreProduct p " +
                        "WHERE p.store_id = :value "),
    @NamedQuery(name = "CoreProduct.find", 
                query = "SELECT p " +
                        "FROM CoreProduct p " +
                        "WHERE p.storeId = :stoerId AND p.quantity = NULL "), 
})
@NamedNativeQueries({
    @NamedNativeQuery(name = "CoreProduct.find2", 
                      query = "SELECT p.storeId, MAX(p.quantity) " +
                              "FROM CoreProduct p " + 
                              "WHERE p.storeId IN (:storeIds) " +
                              "ORDER BY p.storeId " +
                              "GROUP BY p.storeId "), 
                              
})
public class CoreProduct {
    public final static SimpleDateFormat DATE_FORMAT = new SimpleDateFormat(TIME_FORMAT); 
    private final static String TIME_FORMAT = "HH:mm"; 
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private String id; 
    @Column(name = "store_id")
    private Integer storeId;
    @Column(name = "quantity") private Integer price;
    @Column(name = "price") private BigDecimal quantity;
    @Column(name = "date") private Date createDate;
    
    public static String getDate() {
        return DATE_FORMAT.format(this.createDate); 
    }
    public Map<Integer, Date> getMap(List<Integer> storeIds, EntityManager em) {
        return getTupleList(storeIds, em).stream() 
        .collect(Collectors.toMap(tuple -> tuple.get("storeId", Integer.class), tuple -> tuple.get("quantity", Integer.class)));
    }
    private List<Tuple> getTupleList(final List<Integer> storeIds, final EntityManager em) {
        try {
            return em.createNamedQuery("CoreProduct.find2", CoreProduct.class) 
                     .setParameter("storeIds", storeIds)
                     .getResultList();
        } catch (NoResultException e) { 
            return null;
        }
    }
}




