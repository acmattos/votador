import User from './user';

export default class UserService{
   constructor(q, log, tokenService){
      this._q = q;
      this._log = log;
      this._tokenService = tokenService;
   }
 
   /**
    * @returns Uma promessa que pode trazer ou nao um usuario.
    * @memberof UserService
    */
   getUser(){
      let self = this;
      let defeered = self._q.defer();
      self._tokenService.getDecodedAccessToken().then(
      function (decodedToken){
         defeered.resolve(new User(decodedToken));
      },
      null);
      return defeered.promise;
   }  
}