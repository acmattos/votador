import jwtDecode from 'jwt-decode';

/**
 * Servico de manipulacao do token OAuth2. Depende das bibliotecas 'jwt-decode'
 * e 'angular-local-storage'.
 * @export
 * @class TokenService
 */
export default class TokenService{
   constructor(log, localStorageService, q){
      let vm = this;
      vm._log = log;  
      vm._localStorageService = localStorageService;
      vm._q = q;
   }

   /**
    * @returns Uma promessa que contem o access token OAuth2 armazernado ou 
    *          vazio.
    * @memberof TokenService
    */
   getAccessToken(){
      return this._getTokenBy('access_token');
   }

   /**
    * @returns Uma promessa que contem refresh token OAuth2 armazernado ou 
    *          vazio.
    * @memberof TokenService
    */
   getRefreshToken(){
      return this._getTokenBy('refresh_token');
   }
   
   /**
    * @param {any} name 'access_token' ou 'refresh_token'.
    * @returns Uma promessa que contem o token OAuth2 armazernado.
    * @memberof TokenService
    */
   _getTokenBy(name){
      let self = this;
      let defeered = self._q.defer();
      let token = self._localStorageService.get(name);
      defeered.resolve(token);
      return defeered.promise;
   }   
   
   /**
    * @param {any} token Access token OAuth2.
    * @returns Uma promessa que indica que o token foi armazenado.
    * @memberof TokenService
    */
   setAccessToken(token){
      return this._setTokenBy('access_token', token);
   }

   /**
    * @param {any} token Refresh token OAuth2.
    * @returns Uma promessa que indica que o token foi armazenado.
    * @memberof TokenService
    */
   setRefreshToken(token){
      return this._setTokenBy('refresh_token', token);
   }

   /**
    * @param {any} name 'access_token' ou 'refresh_token'.
    * @param {any} token Token OAuth2.
    * @returns Uma promessa que indica que o token foi armazenado.
    * @memberof TokenService
    */
   _setTokenBy(name, token){
      let self = this;
      let defeered = self._q.defer();
      self._localStorageService.set(name, token);
      defeered.resolve(token);
      return defeered.promise;  
   }

   /**
    * @returns Uma promessa que indica que access e refresh tokens foram 
    *          apagados.
    * @memberof TokenService
    */
   removeTokens(){
      let self = this;
      let defeered = self._q.defer();     
      this._localStorageService.clearAll();
      defeered.resolve('Tokens removidos!');
      return defeered.promise;
   }

   /**
    * @returns Uma promessa que contem 'true' se o token existir e estiver 
    *          dentro da validade  ou falso, se estiver expirado ou nao existir.
    * @memberof TokenService
    */
   isAccessTokenExpired() {
      let self = this;
      let defeered = self._q.defer();
      self.getAccessToken().then(
      function (token) {
         if(token){
         defeered.resolve(self._isExpired(token));
         } else {
            defeered.reject('NO_TOKEN');
         }
      }, 
      null); 
      return defeered.promise;
   }

   /**
    * @returns Uma promessa que contem 'true' se o token existir e estiver 
    *          dentro da validade  ou falso, se estiver expirado ou nao existir.
    * @memberof TokenService
    */
   isRefreshTokenExpired() {
      let self = this;
      let defeered = self._q.defer();
      self.getRefreshToken().then(
      function (token) {
         defeered.resolve(self._isExpired(token));
      }, 
      null); 
      return defeered.promise;
   }

   /**
    * @param {any} token Token a ser avaliado.
    * @returns 'true' se o token existir e falso se estiver expirado ou nao 
    *           existir.
    * @memberof TokenService
    */
   _isExpired(token) {
      if(token){
         let exp = jwtDecode(token).exp;
         let now = Math.floor(Date.now() / 1000);
         return exp <= now;
      }
      return true;
   }

   /**
    * @returns Uma promessa que contem o access token decodificado (padrão JSON).
    * @memberof TokenService
    */
   getDecodedAccessToken() {
      let self = this;
      let defeered = self._q.defer();
      self.getAccessToken().then(
      function (data) {
         let decodedToken = {};
         if(data){
            decodedToken = jwtDecode(data);
         }
         defeered.resolve(decodedToken);
      }, 
      null); 
      return defeered.promise;      
   }
}