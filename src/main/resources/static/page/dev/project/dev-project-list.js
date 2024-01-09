const ngDevProjectList = () => {
  this.target = "list";
  this.search = {};
  this.list = [];
  this.detail = id => {
    this.target = "detail";
  };
};

ngApp.component("devProjectList", {
  bindings: { subject: "<" },
  templateUrl: "/page/dev/project/dev-project-list.html",
  controller: ngDevProjectList,
});
