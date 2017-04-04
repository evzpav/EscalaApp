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
<jsp:include page="modalChangePeriodOfWork.jsp"></jsp:include>
<jsp:include page="js-files.jsp"></jsp:include>

<div class="col-sm-9 content">
    <div class="dashhead">
        <div class="dashhead-titles">
            <h6 class="dashhead-subtitle">Gráficos</h6>
            <h2 class="dashhead-title">Escala</h2>
        </div>

        <div class="btn-toolbar dashhead-toolbar">
            <div id="datepickerDiv" class="btn-toolbar-item input-with-icon">


                <input id="datepicker" type="text" class="form-control" data-provide="datepicker"
                       data-date-format="dd/mm/yyyy">
                <span class="icon icon-calendar"></span>

            </div>
        </div>
    </div>

    <hr class="m-t">
    <div class="row">
        <div class="col-sm-6"><h2 id="storeLabel"></h2></div>


    </div>


    <div><label id="timeLineLabel1" class="timelineLabel"> </label></div>
    <div id="timeLineGraph1" style="height: 500px;"></div>

    <div><label id="timeLineLabel2" class="timelineLabel"> </label></div>
    <div id="timeLineGraph2" style="height: 500px;"></div>

    <div><label id="timeLineLabel3" class="timelineLabel"> </label></div>
    <div id="timeLineGraph3" style="height: 500px;"></div>

    <div><label id="timeLineLabel4" class="timelineLabel"> </label></div>
    <div id="timeLineGraph4" style="height: 500px;"></div>

    <div><label id="timeLineLabel5" class="timelineLabel"> </label></div>
    <div id="timeLineGraph5" style="height: 500px;"></div>

    <div><label id="timeLineLabel6" class="timelineLabel"> </label></div>
    <div id="timeLineGraph6" style="height: 500px;"></div>

    <div><label id="timeLineLabel0" class="timelineLabel"> </label></div>
    <div id="timeLineGraph0" style="height: 500px;"></div>


</div>
</div>
</div>


<script type="text/javascript">


    var weekDayPort = [];

    function parseLocalDateTime(localDateTime) {
        return moment()
            .year(localDateTime.date.year)
            .month(localDateTime.date.month)
            .date(localDateTime.date.day)
            .hour(localDateTime.time.hour)
            .minute(localDateTime.time.minute);
    }

    function parseLocalDate(localDate) {
        return moment()
            .year(localDate.year)
            .month(localDate.month - 1)
            .date(localDate.day)

    }
    $(document).ready(function () {


        weekDayPort = new Array(7);
        weekDayPort[1] = 'Segunda-Feira';
        weekDayPort[2] = 'Terça-Feira';
        weekDayPort[3] = 'Quarta-Feia';
        weekDayPort[4] = 'Quinta-Feira';
        weekDayPort[5] = 'Sexta-Feira';
        weekDayPort[6] = 'Sábado';
        weekDayPort[0] = 'Domingo';

        //date picker
        var today = $("#datepicker").datepicker("setDate", new Date());


        var selectedDate;
        var timeLineGraph = "";
        var timeLineLabel = "";

        function parseLocalDateTime(localDateTime) {
            return moment()
                .year(localDateTime.date.year)
                .month(localDateTime.date.month)
                .date(localDateTime.date.day)
                .hour(localDateTime.time.hour)
                .minute(localDateTime.time.minute);
        }

        function parseLocalDate(localDate) {
            return moment()
                .year(localDate.year)
                .month(localDate.month - 1)
                .date(localDate.day)

        }

        $(function () {

            $("#datepicker").datepicker(
                {
                    dateFormat: "dd/mm/yyyy",
                    showOtherMonths: true,
                    selectOtherMonths: true,
                    autoclose: true,
                    changeMonth: true
                });
            $("#datepicker").on("change", function () {
                selectedDate = $(this).val();


                var jsonSelectedDate = {
                    "selectedDate": $("#datepicker").val()

                }


                $.ajax({
                    type: "POST",
                    url: "TimelineServlet?command=LIST_TIMELINE_WITH_SELECTED_DATE",
                    dataType: "json",
                    contentType: 'application/json; charset=utf-8',
                    data: JSON.stringify(jsonSelectedDate),
                    success: function (timelineDTO) {


                        var arrayFirstPeriod = [];
                        var arraySecondPeriod = [];
                        var arrayDataTimeline = [];
                        var dayOfWeekNumber = null;
                        var cont = 0;


                        $('#storeLabel').text(timelineDTO.store.storeName);

                        $.each(timelineDTO.listWeekPeriodOfWork, function (i, listWeekPeriodOfWork) {


                            if (dayOfWeekNumber == null) {
                                dayOfWeekNumber = listWeekPeriodOfWork.dayOfWeek;
                            } else if (dayOfWeekNumber != listWeekPeriodOfWork.dayOfWeek) {

                                timeLineGraph = "timeLineGraph" + dayOfWeekNumber;
                                drawTimeline(timeLineGraph, arrayDataTimeline);
                                dayOfWeekNumber = listWeekPeriodOfWork.dayOfWeek;


                                arrayFirstPeriod = [];
                                arraySecondPeriod = [];
                                arrayDataTimeline = [];
                                cont = 0;


                            }


                            var dayLabel = listWeekPeriodOfWork.periodOfWork.day;
                            // var dayLabelFormatted = moment(dayLabel).format('DD/MM/YYYY');
                            var dayLabelFormatted = parseLocalDate(dayLabel).format('DD/MM/YYYY');

                            timeLineLabel = "#timeLineLabel" + listWeekPeriodOfWork.dayOfWeek;
                            $(timeLineLabel).text(weekDayPort[listWeekPeriodOfWork.dayOfWeek] + " " + dayLabelFormatted);


                            arrayFirstPeriod [cont] = [listWeekPeriodOfWork.periodOfWork.employee.employeeName, listWeekPeriodOfWork.periodOfWork.employee.workFunction, listWeekPeriodOfWork.periodOfWork.workingTimeId, new Date(parseLocalDateTime(listWeekPeriodOfWork.periodOfWork.startTime).toDate()), new Date(parseLocalDateTime(listWeekPeriodOfWork.periodOfWork.intervalStart).toDate()), listWeekPeriodOfWork.periodOfWork.working];
                            arraySecondPeriod [cont] = [listWeekPeriodOfWork.periodOfWork.employee.employeeName, listWeekPeriodOfWork.periodOfWork.employee.workFunction, listWeekPeriodOfWork.periodOfWork.workingTimeId, new Date(parseLocalDateTime(listWeekPeriodOfWork.periodOfWork.intervalEnd).toDate()), new Date(parseLocalDateTime(listWeekPeriodOfWork.periodOfWork.endTime).toDate()), listWeekPeriodOfWork.periodOfWork.working];
                            arrayDataTimeline = arrayFirstPeriod.concat(arraySecondPeriod);

                            if (i == timelineDTO.listWeekPeriodOfWork.length - 1) {


                                timeLineGraph = "timeLineGraph" + dayOfWeekNumber;
                                drawTimeline(timeLineGraph, arrayDataTimeline);
                                dayOfWeekNumber = listWeekPeriodOfWork.dayOfWeek;


                            }

                            cont++;


                        });


                    },


                    error: function (data) {
                        console.log("LIST_TIMELINE_WITH_SELECTED_DATE error");
                    }
                });


            });
        });


    });


    google.charts.setOnLoadCallback(function () {
        $("#datepicker").trigger("click");
    });


    google.charts.load('current', {packages: ['timeline']});

    function drawTimeline(timeLineGraph, arrayDataTimeline) {

        var container = document.getElementById(timeLineGraph);
        var chart = new google.visualization.Timeline(container);


        var dataTable = new google.visualization.DataTable();


        dataTable.addColumn({type: 'string', id: 'Nome'});
        dataTable.addColumn({type: 'string', id: 'Cargo'});
        dataTable.addColumn({type: 'number', role: 'id'});
        dataTable.addColumn({type: 'date', id: 'Começo'});
        dataTable.addColumn({type: 'date', id: 'Fim'});
        dataTable.addColumn({type: 'boolean', role: 'scope'});


        console.log(arrayDataTimeline);

        dataTable.addRows(arrayDataTimeline);

        var dataView = new google.visualization.DataView(dataTable);
        dataView.hideColumns([5]);


        var options = {
            backgroundColor: '#ffffff',
            showBarLabels: true,
            groupByRowLabel: true,
            colors: ['#9f86ff', '#1ca8dd'],
            timeline: {colors: ['#9f86ff', '#1ca8dd']},
            rowLabelStyle: {fontName: 'sans-serif'},

            hAxis: {
                format: 'HH:mm',

            }

        };


        var observer = new MutationObserver(setScope);

        var outOfScope = [];
        google.visualization.events.addListener(chart, 'ready', function () {
            var rowIndex = 0;
            var entrou = false;
            var cont = 1;

            Array.prototype.forEach.call(container.getElementsByTagName('rect'), function (bar) {
                if (parseFloat(bar.getAttribute('x')) > 0) {


                    if (!dataTable.getValue(rowIndex, 5)) {


                        bar.setAttribute('fill', '#dddddd');
                        outOfScope.push([
                            bar.getAttribute('x'),
                            bar.getAttribute('y')
                        ]);
                    }


                    if (cont % 2 == 0) {
                        rowIndex++;
                    }

                    cont++;


                }
            });

            observer.observe(container, {
                childList: true,
                subtree: true
            });
        });

        function setScope() {
            Array.prototype.forEach.call(container.getElementsByTagName('rect'), function (bar) {
                outOfScope.forEach(function (coords) {
                    if ((bar.getAttribute('x') === coords[0]) && (bar.getAttribute('y') === coords[1])) {
                        bar.setAttribute('fill', '#dddddd');
                    }
                });
            });
        }

        var workingTimeId = 0;


        function selectHandler() {
            var selectedItem = chart.getSelection()[0];
            if (selectedItem) {
                workingTimeId = dataTable.getValue(selectedItem.row, 2);

                var jsonWorkingTimeSelected = {
                    'workingTimeId': workingTimeId
                }

                $.ajax({
                    type: "POST",
                    url: "TimelineServlet?command=GET_PERIOD_OF_WORK_BY_ID",
                    dataType: "json",
                    contentType: 'application/json; charset=utf-8',
                    data: JSON.stringify(jsonWorkingTimeSelected),
                    success: function (periodOfWork) {
                        console.log("Success: " + periodOfWork);
                        $('#modalChangePeriodOfWork').modal('toggle');
                        $('#employeeNameLabel').text(periodOfWork.employee.employeeName);
                        $('#startTimeInput').val(parseLocalDateTime(periodOfWork.startTime).format('HH:mm'));
                        $('#endTimeInput').val(parseLocalDateTime(periodOfWork.endTime).format('HH:mm'));
                        $('#intervalStartInput').val(parseLocalDateTime(periodOfWork.intervalStart).format('HH:mm'));
                        $('#intervalEndInput').val(parseLocalDateTime(periodOfWork.intervalEnd).format('HH:mm'));
                        $('#dayLabel').text(parseLocalDate(periodOfWork.day).format('DD/MM/YYYY'));
                        $('#dayOfWeekLabel').text(weekDayPort[moment(periodOfWork.day).day()]);
                        $('#workingCheckbox').prop('checked', periodOfWork.working);
                        $('#workingTimeIdHidden').val(periodOfWork.workingTimeId);

                    },
                    error: function (data) {
                        console.log("GET_WORKING_TIME_BY_ID ERROR");
                    }


                });
            }
        }

        function convertHourToDate(dayString, hourString) {
            var time = hourString.split(':');

            var dayDate = moment(dayString, 'DD-MM-YYYY');

            console.log(dayString);

            dayDate.hour(parseInt(time[0]));
            dayDate.minute(parseInt(time[1]));

            return dayDate.utc();
        }


        $('#saveButton').click(function () {

            var dayString = $('#dayLabel').text();

            var jsonUpdatePeriodOfWork = {

                day: dayString,
                startTime: $('#startTimeInput').val(),
                endTime: $('#endTimeInput').val(),
                intervalStart: $('#intervalStartInput').val(),
                intervalEnd: $('#intervalEndInput').val(),
                working: $('#workingCheckbox').prop("checked"),
                workingTimeId: $('#workingTimeIdHidden').val()
            }
            console.log(jsonUpdatePeriodOfWork);


            $.ajax({
                type: "POST",
                url: "TimelineServlet?command=UPDATE_PERIOD_OF_WORK",
                dataType: "json",
                contentType: 'application/json; charset=utf-8',
                data: JSON.stringify(jsonUpdatePeriodOfWork),
                success: function (data) {

                    window.location.href = window.location.href;
                },

                error: function (data) {
                    console.log("UPDATE_PERIOD_OF_WORK ERROR");
                }
            });

        });

        google.visualization.events.addListener(chart, 'select', selectHandler);

        chart.draw(dataView, options);

    }


</script>


</body>
</html>