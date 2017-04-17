angular.module("escala").factory("timelineService", function ($http, linkValues, $q) {

    function listTimelineForSelectedDate(date) {
        var promise = $q.defer();
        var obj = {selectedDate: date}
        $http.post(linkValues.UrlListTimelineWithSelectedDate, obj)
            .then(function (data) {
                promise.resolve(data.data);
            });
        return promise.promise;
    }


    function buildHours(start, end) {
        var hours = [];
        for (var i = start; i <= end; i++) {
            hours.push(i);
        }
        return hours;
    };

    function isWorking(periodOfWork, hour) {
        function firstPeriodWorking(hour) {
            return (hour >= periodOfWork.startTime.time.hour && hour < periodOfWork.intervalStart.time.hour)
        }

        function secondPeriodWorking(hour) {
            return (hour >= periodOfWork.intervalEnd.time.hour && hour < periodOfWork.endTime.time.hour)
        }

        if (periodOfWork.endTime.time.hour === 1) {
            return periodOfWork.working && (firstPeriodWorking(hour) ||
                (hour >= periodOfWork.intervalEnd.time.hour && hour <= 25) )
        } else if (periodOfWork.endTime.time.hour === 0) {
            return periodOfWork.working && (firstPeriodWorking(hour) ||
                (hour >= periodOfWork.intervalEnd.time.hour && hour <= 24) )
        } else {
            return (periodOfWork.working && (firstPeriodWorking(hour)) || (periodOfWork.working && secondPeriodWorking(hour)))
        }

    };


    function countWorking(periodOfWork, myHours, heatMapArray) {

        for (var i = 0; i < myHours.length; i++) {
            var hour = myHours[i];
            if (isWorking(periodOfWork, hour)) {
                var countTemp = heatMapArray[i];
                if (!countTemp) {
                    countTemp = 0;
                }
                countTemp++;
                heatMapArray[i] = countTemp;

            }
        }
    }

    function fillHeatMapArray(listWeekPeriodOfWork, myHours) {
        var heatMapArray = new Array(20);
        for (var i = 0; i < heatMapArray.length; i++) {
            heatMapArray[i] = 0;
        }
        for (var i = 0; i < listWeekPeriodOfWork.length; i++) {
            countWorking(listWeekPeriodOfWork[i].periodOfWork, myHours, heatMapArray);
        }
        return heatMapArray;
    }

    function updatePeriodOfWork(json) {
        return $http.post(linkValues.UrlUpdatePeriodOfWork, json);

    }

    return {
        listTimelineForSelectedDate: listTimelineForSelectedDate,
        fillHeatMapArray: fillHeatMapArray,
        buildHours: buildHours,
        isWorking: isWorking,
        updatePeriodOfWork: updatePeriodOfWork
    }
})
;