package br.com.livraria.servlet;

import br.com.livraria.dao.ClienteDAO;
import br.com.livraria.model.Endereco;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

@WebServlet("/ProcessarEndereco")
public class ProcessarEnderecoServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();

        try {
            int enderecoId = Integer.parseInt(request.getParameter("enderecoId"));


            Endereco enderecoSelecionado = new ClienteDAO().listarEnderecoEntrega(enderecoId);
            session.setAttribute("enderecoSelecionado", enderecoSelecionado);
            session.setAttribute("enderecoId", enderecoId);

            response.sendRedirect("pagamento.jsp");
        } catch (NumberFormatException e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "ID de endereço inválido");
        } catch (IndexOutOfBoundsException e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_NOT_FOUND, "Endereço não encontrado");
        }
    }
}
