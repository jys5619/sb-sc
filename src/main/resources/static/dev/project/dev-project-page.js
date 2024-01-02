function ngDevProject() {
  this.title;
  this.target = "list";
  this.search = {};
  this.list = [];
  this.detail = id => {
    this.target = "detail";
  };
  //this.name = store().getData().user.name;
}
