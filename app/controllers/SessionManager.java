package controllers;

import play.mvc.Controller;

public class SessionManager  {

	public enum TermDef{
		UserEmail("email"),
		;
		
		private String _name;
		TermDef(String name){
			_name = name;
		}
		
		public String getName(){
			return _name;
		}
	}
	
	public static String getUserEmail(){		
		return Controller.ctx().session().get(TermDef.UserEmail.getName());
	}
	
	public static boolean logined(){
		return getUserEmail()!=null;
	}
	
	public static void putValue(TermDef term,String value){
		Controller.ctx().session().put(term.getName(),value);
	}
}
