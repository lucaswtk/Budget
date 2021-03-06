package br.com.cpsoftware.budget.control;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.com.cpsoftware.budget.dao.OrcamentoDAO;
import br.com.cpsoftware.budget.dao.ProjetoDAO;
import br.com.cpsoftware.budget.model.Orcamento;
import br.com.cpsoftware.budget.model.Usuario;
import br.com.cpsoftware.budget.util.GerarEstruturaAdmin;
import br.com.cpsoftware.budget.util.GerarEstruturaAcademica;

@SuppressWarnings("serial")
public class CadastrarOrcamento extends HttpServlet {
	
	private OrcamentoDAO dao = new OrcamentoDAO();
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
	   
		Usuario gerente = (Usuario) req.getSession().getAttribute("usuario");
		
		req.setAttribute("projetos", new ProjetoDAO().getProjetos(gerente.getId()));

		req.setAttribute("page", "criarOrcamento");
	    req.getRequestDispatcher("/WEB-INF/base.jsp").forward(req, resp);
		
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		Long projetoId = Long.parseLong((String) req.getSession().getAttribute("projetoEditavel"));
		
		String nome = req.getParameter("nome");
				 
		Orcamento orcamento = new Orcamento(
			projetoId, 
			nome, 
			0d, //valorEstimado
			0d, // valorOrcado
			0d, // valorParcial
			0d, // valorComprovado
			Orcamento.STATUS_ELABORACAO
		);
		
		System.out.println("Projeto_Id - " + projetoId);
		System.out.println("Nome - " + orcamento.getNome());
		System.out.println("Valor - " + orcamento.getValorEstimado());
		
		
		Long orcamentoId = this.dao.create(orcamento);
		
		Orcamento aux = (Orcamento) this.dao.read(orcamentoId);
		System.err.println("Nome do orcamento no banco - " + aux.getNome());
		System.err.println("Valor do orcamento no banco - " + aux.getValorEstimado());
		
		
		int modelo = Integer.parseInt(req.getParameter("modelo"));
		if(modelo == 2) {
			GerarEstruturaAdmin.gerarCategorias(orcamentoId);
		} else if(modelo == 3) {
			GerarEstruturaAcademica.gerarCategorias(orcamentoId);
		}
		
		resp.sendRedirect("/listarOrcamentos");
		
	    /*try {
	        Long id = dao.create(orcamento);
	        resp.sendRedirect("/read?id=" + id.toString());   // read what we just wrote
	      } catch (Exception e) {
	        throw new ServletException("Erro criando orcamento", e);
	      }*/
	}
}
