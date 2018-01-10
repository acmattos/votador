import 'bootstrap/dist/css/bootstrap.min.css';

import angular         from 'angular';
import ngUiBootstrap   from 'angular-ui-bootstrap';
import ngUiRouter      from 'angular-ui-router';

import appConfig       from '../config/app.config';
import indexController from './index.controller';

import login           from './login';
import participantes   from './eleicao/participantes';
import recursos        from './eleicao/recursos';
import resultados      from './eleicao/resultados';

import oauth2          from './oauth2';
import appRun          from './oauth2/oauth2.run';

export default angular.module('votador.fe', [
   ngUiBootstrap,
   ngUiRouter,
   login,
   participantes,
   recursos,
   resultados,
   oauth2
])
.constant('APP_NAME', 'VOTADOR FE')
.config(appConfig)
.run(appRun)
.controller('indexController', indexController)
.name;