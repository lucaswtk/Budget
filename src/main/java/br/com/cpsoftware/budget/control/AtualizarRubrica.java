package br.com.cpsoftware.budget.control;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.com.cpsoftware.budget.dao.CategoriaDAO;
import br.com.cpsoftware.budget.dao.OrcamentoDAO;
import br.com.cpsoftware.budget.dao.RubricaDAO;
import br.com.cpsoftware.budget.model.Categoria;
import br.com.cpsoftware.budget.model.Orcamento;
import br.com.cpsoftware.budget.model.Rubrica;
import br.com.cpsoftware.budget.util.AtualizarValoresComprovados;
import br.com.cpsoftware.budget.util.AtualizarValoresEstimados;
import br.com.cpsoftware.budget.util.AtualizarValoresOrcados;
import br.com.cpsoftware.budget.util.AtualizarValoresRealizados;


@SuppressWarnings("serial")
public class AtualizarRubrica extends HttpServlet {
	
	private RubricaDAO rubricaDao = new RubricaDAO();
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		Long orcamentoEditavelId = Long.parseLong((String) req.getSession().getAttribute("orcamentoEditavel"));
		Orcamento orcamento = (Orcamento) new OrcamentoDAO().read(orcamentoEditavelId);
		List<Categoria> categorias = new ArrayList<>();
		
		for(Categoria categoria : new CategoriaDAO().getCategorias(orcamento.getId())) {
			categorias.add(categoria);
		}

		Long rubricaId = Long.parseLong(req.getParameter("rubricaId"));
		Rubrica rubrica = (Rubrica) this.rubricaDao.read(rubricaId);
		
		req.setAttribute("orcamentoSelecionado", orcamento.getNome());
		req.setAttribute("categoriaAtualId", rubrica.getCategoriaId());
		req.setAttribute("categorias", categorias);
		req.setAttribute("rubrica", rubrica);
		req.setAttribute("page", "atualizarRubrica");           
		req.getRequestDispatcher("/WEB-INF/base.jsp").forward(req, resp);
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		Long categoriaId = Long.parseLong(req.getParameter("categoriaId"));
		Long rubricaId = Long.parseLong(req.getParameter("rubricaId"));
		String nome = req.getParameter("nome");
		int codigo = Integer.parseInt(req.getParameter("codigo"));
		
		Rubrica rubrica = (Rubrica) this.rubricaDao.read(rubricaId);
		
		Long categoriaAnterior = rubrica.getCategoriaId();
		
		rubrica.setCategoriaId(categoriaId);
		rubrica.setNome(nome);
		rubrica.setCodigo(codigo);
		
		//TODO Fazer classe util.Mover ??
		
		if(!categoriaAnterior.equals(rubrica.getCategoriaId())) {
			
			AtualizarValoresOrcados.atualizarPrecoCategoria(
				AtualizarValoresOrcados.EXCLUIR,
				categoriaAnterior,
				rubrica.getValorOrcado()
			);
			
			//Atualizo a categoria anterior, excluindo o valor parcial da rubrica
			AtualizarValoresRealizados.atualizarPrecoCategoria(
				AtualizarValoresRealizados.EXCLUIR,
				categoriaAnterior,
				rubrica.getValorRealizado()
			);
			
			AtualizarValoresComprovados.atualizarPrecoCategoria(
        		AtualizarValoresComprovados.EXCLUIR,
        		categoriaAnterior,
        		rubrica.getValorComprovado()
    		);
		}
		
		this.rubricaDao.update(rubrica);
		
		if(!categoriaAnterior.equals(rubrica.getCategoriaId())) {
			//Atualizar os valores estimados, se a Rubrica for movido para outra Categoria
			AtualizarValoresEstimados.atualizarValorEstimadoCategoria(categoriaAnterior);
			AtualizarValoresEstimados.atualizarValorEstimadoCategoria(rubrica.getCategoriaId());
			
			
			AtualizarValoresOrcados.atualizarPrecoCategoria(
				AtualizarValoresOrcados.EDITAR,
				rubrica.getCategoriaId(),
				rubrica.getValorOrcado()
			);
			
			//Atualizo a nova categoria, adicionando o valor parcial da rubrica
			AtualizarValoresRealizados.atualizarPrecoCategoria(
				AtualizarValoresRealizados.EDITAR,
				rubrica.getCategoriaId(),
				rubrica.getValorRealizado()
			);
			
			AtualizarValoresComprovados.atualizarPrecoCategoria(
        		AtualizarValoresComprovados.EDITAR,
        		rubrica.getCategoriaId(),
				rubrica.getValorComprovado()
    		);
		}/* else {
			AtualizarValoresEstimados.atualizarValorEstimadoCategoria(rubrica.getCategoriaId());
		}*/
		
		
		
		
		resp.sendRedirect("/listarRubricas");
		
	}
	
}
