import angular from 'angular';

import controller from './participantes.controller';
import route from './participantes.route';

export default angular.module('votador.fe.eleicao.participantes',[])
.controller('participantesController', controller)
.config(route)
.name;