export default class IndexController {
   constructor(
      scope, state, log, localStorageService, oAuth2Service, userService) {
      let vm = this;
      vm._scope = scope;
      vm._state = state;
      vm._log = log;
      vm._localStorageService = localStorageService;
      vm._oAuth2Service = oAuth2Service;
      vm._userService = userService;
      vm.funcionario = {};
      vm.show = false;

      scope.$on('onLoginSuccessful', function(event, value) { 
         vm._log.debug('IndexControler - onLoginSuccessful', value);
         vm.show = value;
         vm._armazenaFuncionario();
      });
   }

   _armazenaFuncionario(){
      let self = this;
      self._userService.getUser().then(
      (usuario) => {
         self._oAuth2Service.http(
            self._getRequest('GET', '/funcionarios')).then(
            (response) => {
             let funcionarios = response.data;
             let funcionario = funcionarios.filter(function(funcionario){
                if(usuario.email === funcionario.email){
                    return funcionario;
                }
             });
             self.funcionario = funcionario[0];
             self._localStorageService.set('funcionario', self.funcionario);
         });
      });
   }

   _getRequest(method, uri, parameter){
      let request = {
          url:  uri,
          method: method
      };
      if(parameter && parameter.params){
          request.params = parameter.params;
      } else if(parameter){
          request.data = parameter;
      }
      return request;
   }

   logout() {
      this._oAuth2Service.logout();
      this.show = false;
      this._scope.$broadcast('onLogoutSuccessful', true);
      this._state.go('/');
   }
}

IndexController.$inject = [
   '$rootScope', 
   '$state',
   '$log',
   'localStorageService',
   'oAuth2Service',
   'userService'
];