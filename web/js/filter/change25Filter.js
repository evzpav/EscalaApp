angular.module("escala").filter('change25Filter', function () {
    return function (hour) {
        if(hour===25){
            return '01';
        }else if(hour === 24) {
             return '00';
        }else{
            return hour;
        }
    };

});