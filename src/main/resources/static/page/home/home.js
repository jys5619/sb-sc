const ngHome = () => {
  this.target = "list";
  this.search = {};
  this.list = [];
  this.detail = id => {
    this.target = "detail";
  };
};
ngApp.component("home", {
  bindings: { subject: "<" },
  templateUrl: "/page/home/home.html",
  controller: ngHome,
});
