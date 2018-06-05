<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<script src="../js/script.js"></script>
 	
<div>
    <div class="container-fluid">
      <!-- Breadcrumbs-->
      <ol class="breadcrumb">
        <li class="breadcrumb-item">
          <c:if test="${not empty orcamentoSelecionado}">
	          <a href="">
				${orcamentoSelecionado}
	          </a>
          </c:if>
        </li>
        <c:if test="${sessionScope.usuario.perfil == 0 || sessionScope.usuario.perfil == 1 }"><!--PERFIL_ADMIN || PERFIL_GERENTE-->
	        <li class="breadcrumb-item active">
	        	<a href="/listarProjetos">Projetos</a>
	        </li>
        </c:if>
        <li class="breadcrumb-item active">
        	<a href="/listarOrcamentos">Orçamentos</a>
        </li>
        <li class="breadcrumb-item active">
        	<a href="/listarCategorias">Categorias</a>
        </li>
        <li class="breadcrumb-item active">
        	<a href="/listarRubricas">Rubricas</a>
        </li>
        <li class="breadcrumb-item active">
        	<a href="/listarItens">Itens</a>
        </li>
      </ol>
    </div>
      
   	<div class="card mb-3">
   	    <div class="card-header">
        	<i class="fa fa-area-chart"></i> Cadastrar itens
        </div>
	   	<div class="card-body">
			<form action="cadastrarItem" method="POST" enctype="multipart/form-data" >
			   <div class="form-group">
			     <label for="text">Item:</label> <input type="text"
			     class="form-control" placeholder="Fornecer o nome do novo item"
			     name="nome" required="required">
			   </div>
			   <div class="form-group">
			     <label for="text">Unidade de medida:</label>
			     <select class="form-control" name="unidade_medida">
			      <option value="0">Verba</option>
			      <option value="1">Unidade</option>
			      <option value="2">Mês</option>
			      <option value="3">Quilograma</option>
			      <option value="4">Metro</option>
			      <option value="5">Litro</option>
			    </select>
			  </div>
			  <div class="form-group">
			   <label for="text">Descrição:</label> <input type="text"
			   class="form-control" placeholder="Fornecer a descrição do item"
			   name="descricao" required="required">
			 </div>
			 <div class="form-group">
			   <label for="text">Quantidade:</label> <input type="text"
			   class="form-control" placeholder="Fornecer a quantidade de itens"
			   name="quantidade" required="required" id="quantidadeItem" onblur="calcularValorTotalItem()">
			 </div>
			 <div class="form-group">
			   <label for="text">Valor:</label> <input type="text"
			   class="form-control" placeholder="Fornecer o valor uniforme do item em reais"
			   name="valor_uniforme" required="required" id="valorUniformeItem" onblur="calcularValorTotalItem()">
			 </div>
			 <div class="form-group">
			   <label for="text">Valor total:</label> <input type="text" readonly="readonly" placeholder="Valor total da soma dos itens" class="form-control" id="resultado">
			 </div>
			 
			 <div class="row">
			  <div class="form-group col-lg-6">
			    <label for="text">Detalhes</label> <input type="file"
			    class="form-control" placeholder="Fornecer detalhes do item"
			    name="arquivo_detalhes" accept=".pdf">
			  </div>
			  <div class="form-group col-lg-6">
			    <label for="text">Auxiliar</label> <input type="file"
			    class="form-control" placeholder="Fornecer arquivo auxiliar"
			    name="arquivo_auxiliar" accept=".pdf">
			  </div>
			</div>
			     <%-- <div class="form-group">
			     	 <label>Orçamento:</label>
			     	 	<select class="form-control" name="orcamento">
								<c:forEach items="${orcamentos}" var="orcamento">
									<option>${orcamento.nome}</option>
								</c:forEach>
						</select>
					</div> --%>
			<div class="row">
			     <div class="form-group col-lg-6">
			       <label>Categoria:</label>
			       <select id="categoria-select" class="form-control" name="categoria">
		         		<option value="">Selecione</option>
		         		<c:forEach items="${categorias}" var="categoria">
			        		 <option value="${categoria.id }">${categoria.nome}</option>
		       			</c:forEach>
			     	</select>
		   		 </div>
			
			     <div class="form-group col-lg-6">
				     <label>Rubrica:</label>
				     <select id="rubrica-select" class="form-control" name="rubrica_id">
				     	<option value="">Selecione uma categoria acima</option>
		       			<%-- <c:forEach items="${rubricas}" var="rubrica">
				      	 	<option value="${rubrica.id }">${rubrica.nome}</option>
			    		 </c:forEach> --%>
				   	</select>
				 </div>
			</div>  
			<button type="submit" class="btn btn-primary botaoCadastro">Cadastrar</button>
			</form>
	  	</div>
  	</div>
</div>

<script>
      $("#categoria-select").change(function() {
          var id = $(this).val();
          if(id){
        	  $.get("escolherRubrica", {
  				categoriaId : id
             }, function(responseJson) {
  	              var $rubricaSelect = $("#rubrica-select");
  	              $rubricaSelect.empty(); 
  	              $.each(responseJson, function(index, rubrica) {
  	                  $("<option>").val(rubrica.id).text(rubrica.nome).appendTo($rubricaSelect);
  	             	});                   
         		});
          }
      });
</script>
