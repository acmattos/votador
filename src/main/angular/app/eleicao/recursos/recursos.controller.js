export default class RecursosController{
    constructor(filter, log, timeout, localStorageService, oAuth2Service, userService) {
        let vm = this;
        vm._filter = filter;
        vm._log = log;
        vm._localStorageService = localStorageService;
        vm._oAuth2Service = oAuth2Service;
        vm._userService = userService;
        vm.recursos = {};
        vm.mensagem = '';
        timeout(function(){ 
            vm._carregarDados();
         }, 2000);
    }
    
   _carregarDados() {
      let self = this;
      let funcionario = this._obtemFuncionario();
      self._oAuth2Service.http(this._getRequest(
         'GET',
         '/eleicao/recursos/'.concat(funcionario.id)))
      .then((response) => {
         self.recursos = response.data;
      }).catch((erro) => {
        self.recursos = {};
        self.mensagem = `Você já votou em todos os recursos 
           ou não existem recursos disponíveis para voto!`; 
     });
   }    
       
   votar(recursoId, comentario) {
      let self = this;
      let funcionario = this._obtemFuncionario();
      let datahora = self._filter('date')(new Date(), "yyyy-MM-dd HH:mm:ss Z");
      let voto = self._criaVoto(
         comentario, datahora, funcionario.id, recursoId);
      self._oAuth2Service.http(self._getRequest(
         'POST',
         '/eleicao/votos', voto))
      .then((response) => {
         voto = response.data;
         self._oAuth2Service.http(self._getRequest(
            'GET',
            '/eleicao/recursos/'.concat(funcionario.id)))
         .then((response) => {
            self.recursos = response.data;
         }).catch((erro) => {
            self.recursos = {};
            self.mensagem = `Você já votou em todos os recursos
               ou não existem recursos disponíveis para voto!`;  
         });
      });
   }

   _obtemFuncionario() {
      let self = this;
      let funcionario = self._localStorageService.get('funcionario');
      if(!funcionario){
      self._userService.getUser().then(
        (usuario) => {
           self._oAuth2Service.http(
              self._getRequest('GET', '/funcionarios')).then(
              (response) => {
               let funcionarios = response.data;
               funcionario = funcionarios.filter(function(funcionario){
                  if(usuario.email === funcionario.email){
                      return funcionario;
                  }
               });
               self.funcionario = funcionario[0];
               self._localStorageService.set('funcionario', self.funcionario);
           });
        });
      }
      return funcionario;
   }
       
  _criaVoto(comentario, datahora, funcionarioId, recursoId){
      let voto = {
         "comentario": comentario,
         "datahora": datahora,
         "funcionarioVO":{
            "id": funcionarioId
         },
         "recursoVO":{
            "id": recursoId
         }         
      };
      return voto;
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
}

RecursosController.$inject = [
   '$filter',
   '$log',
   '$timeout',
   'localStorageService',
   'oAuth2Service',
   'userService'
];
