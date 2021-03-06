package br.com.cpsoftware.budget.control;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.com.cpsoftware.budget.dao.ProjetoDAO;
import br.com.cpsoftware.budget.model.Projeto;
import br.com.cpsoftware.budget.model.Usuario;

@SuppressWarnings("serial")
public class CadastrarProjeto extends HttpServlet {

	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		 req.setAttribute("page", "criarProjeto");
		 req.getRequestDispatcher("/WEB-INF/base.jsp").forward(req, resp);
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		ProjetoDAO dao = new ProjetoDAO();
		
		Usuario usuario = (Usuario) req.getSession().getAttribute("usuario");
		
		String nome = req.getParameter("nome");
		
		/*Do tempo em que se colocava o valor estimado no cadastro
		Double valor;
		try {
			valor = Double.parseDouble(req.getParameter("valor"));
		}catch (NumberFormatException e) {
			valor = 0d;
			e.printStackTrace();
		}*/
		
		Projeto projeto = new Projeto(
			usuario.getId(),
			nome,
			0d, //valorEstimado
			0d,// valorOrcado
			0d, // valorParcial
			0d // valorComprovado
		);
		
		System.out.println("Gerente_Id - " + usuario.getId());
		System.out.println("Nome - " + projeto.getNome());
		System.out.println("Valor - " + projeto.getValorEstimado());
		
		
		Long projetoId = dao.create(projeto);
		
		Projeto aux = (Projeto) dao.read(projetoId);
		System.err.println("Nome do projeto no banco - " + aux.getNome());
		System.err.println("Valor do projeto no banco - " + aux.getValorEstimado());
		
		
		resp.sendRedirect("/listarProjetos");
	}
	
}
