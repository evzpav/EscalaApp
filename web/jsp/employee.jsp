<!DOCTYPE html>
<%@ page contentType="text/html; charset=UTF-8" %>
<html lang="en">
<head>
    <!-- These meta tags come first. -->
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">

    <title>Escala App</title>

    <jsp:include page="bootstrap-css-files.jsp"></jsp:include>


</head>
<body>

<jsp:include page="sidebar.jsp"></jsp:include>

<div class="col-sm-9 content">
    <div class="dashhead">
        <div class="dashhead-titles">
            <h6 class="dashhead-subtitle">Cadastros</h6>
            <h2 class="dashhead-title">Funcionários</h2>

        </div>

    </div>

    <hr class="m-t">
    <div class="row">
        <div class="col-sm-6"><h2 id="storeLabel"></h2></div>

        <div class="col-sm-6">
            <button id="addEmployeeButton" type="button" class="btn btn-pill btn-primary"><span
                    class="icon icon-add-user"></span> Adicionar Novo Funcionário
            </button>
        </div>
    </div>

    <jsp:include page="modalAddEmployee.jsp"></jsp:include>


    <jsp:include page="js-files.jsp"></jsp:include>


    <div class="table-full">
        <div class="table-responsive">
            <table class="table" data-sort="table">
                <thead>
                <tr>
                    <th>Nome</th>
                    <th>Cargo</th>
                    <th>ID</th>
                    <th>Editar</th>
                    <th>Excluir</th>
                </tr>


                </thead>

                <tbody id="employeeTable">


                </tbody>
            </table>
        </div>
    </div>
    <!--<div id="table_div_employees"></div>-->


</div>
<script type="text/javascript">


    var weekDayPort = [];
    $(document).ready(function () {


        weekDayPort = new Array(7);
        weekDayPort[1] = 'Segunda-Feira';
        weekDayPort[2] = 'Terça-Feira';
        weekDayPort[3] = 'Quarta-Feia';
        weekDayPort[4] = 'Quinta-Feira';
        weekDayPort[5] = 'Sexta-Feira';
        weekDayPort[6] = 'Sábado';
        weekDayPort[0] = 'Domingo';

        var arrayDataTable = [];
        $.ajax({
            type: "POST",
            url: "EmployeeServlet?command=LIST_EMPLOYEES",
            dataType: "json",
            contentType: 'application/json; charset=utf-8',
            success: function (listOfEmployeesDTO) {
                $('#storeLabel').text(listOfEmployeesDTO.store.storeName);

                $.each(listOfEmployeesDTO.listOfEmployees, function (i, listOfEmployees) {
                    arrayDataTable [i] =
                        [listOfEmployees.employeeName,
                            listOfEmployees.workFunction,
                            listOfEmployees.employeeId];


                });
                refreshTable();

            },
            error: function (listOfEmployeesDTO) {
                console.log("error: list employees");
            }
        });


        console.log(arrayDataTable);

        var refreshTable = function () {

            $("#employeeTable").html('');


            for (var i = 0; i < arrayDataTable.length; i++) {
                var linha = arrayDataTable[i];

                $("#employeeTable").append("<tr>");
                $("#employeeTable").append(
                    "<td class='employeeName'>" + linha[0] + "</td>");
                $("#employeeTable").append(
                    "<td class='workFunction'>" + linha[1] + "</td>");
                $("#employeeTable").append(
                    "<td class='employeeId'>" + linha[2] + "</td>");
                $("#employeeTable").append('<td> <button attr-update="' + linha[2] + '" class="btn btn-xs btn-default-outline updateButton" type="button">  <span class="icon icon-edit"></span></button> </td>');

                $("#employeeTable").append('<td> <button attr-delete="' + linha[2] + '" class="btn btn-xs btn-default-outline deleteButton" type="button"> <span class= "icon icon-squared-minus"> </span>  </button> </td>');
                $("#employeeTable").append("</tr>");

            }
        }


        /*
         google.charts.load('current', {'packages': ['table']});
         google.charts.setOnLoadCallback(drawTable);


         function drawTable() {
         var data = new google.visualization.DataTable();
         data.addColumn('string', 'Nome');
         data.addColumn('string', 'Cargo');
         data.addColumn('number', 'Id');
         data.addColumn('string', 'Editar');
         data.addColumn('string', 'Deletar');
         data.addRows(arrayDataTable);


         var table = new google.visualization.Table(document.getElementById('table_div_employees'));



         // data.setColumns([0,1,3,4]);

         data.setProperty(0, 3, 'style', 'width:50px');
         data.setProperty(0, 4, 'style', 'width:50px');

         var cssClassNames = {headerRow: 'table-bordered table-striped'};

         table.draw(
         data,
         {allowHtml: true, showRowNumber: true, width: '100%', height: '100%'});


         function selectionHandler(table) {
         var selection = table.getSelection();
         var message = '';

         for (var i = 0; i < selection.length; i++) {
         var item = selection[i];
         if (item.row != null && item.column != null) {
         message += '{row:' + item.row + ',column:' + item.column + '}';
         } else if (item.row != null) {
         message += '{row:' + item.row + '}';
         } else if (item.column != null) {
         message += '{column:' + item.column + '}';
         }
         }
         if (message == '') {
         message = 'nothing';
         }
         alert('You selected ' + message);
         }

         function getSelectedRowNumber() {

         var selectedItem = table.getSelection()[0];
         if (selectedItem) {
         employeeId = data.getValue(selectedItem.row, 2);
         alert(employeeId);
         };

         };

         google.visualization.events.addListener(table, 'select', getSelectedRowNumber);


         }


         drawTable()

         */
        function parseLocalDateTime(localDateTime) {
            return moment()
                .year(localDateTime.date.year)
                .month(localDateTime.date.month)
                .date(localDateTime.date.day)
                .hour(localDateTime.time.hour)
                .minute(localDateTime.time.minute);
        }

        function formatLocalDateTimeToTime(dateFromJson) {
            return parseLocalDateTime(dateFromJson).format('HH:mm')
        }

        $(document).on('click', '.deleteButton', function () {
            var attrDelete = {
                "employeeId": $(this).attr('attr-delete')
            }

            $.ajax({
                type: "POST",
                url: "EmployeeServlet?command=DELETE_EMPLOYEE",
                dataType: "json",
                data: JSON.stringify(attrDelete),
                contentType: 'application/json; charset=utf-8',
                success: function () {
                    refreshTable();
                    window.location.href = window.location.href;
                },
                error: function () {
                    console.log("error: delete employee");
                }
            });

        });

        $(document).on('click', '.updateButton', function () {
            var attrUpdate = {
                "employeeId": $(this).attr('attr-update')
            }

            $.ajax({
                type: "POST",
                url: "EmployeeServlet?command=GET_EMPLOYEE",
                dataType: "json",
                data: JSON.stringify(attrUpdate),
                contentType: 'application/json; charset=utf-8',
                success: function (employee) {
                    $('#modalAddEmployee').modal('toggle')
                    $('#addOrUpdate').val('update');
                    $('#employeeNameInput').val(employee.employeeName);
                    $('#workFunctionInput').val(employee.workFunction);
                    $('.line').each(function (i) {
                        var tp = employee.listOfWeekTimePattern[i].timePattern;
                        $(this).find('.startTimeInput').val(formatLocalDateTimeToTime(tp.startTime)),
                            $(this).find('.intervalStartInput').val(formatLocalDateTimeToTime(tp.intervalStart)),
                            $(this).find('.intervalEndInput').val(formatLocalDateTimeToTime(tp.intervalEnd)),
                            $(this).find('.endTimeInput').val(formatLocalDateTimeToTime(tp.endTime)),
                            $(this).find('.workingCheckbox').prop('checked', tp.working),
                            $(this).find('.repeatFrequency').val(tp.repeatFrequency)

                    });
                },
                error: function () {
                    console.log("error: get employee");
                }
            });
        });


        $('#addEmployeeButton').click(function () {
            $('#modalAddEmployee').modal('toggle');
            $('#addOrUpdate').val('');
            $('#employeeNameInput').val('');
            $('#workFunctionInput').val('');
            $('.line').each(function () {
                var tableLineObject = {
                    startTime: $(this).find('.startTimeInput').val(''),
                    intervalStart: $(this).find('.intervalStartInput').val(''),
                    intervalEnd: $(this).find('.intervalEndInput').val(''),
                    endTime: $(this).find('.endTimeInput').val('')
                }

            });
        });


        $('#saveEmployeeButton').click(function () {


            var error = "";

            if ($('#employeeNameInput').val() == "") {
                error += "Nome em branco. <br>";
            }
            if ($('#workFunctionInput').val() == "") {
                error += "Função em branco. <br>";
            }

            if ($('#startWeekDate').val() == "") {
                error += "Data em branco. <br>";
            }

            if (error != "") {
                var alertErrorInput = '<div class="alert alert-danger alert-dismissible" role="alert"><p><strong>Erro(s) ao salvar funcionário:</strong></p>' + error + '</div>';
                $("#alert").html(alertErrorInput);

            } else {

                var tableLineArray = [];
                $('.line').each(function () {
                    var tableLineObject = {
                        startTime: $(this).find('.startTimeInput').val(),
                        intervalStart: $(this).find('.intervalStartInput').val(),
                        intervalEnd: $(this).find('.intervalEndInput').val(),
                        endTime: $(this).find('.endTimeInput').val(),
                        working: $(this).find('.workingCheckbox').prop("checked"),
                        repeatFrequency: $(this).find('.repeatFrequency').val()
                    }

                    tableLineArray.push(tableLineObject);
                });

                var employeeId = {
                    "employeeId": $(this).attr('attr-update')
                }

                var jsonEmployeeTable = {
                    employeeName: $('#employeeNameInput').val(),
                    workFunction: $('#workFunctionInput').val(),
                    startWeekDate: $('#startWeekDate').val(),
                    periodOfWorkStrDTO: tableLineArray,
                    employeeIdDTO: employeeId

                }

                var ajaxType = '';
                var ajaxUrl = '';
                if ($('#addOrUpdate').val() === 'update') {
                    ajaxType = "PUT";
                    ajaxUrl = "EmployeeServlet";
                } else {
                    ajaxType = "POST";
                    ajaxUrl = "EmployeeServlet?command=ADD_EMPLOYEE";
                }

                $.ajax({
                    type: ajaxType,
                    url: ajaxUrl,
                    dataType: 'json',
                    contentType: 'application/json; charset=utf-8',
                    data: JSON.stringify(jsonEmployeeTable),
                    success: function (data) {
                        window.location.href = window.location.href
                        var alertSuccess = '<div class="alert alert-success" role="alert">  <strong>Sucesso! </strong>.</div>';
                        $('#alert').html(alertSuccess);

                    },

                    error: function (data) {

                        var alertError = '<div class="alert alert-warning" role="alert">  <strong>Warning! </strong>.</div>';
                        $('#alert').html(alertError);
                    }
                });

            }
        });

        $("#buttonrepeat").click(function () {

            var tableRepeat = []
            $('.line').each(function () {
                var tableLineObject = {
                    startTime: $(this).find('.startTimeInput').val(),
                    intervalStart: $(this).find('.intervalStartInput').val(),
                    intervalEnd: $(this).find('.intervalEndInput').val(),
                    endTime: $(this).find('.endTimeInput').val(),
                    working: $(this).find('.workingCheckbox').prop("checked"),
                    repeatFrequency: $(this).find('.repeatFrequency').val()
                }

                tableRepeat.push(tableLineObject);
            });

            var item = tableRepeat[0]
            $('.line').each(function () {
                var tableLineObject = {
                    startTime: $(this).find('.startTimeInput').val(item.startTime),
                    intervalStart: $(this).find('.intervalStartInput').val(item.intervalStart),
                    intervalEnd: $(this).find('.intervalEndInput').val(item.intervalEnd),
                    endTime: $(this).find('.endTimeInput').val(item.endTime)
                }

            });


        })

        $('.startTimeInput').timepicker({
            showMeridian: false,
            template: 'modal',
            appendWidgetTo: 'body',
            explicitMode: true,
            minuteStep: 30,
            defaultTime: '08:00'
        });

        $('.intervalStartInput').timepicker({
            showMeridian: false,
            template: 'modal',
            appendWidgetTo: 'body',
            explicitMode: true,
            minuteStep: 30,
            defaultTime: '12:00'
        });

        $('.intervalEndInput').timepicker({
            showMeridian: false,
            template: 'modal',
            appendWidgetTo: 'body',
            explicitMode: true,
            minuteStep: 30,
            defaultTime: '13:00'
        });

        $('.endTimeInput').timepicker({
            showMeridian: false,
            template: 'modal',
            appendWidgetTo: 'body',
            explicitMode: true,
            minuteStep: 30,
            defaultTime: '18:00'
        });
    });


</script>


</body>
</html>