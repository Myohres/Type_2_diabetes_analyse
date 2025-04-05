export default class PatInformationToAdd {
    lastName;
    firstName;
    birthDay;
    gender;
    address;
    phone;

    constructor( lastName, firstName, birthDay, gender, address, phone) {
        this.lastName = lastName;
        this.firstName = firstName;
        this.birthDay = birthDay;
        this.gender = gender;
        this.address = address;
        this.phone = phone
    }
}