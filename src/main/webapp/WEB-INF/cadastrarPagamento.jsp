<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>

<div>
  <div class="container">
    <h2>Cadastro de pagamento</h2>
    <br/>
    <form action="cadastrarPagamento" method="POST" enctype="multipart/form-data">
      <div class="form-group">
      	<input type="hidden" name="nota_fiscal_id" value="${nota_fiscal_id }">
        <label for="text">Arquivo</label> <input type="file"
        class="form-control" placeholder="Fornecer o arquivo da nota"
        name="arquivo" required="required" accept=".pdf">
      </div>
      <div class="row">
        <div class="form-group col-lg-6">
          <label for="text">Valor</label> <input type="text"
          class="form-control" placeholder="Fornecer o valor da nota"
          name="valor" required="required">
        </div>


        <div class="form-group col-lg-6">
          <label for="text">Data</label> <input type="date"
          class="form-control" placeholder="Fornecer a data de emissão da nota"
          name="data" required="required">
        </div>
      </div>
      
      <br/>
      <button type="submit" class="btn btn-dark">Cadastrar</button> 
    </form>
  </div>
</div>