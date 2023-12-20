const scApp = angular.module("sc", []);

scApp.controller("ScCtrl", function ($scope, $http) {
  $scope.server_url = "http://localhost:8992";

  $scope.search = {};
  $scope.search.target = "table";
  $scope.search.keyword = "";
  $scope.search.loading = false;

  $scope.data = {};

  $scope.data.table = {};
  $scope.data.table.table = {};
  $scope.data.table.column = {};
  $scope.data.table.index = {};

  $scope.data.column = {};
  $scope.data.column.column = {};

  $scope.data.dom = {};
  $scope.data.dom.tree = {};
  $scope.data.dom.attribute = {};
  $scope.data.dom.relation = {};
  $scope.data.dom.column = {};
  $scope.data.dom.index = [];

  $scope.data.organization = {};
  $scope.data.organization.organization = {};

  $scope.data.menu = {};
  $scope.data.menu.menu = {};

  $scope.searchKeydown = event => {
    if (event.keyCode === 13) {
      $scope.search($scope.search.keyword);
    }
  };

  $scope.search = keyword => {
    if (!keyword) return;
    $scope.search.loading = true;
    if ($scope.search.target === "table") {
      $scope.searchTableInfo(keyword);
    } else if ($scope.search.target === "column") {
      $scope.searchColumnInfo(keyword);
    } else if ($scope.search.target === "dom") {
      $scope.searchDomInfo(keyword);
    } else if ($scope.search.target === "organization") {
      $scope.searchOrganizationInfo(keyword);
    } else if ($scope.search.target === "menu") {
      $scope.searchMenuInfo(keyword);
    }
  };

  $scope.searchTableInfo = keyword => {
    $scope.data.table.table = [];
    $scope.data.table.column = [];
    $scope.data.table.index = [];

    $http.get(`${$scope.server_url}/table/${keyword}`).then(
      function successCallback(response) {
        const { data } = response;
        $scope.data.table.table = data.table;
        $scope.data.table.column = data.column;
        $scope.data.table.index = data.index;
        $scope.search.loading = false;
      },
      function errorCallback(response) {
        $scope.search.loading = false;
      }
    );
  };

  $scope.searchColumnInfo = keyword => {
    $scope.data.column.column = [];

    $http.get(`${$scope.server_url}/column/${keyword}`).then(
      function successCallback(response) {
        const { data } = response;
        $scope.data.column.column = data.column;
        $scope.search.loading = false;
      },
      function errorCallback(response) {
        $scope.search.loading = false;
      }
    );
  };

  $scope.searchDomInfo = keyword => {
    $scope.data.dom.tree = [];
    $scope.data.dom.attribute = [];
    $scope.data.dom.relation = [];
    $scope.data.dom.column = [];
    $scope.data.dom.index = [];

    $http.get(`${$scope.server_url}/dom/${keyword}`).then(
      function successCallback(response) {
        const { data } = response;
        $scope.data.dom.tree = data.tree.map(row => {
          return {
            className: row.className,
            targetClassName: row.targetClassName,
            lvl: row.lvl,
          };
        });
        $scope.data.dom.attribute = data.attribute;
        $scope.data.dom.relation = data.relation;
        $scope.data.dom.column = data.column;
        $scope.data.dom.index = data.index;
        $scope.search.loading = false;
      },
      function errorCallback(response) {
        $scope.search.loading = false;
      }
    );
  };

  $scope.searchMenuInfo = keyword => {
    $scope.data.menu.menu = [];

    $http.get(`${$scope.server_url}/menu/${keyword}`).then(
      function successCallback(response) {
        const { data } = response;
        $scope.data.menu.menu = data.menu;
        $scope.search.loading = false;
      },
      function errorCallback(response) {
        $scope.search.loading = false;
      }
    );
  };

  $scope.searchOrganizationInfo = keyword => {
    $scope.data.organization.organization = [];

    $http.get(`${$scope.server_url}/organization/${keyword}`).then(
      function successCallback(response) {
        const { data } = response;
        $scope.data.organization.organization = data.organization;
        $scope.search.loading = false;
      },
      function errorCallback(response) {
        $scope.search.loading = false;
      }
    );
  };
});
