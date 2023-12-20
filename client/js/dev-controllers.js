const devApp = angular.module("dev", []);

devApp.controller("DevCtrl", function ($scope, $http) {
  $scope.server_url = "http://localhost:8992";

  $scope.target = "project";
  $scope.loading = false;

  $scope.data = {};

  $scope.successCallback = response => {
    const { data } = response;
    setResponseData(data);
    $scope.data = { ...$scope.data, ...data };
    $scope.loading = false;
  };

  $scope.errorCallback = response => {
    $scope.loading = false;
  };

  $scope.get = (url) => {
    $http.get(`${$scope.server_url}${url}`).then(response => {
      const { data } = response;
      processResponseData(data);
      $scope.data = { ...$scope.data, ...data };
      $scope.loading = false;
    }, response => {
      $scope.loading = false;
    });
  }

  $scope.post = (url, data) => {

    $http.post(`${$scope.server_url}${url}`, getRequestData(data)).then(response => {
      $scope.loading = false;
    }, response => {
      $scope.loading = false;
    });
  }

  $scope.searchDevProject = () => {
    $scope.data.project = {};
    $scope.get('/dev/project');
  };

  $scope.saveDevProject = data => {
    $scope.post("/dev/project", data);
  };
});
