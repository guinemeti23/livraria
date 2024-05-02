package br.com.livraria.servlet;

import br.com.livraria.dao.LivroDAO;
import br.com.livraria.model.Livro;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.apache.commons.fileupload.servlet.ServletFileUpload.isMultipartContent;

@WebServlet("/CadastroLivro")
public class CadastroLivroServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/cadastroProduto.html").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            String nome = req.getParameter("nome");
            System.out.println("Valor do parâmetro 'nome': " + nome);

            double preco = Double.parseDouble(req.getParameter("valor"));
            System.out.println("Valor do parâmetro ' preco': " + preco);

            int quantidade = Integer.parseInt(req.getParameter("quantidade"));
            System.out.println("Valor do parâmetro 'qtd': " + quantidade);

            double avaliacao = Double.parseDouble(req.getParameter("avaliacao"));
            String descricao = req.getParameter("descricao");

            List<String> imagens = new ArrayList<>();

            DiskFileItemFactory factory = new DiskFileItemFactory();
            ServletFileUpload upload = new ServletFileUpload(factory);

            try {
                List<FileItem> items = upload.parseRequest(req);

                for (FileItem item : items) {
                    if (!item.isFormField()) {
                        String imageName = item.getName();
                        if (imageName != null && !imageName.isEmpty()) {
                            File uploadedFile = new File("Img/" + imageName);
                            item.write(uploadedFile);
                            imagens.add(imageName);
                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            Livro livro = new Livro(nome, quantidade, preco, descricao, avaliacao);

            LivroDAO livroDAO = new LivroDAO();
            livroDAO.cadastrarLivro(livro);

            resp.sendRedirect(req.getContextPath() + "/ListaProduto.jsp");
        } catch (Exception ex) {
            ex.printStackTrace();
            // Tratamento de erro - você pode redirecionar para uma página de erro ou retornar uma mensagem de erro para o usuário
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Ocorreu um erro durante o processamento da requisição.");
        }
    }




    private Map< String, String> uploadImage(HttpServletRequest httpServletRequest) {

        HashMap<String, String>  parameters = new HashMap<>();

        if (isMultipartContent(httpServletRequest)){
            try {
                DiskFileItemFactory diskFileItemFactory = new DiskFileItemFactory();
                List<FileItem> fileItems = new ServletFileUpload(diskFileItemFactory).parseRequest(httpServletRequest);

                for (FileItem item: fileItems){
                    checkFieldType(item, parameters);
                }

            }catch (Exception e){
                parameters.put("image", "img/default.jpg");
            }

            return  parameters;
        }

        return parameters;

    }

    private void checkFieldType(FileItem fileItem, Map requestParameters) throws Exception {

        if (fileItem.isFormField()){
            requestParameters.put(fileItem.getFieldName(), fileItem.getString());
        }else{
            String fileName = processUploadedFile(fileItem);
            requestParameters.put("image", fileName);
        }

    }

    private String processUploadedFile(FileItem fileItem) throws Exception {
        Long currentTime = new Date().getTime();

        String fileName = currentTime.toString().concat("-").concat(fileItem.getName().replace(" ",""));
        String filePath = this.getServletContext().getRealPath("img").concat(File.separator).concat(fileName);

        fileItem.write(new File(filePath));

        return fileName;
    }
}

