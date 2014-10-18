package models;

import java.util.*;
import java.sql.Timestamp;

import javax.persistence.*;

import models.Category.TblDef;
import play.db.ebean.*;
import play.data.format.*;
import play.data.validation.*;

import com.avaje.ebean.*;
import com.avaje.ebean.annotation.CreatedTimestamp;
import com.avaje.ebean.annotation.UpdatedTimestamp;


@Entity 
@Table(name=Book.TblDef.TABLE_NAME) 
public class Book extends Model {
  
  private static final long serialVersionUID = 1L;
  public static final int TOP_BOOKS_NUM = 16;
  
  public static class TblDef{
	 public static final String TABLE_NAME = "t_book";
	 public static final String BOOK_ID = "book_id";
	 public static final String BOOK_NAME = "book_name";
	 public static final String CATEGORY_ID = "category_id";
	 public static final String RANK = "rank";
	 public static final String COMMENT = "comment";
	 public static final String AMAZON_URI = "amazon_uri";
	 public static final String RECORDED_ON = "recorded_on";	 
  }

 
 
  @Id  
  @SequenceGenerator(name=TblDef.BOOK_ID + "_gen",sequenceName=TblDef.BOOK_ID + "_seq", allocationSize = 1, initialValue = 1 )
  @GeneratedValue(strategy=GenerationType.SEQUENCE, generator=TblDef.BOOK_ID + "_gen")  
  @Column(name=TblDef.BOOK_ID) 
  //@Column(name=TblDef.BOOK_ID, columnDefinition="bigserial")
  public Long bookId;
  
  @Constraints.Required
  @Column(name=TblDef.BOOK_NAME)
  public String bookName;
  
  @Constraints.Required  
  public int rank;
  
  @ManyToOne  
  @JoinColumn(name=TblDef.CATEGORY_ID )  
  public Category category;
  
  public String comment;  
  
  
  @Column(name=TblDef.AMAZON_URI,length=2048)
  public String amazonUri;
  
  @CreatedTimestamp
  public Timestamp recordedOn; 
  
  @UpdatedTimestamp
  public Timestamp updatedOn; 
 
 
    
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
              .ilike(TblDef.BOOK_NAME, "%" + filter + "%")
              .orderBy(sortBy + " " + order)              
              .findPagingList(pageSize)              
              .getPage(page);
  }

    public static Page<Book> page(String target, int page, int pageSize, String sortBy, String order, String filter) {
        return
                find.where()
                        .ilike(target, "%" + filter + "%")
                        .orderBy(sortBy + " " + order)
                        .findPagingList(pageSize)
                        .getPage(page);
    }


  
  public static Map<String,String> rankOptions() {
      LinkedHashMap<String,String> options = new LinkedHashMap<String,String>();
      for(int i=1;i<6;++i){
          options.put(String.valueOf(i),String.valueOf(i));
      }
      return options;
  }
  
  public static List<Book> getTopBooks() {
	  
	  List<Book> books =  find.where()
              .like(TblDef.AMAZON_URI,"%img%")
              .orderBy(Book.TblDef.RECORDED_ON + " desc")              
              .findPagingList(TOP_BOOKS_NUM)              
              .getPage(0).getList();
	  
	  int diff = TOP_BOOKS_NUM - books.size();
	  if( diff > 0){		 
		 Random rnd = new Random();
		 for(int i=0;i<diff;++i){
			 int index = rnd.nextInt(books.size()) ;
			 books.add(books.get(index));
		 }
	 }
	  
	  return books;
  }
  
  public static String getImageFromAmazonUri(String uri){
	  int start = uri.indexOf("<img");
	  int end = uri.indexOf("</a>");
	  return uri.substring(start,end);
  }


}

