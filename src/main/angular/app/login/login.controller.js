export default class LoginController{
   constructor(scope, state, log, oAuth2Service, tokenService) {
      let vm = this;
      vm._scope = scope; 
      vm._state = state;
      vm._log = log;
      vm._oAuth2Service = oAuth2Service; 
      vm._tokenService = tokenService;
      vm.erro = {};
      vm.funcionario = {};
      vm.funcionario.email = '';
      vm.funcionario.senha = '';
      vm._verifyPreviousAuthentication();      
   }
   
   login(){
      let self = this;
      let request = self._getRequest();
      self._oAuth2Service.login(request).then(
      function(data) {
         self._onLoginSuccessful();
      })
      .catch(function (error) {
         self.error = self._prepareErrorResponse(error);
      });
   }

   _verifyPreviousAuthentication(){
      let self = this;
      self._tokenService.getRefreshToken().then(
      function(refreshToken){
         if(refreshToken) {
            return self._oAuth2Service.refreshToken();
         }
      })
      .then(
      function(response){
         if(response && 200 == response.status){
            self._onLoginSuccessful();
         }
      })
      .catch(
      function(error){
         self._log.error(
           'LoginController._verifyPreviousAuthentication:' + 
           ' Não foi possível autenticar automaticamente!');
      });
   }

   _onLoginSuccessful(){
      let self = this;
      self._state.go('recursos');
      self._scope.$broadcast('onLoginSuccessful', true);
      self._log.debug('LoginController._onLoginSuccessful: OK!');
   }
  
   _getRequest(){
      let request = {
         method: 'POST',
         url: '/oauth/token',
         client: 'votador-fe',
         client_secret: 'secret',
         grant_type: 'password',
         username: this.funcionario.email,
         password: this.funcionario.senha
      };
      return request;
   }

   _prepareErrorResponse(response){
      let error = {};
      if(response.stack){
         error.status = -500;
         error.message = response.message;
      } else {
         error.status = response.status;
      }
      if(504 === response.status) {
         error.message = 'Servidor de autenticação fora do ar!';
      } else if(401 === response.status) {
         error.message = 
            'Acesso não autorizado! Verifique usuário e senha informados.';
      }
      return error;
   }   
}
LoginController.$inject = [
   '$rootScope',
   '$state',
   '$log',
   'oAuth2Service',
   'tokenService'
];