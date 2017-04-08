angular.module("escala").controller("timelineController", function ($scope, $timeout, timelineService, storeService, $uibModal, alertify) {
    $scope.subtitle = "GRÁFICOS";
    $scope.title = "Escala"


    $scope.timelineDTO = [];
    $scope.listTimelineForSelectedDate = function (date) {
        timelineService.listTimelineForSelectedDate(date)
            .then(function (data) {
                if (data.listWeekPeriodOfWork.length) {
                    $scope.timelineDTO = data.listWeekPeriodOfWork;
                    $scope.heatMap($scope.timelineDTO);
                    storeService.getStore(1).then(function (data) {
                        $scope.store = data;
                    });

                } else {
                    alertify.closeLogOnClick(true)
                        .log("Nenhum horário registrado para a semana do dia " + date);
                }
            })


    }


    $scope.isWorking = timelineService.isWorking;

    $scope.hours = timelineService.buildHours(6, 25);
    var myHours = timelineService.buildHours(6, 25);

    $scope.heatMap = function (json) {
        $scope.heatMapArray = [];
        var arrayDayOfWeek = _.groupBy(json, 'dayOfWeek');
        $scope.amountWorkers = arrayDayOfWeek[1].length;
        for (var i = 1; i <= Object.keys(arrayDayOfWeek).length; i++) {
            $scope.heatMapArray[i] = timelineService.fillHeatMapArray(arrayDayOfWeek[i], myHours)

        }

    }


    $scope.fillColour = function (numberOfEmployees, currentHeatMapArray) {
        var maxHeat = _.max(currentHeatMapArray);
        var numberOfClasses = 10;
        var totalNumberOfEmployees = $scope.amountWorkers;
        var value = Math.floor((numberOfEmployees / (maxHeat)) * numberOfClasses);

        switch (value) {
            case 0:
                return 'heatMap0';
                break;
            case 1:
                return 'heatMap1';
                break;
            case 2:
                return 'heatMap2';
                break;
            case 3:
                return 'heatMap3';
                break;
            case 4:
                return 'heatMap4';
                break;
            case 5:
                return 'heatMap5';
                break;
            case 6:
                return 'heatMap6';
                break;
            case 7:
                return 'heatMap7';
                break;
            case 8:
                return 'heatMap8';
                break;
            case 9:
                return 'heatMap9';
                break;
            case 10:
                return 'heatMap10';
                break;
            default:
                return 'heatMap0';
        }

    }

    $scope.updatePeriodOfWork = function (json) {


        var modalInstance = $uibModal.open({
            ariaLabelledBy: 'modal-title',
            ariaDescribedBy: 'modal-body',
            templateUrl: 'group-select-modal.html',
            controller: "ModalGroupSelect",
            backdrop: 'static',
            size: 'md',
            resolve: {
                periodOfWork: json.periodOfWork
            }
        });


        modalInstance.result.then(function () {

            $timeout(function () {
                $scope.listTimelineForSelectedDate($scope.selectedDate);
            }, 300)


        }, function () {

        });
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
        $scope.listTimelineForSelectedDate($scope.selectedDate);
    });


})


//MODAL
    .controller("ModalGroupSelect", function ($scope, $uibModalInstance, periodOfWork, timePatternService, timelineService, alertify, $state) {

        $scope.item = {};
        $scope.item.selectedGroups = [];

        $scope.employee = periodOfWork.employee;
        $scope.working = periodOfWork.working;
        $scope.day = periodOfWork.day;
        $scope.startTime = timePatternService.formatLocalDateTimeToTime(periodOfWork.startTime);
        $scope.intervalStart = timePatternService.formatLocalDateTimeToTime(periodOfWork.intervalStart);
        $scope.intervalEnd = timePatternService.formatLocalDateTimeToTime(periodOfWork.intervalEnd);
        $scope.endTime = timePatternService.formatLocalDateTimeToTime(periodOfWork.endTime);

        $scope.cancelar = function () {
            $uibModalInstance.dismiss('cancel');
        };

        $scope.goToTimePattern = function (employee) {
            $state.go('timePattern', {employeeId: employee.employeeId});
            $uibModalInstance.dismiss('cancel');
        }


        $scope.updatePeriodOfWork = function () {
            $uibModalInstance.close($scope.item.selectedGroups);

            jsonUpdatePeriodOfWork = {
                day: timePatternService.formatLocalDateToDate(periodOfWork.day),
                startTime: timePatternService.formatHourToSave($scope.startTime),
                endTime: timePatternService.formatHourToSave($scope.endTime),
                intervalStart: timePatternService.formatHourToSave($scope.intervalStart),
                intervalEnd: timePatternService.formatHourToSave($scope.intervalEnd),
                working: $scope.working,
                workingTimeId: periodOfWork.workingTimeId
            };

            timelineService.updatePeriodOfWork(jsonUpdatePeriodOfWork)
                .then(function (data) {
                    alertify.success(data.data)
                })
                .catch(function (data) {
                    alertify.error("error " + data)
                });


            $scope.formatTime = function (datetime) {
                return timePatternService.formatLocalDateTimeToTime(datetime);
            }

            $scope.formatDay = function (day) {
                return timePatternService.formatLocalDateToDate(day);
            }

        }


    });


