import angular from 'angular';

import controller from './resultados.controller';
import route from './resultados.route';

export default angular.module('votador.fe.eleicao.resultados',[])
.controller('resultadosController', controller)
.config(route)
.name; 