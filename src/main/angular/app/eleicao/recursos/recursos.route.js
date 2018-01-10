import templateHtml from './recursos.template.html';

export default function routes(stateProvider) {
    stateProvider
    .state('recursos', {
        url: '/recursos',
        template: templateHtml,
        controller: 'recursosController',
        controllerAs: 'vm',
        authorize: true
    });
}

routes.$inject = ['$stateProvider'];