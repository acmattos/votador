import templateHtml from './resultados.template.html';

export default function routes(stateProvider) {
    stateProvider
    .state('resultados', {
            url: '/resultados',
            template: templateHtml,
            controller: 'resultadosController',
            controllerAs: 'vm',
            authorize: true
    });
}

routes.$inject = ['$stateProvider'];