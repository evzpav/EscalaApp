angular.module("escala").service("loginService", function ($localStorage) {


    this.saveUser = function(data){
        $localStorage.currentUser = data;
    };

    this.retrieveUser = function(){
        return $localStorage.currentUser;
    };

    this.logout = function(){
        $localStorage.$reset()
    };

});