function ngRoot() {
  this.mainMenuList = [
    {
      id: "home",
      title: "Home",
      url: "/home/home.html",
    },
    {
      id: "myWork",
      title: "진행업무",
      menu: [
        {
          id: "devProject",
          title: "개발 프로젝트",
          url: "/dev/project/dev-project.html",
        },
        {
          id: "devMenu",
          title: "개발 메뉴",
          url: "/main/main4.html",
        },
        {
          id: "devTodo",
          title: "TODO",
          url: "/main/main3.html",
        },
      ],
    },
    {
      id: "board",
      title: "게시판",
      menu: [
        {
          id: "boardJob",
          title: "업무게시판",
          url: "/main/main4.html",
        },
        {
          id: "boardJob1",
          title: "일반게시판",
          url: "/main/main3.html",
        },
      ],
    },
    {
      id: "admin",
      title: "ADMIN",
      menu: [
        {
          id: "adminProject",
          title: "프로젝트 관리",
          url: "/dev/project/dev-project.html",
        },
        {
          id: "adminBoard",
          title: "게시판관리",
          url: "/main/main.html",
        },
      ],
    },
    {
      id: "memo",
      title: "MEMO",
      url: "/main/main.html",
    },
    {
      id: "test",
      title: "TEST",
    },
  ];

  this.activeMenuList = this.mainMenuList.filter(m => m.id === "home");
  this.mainMenuList = this.mainMenuList.filter(m => m.id !== "home");
  this.currentActiveMenu = this.activeMenuList[0];
  this.currentMainMenu = this.mainMenuList[0];
  this.showSubMenu = true;

  this.selectMainMenu = menu => {
    this.currentMainMenu = menu;
    if (!menu.menu || menu.menu.length === 0) {
      this.appendActiveMenu(menu);
    }
  };

  this.isCurrentMainMenu = menu => {
    return this.currentMainMenu?.id === menu.id;
  };

  this.appendActiveMenu = menu => {
    const findMenu = this.activeMenuList.find(m => m.id === menu.id);
    if (!findMenu) {
      if (!menu.url) {
        alert("등록할 수 없는 메뉴 입니다.");
        return;
      }

      this.activeMenuList.push(menu);
      this.setActiveMenu(menu);
    } else {
      this.setActiveMenu(findMenu);
    }
  };

  this.removeActiveMenu = menu => {
    if (menu.id === this.currentActiveMenu.id) {
      const findIndex = this.activeMenuList.findIndex(m => m.id === menu.id);
      this.activeMenuList = this.activeMenuList.filter(m => m.id !== menu.id);
      this.setActiveMenu(this.activeMenuList[findIndex - 1]);
    } else {
      this.activeMenuList = this.activeMenuList.filter(m => m.id !== menu.id);
    }
  };

  this.setActiveMenu = menu => {
    this.currentActiveMenu = menu;
  };

  this.toggleSubMenu = () => {
    this.showSubMenu = !this.showSubMenu;
  };
}

ngApp.component("root", {
  templateUrl: "/page/root.html",
  controller: ngRoot,
});
