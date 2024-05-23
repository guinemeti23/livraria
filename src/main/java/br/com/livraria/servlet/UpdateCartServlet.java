package br.com.livraria.servlet;

import br.com.livraria.model.ItemCarrinho;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/updateCart")
public class UpdateCartServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        List<ItemCarrinho> carrinho = (List<ItemCarrinho>) session.getAttribute("carrinho");
        int id = Integer.parseInt(request.getParameter("id"));
        String action = request.getParameter("action");

        ItemCarrinho itemToRemoveOrUpdate = null;
        int newQuantity = 0;
        for (ItemCarrinho item : carrinho) {
            if (item.getLivroId() == id) {
                itemToRemoveOrUpdate = item;
                break;
            }
        }

        if (itemToRemoveOrUpdate != null) {
            if ("remove".equals(action)) {
                carrinho.remove(itemToRemoveOrUpdate);
            } else {
                int delta = "increase".equals(action) ? 1 : "decrease".equals(action) ? -1 : 0;
                newQuantity = itemToRemoveOrUpdate.getQuantidade() + delta;
                if (newQuantity > 0) {
                    itemToRemoveOrUpdate.setQuantidade(newQuantity);
                } else {
                    carrinho.remove(itemToRemoveOrUpdate);
                    newQuantity = 0;

                }
            }
        }

        session.setAttribute("carrinho", carrinho);
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write("{\"newQuantity\":" + newQuantity + "}");
    }
}
