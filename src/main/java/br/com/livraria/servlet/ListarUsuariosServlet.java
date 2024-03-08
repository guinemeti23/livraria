package br.com.livraria.servlet;

import br.com.livraria.model.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import java.util.List;

@Controller
@RequestMapping
public class ListarUsuariosServlet {

    @Autowired
    private ListarUsuariosServlet usuarioService;

    @GetMapping("/listar-usuarios")
    public String listarUsuarios(Model model, @RequestParam(required = false) String filtro) {
        List<Usuario> usuarios;

        if (filtro != null && !filtro.isEmpty()) {
            usuarios = usuarioService.buscarUsuariosPorNome(filtro);
        }


        model.addAttribute("usuarios", usuarios);

        return "listar-usuarios";
    }
}

