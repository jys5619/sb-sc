const store = function () {
    const data = {
        user: {
            name: 'Jung Young Shin',
        }
    }


    return {
        getData: () => {
            return data;
        }
    }
};