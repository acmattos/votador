import angular                 from 'angular';

import oauth2ServiceModule      from './oauth2.service.module';
import requestInterceptorModule from './request.interceptor.module';
import userServiceModule        from './user.service.module';
import tokenServiceModule       from './token.service.module';

/**
 * Modulo que define os servicos de manipulacao de tokens OAuth2.
 */ 
export default angular.module(
'votador.fe.oauth2.localstorage', [ 
   oauth2ServiceModule, 
   requestInterceptorModule, 
   tokenServiceModule,
   userServiceModule
]).name;