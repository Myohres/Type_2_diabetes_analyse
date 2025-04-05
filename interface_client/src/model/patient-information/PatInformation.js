export default class PatInformation {
    patId;
    lastName;
    firstName;
    birthDay;
    gender;
    address;
    phone;

    constructor(patId, lastName, firstName, birthDay, gender, address, phone) {
        this.patId = patId;
        this.lastName = lastName;
        this.firstName = firstName;
        this.birthDay = birthDay;
        this.gender = gender;
        this.address = address;
        this.phone = phone
    }

}