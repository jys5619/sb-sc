const ngDevProject = () => {
  this.target = "list";
  this.search = {};
  this.list = [];
  this.detail = id => {
    this.target = "detail";
  };
};

ngApp.component("devProject", {
  bindings: { subject: "<" },
  templateUrl: "/page/dev/project/dev-project.html",
  controller: ngDevProject,
});
