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

@WebServlet("/ListarUsuariosServlet")
public class ListarUsuariosServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<Usuario> usuarios = new UserDAO().listarUsuarios();


        if (usuarios.isEmpty()) {

            System.out.println("A lista de usuários está vazia.");
        } else {
            System.out.println("Lista de Usuários 2:");
            for (Usuario usuario : usuarios) {
                System.out.println("ID: " + usuario.getId());
                System.out.println("Nome: " + usuario.getNome());
                System.out.println("CPF: " + usuario.getCpf());
                System.out.println("Email: " + usuario.getEmail());
                System.out.println("Grupo: " + usuario.getGrupo());
                System.out.println("Status: " + (usuario.isAtivo() ? "Ativo" : "Inativo"));
                System.out.println();
            }

            req.setAttribute("usuarios", usuarios);
            req.getRequestDispatcher("listarUsuarios.jsp").forward(req, resp);
        }
    }
}