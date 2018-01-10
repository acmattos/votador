export default class ResultadosController{
    constructor(log, oAuth2Service) {
        let vm = this;
        vm._log = log;
        vm._oAuth2Service = oAuth2Service;
        vm.resultados = {};
        vm.mensagem = '';
        this.carregarDados(); 
    }
    
    carregarDados() {
       let self = this;
       self._oAuth2Service.http(this._getRequest(
          'GET',
          '/eleicao/resultados'))
       .then((response) => {
          self.resultados = response.data;
       }).catch((erro)=>{
           self.mensagem = 'Nenhum recurso disponível no momento!';
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

ResultadosController.$inject = [
   '$log',
   'oAuth2Service'
];
