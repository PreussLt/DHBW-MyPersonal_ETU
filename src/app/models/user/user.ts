export class User {
  vorname: string;
  nachname: string;
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
    this.vorname = vorname;
    this.nachname = nachname;
    this.personalnummer = personalnummer;
    this.passwort = passwort;
    this.arbeitsmodell = arbeitsmodell;
    this.uklasse = uklasse;
    this.gebDatum = gebDatum;
  }
}
