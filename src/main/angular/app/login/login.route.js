import templateHtml from './login.template.html';

export default function routes(stateProvider) {
    stateProvider
        .state('/', {
            url: '/',
            template: templateHtml,
            controller: 'loginController',
            controllerAs: 'vm'
        })
        ;
}

routes.$inject = ['$stateProvider'];