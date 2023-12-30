ngApp.controller("main-controller", function ($scope, $http) {
    let mainCtrl = this;

    mainCtrl.activeMenuList = [
        {
            id: "main",
            title: "메인메뉴",
            url: "/main/main.html"
        }
    ];
    mainCtrl.mainMenuList = [
        {
            id: 'myWork',
            title: "진행업무",
            menu: [
                {
                    id: 'devProject',
                    title: '개발 프로젝트',
                    url: "/main/main3.html",
                },
                {
                    id: 'devMenu',
                    title: '개발 메뉴',
                    url: "/main/main4.html",
                },
                {
                    id: 'devTodo',
                    title: 'TODO',
                    url: "/main/main3.html",
                }
            ]
        },
        {
            id: 'board',
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
            ]
        },
        {
            id: 'admin',
            title: "ADMIN",
            menu: [
                {
                    id: "adminProject",
                    title: "프로젝트 관리",
                    url: "/main/main4.html",
                },
                {
                    id: "adminBoard",
                    title: "게시판관리",
                    url: "/main/main.html",
                }
            ]
        },
        {
            id: 'memo',
            title: "MEMO",
            url: "/main/main.html",
        },
        {
            id: 'test',
            title: "TEST",
        },
    ];

    mainCtrl.currentActiveMenu = mainCtrl.activeMenuList[0];
    mainCtrl.currentMainMenu = mainCtrl.mainMenuList[0]
    mainCtrl.showSubMenu = true;

    mainCtrl.selectMainMenu = (menu) => {
        mainCtrl.currentMainMenu = menu;
        if (!menu.menu || menu.menu.length === 0) {
            this.appendActiveMenu(menu);
        }
    }

    mainCtrl.isCurrentMainMenu = (menu) => {
        return mainCtrl.currentMainMenu?.id === menu.id;
    };

    mainCtrl.appendActiveMenu = (menu) => {
        const findMenu = mainCtrl.activeMenuList.find(m => m.id === menu.id);
        if (!findMenu) {
            if (!menu.url) {
                alert("등록할 수 없는 메뉴 입니다.");
                return;
            }
            mainCtrl.activeMenuList.push(menu);
            mainCtrl.setActiveMenu(menu);
        } else {
            mainCtrl.setActiveMenu(findMenu);
        }
    }

    mainCtrl.removeActiveMenu = (menu) => {
        if (menu.id === mainCtrl.currentActiveMenu.id) {
            const findIndex = mainCtrl.activeMenuList.findIndex(m => m.id === menu.id);
            mainCtrl.activeMenuList = mainCtrl.activeMenuList.filter(m => m.id !== menu.id);
            mainCtrl.setActiveMenu(mainCtrl.activeMenuList[findIndex - 1]);
        } else {
            mainCtrl.activeMenuList = mainCtrl.activeMenuList.filter(m => m.id !== menu.id);
        }
    }

    mainCtrl.setActiveMenu = (menu) => {
        mainCtrl.currentActiveMenu = menu;
    }

    mainCtrl.toggleSubMenu = () => {
        mainCtrl.showSubMenu = !mainCtrl.showSubMenu;
    };
});