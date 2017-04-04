<!DOCTYPE html>
<%@ page contentType="text/html; charset=UTF-8" %>

<div id="modalChangePeriodOfWork" class="modal">
  <div class="modal-dialog modal-md">
    <div class="modal-content">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
	       <div>
	 	   		 	<h4 class="modal-title">Alterar horário</h4>
	 	   		  	       		  
	        
	         <div>
	          		<label id="employeeNameLabel"></label>
	         </div>
	         </div>
	      	 	  
      </div>
      <div class="modal-body">
      
       		
	    	 <label id="dayOfWeekLabel"></label>
	         <label id="dayLabel"></label>
	         
	        <div class="checkbox custom-control custom-checkbox">
			  <label>
			    <input id="workingCheckbox" type="checkbox">
			    <span class="custom-control-indicator"></span>
			    Trabalhando?
			  </label>
			</div>
	        
	       <div class="flextable">
			  <div class="flextable-item">
			    <p>Início<p>
			    <input id="startTimeInput" type="text" class="form-control">
			  </div>
			  
			  <div class="flextable-item">
			  <p>Início Intervalo<p>
			     <input id="intervalStartInput" type="text" class="form-control">
			   
			   </div>
			   
				<div class="flextable-item">
				<p>Fim Intervalo<p>
			     <input id="intervalEndInput" type="text" class="form-control">
			   
			   </div>
				<div class="flextable-item">
				<p>Fim<p>
			     <input id="endTimeInput" type="text" class="form-control">
			   
			    <input id="workingTimeIdHidden" type="hidden" class="form-control">
			    
			   </div>
			</div>
	        
	        
        
      </div>
      <div class="modal-actions">
       
        <button id="saveButton" type="button" class="btn-link modal-action" data-dismiss="modal">
          <strong>Salvar</strong>
        </button>
        
         <button id="cancelButton" type="button" class="btn-link modal-action" data-dismiss="modal">Cancelar</button>
      </div>
    </div>
  </div>
</div>