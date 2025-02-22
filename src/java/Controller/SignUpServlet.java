/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Controller;

import dao.UserDAO;
import entity.User;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 *
 * @author Wind
 */
@WebServlet(name = "SignUp", urlPatterns = {"/signup"})
public class SignUpServlet extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");

    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.getRequestDispatcher("login.jsp").forward(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            String user_name = request.getParameter("username");
            String email = request.getParameter("email");
            String pass = request.getParameter("pass");
            String re_pass = request.getParameter("repass");

//            System.out.println("Username: " + user_name);
//            System.out.println("Email: " + email);
//            System.out.println("Password: " + pass);
//            System.out.println("Re-entered Password: " + re_pass);
            
            if (!pass.equals(re_pass)) {
                request.setAttribute("mess", "Registration failed!!");
                request.getRequestDispatcher("login.jsp").forward(request, response);
            } else {
                UserDAO dao = new UserDAO();
                User a = dao.checkUserExist(user_name);
                User b = dao.checkEmailExist(email);

                if (a == null && b == null) {
                    // dc signUp
                    User user = new User(user_name, email, re_pass);
                    dao.insert(user);
                    request.setAttribute("mess", " Registration Success!! Sign in again");
                    request.getRequestDispatcher("login.jsp").forward(request, response);
                    
                } else {
                    request.setAttribute("mess", " Registration failed!!");
                    request.getRequestDispatcher("login.jsp").forward(request, response);
                }
            }
        } catch (Exception e) {
        }
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
