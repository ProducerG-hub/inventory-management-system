export const emailValidation = {
    required: "Email is required",

    pattern: {
        value: /^[^\s@]+@[^\s@]+\.[^\s@]+$/,

        message: "Enter a valid email address"
    }
};


export const passwordValidation = {

    required: "Password is required",

    minLength: {
        value: 6,

        message: "Password must contain at least 6 characters"

    }

};