import templateHtml from './participantes.template.html';

export default function routes(stateProvider) {
    stateProvider
    .state('participantes', {
        url: '/participantes',
        template: templateHtml,
        controller: 'participantesController',
        controllerAs: 'vm',
        authorize: true,
    });
}

routes.$inject = ['$stateProvider'];