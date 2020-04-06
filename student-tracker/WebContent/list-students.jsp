<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html>
<html>
<head>
<title>Student Tracker Application</title>
<link type="text/css" rel="stylesheet" href="css/style.css">

</head>

<body>

  <div id="Wrapper">
   <div id="header">
     <h2>Students List</h2>   
   </div>
  </div>
  
  <div id="container">
   <div id="content">
   
   <input type="button" value="Add Student"
         onclick="window.location.href='add-student-form.jsp';return false;"
         class="add-student-button"
   /> 
   
     <table>
      <tr>
      
       <th>FirstName</th>
       <th>LastName</th>
       <th>Email</th>
       <th>Action</th>
            
      </tr>
      
      <c:forEach var="tempStudent" items="${Student_List}">
           
          <c:url var="templink" value="StudentControllerServlet">
           <c:param name="command" value="LOAD"/> 
           <c:param name="studentId" value="${tempStudent.id}"/>        
           </c:url>
      
         <c:url var="deletelink" value="StudentControllerServlet">
           <c:param name="command" value="DELETE"/> 
           <c:param name="studentId" value="${tempStudent.id}"/>        
           </c:url>
      
          <tr>
           <td>${tempStudent.firstname}</td>
           <td>${tempStudent.lastname}</td>
           <td>${tempStudent.email}</td>
           <td> <a href="${templink}">Update</a>
               |<a href="${deletelink}"
                onclick="if (!(confirm('Are u sure You Want to Delete?')))return false">
                Delete</a>
            </td>
          </tr>     
      
     </c:forEach>
      
     </table>  
   
    </div>
  </div> 


</body>

</html>