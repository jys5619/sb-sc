const devApp = angular.module("dev", []);

devApp.controller("DevCtrl", function ($scope, $http) {
  $scope.server_url = "http://localhost:8992";

  $scope.target = "project";
  $scope.loading = false;

  $scope.project = {};
  // $scope.project.id = "";
  // $scope.project.projectName = "";
  // $scope.project.discriptions = "";
  // $scope.project.planStartDate = "";
  // $scope.project.planStartDateDT = null;
  // $scope.project.planEndDate = "";
  // $scope.project.planEndDateDT = null;
  // $scope.project.realStartDate = "";
  // $scope.project.realStartDateDT = null;
  // $scope.project.realEndDate = "";
  // $scope.project.realEndDateDT = null;
  // $scope.project.modifier = "";
  // $scope.project.modified = "";
  // $scope.project.modifiedDTM = null;
  // $scope.project.creator = "";
  // $scope.project.created = "";
  // $scope.project.createdDTM = null;

  $scope.setProjectDate = () => {
    $scope.project.planStartDate = convertDateString($scope.project.planStartDateDT);
    $scope.project.planEndDate = convertDateString($scope.project.planEndDateDT);
    $scope.project.realStartDate = convertDateString($scope.project.realStartDateDT);
    $scope.project.realEndDate = convertDateString($scope.project.realEndDateDT);
    $scope.project.modified = convertDateTimeString($scope.project.modifiedDTM);
    $scope.project.created = convertDateTimeString($scope.project.createdDTM);
  };

  $scope.setProjectDateString = () => {
    $scope.project.planStartDateDT = convertDate($scope.project.planStartDate);
    $scope.project.planEndDateDT = convertDate($scope.project.planEndDate);
    $scope.project.realStartDateDT = convertDate($scope.project.realStartDate);
    $scope.project.realEndDateDT = convertDate($scope.project.realEndDate);
    $scope.project.modifiedDTM = convertDateTime($scope.project.modified);
    $scope.project.createdDTM = convertDateTime($scope.project.created);
  };

  $scope.successCallback = response => {
    $scope.loading = false;
    const { data } = response;
    for (let keyKey of Object.keys(data)) {
      if (!data[keyKey]) continue;
      let dataObject = data[keyKey];
      for (let key of Object.keys(dataObject)) {
        if (!dataObject[key]) continue;
        if (dataObject[key] instanceof Object) {
          if ("date" in dataObject[key]) {
            dataObject[key].date = convertDate(dataObject[key].val);
          }
          if ("datetime" in dataObject[key]) {
            dataObject[key].datetime = convertDate(dataObject[key].val);
          }
        }
      }
    }
  };

  function setResponseDataDate(data) {
    for (let keyKey of Object.keys(data)) {
      if (!data[keyKey]) continue;
      let dataObject = data[keyKey];
      for (let key of Object.keys(dataObject)) {
        if (!dataObject[key]) continue;
        if (dataObject[key] instanceof Object) {
          if ("date" in dataObject[key]) {
            dataObject[key].date = convertDate(dataObject[key].val);
          }
          if ("datetime" in dataObject[key]) {
            dataObject[key].datetime = convertDate(dataObject[key].val);
          }
        } else if (dataObject[key] instanceof Array) {
          for (let arrData of dataObject[key]) {
            setResponseDataDate(arrData);
          }
        }
      }
    }
  }

  $scope.searchDevProject = () => {
    $scope.project = {};

    $http.get(`${$scope.server_url}/dev/project`).then(
      function successCallback(response) {
        $scope.successCallback(response);
        const { data } = response;
        $scope.project = data.project;
        $scope.loading = false;
      },
      function errorCallback(response) {
        $scope.loading = false;
      }
    );
  };

  $scope.saveDevProject = data => {
    $http.post(`${$scope.server_url}/dev/project`, JSON.stringify(data)).then(
      response => ($scope.loading = false),
      response => ($scope.loading = false)
    );
  };
});
