const ngDevProjectInfo = () => {
  this.target = "list";
  this.search = {};
  this.list = [];
  this.detail = id => {
    this.target = "detail";
  };
};

ngApp.component("devProjectInfo", {
  bindings: { subject: "<" },
  templateUrl: "/page/dev/project/dev-project-info.html",
  controller: ngDevProjectInfo,
});
