import angular       from 'angular';

import OAuth2Service from './oauth2.service';

export default angular.module(
'votador.fe.oauth2.localstorage.oauth', [])
.factory('oAuth2Service', [
'$http', '$q', '$log', '$window', 'tokenService', 
($http, $q, $log , $window, tokenService) => 
   new OAuth2Service($http, $q, $log, $window, tokenService)
]).name;