package br.com.livraria.servlet;

import br.com.livraria.model.Usuario;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import br.com.livraria.dao.UserDAO;

import static org.mockito.Mockito.*;

public class LoginServletTest {

    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpServletResponse response;

    @Mock
    private HttpSession session;

    @Mock
    private UserDAO userDAO;

    @InjectMocks
    private LoginServlet loginServlet;

    @Before
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        when(request.getSession()).thenReturn(session);
    }

    @Test
    public void testDoPost_UsuarioValidoAdmin() throws ServletException, IOException {
        when(request.getParameter("email")).thenReturn("admin@example.com");
        when(request.getParameter("senha")).thenReturn("password");
        when(userDAO.verifyCredentials(any(Usuario.class))).thenReturn(true);
        when(userDAO.isAdmin(any(Usuario.class))).thenReturn(true);

        loginServlet.doPost(request, response);

        verify(session).setAttribute("loggedUser", "admin@example.com");
        verify(session).setAttribute("isAdmin", true);
        verify(response).sendRedirect("inicioADM.html");
    }

    @Test
    public void testDoPost_UsuarioValidoNaoAdmin() throws ServletException, IOException {
        when(request.getParameter("email")).thenReturn("user@example.com");
        when(request.getParameter("senha")).thenReturn("password");
        when(userDAO.verifyCredentials(any(Usuario.class))).thenReturn(true);
        when(userDAO.isAdmin(any(Usuario.class))).thenReturn(false);

        loginServlet.doPost(request, response);

        verify(session).setAttribute("loggedUser", "user@example.com");
        verify(response).sendRedirect("inicio.jsp");
    }

    @Test
    public void testDoPost_UsuarioInvalido() throws ServletException, IOException {
        when(request.getParameter("email")).thenReturn("invalid@example.com");
        when(request.getParameter("senha")).thenReturn("password");
        when(userDAO.verifyCredentials(any(Usuario.class))).thenReturn(false);

        loginServlet.doPost(request, response);

        verify(response).sendRedirect("index.html?error=invalid");
    }
}