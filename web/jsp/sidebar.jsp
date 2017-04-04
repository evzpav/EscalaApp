<!DOCTYPE html>
<%@ page contentType="text/html; charset=UTF-8" %>

<div class="container">
    <div class="row">
      <div class="col-sm-3 sidebar">
        <nav class="sidebar-nav">
          <div class="sidebar-header">
            <button class="nav-toggler nav-toggler-sm sidebar-toggler" type="button" data-toggle="collapse" data-target="#nav-toggleable-sm">
              <span class="sr-only">Toggle nav</span>
            </button>
            <a class="sidebar-brand img-responsive" href="../index.html">
            <!--   <span class="icon icon-leaf sidebar-brand-icon"></span> -->
            </a>
          </div>

          <div class="collapse nav-toggleable-sm" id="nav-toggleable-sm">
            <form class="sidebar-form">
              <input class="form-control" type="text" placeholder="Busca...">
              <button type="submit" class="btn-link">
                <span class="icon icon-magnifying-glass"></span>
              </button>
            </form>
            <ul class="nav nav-pills nav-stacked">
              <li class="nav-header">gráficos</li>
              <li class="active">
                <a href="TimelineServlet">Escala</a>
              </li>
            
                  

              <li class="nav-header">Cadastros</li>
              
              <li >
                <a href="EmployeeServlet">Funcionários</a>
              </li>
              
                           
           
            </ul>
            <hr class="visible-xs m-t">
          </div>
        </nav>
      </div>