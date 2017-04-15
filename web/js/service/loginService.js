angular.module("escala").service("loginService", function () {

    var email;

    this.saveEmail = function(data){
        email = data;
    };

    this.retrieveEmail = function(){
        return email;
    };

});