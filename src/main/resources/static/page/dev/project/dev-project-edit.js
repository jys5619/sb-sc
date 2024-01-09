const ngDevProjectEdit = () => {
  this.target = "list";
  this.search = {};
  this.list = [];
  this.detail = id => {
    this.target = "detail";
  };
};

ngApp.component("devProjectEdit", {
  bindings: { subject: "<" },
  templateUrl: "/page/dev/project/dev-project-edit.html",
  controller: ngDevProjectEdit,
});
