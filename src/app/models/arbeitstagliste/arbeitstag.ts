export class Arbeitstag {
  ersterStempel: string;
  letzterStempel: string;
  mid: string;

  // Public Boolens
  tag: string;
  arbeitszeit: number;
  Zeistempel: string[];


  // Boolens
  feiertag: boolean;
  gleitzeittag: boolean;
  arbeitszeitenEingehalten: boolean;
  pausenEingehalten: boolean;
  bmaxArbeitszeitEingehalten: boolean;
}
