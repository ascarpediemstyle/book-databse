package models;

import java.util.LinkedHashMap;
import java.util.Map;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import play.data.validation.Constraints;
import play.db.ebean.Model;

import com.avaje.ebean.Query;

@Entity 
@Table(name=Category.TblDef.TABLE_NAME) 
public class Category extends Model {
  
  private static final long serialVersionUID = 1L;
  
  public static class TblDef{
	 public static final String TABLE_NAME = "t_category";
	 public static final String CATEGORY_ID = "category_id";
	 public static final String SUBJECT = "subject";
	 public static final String SORT_ORDER = "sort_order";
	 
  }

  
  
  
  @Id  
  @SequenceGenerator(name=TblDef.CATEGORY_ID + "_gen",sequenceName=TblDef.CATEGORY_ID + "_seq", allocationSize = 1, initialValue = 1 )
  @GeneratedValue(strategy=GenerationType.SEQUENCE, generator=TblDef.CATEGORY_ID + "_gen")  
  @Column(name=TblDef.CATEGORY_ID) 
  public long categoryId; 
  
  @Constraints.Required  
  public String subject;  
  
  @Column(name=TblDef.SORT_ORDER)
  public int sortOrder;
  
    
  public static Finder<Integer, Category> find = new Finder<Integer, Category>(
    Integer.class, Category.class
  );
  
  /**
   * Return a page of computer
   *
   * @param page Page to display
   * @param pageSize Number of computers per page
   * @param sortBy Computer property used for sorting
   * @param order Sort order (either or asc or desc)
   * @param filter Filter applied on the name column
   */
  public static Query<Category> page(String sortBy, String order, String filter) {
      return 
          find.where()
              .ilike(TblDef.SUBJECT, "%" + filter + "%")
              .orderBy(sortBy + " " + order)   
              ;
  }
  
  public static Map<String,String> options() {
      LinkedHashMap<String,String> options = new LinkedHashMap<String,String>();
      for(Category c: Category.find.orderBy(TblDef.SORT_ORDER).findList()) {
          options.put(String.valueOf(c.categoryId), c.subject);
      }
      return options;
  }


}

