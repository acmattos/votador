import angular  from 'angular';

import loginTemplate   from './login.template.html';
import loginController from './login.controller';
import loginRoute      from './login.route';

export default angular.module('votador.fe.oauth2.login', [])
.controller('loginController', loginController)
.config(loginRoute)
.name;