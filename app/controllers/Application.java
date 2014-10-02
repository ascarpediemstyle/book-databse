package controllers;

import play.mvc.*;
import play.data.*;
import static play.data.Form.*;

import views.html.*;

import models.*;

/**
 * Manage a database of computers
 */
public class Application extends Controller {
    
    /**
     * This result directly redirect to application home.
     */
    public static Result GO_HOME = redirect(
        routes.Application.list(0, "", "", "")
    );
    
    /**
     * Handle default path requests, redirect to computers list
     */
    public static Result index() {
        return GO_HOME;
    }

    /**
     * Display the paginated list of books.
     *
     * @param page Current page number (starts from 0)
     * @param sortBy Column to be sorted
     * @param order Sort order (either asc or desc)
     * @param filter Filter applied on book names
     */
    public static Result list(int page, String sortBy, String order, String filter) {
        return ok(
            list.render(
                Book.page(page, 10, sortBy, order, filter),
                sortBy, order, filter
            )
        );
    }
    
    /**
     * Display the 'edit form' of a existing Computer.
     *
     * @param id Id of the computer to edit
     */
    public static Result edit(Long id) {
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
    public static Result update(Long id) {
        Form<Book> bookForm = form(Book.class).bindFromRequest();
        if(bookForm.hasErrors()) {
            return badRequest(editForm.render(id, bookForm));
        }
        bookForm.get().update(id);
        flash("success", "Book " + bookForm.get().bookName + " has been updated");
        return GO_HOME;
    }
    
    /**
     * Display the 'new computer form'.
     */
    public static Result create() {
        Form<Book> bookForm = form(Book.class);
        return ok(
            createForm.render(bookForm)
        );
    }
    
    /**
     * Handle the 'new computer form' submission 
     */
    public static Result save() {
        Form<Book> bookForm = form(Book.class).bindFromRequest();
        if(bookForm.hasErrors()) {
            return badRequest(createForm.render(bookForm));
        }
        bookForm.get().save();
        flash("success", "Book " + bookForm.get().bookName + " has been created");
        return GO_HOME;
    }
    
    /**
     * Handle computer deletion
     */
    public static Result delete(Long id) {
        Book.find.ref(id).delete();
        flash("success", "Book has been deleted");
        return GO_HOME;
    }
    

}
            
