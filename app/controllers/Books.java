package controllers;

import static play.data.Form.form;
import models.Book;
import models.Category;
import play.data.Form;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.Security;
import views.html.createForm;
import views.html.editForm;
import views.html.list;


public class Books extends Controller {
	
	
	
	/**
     * Display the paginated list of books.
     *
     * @param page Current page number (starts from 0)
     * @param sortBy Column to be sorted
     * @param order Sort order (either asc or desc)
     * @param filter Filter applied on book names
     */
    public static Result list(int page, String sortBy,String order,String filter) {
    	boolean logined = SessionManager.logined();    	
        return ok(
            list.render(
                Book.page(page, 10,sortBy, order, filter),
                sortBy, order, filter,logined
            )
        );
    }
   
    
    public static Result list() {
    	return list(0,Book.TblDef.BOOK_NAME,"asc","");
    }
    
    /**
     * Display the 'edit form' of a existing Computer.
     *
     * @param id Id of the computer to edit
     */
    @Security.Authenticated(Secured.class)
    public static Result edit(Long id) {
        Form<Book> bookForm = form(Book.class).fill(
            Book.find.byId(id)
        );
        return ok(
            editForm.render(id, bookForm)
        );
    }
    
    public static Result ref(Long id) {
        Form<Book> bookForm = form(Book.class).fill(
            Book.find.byId(id)
        );
        return ok(
            editForm.render(id, bookForm)
        );
    }
    
    /**
     * Handle the 'edit form' submission 
     *
     * @param id Id of the computer to edit
     */
    @Security.Authenticated(Secured.class)
    public static Result update(Long id) {
        Form<Book> bookForm = form(Book.class).bindFromRequest();
        if(bookForm.hasErrors()) {
            return badRequest(editForm.render(id, bookForm));
        }
        bookForm.get().update(id);
        flash("success", "Book " + bookForm.get().bookName + " has been updated");
        return Application.GO_HOME;
    }
    
    /**
     * Display the 'new computer form'.
     */
    @Security.Authenticated(Secured.class)
    public static Result create() {
        Form<Book> bookForm = form(Book.class);
        return ok(
        		createForm.render(
	            bookForm	            
            )
        );
    }
    
    /**
     * Handle the 'new computer form' submission 
     */
    @Security.Authenticated(Secured.class)
    public static Result save() {
        Form<Book> bookForm = form(Book.class).bindFromRequest();
        if(bookForm.hasErrors()) {
            return badRequest(
            		createForm.render(
            	            bookForm            	            
            	            )
        	    );
        }
        bookForm.get().save();
        flash("success", "Book " + bookForm.get().bookName + " has been created");
        return Application.GO_HOME;
    }
    
    /**
     * Handle computer deletion
     */
    @Security.Authenticated(Secured.class)
    public static Result delete(Long id) {
        Book.find.ref(id).delete();
        flash("success", "Book has been deleted");
        return Application.GO_HOME;
    }
    

}
