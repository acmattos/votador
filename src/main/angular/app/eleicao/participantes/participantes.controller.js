export default class ParticipantesController{
    constructor(log, oAuth2Service) {
        let vm = this;
        vm._log = log;
        vm._oAuth2Service = oAuth2Service;
        vm.participantes = {};
        vm.mensagem = '';
        this.carregarDados(); 
    }
    
    carregarDados() {
       let self = this;
       self._oAuth2Service.http(this._getRequest(
          'GET',
          '/eleicao/participantes'))
       .then((response) => {
          self.participantes = response.data;
       }).catch((erro)=>{
          self.mensagem = 'Nenhum participante votou até o momento!';
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
}

ParticipantesController.$inject = [
   '$log',
   'oAuth2Service'
];
