const ngApp = angular.module("ng-app", []);
ngApp.component("root", {
  templateUrl: "/root.html",
  controller: ngRoot,
});
ngApp.component("devProject", {
  templateUrl: "/dev/project/dev-project.html",
  controller: ngDevProject,
});

// (function (angular) {
//   "use strict";
//   angular
//     .module("ng-app", [])
//     .component("root", {
//       templateUrl: "/root.html",
//       controller: ngRoot,
//     })
//     .component("mainPage", {
//       bindings: { title: "<" },
//       templateUrl: "/main/main-page.html",
//       controller: function () {},
//     })
//     .component("devProject", {
//       bindings: { title: "<" },
//       templateUrl: "/dev/project/dev-project.html",
//       controller: ngDevProject,
//     });
// })(window.angular);
