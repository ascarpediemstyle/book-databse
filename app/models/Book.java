package models;

import java.util.*;
import javax.persistence.*;

import play.db.ebean.*;
import play.data.format.*;
import play.data.validation.*;

import com.avaje.ebean.*;

@Entity 
@Table(name=Book.TblDef.TABLE_NAME) 
public class Book extends Model {
  
  private static final long serialVersionUID = 1L;
  
  public static class TblDef{
	 public static final String TABLE_NAME = "t_book";
	 public static final String BOOK_ID = "book_id";
	 public static final String BOOK_NAME = "book_name";
  }

  @Id
  @Column(name=TblDef.BOOK_ID, columnDefinition="SERIAL")
  public Long bookId;
  
  @Constraints.Required
  @Column(name=TblDef.BOOK_NAME)
  public String bookName;
  
  @Constraints.Required  
  public int rank;
  
  @Formats.DateTime(pattern="yyyy-MM-dd")
  public Date introduced;
  
  @Formats.DateTime(pattern="yyyy-MM-dd")
  public Date discontinued;  
 
    
  public static Finder<Long, Book> find = new Finder<Long, Book>(
    Long.class, Book.class
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
  public static Page<Book> page(int page, int pageSize, String sortBy, String order, String filter) {
      return 
          find.where()
              .ilike("book_name", "%" + filter + "%")
              .orderBy(sortBy + " " + order)              
              .findPagingList(pageSize)              
              .getPage(page);
  }


}

