package com.web.jdbc;

import java.io.IOException;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

/**
 * Servlet implementation class StudentControllerServlet
 */
@WebServlet("/StudentControllerServlet")
public class StudentControllerServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private StudentdbUtil studentdbUtil;
	
	@Resource(name="jdbc/student-tracker")
	 private DataSource dataSource;
	
	
	
	@Override
	public void init() throws ServletException {
		
		super.init();
		
		try {
			studentdbUtil=new StudentdbUtil(dataSource);
		 }catch(Exception exc) {
			throw new ServletException();
		}
		
	} 

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
              String theCommand=request.getParameter("command"); 
			  
               if(theCommand==null) {
            	   theCommand="LIST";
               }
			
		     switch(theCommand)
		     {
		      
		     case "LIST": listStudents(request,response); break;
		     
		     case "ADD" : addStudent(request,response);break;
		     
		     case "LOAD" :loadStudent(request,response);break;
		     
		     case "UPDATE" :updateStudent(request,response);break;
		     
		     case "DELETE" : deleteStudent(request,response);break;
		           
		     default: listStudents(request,response); 
		     }
			
		   }catch( Exception e) {
		
			throw new ServletException(e);
		}
	
	}

	private void deleteStudent(HttpServletRequest request, HttpServletResponse response) 
	throws Exception{
		
      String theStudentId=request.getParameter("studentId");
      
      studentdbUtil.deleteStudent(theStudentId);
      
      listStudents(request,response);
	} 

	private void updateStudent(HttpServletRequest request, HttpServletResponse response) throws Exception{
		
		int id=Integer.parseInt(request.getParameter("studentId"));
		String firstname=request.getParameter("firstname");
		String lastname=request.getParameter("lastname");
		String email=request.getParameter("email");
		
		Student theStudent=new Student(id,firstname,lastname,email);
		
		studentdbUtil.updateStudent(theStudent);
		listStudents(request,response);
	}

	private void loadStudent(HttpServletRequest request, HttpServletResponse response) 
	throws Exception{
		
		//read student id from FORMDATA 
		
		String theStudentId=request.getParameter("studentId");
		
		//get student from database
		Student theStudent=studentdbUtil.getStudent(theStudentId);
		//add student to request object
		request.setAttribute("THE_STUDENT",theStudent);
		//send to JSP page
		RequestDispatcher dispatcher=request.getRequestDispatcher("/update-student-form.jsp");
        dispatcher.forward(request, response);		
	}

	private void addStudent(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
        String firstname=request.getParameter("firstname");		
        String lastname=request.getParameter("lastname");
        String email=request.getParameter("email");
        
        Student theStudent=new Student(firstname,lastname,email);
        
        studentdbUtil.addStudent(theStudent);
        
        listStudents(request,response);
		
	}

	private void listStudents(HttpServletRequest request, HttpServletResponse response) throws Exception{
		 List<Student> students=studentdbUtil.getStudent();
		 
	     request.setAttribute("Student_List", students);	 
	      
	     RequestDispatcher dispatcher=request.getRequestDispatcher("/list-students.jsp");
		dispatcher.forward(request, response);
	}

}
