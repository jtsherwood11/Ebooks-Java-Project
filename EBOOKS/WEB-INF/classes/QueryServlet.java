
import java.sql.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import javax.servlet.*;
import javax.servlet.http.*;

public class QueryServlet extends HttpServlet {

    private String databaseURL, username, password;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        ServletContext context = config.getServletContext();
        databaseURL = context.getInitParameter("databaseURL");
        username = context.getInitParameter("username");
        password = context.getInitParameter("password");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();

        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException ex) {
        }
        try {


        	Connection conn;
        	Statement stmt;
        	conn = DriverManager.getConnection(databaseURL, username, password);
	        stmt = conn.createStatement();
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Books</title>");
            out.println("<style type = 'text/css'>");
            out.println("body { margin-left: auto; margin-right: auto; width: 70%; background-color:#DADACA; padding:3em;}");
            out.println("ul {list-style: square inside;}");
            out.println("p { font-size: 18px;} </style>");
            out.println("</head>");
            out.println("<body>");
            out.println("<p style='color:darkred; font-Family: serif; font-size:3.0em'>Programming Books</p>");
            out.println("<p style='color: #000009;font-family: sans; font-size:1.6em'>Languages</p>");

            out.println("<form method='post' action='query' >");
            out.println("<input type='checkbox' name='DATA[]' value='C' />C");
            out.println("<input type='checkbox' name='DATA[]' value='Java' />Java");
            out.println("<input type='checkbox' name='DATA[]' value='Javascript' />Javascript");
            out.println("<input type='checkbox' name='DATA[]' value='HTML5' />HTML5");
            out.println("<input type='submit' name='submit'  />");
            out.println("</form>");
            String [] language = request.getParameterValues("DATA[]");

            out.println("<br><br><p style='font-family:monospaced;font-size:1em; padding-left:5em'> -- OR -- </p>");
            Statement stmt2 = conn.createStatement();
            String sqlStr = "SELECT author from books";
            ResultSet rset = stmt2.executeQuery(sqlStr);
            out.println("<form method='post' action='author'>");
            out.println("<fieldset><legend> Choose an Author: </legend><select name='author' size='1'>");
            out.println("<option value=''>Select...</option>");  // no­selection
            while (rset.next()) {
                String author = rset.getString("author");
                out.println("<option value='" + author + "'>" + author + "</option>");
            }
            out.println("</select>");
            out.println("<input type='submit' name='author' value='author' /></fieldset></form>");


            if(!language[0].isEmpty()) {
                out.println("<br><ul>");
                for (String language1 : language) {
                    String sql = "SELECT * FROM books WHERE language = '" + language1 + "'";
                    ResultSet rs = stmt.executeQuery(sql);
                    while (rs.next()) {
                       out.println("<li><a href='" + rs.getString(5) + "' download>" + rs.getString(2) + "</a> - " + rs.getString(3) + "</li>");
                    }
                }
                out.println("</ul>");
            }

           out.println("</body>");
           out.println("</html>");

        } catch (SQLException se) {
        }

    }    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        doGet(request, response);

    }
}
/*
    public MyPdfServlet extends HttpServlet {
  
  	protected doGet(HttpServletRequest req, HttpServletResponse resp){
        	 OutputStream os = resp.getOutputStream();
        	 resp.setContentType("Application/x-pdf");
        	 os.write(yourMethodToGetPdfAsByteArray());
    } 

}
*/
