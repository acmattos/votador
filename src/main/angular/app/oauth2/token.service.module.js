import angular        from 'angular';
import ngLocalStorage from 'angular-local-storage';

import TokenService   from './token.service';

/**
 * Modulo que define o servico de armazenamento de tokens OAuth2.
 */
export default angular.module(
'votador.fe.oauth2.localstorage.token', [ngLocalStorage])
.factory('tokenService', ['$log', 'localStorageService', '$q', 
   (log, localStorageService, q) => new TokenService(log, localStorageService, q) ])
.config(['localStorageServiceProvider', function (localStorageServiceProvider) {
   localStorageServiceProvider.setPrefix('oauth2');
   localStorageServiceProvider
   .setStorageCookie(45, 'oauth2', false);
   localStorageServiceProvider.setNotify(true, true);
}])   
.name;