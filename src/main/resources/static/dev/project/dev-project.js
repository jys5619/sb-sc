function ngDevProject() {
    this.title = "DEV PROJECT";
    this.name = store().getData().user.name;


    function uuidv4() {
        return ([1e7] + -1e3 + -4e3 + -8e3 + -1e11).replace(/[018]/g, c =>
            (c ^ crypto.getRandomValues(new Uint8Array(1))[0] & 15 >> c / 4).toString(16)
        );
    }

    this.title = uuidv4();

    this.getId = () => {
        return this.title + " :: " + this.store.user.name;
    }


}   