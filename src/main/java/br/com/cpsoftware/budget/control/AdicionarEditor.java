package br.com.cpsoftware.budget.control;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import br.com.cpsoftware.budget.dao.OrcamentoDAO;
import br.com.cpsoftware.budget.dao.OrcamentoUsuarioDAO;
import br.com.cpsoftware.budget.dao.UsuarioDAO;
import br.com.cpsoftware.budget.model.OrcamentoUsuario;
import br.com.cpsoftware.budget.model.Usuario;

@SuppressWarnings("serial")
public class AdicionarEditor extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		if(req.getParameter("orcamento") != null) { //Quando o gerente clica no botao de AdicionarEditor na tela de listarOrcamento;
			Long orcamentoId = Long.parseLong(req.getParameter("orcamento"));
			req.getSession().setAttribute("orcamentoEditavel", orcamentoId.toString());
		}// O parameter é null quando a página é recarregada depois de adicionarEditor, e já tem um orcamentoEditavel na sessão;
		
		Long orcamentoEditavelId = Long.parseLong((String) req.getSession().getAttribute("orcamentoEditavel"));
		
		List<OrcamentoUsuario> orcamentoUsuarioLista = new OrcamentoUsuarioDAO().getOrcamentoUsuarioByOrcamentoId(orcamentoEditavelId);
		List<Usuario> usuarios = new ArrayList<Usuario>();
		for(OrcamentoUsuario orcamentoUsuario : orcamentoUsuarioLista) {
			usuarios.add(new UsuarioDAO().read(orcamentoUsuario.getEditorId()));
		}
		
		req.setAttribute("editoresCadastrados", usuarios);
		req.setAttribute("orcamento", new OrcamentoDAO().read(orcamentoEditavelId).getNome());
		
		req.setAttribute("confirmacao", req.getSession().getAttribute("confirmacao"));
		req.getSession().setAttribute("confirmacao", null);

		req.setAttribute("usuarioAdicionado", req.getSession().getAttribute("usuarioAdicionado"));
		req.getSession().setAttribute("usuarioAdicionado", null);

		req.setAttribute("page", "adicionarEditor");
		req.getRequestDispatcher("/WEB-INF/base.jsp").forward(req, resp);
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		Long orcamentoEditavelId = Long.parseLong((String) req.getSession().getAttribute("orcamentoEditavel"));
		
		String email = req.getParameter("email");
		
		Usuario usuario = new UsuarioDAO().getUsuarioByEmail(email);

		List<OrcamentoUsuario> orcamentoUsuarioLista = new OrcamentoUsuarioDAO().getOrcamentoUsuarioByOrcamentoId(orcamentoEditavelId);
		List<Usuario> usuarios = new ArrayList<Usuario>();

		boolean emailExistente = false;

		for(OrcamentoUsuario orcamentoUsuario : orcamentoUsuarioLista) {
			usuarios.add(new UsuarioDAO().read(orcamentoUsuario.getEditorId()));
		}
		
		req.getSession().setAttribute("confirmacao", "confirmacao");
		if(usuario == null) {
			System.out.println("Usuario adicionado null");
			req.getSession().setAttribute("usuarioAdicionado", usuario);
		}else {
			for(Usuario usuarioAux: usuarios) {
				if(usuario.getEmail().equals(usuarioAux.getEmail())) {
					emailExistente = true;
				}
			}
			
			if(!emailExistente) {
				OrcamentoUsuario orcamentoUsuario = new OrcamentoUsuario(usuario.getId(), orcamentoEditavelId);
				new OrcamentoUsuarioDAO().create(orcamentoUsuario);
				req.getSession().setAttribute("usuarioAdicionado", usuario);
			}else {
				//TODO mensagem de email duplicado, "usuario ja esta no projeto"
			}
		}
		
		resp.sendRedirect("/adicionarEditor");
	}
	
}
