angular.module("escala").factory("timePatternService", function ($http, linkValues) {

    function getTimePattern(employeeId) {
        return $http.post(linkValues.UrlGetEmployee, {employeeId: employeeId})
            .then(function (data) {
                return data.data;
            });
    }

    function parseLocalDateTime(localDateTime) {
        return moment()
            .year(localDateTime.date.year)
            .month(localDateTime.date.month)
            .date(localDateTime.date.day)
            .hour(localDateTime.time.hour)
            .minute(localDateTime.time.minute);
    }

    function formatLocalDateTimeToTime(dateFromJson) {
        return parseLocalDateTime(dateFromJson).format('HHmm')
    }


    function parseLocalDate(localDate) {
        return moment()
            .year(localDate.year)
            .month(localDate.month)
            .date(localDate.day)

    }

    function formatLocalDateToDate(dateFromJson) {
        return parseLocalDate(dateFromJson).format('DD/MM/YYYY');
    }

    function saveOrUpdateTimePattern(obj, isNewTimePattern) {
        if (isNewTimePattern) {
            return $http.post(linkValues.UrlAddEmployeeTimePattern, obj)
                .then(function (data) {
                    return data.data;
                }).catch(function (data) {
                    return data.data
                })

        } else {
            return $http.post(linkValues.UrlUpdateEmployeeTimePattern, obj)
                .then(function (data) {
                    return data.data;
                }).catch(function (data) {
                    return data.data
                })
        }
    }

    function formatHourToSave(time) {
        var hourFirstHalf = time.slice(0, 2);
        var hourSecondHalf = time.slice(2, 4);
        return hourFirstHalf.concat(":", hourSecondHalf)
    }

    function isValidTimePattern(startTime, intervalStart, intervalEnd, endTime) {
        var startTimeInt;
        var intervalStartInt;
        var intervalEndInt;
        var endTimeInt;

        startTimeInt = parseInt(startTime,10);
        intervalStartInt = parseInt(intervalStart,10);
        intervalEndInt = parseInt(intervalEnd,10);
        endTimeInt = parseInt(endTime,10);
        if (startTimeInt > 2359 || intervalStartInt > 2359 || intervalEndInt > 2359 || endTimeInt > 2359) {
            return false;
        } else if (startTimeInt < 600) {
            return false;
        } else if (endTimeInt > 100 && intervalEndInt > endTimeInt) {
            return false;

        } else if (startTimeInt > intervalStartInt || intervalStartInt > intervalEndInt) {
            return false;
        } else {
            return true;
        }
    }


    return {
        getTimePattern: getTimePattern,
        formatLocalDateTimeToTime: formatLocalDateTimeToTime,
        formatLocalDateToDate: formatLocalDateToDate,
        saveOrUpdateTimePattern: saveOrUpdateTimePattern,
        formatHourToSave: formatHourToSave,
        isValidTimePattern: isValidTimePattern
    }
});