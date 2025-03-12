export default class User {
    constructor(login, password, lastName, firstName, role) {
        this.login = login;
        this.password = password;
        this.lastName = lastName;
        this.firstName = firstName;
        this.role = role;
    }
}