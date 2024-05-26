package br.com.livraria.servlet;

import br.com.livraria.model.ItemCarrinho;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;

public class UpdateCartServletTest extends Mockito {

    @Test
    public void testDoPost() throws Exception {
        // Mock objects
        UpdateCartServlet servlet = new UpdateCartServlet();
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        HttpSession session = mock(HttpSession.class);
        PrintWriter writer = mock(PrintWriter.class);

        // Create a mock cart
        List<ItemCarrinho> carrinho = new ArrayList<>();
        ItemCarrinho item = new ItemCarrinho(1, "Livro Teste", 10.0, 2);
        carrinho.add(item);

        // Define behaviors
        when(request.getSession()).thenReturn(session);
        when(session.getAttribute("carrinho")).thenReturn(carrinho);
        when(request.getParameter("id")).thenReturn("1");
        when(request.getParameter("action")).thenReturn("increase");
        when(response.getWriter()).thenReturn(writer);

        // Execute the servlet method
        servlet.doPost(request, response);

        // Verify results
        verify(writer).write("{\"newQuantity\":3}");
        assert(item.getQuantidade() == 3);
    }
}