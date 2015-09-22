fred.config(['$httpProvider',function ($httpProvider) {
    $httpProvider.defaults.headers.post['X-Requested-With'] = 'XMLHttpRequest';
    
    $httpProvider.defaults.headers.post['Access-Control-Allow-Origin'] = 'http://localhost:8080/fredweb' ;
    
}]);