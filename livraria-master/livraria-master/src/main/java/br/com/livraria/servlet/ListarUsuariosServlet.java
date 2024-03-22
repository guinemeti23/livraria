package br.com.livraria.servlet;

import br.com.livraria.dao.UserDAO;
import br.com.livraria.model.Usuario;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/ListaUsuarioServlet")
public class ListaUsuarioServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");

        if (action == null) {
            listarUsuarios(req, resp);
        } else if (action.equals("alterar")) {
            String id = req.getParameter("id");
            // Implemente o redirecionamento para a página de alteração com base no ID
        } else if (action.equals("inativarReativar")) {
            String id = req.getParameter("id");
            inativarReativarUsuario(id);
            resp.sendRedirect("ListaUsuarioServlet");
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String filtroNome = req.getParameter("filtroNome");
        if (filtroNome != null && !filtroNome.isEmpty()) {
            List<Usuario> usuariosFiltrados = UserDAO.buscarUsuariosPorNome(filtroNome);
            req.setAttribute("usuarios", usuariosFiltrados);
        } else {
            listarUsuarios(req, resp);
        }
        req.getRequestDispatcher("listaUsuarios.jsp").forward(req, resp);
    }

    private void listarUsuarios(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<Usuario> usuarios = UserDAO.listarUsuarios();
        req.setAttribute("usuarios", usuarios);
        req.getRequestDispatcher("listaUsuarios.jsp").forward(req, resp);
    }

    private void inativarReativarUsuario(String id) {
        // Recupera o usuário com base no ID
        Usuario usuario = UserDAO.buscarUsuarioPorId(id);
    
        if (usuario != null) {
            // Inverte o status do usuário
            boolean novoStatus = !usuario.isAtivo();
            usuario.setAtivo(novoStatus);
    
            // Atualiza o usuário no banco de dados
            boolean atualizacaoBemSucedida = UserDAO.atualizarUsuario(usuario);
    
            if (atualizacaoBemSucedida) {
                System.out.println("Usuário com ID " + id + " foi " + (novoStatus ? "reativado" : "inativado") + " com sucesso.");
            } else {
                System.out.println("Falha ao inativar/reativar o usuário com ID " + id + ".");
            }
        } else {
            System.out.println("Usuário com ID " + id + " não encontrado.");
        }
    }
    

}


