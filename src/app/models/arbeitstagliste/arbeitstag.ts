export class Arbeitstag {
  aTag: any;

  ersterStempel: string;
  letzterStempel: string;
  mid: string;

  // Public Boolens
  tag: string;
  arbeitszeit: number;
  zeitstempel: string[][];

  feiertag: boolean;
  gleitzeittag: boolean;
  arbeitszeitenEingehalten: boolean;
  pausenEingehalten: boolean;
  maxArbeitszeitEingehalten: boolean;
}
