import angular      from 'angular';

import UserService from './user.service';

/**  
 * Modulo que define o servico de usuario.
 */
export default angular.module(
'votador.fe.oauth2.localstorage.user', [])
.factory('userService', [ '$q', '$log', 'tokenService', 
   ($q, $log, tokenService) => new UserService($q, $log, tokenService) 
]).name;