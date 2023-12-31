// ngApp.component("root", {
//     templateUrl: '/root.html',
//     controller: ngRoot,
// });
// ngApp.component('devProject', {
//     templateUrl: '/dev/project/dev-project.html',
//     controller: ngDevProject,
// });



(function (angular) {
    'use strict';
    angular.module('ng-app', [])
        .component("root", {
            templateUrl: '/root.html',
            controller: ngRoot,
        })
        .component('devProject', {
            bindings: { title: '<' },
            templateUrl: '/dev/project/dev-project.html',
            controller: ngDevProject,
        });
})(window.angular);



