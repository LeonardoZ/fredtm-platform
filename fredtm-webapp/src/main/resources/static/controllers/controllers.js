fred.controller('loginController', ['$scope', '$http', function ($scope, $http) {
    $http.defaults.headers.post['X-Requested-With'] = 'XMLHttpRequest';
    $http.defaults.headers.post['Access-Control-Allow-Origin'] = 'http://localhost:8080/fredweb';
    
    $scope.onLoginSubmit = function () {
        var user = $scope.user;
        $http.post('http://localhost:9000/fredapi/account/login', user).success(function (data, result) {
            console.log(data);
            console.log(result);
        });
    };




}]);