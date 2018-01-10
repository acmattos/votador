function routing($urlRouterProvider, $locationProvider) {
   $locationProvider.html5Mode(false);
   $urlRouterProvider.otherwise('/');
}

export default function config($urlRouterProvider, $locationProvider, 
                               $httpProvider){
   routing($urlRouterProvider, $locationProvider);
}
config.$inject = [
   '$urlRouterProvider',
   '$locationProvider',
   '$httpProvider'
];