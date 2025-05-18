export default class PatHistories {
    constructor(patId, patient, noteListHistories) {
        this._patId = patId;
        this._patient = patient;
        this._noteListHistories = noteListHistories;
    }

    get patId() {
        return this._patId;
    }

    set patId(value) {
        this._patId = value;
    }

    get patient() {
        return this._patient;
    }

    set patient(value) {
        this._patient = value;
    }

    get noteListHistories() {
        return this._noteListHistories;
    }

    set noteListHistories(value) {
        this._noteListHistories = value;
    }
}