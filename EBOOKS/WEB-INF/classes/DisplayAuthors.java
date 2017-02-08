
import java.sql.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import javax.servlet.*;
import javax.servlet.http.*;

public class DisplayAuthors extends HttpServlet {

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
            out.println("body { background-color:#DCDCCC; padding:3em;}");
            out.println("ul {list-style: square inside;}");
            out.println("p { font-size: 16pt;} </style>");
            out.println("</head>");
            out.println("<body>");

            String author = request.getParameter("author");
		
            if(!author.isEmpty()) {
                out.println("<ul>");
                String sql = "SELECT *  FROM books WHERE author = '" + author + "'";
                ResultSet rs = stmt.executeQuery(sql);
                while (rs.next()) {
                    out.println("<li><a href='" + rs.getString(5) + "'>" + rs.getString(2) + "</a> - " + rs.getString(3) + "</li>");
                }
                out.println("</ul>");
            }else{
                out.println("<p>ERROR</p>");
    
            }

            out.println("<a style='color:darkred' href='query'>Back to Select Menu</a>");
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

