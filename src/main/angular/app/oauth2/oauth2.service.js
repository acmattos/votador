/**
 * Servico de conexao com o Servidor de Autenticacao OAuth2, utilizando
 * Local Storage.
 * @export
 * @class OAuth2Service
 */
export default class OAuth2Service {
   constructor(http, q, log, window, tokenService){
      this._http = http;
      this._q = q;
      this._log = log;
      this._window = window;
      this._tokenService = tokenService;
   }

   /**
    * Realiza o processo de logout do usuario da aplicacao.
    * @memberof OAuth2Service
    */
   logout() {
      let self = this;
      self._tokenService.removeTokens().then(
      function(success){
         self._log.debug('OAuth2Service.logout: SUCESSO!'); 
      },
      function(error){
         self._log.warn('OAuth2Service.logout: FALHA!', error); 
      });
   } 

   /**
    * @param {any} config Atributos de configuracao para realizacao do processo 
    *              de login.
    * @returns Uma promessa que indica ou nao que o login foi realizado com 
    *          sucesso.
    * @memberof OAuth2Service
    */
   login(config) {
      let self = this;
      let request = self._getLoginRequest(config);
      let defeered = this._q.defer(); 
      self._tokenService.removeTokens().then(
      function(success){
         self._http(request).then(
         function (response) { // Sucesso conexao HTTP
            self._log.debug('OAuth2Service.login - response.data.access_token:', 
               response.data.access_token); 
            self._tokenService.setAccessToken(response.data.access_token).then(
            function(success){ // Sucesso armazenamento access token
               let token = self._getRefreshRequest(config, 
                  response.data.refresh_token);
               self._tokenService.setRefreshToken(JSON.stringify(token)).then(
               function(success){ // Sucesso armazenamento refresh token
                  defeered.resolve(response);
               },
               function(error){ // FALHA armazenamento refresh token
                  self._log.error(
                     'OAuth2Service.login: Falha ao armazenar o refresh token');
                  defeered.reject(error);   
               });
            },
            function(error){ // FALHA armazenamento access token
               self._log.error(
                  'OAuth2Service.login: Falha ao armazenar o access token');
               defeered.reject(error);   
            });
         }, 
         function (error) { // FALHA conexao HTTP
            self._log.error(
               'OAuth2Service.login - Falha na autenticação com servidor: ', 
               error); 
            defeered.reject(error);
         });         
      });
      return defeered.promise;
   }
   
   /**
    * @param {any} config Atributos de configuracao para realizacao do processo 
    *              de login.
    * @returns Uma promessa que indica ou nao que o login foi realizado com 
    *          sucesso.
    * @memberof OAuth2Service
    */
   refreshToken() {
      let self = this;
      let defeered = this._q.defer();
      self._tokenService.getRefreshToken().then(
      function(token){ // Sucesso obter token
         let request = JSON.parse(token);
         self._http(request).then(
         function (response) { // Sucesso conexao HTTP
            self._log.debug('OAuth2Service.refreshToken - response.data.access_token: ', 
               response.data.access_token);
            self._tokenService.setAccessToken(response.data.access_token).then(
            function(success){ // Sucesso - setAccessToken
               let token = self._getRefreshRequest(
                  request, response.data.refresh_token);
               self._tokenService.setRefreshToken(JSON.stringify(token)).then(
               function(success){ // Sucesso - setRefreshToken
                  defeered.resolve(response);
               },
               function(error){ // FALHA - setRefreshToken
                  self._log.error(
                     'OAuth2Service.refreshToken: Falha setRefreshToken');
                  defeered.reject(error);   
               });   
            }, 
            function(error){ // FALHA - setAccessToken
               self._log.error(
                  'OAuth2Service.refreshToken: Falha setAccessToken');
               defeered.reject(error);
            });
         }, 
         function (error) { // FALHA conexao HTTP
            self._log.error(
               'OAuth2Service.refreshToken - Falha na autenticação com servidor: ', 
               error); 
            defeered.reject(error);
         });         
      },
      function(error){ // FALHA obter token
         self._log.error(
            'OAuth2Service.login - Falha ao obter o token: ', error); 
         defeered.reject(error);
      });
      return defeered.promise;
   }   
   
   /**
    * @param {any} config Atributos de configuracao para realizacao de chamadas
    *              HTTP.
    * @returns O recurso externo desejado.
    * @memberof OAuth2Service
    */
   http(config){
      var self = this;
      let defeered = this._q.defer();
      self._tokenService.isAccessTokenExpired().then(
      function(expired){ // Sucesso - isAccessTokenExpired
         if(expired){ // expired - Token expirado
            self.refreshToken().then(
            function(success){// Sucesso - refreshToken
               self._http(config).then(
               function (response){ // Sucesso - http 
                  defeered.resolve(response);
               }, 
               function(error){ // FALHA - http
                  self._log.error(
                     'OAuth2Service.http - Falha http expired: ', error);
                  defeered.reject(error);
               });
            },
            function(error){ // FALHA - refeshToken
               self._log.error(
                  'OAuth2Service.http - Falha refreshToken: ', error);
               defeered.reject(error);
            });
         } else { // expired - Token nao expirado
            self._http(config).then(
            function (response){ // Sucesso - http
               defeered.resolve(response);
            }, 
            function(error){ // FALHA - http
               self._log.warn(
                  'OAuth2Service.http - Falha http non-expired: ', error);
               defeered.reject(error);
            });
         }
      },
      function(error){ // FALHA - isAccessTokenExpired
         if('NO_TOKEN' === error){
            self._log.warn(
               'Requisição SEM token!!!', error);
            self._http(config).then(
            function(response){
               defeered.resolve(response);
            },
            function(error){ // FALHA - http
               self._log.error(
                  'OAuth2Service.http - Falha http expired: ', error);
               defeered.reject(error);
            });
         } else {
            self._log.error(
            'OAuth2Service.http - Falha isAccessTokenExpired: ', error);
            defeered.reject(error);
         }
      });
      return defeered.promise;
   }

   /**
    * @param {any} config Atributos de configuracao para montagem da requisição
    *                     de login.
    * @returns A requisição de login.
    * @memberof OAuth2Service
    */
   _getLoginRequest(config){
      let request = {
         url: config.url,
         method: config.method || 'POST', 
         headers: {
            'Content-Type' : 'application/x-www-form-urlencoded', 
            'Authorization': this._getAuthorization(config)
         },
         data:
            'username='    + config.username + 
            '&password='   + config.password +
            '&grant_type=' + config.grant_type +
            '&client='     + config.client 
      };
      return request;
   }

   /**
    * @param {any} config Atributos de configuracao para montagem da requisição
    *                     de refresh token.
    * @returns A requisição de refresh token.
    * @memberof OAuth2Service
    */
    _getRefreshRequest(config, token){
      let request = {
         url: config.url,
         method: config.method || 'POST', 
         headers: {
            'Content-Type' : 'application/x-www-form-urlencoded', 
            'Authorization': this._getAuthorization(config)
         },
         data: 
            'grant_type=refresh_token' +
            '&refresh_token=' + token 
      };
      return request;
   } 

   /**
    * @param {any} config Atributos de configuracao para montagem da requisição.
    * @returns A autorização da aplicação cliente codificada em base 64.
    * @memberof OAuth2Service
    */
   _getAuthorization(config){
      if(config.client){
         return 'Basic ' +  
            this._window.btoa(config.client + ':' + config.client_secret);
      }
      return config.headers.Authorization;
   }    
}