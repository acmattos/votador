import angular from 'angular';

import controller from './recursos.controller';
import route from './recursos.route';

export default angular.module('votador.fe.eleicao.recursos',[])
.controller('recursosController', controller)
.config(route)
.name; 