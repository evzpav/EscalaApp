angular.module("escala").filter('formatDateFilter', function () {
    return function (localDate) {
            return moment()
                .year(localDate.year)
                .month(localDate.month -1)
                .date(localDate.day)
                .locale('pt-BR')
                .format('DD/MM/YYYY dddd');


    };

});