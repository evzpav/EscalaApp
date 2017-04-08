angular.module("escala").controller("timePatternController", function ($scope, timePatternService, $state, $stateParams, alertify, emptyTimePattern) {
    $scope.subtitle = "CADASTROS";
    $scope.title = "Horários";

    $scope.formTitle = "Cadastre o padrão de horários"
    $scope.timeRegex = '/^([01]?[0-9]|2[0-3]):[0-5][0-9]$/';

    function init() {

        timePatternService.getTimePattern($stateParams.employeeId)
            .then(function (data) {
                    $scope.employee = data;

                    $scope.listOfWeekTimePattern = data.listOfWeekTimePattern;
                    var copyOflistOfTimePattern = angular.copy(data.listOfWeekTimePattern);
                    if (copyOflistOfTimePattern.length === 0) {

                        $scope.listOfWeekTimePattern = [];
                        $scope.listOfWeekTimePattern = angular.copy(emptyTimePattern.listOfWeekTimePattern);
                        $scope.isNewTimePattern = true;
                        alertify.delay(5000).log("Nenhum horário cadastrado para "+ data.employeeName);


                    } else {
                        $scope.isNewTimePattern = false;
                        for (var i = 0; i < $scope.listOfWeekTimePattern.length; i++) {
                            var item = $scope.listOfWeekTimePattern[i];
                            delete item.timePattern.day;
                            item.timePattern.startTime = $scope.formatTime(item.timePattern.startTime);
                            item.timePattern.intervalStart = $scope.formatTime(item.timePattern.intervalStart);
                            item.timePattern.intervalEnd = $scope.formatTime(item.timePattern.intervalEnd);
                            item.timePattern.endTime = $scope.formatTime(item.timePattern.endTime);
                        }
                    }
                }
            ).catch(function (data) {

            alertify.error("Adicione horários para o funcionário");

        })
    }


    init();

    //select
    $scope.selectWeeks = [
        {name: 1},
        {name: 2}
    ]


    $scope.formatTime = function (datetime) {
        return timePatternService.formatLocalDateTimeToTime(datetime);

    }

    $scope.repeatFirstLine = function () {
        var firstLine = $scope.listOfWeekTimePattern[0].timePattern;
        for (var i = 1; i < 7; i++) {
            $scope.listOfWeekTimePattern[i].timePattern = angular.copy(firstLine);
        }

    }


    //datepicker
    $scope.today = function () {
        $scope.datePicker = new Date();
    };
    $scope.today();
    $scope.clear = function () {
        $scope.datePicker = null;
    };
    $scope.inlineOptions = {
        customClass: getDayClass,
        minDate: new Date(),
        showWeeks: true
    };
    $scope.dateOptions = {
        formatYear: 'yy',
        maxDate: new Date(2020, 5, 22),
        minDate: new Date(),
        startingDay: 1
    };

    // Disable weekend selection
    function disabled(data) {
        var date = data.date,
            mode = data.mode;
        return mode === 'day' && (date.getDay() === 0 || date.getDay() === 6);
    }

    $scope.toggleMin = function () {
        $scope.inlineOptions.minDate = $scope.inlineOptions.minDate ? null : new Date();
        $scope.dateOptions.minDate = $scope.inlineOptions.minDate;
    };

    $scope.toggleMin();
    $scope.open1 = function () {
        $scope.popup1.opened = true;
    };
    $scope.open2 = function () {
        $scope.popup2.opened = true;
    };
    $scope.setDate = function (year, month, day) {
        $scope.datePicker = new Date(year, month, day);
    };
    $scope.formats = ['dd-MMMM-yyyy', 'yyyy/MM/dd', 'dd/MM/yyyy', 'shortDate'];
    $scope.format = $scope.formats[2];
    $scope.altInputFormats = ['M!/d!/yyyy'];
    $scope.popup1 = {
        opened: false
    };
    $scope.popup2 = {
        opened: false
    };
    var tomorrow = new Date();
    tomorrow.setDate(tomorrow.getDate() + 1);
    var afterTomorrow = new Date();
    afterTomorrow.setDate(tomorrow.getDate() + 1);
    $scope.events = [
        {
            date: tomorrow,
            status: 'full'
        },
        {
            date: afterTomorrow,
            status: 'partially'
        }
    ];
    function getDayClass(data) {
        var date = data.date,
            mode = data.mode;
        if (mode === 'day') {
            var dayToCheck = new Date(date).setHours(0, 0, 0, 0);
            for (var i = 0; i < $scope.events.length; i++) {
                var currentDay = new Date($scope.events[i].date).setHours(0, 0, 0, 0);
                if (dayToCheck === currentDay) {
                    return $scope.events[i].status;
                }
            }
        }
        return '';
    }

    $scope.$watch('datePicker', function (val) {
        $scope.selectedDate = moment(val).format('DD/MM/YYYY');
        $scope.setStartWeekDate($scope.selectedDate);
    });

    $scope.setStartWeekDate = function (date) {
        $scope.startWeekDate = date;
    }
    $scope.saveOrUpdateTimePattern = function () {
        var tp = [];
        for (var i = 0; i < $scope.listOfWeekTimePattern.length; i++) {
            tp.push($scope.listOfWeekTimePattern[i].timePattern);

        }

        var timePatternDTOs = {
            timePatternDTOs: tp,
            employeeId: $stateParams.employeeId,
            startWeekDate: $scope.startWeekDate
        }

        timePatternService.saveOrUpdateTimePattern(timePatternDTOs, $scope.isNewTimePattern)
            .then(function (data) {
                alertify.success(data)
                $state.go('employees');
            }).catch(function (data) {
            alertify.error("Não foi possível salvar os horários: "+ data);
        })

    }

    $scope.cancel = function () {
        $state.go('employees');
    }

});