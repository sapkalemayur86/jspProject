package com.web.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

public class StudentdbUtil {
	
	private DataSource dataSource;
	
	public StudentdbUtil(DataSource theDataSourse) {
		
		dataSource =theDataSourse;
	}
	 
	public List<Student> getStudent()throws Exception
	{
      List<Student> students =new ArrayList<>();
      
      Connection myConn=null;
      Statement myStmt=null;
      ResultSet myRs=null;
      
      try {
    	  
    	  myConn=dataSource.getConnection();
    	  
    	   String sql="select * from student order by last_name";
    	  
    	 myStmt=myConn.createStatement();
    	 
    	 myRs=myStmt.executeQuery(sql);
    	  
    	 while(myRs.next()) {
    		 
    	 int id=myRs.getInt("id");
    	 String firstname=myRs.getString("first_name");
    	 String lastname=myRs.getString("last_name");
    	 String email=myRs.getString("email");
    	 
    	 Student tempStudent=new Student(id,firstname,lastname,email);
    	 
    	 students.add(tempStudent);
    	 }
    	  
    	  return students;  
      }
      finally {
    	  close(myConn,myStmt,myRs);
    	  
      }
      
      
    }

	private void close(Connection myConn, Statement myStmt, ResultSet myRs) {
		// TODO Auto-generated method stub
		
		try {
			
			if(myRs!=null) {
				myRs.close();
			}
			
			if(myStmt!= null) {
				myStmt.close();
			}
			
			if(myConn!=null) {
				myConn.close();
			}
			
		}catch(Exception exe) {
		
			exe.printStackTrace();
	 }
	}

	public void addStudent(Student theStudent) throws Exception {
		
		Connection myConn=null;
		PreparedStatement myStmt=null;
		
		try {
			myConn=dataSource.getConnection();
			
			String sql="insert into student"
					+"(first_name, last_name, email)" 
                    +"values(?,?,?)";		
			
			myStmt=myConn.prepareStatement(sql);
			
			myStmt.setString(1,theStudent.getFirstname());
			myStmt.setString(2,theStudent.getLastname());
			myStmt.setString(3,theStudent.getEmail());
			
			myStmt.execute();
			
		}finally {
			
			//clean upJDBC objects
			close(myConn,myStmt,null);
		}
		
	}

	public Student getStudent(String theStudentId) throws Exception{
		
		
		Student theStudent=null;
		
		Connection myConn=null;
		PreparedStatement myStmt=null;
		ResultSet myRs=null;
		
		int  studentId;
		try {
			//convert student id to int
			studentId=Integer.parseInt(theStudentId);
			//get Connection
			myConn=dataSource.getConnection();
 			
			//create SQL to select Student
			String sql="select * from student where id=?";
		   //create statement 
			myStmt=myConn.prepareStatement(sql);
			//setParameter
			myStmt.setInt(1, studentId);
			//execute Statement
			myRs=myStmt.executeQuery();
		   
			if(myRs.next()) {
				String firstname=myRs.getString("first_name");
				String lastname=myRs.getString("last_name");
				String email=myRs.getString("email");
				
				theStudent=new Student(studentId,firstname,lastname,email);
				
			}
			else {
				throw new Exception("could not find Student id"+ studentId);
			}
			return theStudent;	
			
		}finally {
			
			close(myConn,myStmt,myRs);
		}
		
		
		
	}

	public void updateStudent(Student theStudent)throws Exception {
	
		Connection myConn=null;
		PreparedStatement myStmt=null;
     try {
		myConn=dataSource.getConnection();
		
		String sql="update student "
				 +"set first_name=?, last_name=?, email=? "
				+"where id=?";
		myStmt=myConn.prepareStatement(sql);
		
		myStmt.setString(1,theStudent.getFirstname());
		myStmt.setString(2,theStudent.getLastname());
		myStmt.setString(3,theStudent.getEmail());
		myStmt.setInt(4, theStudent.getId());
		
		myStmt.execute();
     	}finally {
	    	    close(myConn,myStmt,null);
	          }
		
	}

	public void deleteStudent(String theStudentId) 
	throws Exception{
		
		Connection myConn=null;
		PreparedStatement myStmt=null;
		
		try {
			
			int studentId=Integer.parseInt(theStudentId);
			myConn=dataSource.getConnection();
			String sql="delete from student where id=?";
			myStmt=myConn.prepareStatement(sql);
			myStmt.setInt(1, studentId);
			myStmt.execute();
		}finally {
			
			close(myConn,myStmt,null);
		}
	 
		
		
		
	}
}
 