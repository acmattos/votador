import angular        from 'angular';

import RequestInterceptor from './request.interceptor';

export default angular.module(
'votador.fe.oauth2.localstorage.interceptor', [])
.factory('oAuth2RequestInterceptor', [
   '$location', '$q', '$log', 'tokenService',
   ($location, $q, $log, tokenService) => 
   new RequestInterceptor($location, $q, $log, tokenService)])
.config(['$httpProvider', function httpConfig($httpProvider) {
   $httpProvider.interceptors.push('oAuth2RequestInterceptor');
}]) 
.name;