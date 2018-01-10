/* jshint ignore:start */
export default class RequestInterceptor {
   constructor(location, q, log, tokenService){
      this._location = location;
      this._q = q;
      this._log = log;
      this._tokenService = tokenService;
   }

   static factory(location, q, log) {
      return new RequestInterceptor(location, q, log);
   }

   request = (config) => {
      let self = this;
      let defeered = self._q.defer();
      self._log.debug('RequestInterceptor.request - config: ', config);
      config.headers = config.headers || {};
      if(config.url && (config.url.indexOf('angularjs') === -1 
         && config.url.indexOf('uib') === -1
         && config.url.indexOf('bootstrap') === -1)){
         self._tokenService.getAccessToken().then(
         function(token){ // Sucesso getAccessToken
            if(!(config.data && typeof config.data === 'string'  
                 && config.data.indexOf("refresh_token"))){
               if (token) {
                  config.headers.Authorization = 'Bearer ' + token;
               } else {
                  self._log.info(
                     'RequestInterceptor.request: Tentando acessar recurso protegido sem token!');
               }
            }
            defeered.resolve(config);
         },
         function(error){ // FALHA getAccessToken
            self._log.warn('RequestInterceptor.request: Falha ao obter access token!');
            defeered.reject(config);
         });
         return defeered.promise;
      } else {
         return config;   
      }
   }
   
   responseError = (response) => {
      this._log.debug('RequestInterceptor.responseError:', response.status);
      if (response.status === 401 || response.status === 403) {
         this._log.debug('ACCESS FORBIDEN!');
      }
      return this._q.reject(response);
   }
}
/* jshint ignore:end */