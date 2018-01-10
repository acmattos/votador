/* jshint ignore:start */
function initStorage($rootScope, $state, $log, oAuth2Service, tokenService, 
   userService, APP_NAME) {
   authAndAuth($rootScope, $state, $log, oAuth2Service, tokenService, 
      userService);
   $log.info('LocalStorage configurado!\n-=[' + APP_NAME + ']=-');
}

function authAndAuth($rootScope, $state, $log, oAuth2Service, tokenService, 
   userService) {
   $rootScope.$on('$stateChangeStart', function(event, toState, toParams, 
      fromState, fromParams, options){
      onStateChangeStart(event, toState, toParams, fromState, fromParams, 
         options,$rootScope, $state, $log, oAuth2Service, tokenService, userService);
   });
}

function onStateChangeStart(event, toState, toParams, fromState, fromParams, 
   options, $rootScope, $state, $log, oAuth2Service, tokenService, userService){
   $log.debug('authAndAuth() - $stateChangeStart - toState:', toState.name);
   if (toState.authorize) {
      tokenService.isAccessTokenExpired().then(
      function(isExpired){ // Sucesso na resposta isExpired
         if (isExpired){
            oAuth2Service.refreshToken().then(
            function(success){ // Sucesso - refreshToken
               $log.debug('authAndAuth() - $stateChangeStart: refreshToken OK!');
            },
            function(error){ // FALHA - refreshToken
               tokenService.removeTokens();
               $rootScope.$evalAsync(function () {
                  $log.debug('LOGIN_URL:', LOGIN_URL);   
                  $state.go('/'); // Tela de Login!
               });
            });
         } else if (!isGrantedToUser(toState.authorize, userService)){
            $log.info('authAndAuth() - Para seguir, você precisa ter: ', 
            toState.authorize);
            event.preventDefault();
         }
      },
      function(error){ // FALHA na resposta isExpired
         $rootScope.$evalAsync(function () {
            $state.go('/'); // Vai para tela de login!
         });
      });
   }            
}

function isGrantedToUser(grantedAuths, userService){
   if(Array.isArray(grantedAuths)){
      let user = (userService) ? userService.getUser() : {};
      let userAuths = (user.grantedAuths) ? user.grantedAuths : grantedAuths;
      return grantedAuths.some(e => userAuths.indexOf(e) >= 0);
   }
   return true;
}
export default function auth2($rootScope, $state, $log, oAuth2Service, 
   tokenService, userService, APP_NAME) {
   initStorage($rootScope, $state, $log, oAuth2Service, tokenService, 
      userService, APP_NAME);
}

auth2.$inject = [
   '$rootScope', 
   '$state', 
   '$log', 
   'oAuth2Service', 
   'tokenService',
   'userService',
   'APP_NAME'
];
/* jshint ignore:end */