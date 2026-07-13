const STORAGE_KEYS = {
    TOKEN: "ims_token",
    USER: "ims_user",
};

const storage = {
    // ==========================
    // TOKEN
    // ==========================

    setToken(token) {
        localStorage.setItem(STORAGE_KEYS.TOKEN, token);
    },

    getToken() {
        return localStorage.getItem(STORAGE_KEYS.TOKEN);
    },

    removeToken() {
        localStorage.removeItem(STORAGE_KEYS.TOKEN);
    },

    // ==========================
    // USER
    // ==========================

    setUser(user) {
        localStorage.setItem(
            STORAGE_KEYS.USER,
            JSON.stringify(user)
        );
    },

    getUser() {
        const user = localStorage.getItem(STORAGE_KEYS.USER);

        return user ? JSON.parse(user) : null;
    },

    removeUser() {
        localStorage.removeItem(STORAGE_KEYS.USER);
    },

    // ==========================
    // AUTH
    // ==========================

    isAuthenticated() {
        return !!this.getToken();
    },

    clear() {
        this.removeToken();
        this.removeUser();
    },
};

export default storage;