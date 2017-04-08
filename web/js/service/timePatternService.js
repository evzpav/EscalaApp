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

    function formatHourToSave(obj) {
        var hourFirstHalf = obj.slice(0, 2);
        var hourSecondHalf = obj.slice(2, 4);
        return hourFirstHalf.concat(":", hourSecondHalf)

    }

    return {
        getTimePattern: getTimePattern,
        formatLocalDateTimeToTime: formatLocalDateTimeToTime,
        formatLocalDateToDate: formatLocalDateToDate,
        saveOrUpdateTimePattern: saveOrUpdateTimePattern,
        formatHourToSave: formatHourToSave
    }
});