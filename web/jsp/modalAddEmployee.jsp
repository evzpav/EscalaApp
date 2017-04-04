<!DOCTYPE html>
<%@ page contentType="text/html; charset=UTF-8" %>

<div id="modalAddEmployee" class="modal">
    <div class="modal-dialog modal-lg">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span
                        aria-hidden="true">&times;</span></button>
                <div>
                    <h4 class="modal-title">Adicionar Novo Funcionário</h4>


                </div>

            </div>
            <div class="modal-body">


                <label id="dayOfWeekLabel"></label>
                <label id="dayLabel"></label>


                <div class="form-inline">
                    <div class="form-group">
                        <p>Nome
                        <p>
                            <input id="employeeNameInput" type="text" class="form-control">
                    </div>

                    <div class="form-group">
                        <p>Função
                        <p>
                            <input id="workFunctionInput" type="text" class="form-control">

                    </div>


                    <div id="datepickerDiv" class="form-group has-feedback" >
                        <p>Registrar horários desde a semana do dia:</p>

                        <input id="startWeekDate" type="text"  class="form-control" data-provide="datepicker" data-date-format="dd/mm/yyyy">
                        <i class="glyphicon glyphicon-user form-control-feedback"></i>
                    </div>

                    <%--<div class="form-group has-feedback">--%>
                        <%--<label class="control-label">Username</label>--%>
                        <%--<input type="text" class="form-control" placeholder="Username" />--%>
                        <%--<i class="glyphicon glyphicon-user form-control-feedback"></i>--%>
                    <%--</div>--%>

                    
            </div>

            <button id="buttonrepeat" class="btn btn-pill btn-secondary">Repetir primeiro dia</button>

            <div class="table-responsive">
                <table class="table  table-bordered table-condensed">
                    <thead>
                    <tr>
                        <th>Dia da semana</th>
                        <th>Início</th>
                        <th>Início Intervalo</th>
                        <th>Fim Intervalo</th>
                        <th>Fim</th>
                        <th>Trabalhando?</th>
                        <th>Repete a cada(semanas)</th>

                    </tr>
                    </thead>
                    <tbody>
                    <tr id="Day1" class="line">
                        <td>Segundas-Feiras</td>
                        <td>
                            <input id="startTimeInput" type="text" class="form-control startTimeInput input-small"
                                   placeholder="08:00"/>

                        </td>
                        <td><input type="text" class="form-control intervalStartInput" placeholder="12:00"/></td>
                        <td><input type="text" class="form-control intervalEndInput" placeholder="13:00"/></td>
                        <td><input type="text" class="form-control endTimeInput" placeholder="17:00"/></td>
                        <td>
                            <div class="checkbox custom-control custom-checkbox">
                                <label> <input class="workingCheckbox" type="checkbox" checked>
                                    <span class="custom-control-indicator"></span>
                                </label>
                            </div>
                        </td>


                        <td><select class="form-control repeatFrequency"/>
                            <option>1</option>
                            <option>2</option>
                            </select>
                        </td>

                    </tr>
                    <tr id="Day2" class="line">
                        <td>Terças-Feiras</td>
                        <td><input type="text" class="form-control startTimeInput" placeholder="08:00"/></td>
                        <td><input type="text" class="form-control intervalStartInput" placeholder="12:00"/></td>
                        <td><input type="text" class="form-control intervalEndInput" placeholder="13:00"/></td>
                        <td><input type="text" class="form-control endTimeInput" placeholder="17:00"/></td>
                        <td>
                            <div class="checkbox custom-control custom-checkbox">
                                <label> <input class="workingCheckbox" type="checkbox" checked>
                                    <span class="custom-control-indicator"></span>
                                </label>
                            </div>
                        </td>
                        <td><select class="form-control repeatFrequency"/>
                            <option>1</option>
                            <option>2</option>
                            </select>
                        </td>

                    </tr>
                    <tr id="Day3" class="line">
                        <td>Quartas-Feiras</td>
                        <td><input type="text" class="form-control startTimeInput" placeholder="08:00"/></td>
                        <td><input type="text" class="form-control intervalStartInput" placeholder="12:00"/></td>
                        <td><input type="text" class="form-control intervalEndInput" placeholder="13:00"/></td>
                        <td><input type="text" class="form-control endTimeInput" placeholder="17:00"/></td>
                        <td>
                            <div class="checkbox custom-control custom-checkbox">
                                <label> <input class="workingCheckbox" type="checkbox" checked>
                                    <span class="custom-control-indicator"></span>
                                </label>
                            </div>
                        </td>
                        <td><select class="form-control repeatFrequency"/>
                            <option>1</option>
                            <option>2</option>
                            </select>
                        </td>

                    </tr>
                    <tr id="Day4" class="line">
                        <td>Quintas-feiras</td>
                        <td><input type="text" class="form-control startTimeInput" placeholder="08:00"/></td>
                        <td><input type="text" class="form-control intervalStartInput" placeholder="12:00"/></td>
                        <td><input type="text" class="form-control intervalEndInput" placeholder="13:00"/></td>
                        <td><input type="text" class="form-control endTimeInput" placeholder="17:00"/></td>
                        <td>
                            <div class="checkbox custom-control custom-checkbox">
                                <label> <input class="workingCheckbox" type="checkbox" checked>
                                    <span class="custom-control-indicator"></span>
                                </label>
                            </div>
                        </td>
                        <td><select class="form-control repeatFrequency"/>
                            <option>1</option>
                            <option>2</option>
                            </select>
                        </td>

                    </tr>
                    <tr id="Day5" class="line">
                        <td>Sextas-Feiras</td>
                        <td><input type="text" class="form-control startTimeInput" placeholder="08:00"/></td>
                        <td><input type="text" class="form-control intervalStartInput" placeholder="12:00"/></td>
                        <td><input type="text" class="form-control intervalEndInput" placeholder="13:00"/></td>
                        <td><input type="text" class="form-control endTimeInput" placeholder="17:00"/></td>
                        <td>
                            <div class="checkbox custom-control custom-checkbox">
                                <label> <input class="workingCheckbox" type="checkbox" checked>
                                    <span class="custom-control-indicator"></span>
                                </label>
                            </div>
                        </td>
                        <td><select class="form-control repeatFrequency"/>
                            <option>1</option>
                            <option>2</option>
                            </select>
                        </td>

                    </tr>
                    <tr id="Day6" class="line">
                        <td>Sábados</td>
                        <td><input type="text" class="form-control startTimeInput" placeholder="08:00"/></td>
                        <td><input type="text" class="form-control intervalStartInput" placeholder="12:00"/></td>
                        <td><input type="text" class="form-control intervalEndInput " placeholder="13:00"/></td>
                        <td><input type="text" class="form-control endTimeInput" placeholder="17:00"/></td>
                        <td>
                            <div class="checkbox custom-control custom-checkbox">
                                <label> <input class="workingCheckbox" type="checkbox">
                                    <span class="custom-control-indicator"></span>
                                </label>
                            </div>
                        </td>
                        <td><select class="form-control repeatFrequency"/>
                            <option>1</option>
                            <option>2</option>
                            </select>
                        </td>

                    </tr>
                    <tr id="Day7" class="line">
                        <td>Domingos</td>
                        <td><input type="text" class="form-control startTimeInput" placeholder="08:00"/></td>
                        <td><input type="text" class="form-control intervalStartInput" placeholder="12:00"/></td>
                        <td><input type="text" class="form-control intervalEndInput" placeholder="13:00"/></td>
                        <td><input type="text" class="form-control endTimeInput" placeholder="17:00"/></td>
                        <td>
                            <div class="checkbox custom-control custom-checkbox">
                                <label> <input class="workingCheckbox" type="checkbox">
                                    <span class="custom-control-indicator"></span>
                                </label>
                            </div>
                        </td>
                        <td><select class="form-control repeatFrequency"/>
                            <option>1</option>
                            <option>2</option>
                            </select>
                        </td>

                    </tr>


                    </tbody>
                </table>

                <div id="alert"></div>

            </div>


        </div>
        <div class="modal-actions">

            <button id="saveEmployeeButton" type="button" class="btn-link modal-action">
                <strong>Salvar</strong>
            </button>

            <button id="cancelButton" type="button" class="btn-link modal-action" data-dismiss="modal">Cancelar</button>
        </div>
    </div>
</div>
</div>

<style type="text/css">
    .input {
        display: block;
        padding: 0;
        margin: 0;
        border: 0;
        width: 100%;
    }

    .td {
        margin: 0;
        padding: 0;
    }

</style>