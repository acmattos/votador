export default class User {
   constructor(token) {
      this._email = token.user_name;
   }
   
   get email() {
      return this._email;
   }
}
