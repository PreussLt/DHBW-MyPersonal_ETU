export class User {
  id:string;
  prename: string;
  lastname: string;
  personalnummer: number;
  passwort: string;
  arbeitsmodell: number;
  uklasse: number;
  gebDatum: string;

  constructor(
    vorname: string,
    nachname: string,
    personalnummer: number,
    passwort: string,
    arbeitsmodell: number,
    uklasse: number,
    gebDatum: string
  ) {
    this.prename = vorname;
    this.lastname = nachname;
    this.personalnummer = personalnummer;
    this.passwort = passwort;
    this.arbeitsmodell = arbeitsmodell;
    this.uklasse = uklasse;
    this.gebDatum = gebDatum;
  }
}
