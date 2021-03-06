package controllers;

import play.Routes;
import play.mvc.*;
import play.data.*;
import static play.data.Form.*;
import views.html.*;
import views.html.books.list;
import models.*;

/**
 * Manage a database of computers
 */

public class Application extends Controller {
    
    /**
     * This result directly redirect to application home.
     */
    public static Result GO_HOME = redirect(
        routes.Books.list(0, "", "", "")
    );
    
    public static class Login {
        
        public String email;
        public String password;
        
        public String validate() {
            if(User.authenticate(email, password) == null) {
                return "Invalid user or password";
            }
            return null;
        }        
    }
    
    /**
     * Login page.
     */
    public static Result login() {
        return ok(
            login.render(form(Login.class))
        );
    }
    
    /**
     * Handle login form submission.
     */
    public static Result authenticate() {
        Form<Login> loginForm = form(Login.class).bindFromRequest();
        if(loginForm.hasErrors()) {
            return badRequest(login.render(loginForm));
        } else {
        	
            session("email", loginForm.get().email);
//            return redirect(
//                routes.Projects.index()
//            );
            return Books.list();
        }
    }

    /**
     * Logout and clean the session.
     */
    public static Result logout() {
        session().clear();
        flash("success", "You've been logged out");        
        return Books.list();
        
    }
    
    
    
    /**
     * Handle default path requests, redirect to computers list
     */
    public static Result index() {
        return GO_HOME;
    }
    
    public static Result top() {    	
    	return ok(
    			top.render(
    	                Book.getTopBooks()
    	            )
            );
    }
    
}
            
